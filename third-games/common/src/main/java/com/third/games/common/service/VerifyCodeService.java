package com.third.games.common.service;

import com.third.games.common.enums.VerifyCodeTypeEnum;
import com.third.games.common.exception.BizException;
import com.third.games.common.result.Result;

public interface VerifyCodeService {
    Result<String> send(Integer templateId, String title, String content, String countryCode, String account, String ip, VerifyCodeTypeEnum type) throws BizException;
}
