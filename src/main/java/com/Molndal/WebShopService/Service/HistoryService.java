package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.HistoryRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HistoryService {


/*
    @Autowired


    /*@Autowired


    @Autowired

    private HistoryRepository historyRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserRepository userRepository;

    // Hämta alla historikposter
    public List<History> getAllHistory(){
        User currentUser = userService.getCurrentUser();
        return historyRepository.findByUser(currentUser);
    }

    // Hämta historik för den aktuella användaren
    public List<History> getUserHistory(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return historyRepository.findByUser(user);
        } else {
            // Hantera fallet när användaren inte finns
            return Collections.emptyList();
        }
    }

    // Registrera ett köp i användarens historik
    public History articlePurchase(Set<Article> articles){
        User currentUser = userService.getCurrentUser();
        int totalCost = calculateTotalCost(articles);

        // Skapa en ny historikpost för köpet
        History history = new History();
        history.setUser(currentUser);
        history.setPurchasedArticles(articles);
        history.setTotalCost(totalCost);

        // Spara historikposten i lagringsstället
        return historyRepository.save(history);
    }

    // Beräkna den totala kostnaden för en uppsättning artiklar
    private int calculateTotalCost(Set<Article> articles) {
        return articles.stream()
                .map(Article::getCost)
                .reduce(0, Integer::sum);
    }



    private int calculateTotalCost(Set<Article> articles) {
        return articles.stream()
                .map(articles::getCost)
                .reduce(0, Integer::sum);
    }


 */


}






