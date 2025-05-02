package com.third.games.product.controller;

import com.third.games.common.annotation.NoAuth;
import com.third.games.common.config.SmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private SmsProperties properties;

    @GetMapping("/product/{id}")
    @NoAuth
    public String getProduct(@PathVariable Long id) {
        // For demo, returning a mock product
        System.out.println(properties);
        return "Product " + id;
    }
}
