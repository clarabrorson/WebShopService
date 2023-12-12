package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.CartRepository;
import com.Molndal.WebShopService.Repository.HistoryRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
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
    // Hämta historik för den aktuella användaren
    public List<History> getUserHistory() {
        User currentUser = userService.getCurrentUser();
        return historyRepository.findByUser(currentUser);
    }

}






