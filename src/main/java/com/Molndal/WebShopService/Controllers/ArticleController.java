package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import com.Molndal.WebShopService.Service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


import java.util.List;

/**
 * Denna klass används för att hantera förfrågningar till <a href="http://localhost:8081/webshop/articles">...</a> från klienten till API:et.
 * Klassen används för att hämta, lägga till, uppdatera och ta bort artiklar från databasen.
 * Beroende på användarens roll, kommer användaren att ha tillgång till olika delar av API:et.
 *
 * @author Fredrik
 */
@RestController
@RequestMapping("/webshop/articles")
@CrossOrigin("*")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * Denna metod används för att hämta alla artiklar från databasen. Alla har tillgång till denna metod, även de som inte är inloggade.
     * Endpoint: GET /webshop/articles
     *
     * @return en lista med alla artiklar.
     */
    @GetMapping("")
    private ResponseEntity<List<Article>> getArticles() {

        return ResponseEntity.ok(articleService.getAllArticles());
    }

    /**
     * Denna metod används för att hämta en artikel från databasen med ett specifikt id.
     * Endpoint: GET /webshop/articles/{id}
     *
     * @param id är id:t för den artikel som ska hämtas.
     * @return en artikel med det specifika id:t.
     */
    @GetMapping("/{id}")
    private ResponseEntity<Article> getOneArticle(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(articleService.getOneArticle(id));
    }

    /**
     * Denna metod används för att lägga till en artikel i databasen. Endast användare med rollen "ADMIN" har tillgång till denna metod.
     * Endpoint: POST /webshop/articles
     *
     * @param article är den artikel som ska läggas till i databasen.
     * @return den artikel som har lagts till i databasen.
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Article> addArticle(
            @RequestBody Article article
    ) {
        return ResponseEntity.ok(articleService.addNewArticle(article));
    }

    /**
     * Denna metod används för att uppdatera en artikel i databasen. Endast användare med rollen "ADMIN" har tillgång till denna metod.
     * Endpoint: PATCH /webshop/articles/{id}
     *
     * @param id är id:t för den artikel som ska uppdateras.
     * @param articleDetails är den artikel som innehåller den nya informationen.
     * @return den uppdaterade artikeln.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long id,
            @RequestBody Article articleDetails
    ) {
        try {
            Article updatedArticle = articleService.updateArticle(id, articleDetails);
            if (updatedArticle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(updatedArticle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Denna metod används för att ta bort en artikel från databasen. Endast användare med rollen "ADMIN" har tillgång till denna metod.
     * Endpoint: DELETE /webshop/articles/{id}
     *
     * @param id är id:t för den artikel som ska tas bort.
     * @return ett meddelande som indikerar om artikeln har tagits bort eller inte.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(
        @PathVariable Long id
) {
    return ResponseEntity.ok(articleService.deleteOneArticle(id));
}
}

