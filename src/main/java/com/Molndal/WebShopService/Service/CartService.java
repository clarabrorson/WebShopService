package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;


    //Admin bör ha möjlighet att se alla carts
    public Cart getCarts() {
        return cartRepository.findById(1L).orElse(null);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    //Användare och artikel bör finnas i databasen
    public void addArticleToCartFromDB(Long id, User currentUser) {
        Cart cart = cartRepository.findById(1L).orElseGet(Cart::new);
        Set<Article> articles = cart.getArticles();
        //Sök efter artikel i databasen.Stream() används för att kunna filtrera på id.
        Article article = currentUser.getArticles().stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
        if (article != null) {
            articles.add(article);
            cart.setArticles(articles);
            cartRepository.save(cart);
        }
    }

    public Cart updateArticleCount(Long cartId, Long articleId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseGet(Cart::new);
        Set<Article> articles = cart.getArticles();

        //Sök efter artikel i databasen.Stream() används för att kunna filtrera på id.
        Article article = articles.stream().filter(a -> a.getId().equals(articleId)).findFirst().orElse(null);
        assert article != null;
        article.setQuantity(quantity);
        articles.add(article);
        cart.setArticles(articles);

        return cartRepository.save(cart);
    }

    public Cart deleteArticleFromCart(Long cartId, Long articleId) {
        Cart cart = cartRepository.findById(cartId).orElseGet(Cart::new);
        Set<Article> articles = cart.getArticles();

        //Filtrera bort artikel med articleId genom Stream()
        articles = articles.stream()
                .filter(article -> !article.getId().equals(articleId))
                .collect(Collectors.toSet());

        cart.setArticles(articles);
        return cartRepository.save(cart);
    }

}
