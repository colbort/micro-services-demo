package com.third.games.user.feign;

import com.third.games.common.bo.VerifyCodeBO;
import com.third.games.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "captcha-service")
public interface VerifyServiceClient {
    @PostMapping("/api/v1/verify")
    Result<Boolean> verify(@RequestBody VerifyCodeBO request);
}
