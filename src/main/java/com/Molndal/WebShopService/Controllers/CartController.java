package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Service.CartService;
import com.Molndal.WebShopService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Den här klassen används för att hantera förfrågningar till webshop/cart från klienten till API:et.
 * Klassen använder requestmapping för att hämta, lägga till, uppdatera och ta bort artiklar från databasen.
 * Beroende på användarens roll, kommer användaren att ha tillgång till olika delar av API:et.
 * @author Clara Brorson
 */
@RestController
@RequestMapping("/webshop/cart")
public class CartController {
    @Autowired private CartService cartService;
    @Autowired private UserService userService;

    /**
     * Denna metod används för att hämta alla Carts från databasen. Endast användare med rollen "ADMIN" har tillgång till denna metod.
     * Endpoint: GET /webshop/cart
     * @return en lista med alla Carts.
     */
    @GetMapping("")
    private List <Cart> getCart() {
        return cartService.getCart();
    }

    /**
     * Denna metod används för att hämta en kundkorg från databasen med ett specifikt id.
     * Endpoint: GET /webshop/cart/{id}
     * @param id är id:t för den Cart som ska hämtas.
     * @return en Cart med det specifika id:t.
     */
    @GetMapping("/{id}")
    private Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    /**
     * Denna metod används för att lägga till en artikel i kundkorgen. Artikeln hämtas från databasen med ett specifikt id.
     * Endpoint: POST /webshop/cart/{id}

//     * @param cart är id:t för artikeln som ska läggas till i kundkorgen.


     * @return en Cart med den nya artikeln.
     */
    @PostMapping("/{id}")
    private Cart addArticleToCart(@PathVariable Long id){
        User currentUser = userService.getCurrentUser();
        cartService.addArticleToCartFromDB(id, currentUser);
        // Ensure you fetch the updated cart associated with the current user
        return cartService.getCartForCurrentUser();
    }

    /**
     * Denna metod används för att uppdatera antalet artiklar i kundkorgen.
     * Endpoint: PATCH /webshop/cart/{cartId}/articles/{articleId}
     * @param cartId är id:t för den Cart som artikeln ska uppdateras i.
     * @param articleId är id:t för den artikel som ska uppdateras.
     * @param quantity = antalet av artiklar man vill uppdatera till.
     * @return en Cart med uppdaterat antal artiklar.
     */
    @PatchMapping("/{cartId}/articles/{articleId}")
    public ResponseEntity<Cart> updateArticleCount(
            @PathVariable Long cartId,
            @PathVariable Long articleId,
            @RequestParam int quantity) throws ChangeSetPersister.NotFoundException {
        Cart updatedCart = cartService.updateArticleCount(cartId, articleId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    /**
     * Denna metod används för att ta bort en artikel från kundkorgen.
     * Endpoint: DELETE /webshop/cart/{cartId}/articles/{articleId}
     * @param cartId är id:t för den Cart som artikeln ska tas bort från.
     * @param articleId är id:t för den artikel som ska tas bort.
     * @return en Cart med borttagen artikel.
     */

    @DeleteMapping("/{cartId}/articles/{articleId}")
    public ResponseEntity<Cart> deleteArticleFromCart(
            @PathVariable Long cartId,
            @PathVariable Long articleId) throws ChangeSetPersister.NotFoundException {
        Cart updatedCart = cartService.deleteArticleFromCart(cartId, articleId);
        return ResponseEntity.ok(updatedCart);
    }
}
