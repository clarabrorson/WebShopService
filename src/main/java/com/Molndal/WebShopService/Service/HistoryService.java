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

    /**
     * @Method getUserHistory
     * @return List<History>
     * denna metod hämtar historik för inloggad användare
     * använder sig av userService.getCurrentUser() för att hämta inloggad användare
     * om användaren inte finns returneras en tom lista
     * */
    public List<History> getUserHistory() {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            // om användaren finns, hämta historik för användaren
            return historyRepository.findByUser(currentUser);
        } else {
            // Hantera fallet när användaren inte finns
            return Collections.emptyList();
        }
    }

}