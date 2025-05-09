package com.third.games.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.third.games.common.bo.UserBO;
import com.third.games.common.entity.User;
import com.third.games.common.exception.BizException;
import com.third.games.common.result.Result;
import com.third.games.common.security.LoginUser;
import com.third.games.common.vo.UserVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-09
 */
public interface IUserService extends IService<User> {
    Result<UserVO> register(UserBO request) throws BizException;

    Result<UserVO> login(UserBO request) throws BizException;

    Result<Boolean> logout(LoginUser user) throws BizException;

    Result<UserVO> updateUserInfo(UserBO request, Long userId) throws BizException;
}
