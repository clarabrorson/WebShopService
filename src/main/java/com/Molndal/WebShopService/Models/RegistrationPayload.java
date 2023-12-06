package com.Molndal.WebShopService.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Clara Brorson
 * This class is used to create a payload for registration
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPayload {
    private String username;
    private String password;
}
