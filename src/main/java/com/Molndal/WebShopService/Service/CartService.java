package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import com.Molndal.WebShopService.Repository.CartRepository;
import com.Molndal.WebShopService.Repository.HistoryRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HistoryRepository historyRepository;


    //Admin bör ha möjlighet att se alla carts
    public Cart getCarts() {
        return cartRepository.findById(1L).orElse(null);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
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

    //Användare och artikel bör finnas i databasen
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

    // Registrera ett köp i användarens historik
    // Purchase the items in the user's cart
    public void purchaseCart(User currentUser) {
        // Fetch the user's cart
        Cart cart = cartRepository.findByUser(currentUser);

        // Ensure the cart is not null
        if (cart != null) {
            // Retrieve the articles from the cart
            Set<Article> articlesInCart = cart.getArticles();

            // Perform the purchase action (create a history entry, etc.)
            if (!articlesInCart.isEmpty()) {
                // Create a purchase history entry
                History purchaseHistory = new History();
                purchaseHistory.setUser(currentUser);

                // Clear the existing purchasedArticles collection in history
                purchaseHistory.getPurchasedArticles().clear();

                // Add new articles to the purchasedArticles in the history entry
                purchaseHistory.getPurchasedArticles().addAll(articlesInCart);

                // Set the history attribute in each Article to link them
                for (Article article : articlesInCart) {
                    article.setHistory(purchaseHistory);
                }

                purchaseHistory.setTotalCost(calculateTotalCost(articlesInCart));

                // Save the purchase history entry
                historyRepository.save(purchaseHistory);

                // Optionally, you can perform additional actions here, such as clearing the cart
                cart.setArticles(new HashSet<>());
                cartRepository.save(cart);
            }
        }
    }


    private int calculateTotalCost(Set<Article> articles) {
        return articles.stream()
                .map(Article::getCost)
                .reduce(0, Integer::sum);
    }
}
