package com.third.games.captcha.service.impl;

import com.third.games.common.entity.VerifyCodeLog;
import com.third.games.common.mapper.VerifyCodeLogMapper;
import com.third.games.captcha.service.IVerifyCodeLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 验证码发送记录表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-07
 */
@Service
public class VerifyCodeLogServiceImpl extends ServiceImpl<VerifyCodeLogMapper, VerifyCodeLog> implements IVerifyCodeLogService {

}
