package com.third.games.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Override
    public Result<UserVO> register(UserBO request) throws BizException {
        return null;
    }

    @Override
    public Result<UserVO> login(UserBO request) throws BizException {
        if (request.getCodeLogin() && StringUtils.isBlank(request.getVerifyCode())) {
            throw new BizException(0, "验证码登录，验证码不能为空");
        }
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getUsername())) query.eq(User::getUsername, request.getUsername());
        if (StringUtils.isNotBlank(request.getPhone())) query.eq(User::getPhone, request.getPhone());
        if (StringUtils.isNotBlank(request.getEmail())) query.eq(User::getEmail, request.getEmail());
        User user = userMapper.selectOne(query);
        if (user == null) {
            throw new BizException(ResultCodeEnum.USER_NOT_FOUND);
        }
        if (user.getStatus() != UserStatusEnum.ENABLED || user.getIsDeleted()){
            throw new BizException(ResultCodeEnum.USER_INVALID);
        }
        if (StringUtils.isNotBlank(request.getVerifyCode())) {
            Result<Boolean> result = verifyServiceClient.verify(new VerifyCodeBO(request.getVerifyCode(), request.getVerifyId()));
            if (result.getCode() != ResultCodeEnum.SUCCESS.getCode()) {
                throw new BizException(result.getCode(), result.getMessage());
            }
        } else {
            if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
                throw new BizException(ResultCodeEnum.PASSWORD_ERROR);
            }
        }

        redisUtils.set(userCacheKey(user.getId()), user, -1);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.success(userVO);
    }

    @Override
    public Result<Boolean> logout(LoginUser user) throws BizException {
        if (!redisUtils.del(userCacheKey(user.getUserId()))) {
            throw new BizException(ResultCodeEnum.DELETE_ERROR);
        }
        return Result.success(true);
    }

    private String userCacheKey(Long userId) {
        return String.format("login_user:%d", userId);
    }
}
