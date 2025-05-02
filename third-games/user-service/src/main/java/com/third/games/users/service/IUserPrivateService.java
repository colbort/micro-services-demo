package com.third.games.users.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.third.games.common.entity.UserPrivate;
import com.third.games.common.exception.BizException;
import com.third.games.common.result.Result;
import com.third.games.common.security.LoginUser;
import com.third.games.common.vo.UserPrivateVO;

/**
 * <p>
 * 用户实名信息表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-01
 */
public interface IUserPrivateService extends IService<UserPrivate> {
    Result<UserPrivateVO> info(LoginUser loginUser) throws BizException;
}
