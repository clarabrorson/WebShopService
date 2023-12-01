package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/webshop/users")
public class UserController {

    @GetMapping("")
    private EntityResponse<User> getUser() {

    }
}
