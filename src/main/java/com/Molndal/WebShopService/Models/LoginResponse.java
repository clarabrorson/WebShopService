package com.Molndal.WebShopService.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  @author Clara Brorson
 *  This class is used to create a response when a user logs in. It contains the user and the jwt token.
 *  The jwt token is used to authenticate the user when accessing the API.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private User user;
    private String jwt;
}
