package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import com.Molndal.WebShopService.Repository.CartRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;



import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ArticleRepository articleRepository;
    @Autowired private UserService userService;


    //Admin bör ha möjlighet att se alla carts
    public Cart getCarts() {
        return cartRepository.findById(1L).orElse(null);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart updateArticleCount(Long cartId, Long articleId, int quantity) throws ChangeSetPersister.NotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Set<Article> articles = cart.getArticles();

        // Sök efter artikel i databasen. Stream() används för att kunna filtrera på id.
        Article article = articles.stream()
                .filter(a -> a.getId().equals(articleId))
                .findFirst()
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Uppdatera artikelkvantiteten
        article.setQuantity(quantity);

        // Spara uppdaterad cart i databasen
        return cartRepository.save(cart);
    }

    public Cart deleteArticleFromCart(Long cartId, Long articleId) throws ChangeSetPersister.NotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Set<Article> articles = cart.getArticles();

        // Filtrera bort artikel med articleId genom Stream()
        articles = articles.stream()
                .filter(article -> !article.getId().equals(articleId))
                .collect(Collectors.toSet());

        // Kontrollera om några ändringar gjordes innan du sparar
        if (articles.size() == cart.getArticles().size()) {
            throw new ChangeSetPersister.NotFoundException();
        }

        cart.setArticles(articles);
        return cartRepository.save(cart);
    }

    public void addArticleToCartFromDB(Long id, User currentUser) {
        Cart cart = currentUser.getCart();

        // If the user doesn't have a cart, create a new one
        if (cart == null) {
            cart = new Cart();
            cart.setUser(currentUser);
            currentUser.setCart(cart);
        }

        Set<Article> articles = cart.getArticles();

        // Fetch the article from the repository using the provided id
        Article articleToAdd = articleRepository.findById(id).orElse(null);

        if (articleToAdd != null) {
            // Make sure the article is not already in the cart
            if (articles == null) {
                articles = new HashSet<>();
            }

            if (!articles.contains(articleToAdd)) {
                articles.add(articleToAdd);
                cart.setArticles(articles);
                cartRepository.save(cart);
                userRepository.save(currentUser);
            }
        }
    }

    public Cart getCartForCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return currentUser != null ? currentUser.getCart() : null;
    }
}
