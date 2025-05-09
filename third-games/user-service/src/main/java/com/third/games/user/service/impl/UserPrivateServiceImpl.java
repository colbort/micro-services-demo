package com.third.games.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.third.games.common.entity.UserPrivate;
import com.third.games.common.mapper.UserPrivateMapper;
import com.third.games.user.service.IUserPrivateService;
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

}
