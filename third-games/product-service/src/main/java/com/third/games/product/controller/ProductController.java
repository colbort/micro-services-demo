package com.third.games.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id) {
        // For demo, returning a mock product
        return "Product " + id;
    }
}
