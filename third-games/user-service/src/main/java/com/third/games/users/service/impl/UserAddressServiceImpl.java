package com.third.games.users.service.impl;

import com.third.games.common.entity.UserAddress;
import com.third.games.common.mapper.UserAddressMapper;
import com.third.games.users.service.IUserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-01
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

}
