package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webshop/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    private ResponseEntity registerUser(@RequestBody User user) {

        return authService.register(payload.getUsername(), payload.getPassword());
    }

    @PostMapping("/login")
    private ResponseEntity<User> loginUser(@RequestBody User user) {

        return authService.login(payload.getUsername(), payload.getPassword());

    }
}
