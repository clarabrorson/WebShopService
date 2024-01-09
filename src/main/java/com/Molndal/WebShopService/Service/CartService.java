package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.*;
import com.Molndal.WebShopService.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

/**
 * Denna klass används som en service för att utföra operationer på tabellen carts.
 * Klassen innehåller metoder för att hämta, uppdatera och ta bort carts.
 * @author Clara Brorson
 */
@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    /**
     * Denna metod används för att hämta alla carts.
     *
     * @return carts är en lista med alla carts.
     * Endast admin har tillgång till denna funktion.
     */

    public List<Cart> getCart() {
        return cartRepository.findAll();
    }

    /**
     * Denna metod används för att hämta en cart med ett specifikt id.
     *
     * @param id är id:t för den cart som ska hämtas.
     * @return cart med det specifika id:t.
     */
    public Cart getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);

        if (cart != null) {
            int totalCost = 0;

            for (CartItem cartItem : cart.getCartItems()) {
                Article article = cartItem.getArticle();
                if (article != null) {

                    int itemCost = article.getCost() * cartItem.getQuantity();
                    totalCost += itemCost;
                }
            }
            cart.setTotalCost(totalCost);
        }
        return cart;
    }

    /**
     * Denna metod används för att uppdatera antalet artiklar i en cart.
     * @param cartId är id:t för den cart som artikeln ska läggas till i.
     * @param articleId är id:t för den artikel som ska läggas till.
     * @param newQuantity är antalet artiklar som ska läggas till.
     * @return cart med den uppdaterade artikeln.
     */
    @Transactional
    public Cart updateArticleCount(Long cartId, Long articleId, int newQuantity) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        CartItem cartItemToUpdate = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getArticle().getId().equals(articleId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Article not found in cart"));

        cartItemToUpdate.setQuantity(newQuantity);

        cartItemRepository.save(cartItemToUpdate);

        int totalCost = cart.getCartItems().stream()
                .mapToInt(item -> item.getArticle().getCost() * item.getQuantity())
                .sum();
        cart.setTotalCost(totalCost);

        return cartRepository.save(cart);
    }

    /**
     * Denna metod används för att ta bort en artikel från en cart.
     *
     * @param cartId    är id:t för den cart som ska uppdateras.
     * @param articleId är id:t för den artikel som ska tas bort.
     * @return cart med den borttagna artikeln.
     */
    @Transactional
    public Cart deleteArticleFromCart(Long cartId, Long articleId) throws ChangeSetPersister.NotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        boolean itemRemoved = cart.getCartItems().removeIf(cartItem ->
                cartItem.getArticle().getId().equals(articleId));

        if (!itemRemoved) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return cartRepository.save(cart);
    }
    /**
     * Denna metod används för att lägga till en artikel i en cart.
     * @param id          är id:t för den artikel som ska läggas till.
     * @param quantity    är antalet artiklar som ska läggas till.
     * @param currentUser är den användare som är inloggad.
     */

    public void addArticleToCartFromDB(Long id, int quantity, User currentUser) {
        Cart cart = currentUser.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(currentUser);
            currentUser.setCart(cart);
        }

        Article articleToAdd = articleRepository.findById(id).orElse(null);

        if (articleToAdd != null) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setArticle(articleToAdd);
            cartItem.setQuantity(quantity);

            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);

            cartRepository.save(cart);
            userRepository.save(currentUser);
        }
    }

    /**
     * Den här metoden används för att hämta kundvagn till aktuell användare.
     *
     * @return userCart är kundvagnen för aktuell användare.
     */
    public Cart getCartForCurrentUser() {
        User currentUser = userService.getCurrentUser();
        Cart userCart = currentUser != null ? currentUser.getCart() : null;

        if (userCart != null) {

            userCart.getCartItems().size();
        }

        return userCart;
    }

    /**
     * Den här metoden utför en köpaktion genom att skapa en köphistorikpost.
     * Den länkar köpta artiklar till den, beräknar den totala kostnaden och sparar sedan köphistorikposten i databasen.
     *
     * @param currentUser är den användare som är inloggad.
     */

    public void purchaseCart(User currentUser) {
        Cart cart = cartRepository.findByUser(currentUser);

        if (cart != null) {

            Set<CartItem> cartItems = cart.getCartItems();

            if (!cartItems.isEmpty()) {

                History purchaseHistory = new History();
                purchaseHistory.setUser(currentUser);

                purchaseHistory.getPurchasedArticles().clear();

                for (CartItem cartItem : cartItems) {
                    Article article = cartItem.getArticle();
                    purchaseHistory.getPurchasedArticles().add(article);
                    article.setHistory(purchaseHistory);
                }
                int totalCost = cartItems.stream()
                        .mapToInt(cartItem -> cartItem.getArticle().getCost() * cartItem.getQuantity())
                        .sum();
                purchaseHistory.setTotalCost(totalCost);

                historyRepository.save(purchaseHistory);

                cartItems.clear();
                cartRepository.save(cart);
            }
        }
    }
}