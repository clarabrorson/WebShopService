package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Service.CartService;
import com.Molndal.WebShopService.Service.HistoryService;
import com.Molndal.WebShopService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
/**
 * En controller-klass som hanterar historikrelaterade operationer för webbshoppen.
 * Denna controller ger möjlighet att hämta historikposter och köpta artiklar samt utföra köptransaktioner.
 * Alla metoder i denna controller kräver att användaren är autentiserad.
 * Endpoint för att hämta alla historikposter: <code>GET /webshop/history</code>
 * Endpoint för att hämta alla köpta artiklar för den aktuella användaren: <code>GET /webshop/history/currentUserHistory</code>
 * Endpoint för att utföra en köptransaktion: <code>POST /webshop/history/purchase</code>
 *
 * @author Jafar
 */
@RestController
@RequestMapping("/webshop/history")
@CrossOrigin("*")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    /**
     * Hämtar alla historikposter från webbshopen.
     * Användare med olika roller har tillgång till denna metod.
     * @return ResponseEntity med en lista av historikposter om framgångsrikt, annars INTERNAL_SERVER_ERROR.
     */
    @GetMapping("")
    private ResponseEntity<List<History>> getAllHistory() {
        List<History> allHistory = historyService.getAllHistory();
        return ResponseEntity.ok(allHistory);
    }

    /**
     * Hämtar alla köpta artiklar för den aktuella användaren.
     * Användare med olika roller har tillgång till denna metod.
     * @return ResponseEntity med en lista av köpta artiklar om framgångsrikt, annars INTERNAL_SERVER_ERROR.
     */
//    @GetMapping("/currentUserHistory")
//    private ResponseEntity<List<Article>> getCurrentUserPurchasedArticles() {
//        try {
//            List<Article> purchasedArticles = historyService.getUserHistory();
//            return ResponseEntity.ok(purchasedArticles);
//        } catch (Exception e) {
//            e.printStackTrace();  // Logga eller skriv ut detaljer om exception
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    @GetMapping("/currentUserHistory")
    private ResponseEntity<List<History>> getCurrentUserPurchasedArticles() {
        try {
            List<History> userHistories = historyService.getUserHistory();
            return ResponseEntity.ok(userHistories);
        } catch (Exception e) {
            e.printStackTrace();  // Log or print exception details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Utför en köptransaktion för den aktuella användaren baserat på innehållet i varukorgen.
     * Endast användare med rollen "USER" har tillgång till denna metod.
     * @return ResponseEntity med en meddelandesträng om att köpet genomfördes framgångsrikt.
     */
    @PostMapping("/purchase")
    private ResponseEntity<String> purchaseCart() {
        User currentUser = userService.getCurrentUser();
        cartService.purchaseCart(currentUser);
        return ResponseEntity.ok("Purchase completed successfully.");
    }
}