package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * En controller-klass som hanterar användarrelaterade operationer för webbshoppen.
 * Denna controller ger möjlighet att hämta information om den inloggade användaren.
 * Alla metoder i denna controller kräver att användaren är autentiserad.
 * Endpoint för att hämta information om den inloggade användaren: GET /webshop/user
 * @author Jafar
 */
@RestController
@RequestMapping("/webshop/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Hämtar information om den inloggade användaren.
     * Endast inloggade användare har tillgång till denna metod.
     * @return ResponseEntity med information om den inloggade användaren om framgångsrikt, annars INTERNAL_SERVER_ERROR.
     */
    @GetMapping("")
    public ResponseEntity<User> getUser() {
        // Hämta inloggad användares autentiseringsinformation
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Hämta användaren från databasen baserat på inloggat användarnamn
        User user;
        try {
            user = (User) userService.loadUserByUsername(username);
            // Rensa lösenordet innan du skickar det som svar
            user.setPassword(null);
            // Skicka tillbaka användaren utan lösenordet som respons
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();  // Logga eller skriv ut detaljer om exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

