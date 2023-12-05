package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/articles")
public class ArticleController {

    @GetMapping("")
    private ResponseEntity<List<Article>> getArticles() {
    return null;

    }

    @GetMapping("/{id}")
    private ResponseEntity<Article> getOneArticle(
            @PathVariable Long id
    ) {
        return null;
    }

    @PostMapping("")
    private ResponseEntity<Article> addNewArticle(
            @RequestBody Article article
    ) {
        return null;
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
