package com.Molndal.WebShopService.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Den här klassen används för att skicka in användarnamn och lösenord vid registrering.
 * @author Clara Brorson
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPayload {
    private String username;
    private String password;
}
