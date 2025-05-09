package com.third.games.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.third.games.common.entity.UserPrivate;
import com.third.games.common.exception.BizException;
import com.third.games.common.mapper.UserPrivateMapper;
import com.third.games.common.result.Result;
import com.third.games.common.result.ResultCodeEnum;
import com.third.games.common.security.LoginUser;
import com.third.games.common.vo.UserPrivateVO;
import com.third.games.user.service.IUserPrivateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户实名信息表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-09
 */
@Service
public class UserPrivateServiceImpl extends ServiceImpl<UserPrivateMapper, UserPrivate> implements IUserPrivateService {
    @Autowired
    private UserPrivateMapper userPrivateMapper;

    @Override
    public Result<UserPrivateVO> info(LoginUser loginUser) throws BizException {
        LambdaQueryWrapper<UserPrivate> query = new LambdaQueryWrapper<>();
        query.eq(UserPrivate::getUserId, loginUser.getUserId());
        UserPrivate user = userPrivateMapper.selectOne(query);
        if (user == null) {
            throw new BizException(ResultCodeEnum.USER_NOT_FOUND);
        }
        UserPrivateVO userPrivateVO = new UserPrivateVO();
        BeanUtils.copyProperties(user, userPrivateVO);
        return Result.success(userPrivateVO);
    }
}
