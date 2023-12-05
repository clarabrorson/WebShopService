package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Artikel;
import com.Molndal.WebShopService.Models.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webshop/cart")
public class CartController {

    @GetMapping("/myCart")
    private ResponseEntity<Cart> getCart() {

    }

    @PostMapping("/addArticle")
    private ResponseEntity<Cart> addArticleToCart(@RequestBody Artikel artikel) {

    }

    @PatchMapping("/update/{quantity}")
    private ResponseEntity<Cart> updateArticleCount(@PathVariable int antal, @RequestBody Artikel artikle) {

    }

    @DeleteMapping("/deleteArticle")
    private ResponseEntity<Cart> deleteArticleFromCart(@RequestBody Artikel artikel) {

    }
}
