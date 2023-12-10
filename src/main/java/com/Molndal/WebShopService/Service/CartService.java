package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import com.Molndal.WebShopService.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;

    public Cart getCarts() {
        return cartRepository.findById(1L).orElse(null);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }


    public Cart addArticleToCart(List<Article> article) {
        Cart cart = cartRepository.findById(1L).orElseGet(Cart::new);
        Set<Article> articles = cart.getArticles();
        articles.add((Article) article);
        cart.setArticles(articles);
        return cartRepository.save(cart);
    }

    public Cart updateArticleCount(int quantity, Article article) {
        Cart cart = cartRepository.findById(1L).orElseGet(Cart::new);
        cart.setArticles((Set<Article>) article);
        return cartRepository.save(cart);
    }

    public Cart deleteArticleFromCart(Article article) {
        Cart cart = cartRepository.findById(1L).orElseGet(Cart::new);
        Set<Article> articles = cart.getArticles();
        articles.remove(article);
        cart.setArticles(articles);
        return cartRepository.save(cart);
    }

    //Använder och artikel bör finnas i databasen
    public void addArticleToCartFromDB(Long id, User currentUser) {
        Cart cart = cartRepository.findById(1L).orElseGet(Cart::new);
        Set<Article> articles = cart.getArticles();
        //Sök efter artikel i databasen. Om den inte finns, returnera null. Stream() används för att kunna filtrera på id.
        Article article = currentUser.getArticles().stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
        articles.add(article);
        cart.setArticles(articles);
        cartRepository.save(cart);
    }
}
