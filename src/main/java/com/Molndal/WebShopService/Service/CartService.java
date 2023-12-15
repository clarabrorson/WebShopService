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
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Denna klass används som en service för att utföra operationer på tabellen carts.
 * Klassen innehåller metoder för att hämta, uppdatera och ta bort carts.
 * @author Clara Brorson
 */
@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ArticleRepository articleRepository;
    @Autowired private UserService userService;
    @Autowired private HistoryRepository historyRepository;

    /**
     * Denna metod används för att hämta alla carts.
     * @return carts
     * Endast admin har tillgång till denna funktion.
     */
    public Cart getCarts() {
        return cartRepository.findById(1L).orElse(null);
    }

    /**
     * Denna metod används för att hämta en cart med ett specifikt id.
     * @param id
     * @return cart
     */
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    /**
     * Denna metod används för att uppdatera antalet artiklar i en cart.
     * @param cartId
     * @param articleId
     * @param quantity
     * @return cart
     */

    public Cart updateArticleCount(Long cartId, Long articleId, int quantity) throws ChangeSetPersister.NotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Set<Article> articles = cart.getArticles();

        Article article = articles.stream()
                .filter(a -> a.getId().equals(articleId))
                .findFirst()
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        article.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    /**
     * Denna metod används för att ta bort en artikel från en cart.
     * @param cartId
     * @param articleId
     * @return cart
     */
    public Cart deleteArticleFromCart(Long cartId, Long articleId) throws ChangeSetPersister.NotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Set<Article> articles = cart.getArticles();

        articles = articles.stream()
                .filter(article -> !article.getId().equals(articleId))
                .collect(Collectors.toSet());

        if (articles.size() == cart.getArticles().size()) {
            throw new ChangeSetPersister.NotFoundException();
        }

        cart.setArticles(articles);
        return cartRepository.save(cart);
    }

    /**
     * Denna metod används för att lägga till en artikel i en cart.
     * @param id
     * @param currentUser
     */
    public void addArticleToCartFromDB(Long id, User currentUser) {
        Cart cart = currentUser.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(currentUser);
            currentUser.setCart(cart);
        }
        Set<Article> articles = cart.getArticles();

        Article articleToAdd = articleRepository.findById(id).orElse(null);

        if (articleToAdd != null) {

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

    /**
     * Den här metoden används för att hämta kundvagn till aktuell användare.
     * @author Jafar Hussein
     */
    public Cart getCartForCurrentUser() {
        User currentUser = userService.getCurrentUser();
        Cart userCart = currentUser != null ? currentUser.getCart() : null;

        if (userCart != null) {
            // Load articles eagerly to include them in the response
            userCart.getArticles().size();
        }

        return userCart;
    }

    /**
     * Den här metoden utför en köpaktion genom att skapa en köphistorikpost.
     * Den länkar köpta artiklar till den, beräknar den totala kostnaden och sparar sedan köphistorikposten i databasen.
     * @param currentUser
     * @author Jafar Hussein
     */


    public void purchaseCart(User currentUser) {
        // Fetch the user's cart
        Cart cart = cartRepository.findByUser(currentUser);

        if (cart != null) {

            Set<Article> articlesInCart = cart.getArticles();

            if (!articlesInCart.isEmpty()) {

                History purchaseHistory = new History();
                purchaseHistory.setUser(currentUser);

                purchaseHistory.getPurchasedArticles().clear();

                purchaseHistory.getPurchasedArticles().addAll(articlesInCart);

                for (Article article : articlesInCart) {
                    article.setHistory(purchaseHistory);
                }

                purchaseHistory.setTotalCost(calculateTotalCost(articlesInCart));

                historyRepository.save(purchaseHistory);

                cart.setArticles(new HashSet<>());
                cartRepository.save(cart);
            }
        }
    }

    /**
     * Den här metoden beräknar den totala kostnaden för en uppsättning artiklar.
     * @param articles
     * @return totalCost
     * @author Jafar Hussein
     */
    private int calculateTotalCost(Set<Article> articles) {
        return articles.stream()
                .map(Article::getCost)
                .reduce(0, Integer::sum);
    }
}
