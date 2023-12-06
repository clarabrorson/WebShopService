package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/webshop/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/getAll")
    private ResponseEntity<List<History>> getAllHistorik() {
        List<History> allHistory = historyService.getAllHistory();
        return ResponseEntity.ok(allHistory);
    }
    @GetMapping("/{id}")
    private ResponseEntity<List<History>> getOneUserHistory(
            @PathVariable Long id
    ) {
        List<History> userHistory = historyService.getUserHistory(id);
        return ResponseEntity.ok(userHistory);
    }

    @PostMapping("/add")
    private ResponseEntity<History> addNewHistorik(
            @RequestBody Set<Article> articles
    ) {
        History newHistory = historyService.articlePurchase(articles);
        return ResponseEntity.ok(newHistory);
    }
}
