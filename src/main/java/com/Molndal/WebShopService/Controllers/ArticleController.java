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

    @Autowired
    private ArticleRepository articleRepository;


    //ALla ska kunna detta, även de som inte är inloggade
    @GetMapping("")
    private ResponseEntity<List<Article>> getArticles() {
    return ResponseEntity.ok(articleService.getAllArticles());

    }

    //Vem ska kunna detta?
    @GetMapping("/{id}")
    private ResponseEntity<Article> getOneArticle(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(articleService.getOneArticle(id));
    }

    //Endast admin ska kunna detta
    @PostMapping("/admin")
    private ResponseEntity<Article> addArticle(
            @RequestBody Article article
    ) {
        return ResponseEntity.ok(articleService.addNewArticle(article));
    }

    //Endast admin ska kunna detta
    //Bör lägga till någon slags try-catch här också
    @PatchMapping("/admin/{id}")
    private ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestBody Article articleDetails
    ) {
        Article article = articleRepository.findById(id).get();
        article.setName(articleDetails.getName());
        article.setCost(articleDetails.getCost());
        article.setDescription(articleDetails.getDescription());
        return ResponseEntity.ok(articleService.updateArticle(article));
    }

    //Endast admin ska kunna detta
    //try-catchen verkar inte fungera så bra...Den returnerar "Artikeln har tagits bort framgångsrikt" oavsett om id:t finns i db eller inte.
    @DeleteMapping("/admin/{id}")
    private ResponseEntity<String> deleteArticle(
        @PathVariable Long id
) {
    try {
        articleService.deleteOneArticle(id);
        return ResponseEntity.ok("Artikeln har tagits bort framgångsrikt");
    } catch (EmptyResultDataAccessException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingen artikel hittades med det angivna ID:et");
    }
}
}

