package com.Molndal.WebShopService.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  Den här klassen används för att skicka tillbaka ett svar till klienten när en användare loggar in.
 *  Den innehåller en användare och en jwt-token som används för att autentisera användaren.
 *  @author Clara Brorson
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private User user;
    private String jwt;
}
