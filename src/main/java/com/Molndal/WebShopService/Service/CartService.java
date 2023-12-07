package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;


    public ResponseEntity<Cart> getCarts() {
        return ResponseEntity.ok(cartRepository.findAll().get(0));
    }

    public ResponseEntity<Cart> getCartById(Long id) {
        return ResponseEntity.ok(cartRepository.findCartById(id).get());
    }


    public ResponseEntity<Cart> addArticleToCart(List<Article> article) {
        Cart cart = cartRepository.findAll().get(0);
        cart.setArticles((Set<Article>) article);
        return ResponseEntity.ok(cartRepository.save(cart));
    }

    public ResponseEntity<Cart> updateArticleCount(int quantity, Article article) {
        Cart cart = cartRepository.findAll().get(0);
        cart.setArticles((Set<Article>) article);
        return ResponseEntity.ok(cartRepository.save(cart));
    }

    public ResponseEntity<Cart> deleteArticleFromCart(Article article) {
        Cart cart = cartRepository.findAll().get(0);
        cart.setArticles((Set<Article>) article);
        return ResponseEntity.ok(cartRepository.save(cart));
    }
}
