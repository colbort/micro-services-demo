package com.third.games.user.feign;

import com.third.games.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/api/v1/detail/{id}")
    Result<Boolean> detail(@PathVariable("id") String id);
}
