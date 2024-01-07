package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.*;
import com.Molndal.WebShopService.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            // Initialize total cost
            int totalCost = 0;

            // Ensure that the articles are loaded for each cartItem, this might involve changing lazy loading to eager or using JOIN FETCH in the query (not shown here)
            for (CartItem cartItem : cart.getCartItems()) {
                Article article = cartItem.getArticle(); // This assumes that the article is eagerly fetched or properly handled if lazy-loaded
                if (article != null) {
                    // Calculate the cost for this item
                    int itemCost = article.getCost() * cartItem.getQuantity();
                    totalCost += itemCost;
                }
            }

            // Set the total cost in the Cart object
            cart.setTotalCost(totalCost);
        }

        return cart; // Return the cart (which will be null if not found or updated with total cost if found)
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
        // sparar id för varukorgen i en variabel
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        // hitar cart item i carten som har samma article id som den article som ska uppdateras
        CartItem cartItemToUpdate = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getArticle().getId().equals(articleId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Article not found in cart"));

        // Uppdaterar antalet artiklar i cart item
        cartItemToUpdate.setQuantity(newQuantity);

        // Sparar cart item
        cartItemRepository.save(cartItemToUpdate);

        // Beräknar den totala kostnaden för varukorgen
        int totalCost = cart.getCartItems().stream()
                .mapToInt(item -> item.getArticle().getCost() * item.getQuantity())
                .sum();
        cart.setTotalCost(totalCost);

        // sparar varukorgen
        return cartRepository.save(cart);
    }


    /**
     * Denna metod används för att ta bort en artikel från en cart.
     *
     * @param cartId    är id:t för den cart som ska uppdateras.
     * @param articleId är id:t för den artikel som ska tas bort.
     * @return cart med den borttagna artikeln.
     */
    public Cart deleteArticleFromCart(Long cartId, Long articleId) throws ChangeSetPersister.NotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Set<CartItem> cartItems = cart.getCartItems();

        cartItems = cartItems.stream()
                .filter(cartItem -> !cartItem.getArticle().getId().equals(articleId))
                .collect(Collectors.toSet());

        if (cartItems.size() == cart.getCartItems().size()) {
            throw new ChangeSetPersister.NotFoundException();
        }

        cart.setCartItems(cartItems);
        return cartRepository.save(cart);
    }

    /**
     * Denna metod används för att lägga till en artikel i en cart.
     *
     * @param id          är id:t för den artikel som ska läggas till.
     * @param currentUser är den användare som är inloggad.
     */
    //Ny metod
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
            cartItem.setQuantity(quantity); // Set the quantity

            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem); // Save the CartItem instance

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
            // Load cart items eagerly to include them in the response
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
    //Ny purchaseCart-metod
    public void purchaseCart(User currentUser) {
        // Fetch the user's cart
        Cart cart = cartRepository.findByUser(currentUser);

        if (cart != null) {

            Set<CartItem> cartItems = cart.getCartItems();

            if (!cartItems.isEmpty()) {

                History purchaseHistory = new History();
                purchaseHistory.setUser(currentUser);

                purchaseHistory.getPurchasedArticles().clear();

                // Add each Article in CartItem to the purchase history
                for (CartItem cartItem : cartItems) {
                    Article article = cartItem.getArticle();
                    purchaseHistory.getPurchasedArticles().add(article);
                    article.setHistory(purchaseHistory);
                }

                // Calculate the total cost based on CartItem quantity and Article cost
                int totalCost = cartItems.stream()
                        .mapToInt(cartItem -> cartItem.getArticle().getCost() * cartItem.getQuantity())
                        .sum();
                purchaseHistory.setTotalCost(totalCost);

                historyRepository.save(purchaseHistory);

                // Clear the cart items
                cartItems.clear();
                cartRepository.save(cart);
            }
        }
    }

    /**
     * Den här metoden beräknar den totala kostnaden för en uppsättning artiklar.
     *
     * //@param articles är en uppsättning artiklar.
     * @return totalCost är den totala kostnaden för artiklarna i uppsättningen.
     */
    //Gamla calculateTotalCost-metoden
    /*private int calculateTotalCost(Set<Article> articles) {
        return articles.stream()
                .map(Article::getCost)
                .reduce(0, Integer::sum);
    }*/

    //Nya calculateTotalCost-metoden
//    private int calculateTotalCost(Set<CartItem> cartItems) {
//        return cartItems.stream()
//                .mapToInt(cartItem -> cartItem.getArticle().getCost() * cartItem.getQuantity())
//                .sum();
//    }
}