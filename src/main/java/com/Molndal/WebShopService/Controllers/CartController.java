package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Service.CartService;
import com.Molndal.WebShopService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**@author Clara Brorson
 * This class is used to handle requests from the client to the API.
 * The class is used to get, add, update and delete articles from the cart.
 * The class is also used to get the cart for the current user.
 * Depending on the role of the user, the user will have access to different parts of the API.
 */
@RestController
@RequestMapping("/webshop/cart")
public class CartController {
    @Autowired private CartService cartService;
    @Autowired private UserService userService;

    @GetMapping("")
    private Cart getCart() {
        return cartService.getCarts();
    }
    @GetMapping("/{id}")
    private Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }
    @PostMapping("/{id}")
    private Cart addArticleToCart(@PathVariable Long id){
        User currentUser = userService.getCurrentUser();
        cartService.addArticleToCartFromDB(id, currentUser);
        // Ensure you fetch the updated cart associated with the current user
        return cartService.getCartForCurrentUser();
    }

    /**
     * This method is used to update the quantity of an article in the cart.
     * @RequestParam int quantity is used to get the quantity from the url.
     */
    @PatchMapping("/{cartId}/articles/{articleId}")
    public ResponseEntity<Cart> updateArticleCount(
            @PathVariable Long cartId,
            @PathVariable Long articleId,
            @RequestParam int quantity) throws ChangeSetPersister.NotFoundException {
        Cart updatedCart = cartService.updateArticleCount(cartId, articleId, quantity);
        return ResponseEntity.ok(updatedCart);
    }
    
    @DeleteMapping("/{cartId}/articles/{articleId}")
    public ResponseEntity<Cart> deleteArticleFromCart(
            @PathVariable Long cartId,
            @PathVariable Long articleId) throws ChangeSetPersister.NotFoundException {
        Cart updatedCart = cartService.deleteArticleFromCart(cartId, articleId);
        return ResponseEntity.ok(updatedCart);
    }
}
