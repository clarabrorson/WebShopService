package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/article")
@CrossOrigin("*")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    private ResponseEntity<List<Article>> getArticles() {
    return ResponseEntity.ok(articleService.getAllArticles());

    }

    @GetMapping("/{id}")
    private ResponseEntity<Article> getOneArticle(
            @PathVariable Long id
    ) {
        return null;
    }

    @PostMapping("")
    private ResponseEntity<Article> addArticle(
            @RequestBody Article article
    ) {
        return ResponseEntity.ok(articleService.addNewArticle(article));
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestBody Article article
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Article> deleteArticle(
            @PathVariable Long id
    ) {
        return null;
    }
}
