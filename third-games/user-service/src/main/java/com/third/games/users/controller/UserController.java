package com.third.games.users.controller;

import com.third.games.users.feign.ProductServiceClient;
import com.third.games.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private ProductServiceClient productServiceClient;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        // For demo, returning a mock user
        User user = new User(id, "User " + id);
        return user;
    }

    @GetMapping("/user/{id}/product")
    public String getUserProduct(@PathVariable Long id) {
        return productServiceClient.getProductById(id);
    }
}
