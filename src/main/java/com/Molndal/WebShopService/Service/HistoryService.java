package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.CartRepository;
import com.Molndal.WebShopService.Repository.HistoryRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoryService {


    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;



    // Hämta alla historikposter
    public List<History> getAllHistory(){
        return historyRepository.findAll();
    }

    // Hämta historik för den aktuella användaren
    public List<Article> getUserHistory() {
        // Get the current user directly within the service
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            // om användaren finns, hämta historik för användaren
            List<History> userHistory = historyRepository.findByUser(currentUser);

            // Collect purchased articles from each history entry
            List<Article> purchasedArticles = userHistory.stream()
                    .flatMap(history -> history.getPurchasedArticles().stream())
                    .collect(Collectors.toList());

            return purchasedArticles;
        } else {
            // Hantera fallet när användaren inte finns
            return Collections.emptyList();
        }
    }

}