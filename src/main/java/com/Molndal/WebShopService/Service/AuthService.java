package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Cart;
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
 * Denna klass är en Service för autentisering av användare och admin.
 * Den har två metoder, en för att registrera en användare och en för att logga in en användare/admin.
 * Den har även en metod för att kryptera lösenordet.
 * @author Clara Brorson
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

    /** Denna metod registrerar en användare i databasen.
     * Den kontrollerar om användarnamnet redan finns i databasen.
     * Om det gör det, returnerar den en felaktig förfrågan (bad request).
     * Om det inte gör det, krypterar den lösenordet och sparar användaren i databasen.
     * @param username
     * @param password
     * @return ResponseEntity<?>
     * Wildcard används för att returnera en response entity med vilken typ av body som helst.
     * Detta beror på att body kan vara antingen en sträng eller en användare.
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

    /** Denna metod loggar in en användare/admin.
     * Den kontrollerar om användarnamnet och lösenordet stämmer överens med det som finns i databasen.
     * Om det gör det, genererar den en token och returnerar en response entity med användaren och token.
     * Om det inte gör det, returnerar den en response entity med en tom användare och en tom token.
     * @param username
     * @param password
     * @return ResponseEntity<LoginResponse>
     * LoginResponse är en klass som innehåller en användare och en token.
     */
    public ResponseEntity<LoginResponse> login(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            // Fetch the user from the repository
            User user = userRepository.findByUsername(username).orElse(null);

            if (user != null) {
                // Check if the user has a cart
                Cart userCart = user.getCart();
                if (userCart == null) {
                    // If the user doesn't have a cart, create a new one and assign it
                    userCart = new Cart();
                    userCart.setUser(user);
                    user.setCart(userCart);
                    userRepository.save(user);
                }
            }

            return ResponseEntity.ok(new LoginResponse(user, token));

        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new LoginResponse(null, ""));
        }
    }


}
