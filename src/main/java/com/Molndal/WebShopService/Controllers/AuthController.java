package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webshop/auth")
public class AuthController {

    @PostMapping("/register")
    private ResponseEntity registerUser(
            @RequestBody User user
    ) {

    }

    @PostMapping("/login")
    private ResponseEntity<User> loginUser(
            @RequestBody User user
    ) {

    }
}
