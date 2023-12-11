package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import com.Molndal.WebShopService.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/articles")
@CrossOrigin("*")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //Alla har tillgång till denna förfrågan, även de som inte är inloggade
    @GetMapping("")
    private ResponseEntity<List<Article>> getArticles() {

        return ResponseEntity.ok(articleService.getAllArticles());
    }

    //Inloggade users och admin-förfrågan
    @GetMapping("/{id}")
    private ResponseEntity<Article> getOneArticle(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(articleService.getOneArticle(id));
    }

    //Endast admin-förfrågan
    @PostMapping("/admin")
    private ResponseEntity<Article> addArticle(
            @RequestBody Article article
    ) {
        return ResponseEntity.ok(articleService.addNewArticle(article));
    }

    //Endast admin-förfrågan
    @PatchMapping("/admin/{id}")
    private ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestBody Article articleDetails
    ) {
        return ResponseEntity.ok(articleService.updateArticle(id ,articleDetails));
    }

    //Endast admin-förfrågan
    @DeleteMapping("/admin/{id}")
    private ResponseEntity<String> deleteArticle(
        @PathVariable Long id
) {
    return ResponseEntity.ok(articleService.deleteOneArticle(id));
}
}

