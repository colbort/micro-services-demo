package com.third.games.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.third.games.common.entity.UserAddress;
import com.third.games.common.mapper.UserAddressMapper;
import com.third.games.user.service.IUserAddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-09
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

}
