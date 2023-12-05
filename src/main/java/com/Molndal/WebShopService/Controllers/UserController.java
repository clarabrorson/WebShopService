package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webshop/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<User> getUser() {
        // Hämta inloggad användares autentiseringsinformation
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Hämta användaren från databasen baserat på inloggat användarnamn
        User user = (User) userService.loadUserByUsername(username);

        // Rensa lösenordet innan du skickar det som svar
        user.setPassword(null);

        // Skicka tillbaka användaren utan lösenordet som respons
        return ResponseEntity.ok(user);
    }
}

