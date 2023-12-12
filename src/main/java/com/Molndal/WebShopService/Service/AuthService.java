package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.LoginResponse;
import com.Molndal.WebShopService.Models.Role;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.RoleRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Clara Brorson
 * This class is a service for the authentication of the user and admin.
 * It has two methods, one for registering a user and one for logging in a user/admin.
 * It also has a method for encrypting the password.
 */

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    /** This method registers a user in the database.
     * It checks if the username already exists in the database.
     * If it does, it returns a bad request.
     * If it doesn't, it encrypts the password and saves the user in the database.
     * @param username
     * @param password
     * @return ResponseEntity<?>
     * The wildcard is used to return a response entity with any type of body.
     * This is because the body can be either a string or a user.
     */
    public ResponseEntity<?> register(String username, String password) {
        try {
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Username already exists");
            }

            String encryptedPassword = passwordEncoder.encode(password);

            Role userRole = roleRepository.findByAuthority("USER").get();

            Set<Role> authorities = new HashSet<>();

            authorities.add(userRole);

            User newUser = new User(0L, username, encryptedPassword, authorities);
            User savedUser = userRepository.save(newUser);

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during registration");
        }
    }

    /** This method logs in a user/admin.
     * It checks if the username and password matches the ones in the database.
     * If it does, it returns a response entity with the user and a jwt token.
     * @param username
     * @param password
     * @return ResponseEntity<LoginResponse>
     * The response entity has a body of type LoginResponse.
     */
    public ResponseEntity<LoginResponse> login(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return ResponseEntity.ok(new LoginResponse(userRepository.findByUsername(username).get(), token));

        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new LoginResponse(null, ""));
        }
    }

}
