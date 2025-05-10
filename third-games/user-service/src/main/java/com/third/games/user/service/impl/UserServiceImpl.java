package com.third.games.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.third.games.common.bo.UserBO;
import com.third.games.common.bo.VerifyCodeBO;
import com.third.games.common.entity.User;
import com.third.games.common.enums.UserStatusEnum;
import com.third.games.common.exception.BizException;
import com.third.games.common.mapper.UserMapper;
import com.third.games.common.result.Result;
import com.third.games.common.result.ResultCodeEnum;
import com.third.games.common.security.LoginUser;
import com.third.games.common.utils.JwtTokenUtil;
import com.third.games.common.utils.RedisUtil;
import com.third.games.common.vo.UserVO;
import com.third.games.user.feign.VerifyServiceClient;
import com.third.games.user.service.IUserService;
import com.third.games.user.utils.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtils;
    @Autowired
    private VerifyServiceClient verifyServiceClient;
    @Autowired
    private JwtTokenUtil tokenUtil;

    @Override
    public Result<UserVO> register(UserBO request) throws BizException {
        if (StringUtils.isBlank(request.getUsername()) &&
                StringUtils.isBlank(request.getEmail()) &&
                StringUtils.isBlank(request.getPhone())) {
            throw new BizException(0, "用户名、邮箱或手机号至少一个！");
        }

        // 校验用户名、手机号或邮箱是否已存在
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getUsername())) {
            query.eq(User::getUsername, request.getUsername());
            if (userMapper.selectOne(query) != null) {
                throw new BizException(0, "用户名已存在！");
            }
        }
        if (StringUtils.isNotBlank(request.getPhone())) {
            query.clear();
            query.eq(User::getPhone, request.getPhone());
            if (userMapper.selectOne(query) != null) {
                throw new BizException(0, "手机号已存在！");
            }
        }
        if (StringUtils.isNotBlank(request.getEmail())) {
            query.clear();
            query.eq(User::getEmail, request.getEmail());
            if (userMapper.selectOne(query) != null) {
                throw new BizException(0, "邮箱已存在！");
            }
        }

        // 如果是验证码注册，校验验证码
        if (request.getCodeLogin() && StringUtils.isNotBlank(request.getVerifyCode())) {
            Result<Boolean> result = verifyServiceClient.verify(new VerifyCodeBO(request.getVerifyCode(), request.getVerifyId()));
            if (result.getCode() != ResultCodeEnum.SUCCESS.getCode()) {
                throw new BizException(result.getCode(), result.getMessage());
            }
            if (!result.getData()) {
                throw new BizException(0, "验证码校验失败");
            }
        }

        // 构建User对象并保存到数据库
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(PasswordUtil.encode(request.getPassword()));  // 加密密码
        user.setStatus(UserStatusEnum.ENABLED);  // 默认启用
        user.setIsDeleted(false);  // 默认未删除

        userMapper.insert(user);
        redisUtils.set(userCacheKey(user.getId()), user, -1);
        String token = tokenUtil.createToken(new LoginUser(user.getId(), user.getUsername()), user.getDeviceType().getCode());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO.getUser());
        userVO.setToken(token);
        redisUtils.set(tokenCacheKey(user.getId()), token, -1);
        return Result.success(userVO);
    }

    @Override
    public Result<UserVO> login(UserBO request) throws BizException {
        if (request.getCodeLogin() && StringUtils.isBlank(request.getVerifyCode())) {
            throw new BizException(0, "验证码登录，验证码不能为空");
        }
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getUsername())) query.or().eq(User::getUsername, request.getUsername());
        if (StringUtils.isNotBlank(request.getPhone())) query.or().eq(User::getPhone, request.getPhone());
        if (StringUtils.isNotBlank(request.getEmail())) query.or().eq(User::getEmail, request.getEmail());
        User user = userMapper.selectOne(query);
        if (user == null) {
            throw new BizException(ResultCodeEnum.USER_NOT_FOUND);
        }
        if (user.getStatus() != UserStatusEnum.ENABLED || user.getIsDeleted()) {
            throw new BizException(ResultCodeEnum.USER_INVALID);
        }
        if (StringUtils.isNotBlank(request.getVerifyCode())) {
            Result<Boolean> result = verifyServiceClient.verify(new VerifyCodeBO(request.getVerifyCode(), request.getVerifyId()));
            if (result.getCode() != ResultCodeEnum.SUCCESS.getCode()) {
                throw new BizException(result.getCode(), result.getMessage());
            }
            if (!result.getData()) {
                throw new BizException(0, "验证码校验失败");
            }
        } else {
            if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
                throw new BizException(ResultCodeEnum.PASSWORD_ERROR);
            }
        }

        redisUtils.set(userCacheKey(user.getId()), user, -1);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO.getUser());
        String token = tokenUtil.createToken(new LoginUser(user.getId(), user.getUsername()), user.getDeviceType().getCode());
        userVO.setToken(token);
        redisUtils.set(tokenCacheKey(user.getId()), token, -1);
        return Result.success(userVO);
    }

    @Override
    public Result<Boolean> logout(LoginUser user) throws BizException {
        if (!redisUtils.del(userCacheKey(user.getUserId()))) {
            throw new BizException(ResultCodeEnum.DELETE_ERROR);
        }
        if (!redisUtils.del(tokenCacheKey(user.getUserId()))) {
            throw new BizException(ResultCodeEnum.DELETE_ERROR);
        }
        return Result.success(true);
    }

    @Override
    public Result<UserVO> updateUserInfo(UserBO request, Long userId) throws BizException {
        User currentUser = userMapper.selectById(userId);
        if (currentUser == null) {
            throw new BizException(ResultCodeEnum.USER_NOT_FOUND);
        }

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        if (StringUtils.isNotBlank(request.getUsername()) && !request.getUsername().equals(currentUser.getUsername())) {
            // 用户名只能修改一次，后续如果修改会抛出异常
            updateWrapper.set(User::getUsername, request.getUsername());
        }
        if (StringUtils.isNotBlank(request.getPhone()) && !request.getPhone().equals(currentUser.getPhone())) {
            // 手机号只能修改一次
            updateWrapper.set(User::getPhone, request.getPhone());
        }
        if (StringUtils.isNotBlank(request.getEmail()) && !request.getEmail().equals(currentUser.getEmail())) {
            // 邮箱只能修改一次
            updateWrapper.set(User::getEmail, request.getEmail());
        }

        // 永久可修改的字段
        if (StringUtils.isNotBlank(request.getNickname())) {
            updateWrapper.set(User::getNickname, request.getNickname());
        }
        if (StringUtils.isNotBlank(request.getAvatar())) {
            updateWrapper.set(User::getAvatar, request.getAvatar());
        }
        if (request.getGender() != null) {
            updateWrapper.set(User::getGender, request.getGender());
        }

        // 更新用户信息
        userMapper.update(null, updateWrapper);

        // 返回更新后的用户信息
        User updatedUser = userMapper.selectById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(updatedUser, userVO);
        return Result.success(userVO);
    }

    private String userCacheKey(Long userId) {
        return String.format("login_user:%d", userId);
    }

    private String tokenCacheKey(Long userId) {
        return String.format("user_token:%d", userId);
    }
}
