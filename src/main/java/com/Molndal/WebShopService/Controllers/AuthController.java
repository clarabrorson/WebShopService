package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.LoginResponse;
import com.Molndal.WebShopService.Models.RegistrationPayload;
import com.Molndal.WebShopService.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Denna klass är en kontroller för autentisering av användaren.
 * Den har två endpoints, en för att registrera en användare och en för att logga in en användare.
 * @author Clara Brorson
 */

@RestController
@RequestMapping("/webshop/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Denna metod används för att registrera en användare i databasen.
     * Endpoint: POST /webshop/auth/register
     * @param payload är användarnamn och lösenord för den användare som ska registreras.
     * @return en respons med statuskod 200 om användaren registrerades.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationPayload payload) {

        return authService.register(payload.getUsername(), payload.getPassword());
    }

    /**
     * Denna metod används för att logga in en användare.
     * Endpoint: POST /webshop/auth/login
     * @param payload är användarnamn och lösenord för den användare som ska loggas in.
     * @return en respons med statuskod 200 om användaren loggades in, samt en token.
     */

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody RegistrationPayload payload) {

        return authService.login(payload.getUsername(), payload.getPassword());
    }

}
