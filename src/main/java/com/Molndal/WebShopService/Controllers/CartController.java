package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Service.CartService;
import com.Molndal.WebShopService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/webshop/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    private Cart getCart() {
        return cartService.getCarts();
    }

    @GetMapping("/{id}")
    private Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping("/add/{id}")
    private Cart addArticleToCart(@PathVariable Long id){
        User currentUser = userService.getCurrentUser();
        cartService.addArticleToCartFromDB(id, currentUser);
        // Ensure you fetch the updated cart associated with the current user
        return cartService.getCartForCurrentUser();
    }
    //@RequestParam int quantity hämtar quantity från URL
    //Exempel: http://localhost:8080/webshop/cart/1/articles/1?quantity=5
    //quantity=5 är en parameter
    //@RequestParam int quantity hämtar quantity från URL
    //Exempel: http://localhost:8080/webshop/cart/1/articles/1?quantity=5
    //quantity=5 är en parameter
    @PatchMapping("/{cartId}/articles/{articleId}")
    public ResponseEntity<Cart> updateArticleCount(
            @PathVariable Long cartId,
            @PathVariable Long articleId,
            @RequestParam int quantity) {
        Cart updatedCart = cartService.updateArticleCount(cartId, articleId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/articles/{articleId}")
    public ResponseEntity<Cart> deleteArticleFromCart(
            @PathVariable Long cartId,
            @PathVariable Long articleId) {
        Cart updatedCart = cartService.deleteArticleFromCart(cartId, articleId);
        return ResponseEntity.ok(updatedCart);
    }
}
