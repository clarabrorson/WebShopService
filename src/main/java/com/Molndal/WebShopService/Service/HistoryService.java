package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.CartRepository;
import com.Molndal.WebShopService.Repository.HistoryRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        User currentUser = userService.getCurrentUser();
        return historyRepository.findByUser(currentUser);
    }

    // Hämta historik för den aktuella användaren
    public List<History> getUserHistory() {
        // Get the current user directly within the service
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            // If the current user is not null, retrieve the history
            return historyRepository.findByUser(currentUser);
        } else {
            // Hantera fallet när användaren inte finns
            return Collections.emptyList();
        }
    }

}