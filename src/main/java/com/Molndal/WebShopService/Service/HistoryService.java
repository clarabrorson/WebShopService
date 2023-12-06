package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HistoryService {
/*
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    public List<History> getAllHistory(){
        return historyRepository.findAll();
    }

    public List<History> getOneHistory(){
        User currentUser = userService.getCurrentUser();
        return historyRepository.findByUser(currentUser);
    }
    public History articlePurchase(Set<Article> articles){
        User currentUser = userService.getCurrentUser();
        int totalCost = calculateTotalCost(articles);

        History history = new History();
        history.setUser(currentUser);
        history.setPurchasedArticles(articles);
        history.setTotalCost(totalCost);
        return historyRepository.save(history);
    }

    private int calculateTotalCost(Set<Article> articles) {
        return articles.stream()
                .map(articles::getCost)
                .reduce(0, Integer::sum);
    }

 */

}
