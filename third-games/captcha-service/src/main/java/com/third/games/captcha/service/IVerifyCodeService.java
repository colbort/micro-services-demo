package com.third.games.captcha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.third.games.common.bo.VerifyCodeBO;
import com.third.games.common.entity.VerifyCodeLog;
import com.third.games.common.enums.VerifyCodeTypeEnum;
import com.third.games.common.exception.BizException;
import com.third.games.common.result.Result;

/**
 * <p>
 * 验证码发送记录表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-05-07
 */
public interface IVerifyCodeService extends IService<VerifyCodeLog> {
    Result<String> send(Integer templateId, String title, String content, String countryCode, String account, String ip, VerifyCodeTypeEnum type) throws BizException;

    Result<Boolean> verify(VerifyCodeBO request) throws BizException;
}
