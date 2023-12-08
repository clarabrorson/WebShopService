package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    private Cart getCart() {
        return cartService.getCarts();

    }

    @GetMapping("/{id}")
    private Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);

    }

    @PostMapping("")
    private Cart addArticleToCart(@RequestBody List<Article> article) {
        return cartService.addArticleToCart(article);

    }

    @PatchMapping("/{quantity}")
    private Cart updateArticleCount(@PathVariable int quantity, @RequestBody Article article) {
        return cartService.updateArticleCount(quantity, article);

    }

    @DeleteMapping("/{id}")
    private Cart deleteArticleFromCart(@RequestBody Article article) {
        return cartService.deleteArticleFromCart(article);

    }
}
