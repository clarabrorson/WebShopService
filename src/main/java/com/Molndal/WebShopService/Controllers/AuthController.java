package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.LoginResponse;
import com.Molndal.WebShopService.Models.RegistrationPayload;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webshop/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationPayload payload) {

        return authService.register(payload.getUsername(), payload.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody RegistrationPayload payload) {

        return authService.login(payload.getUsername(), payload.getPassword());

    }

}
