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
    private CartService Service;

    @GetMapping("/myCart")
    private ResponseEntity<Cart> getCart() {
        return Service.getCart();

    }

    @PostMapping("/addArticle")
    private ResponseEntity<Cart> addArticleToCart(@RequestBody List<Article> article) {
        return Service.addArticleToCart(article);

    }

    @PatchMapping("/update/{quantity}")
    private ResponseEntity<Cart> updateArticleCount(@PathVariable int quantity, @RequestBody Article article) {
        return Service.updateArticleCount(quantity, article);

    }

    @DeleteMapping("/deleteArticle/{id}")
    private ResponseEntity<Cart> deleteArticleFromCart(@RequestBody Artikel artikel) {

    }
}
