package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/artiklar")
public class ArticleController {

    @GetMapping("")
    private ResponseEntity<List<Article>> getArtiklar() {
<<<<<<< HEAD
    return null;
=======

>>>>>>> fredrik
    }

    @GetMapping("/{id}")
    private ResponseEntity<Article> getOneArtikle(
            @PathVariable Long id
    ) {
<<<<<<< HEAD
        return null;
=======

>>>>>>> fredrik
    }

    @PostMapping("")
    private ResponseEntity<Article> addNewArtikle(
            @RequestBody Article artikle
    ) {
<<<<<<< HEAD
        return null;
=======

>>>>>>> fredrik
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Article> updateArtikle(
            @PathVariable Long id,
            @RequestBody Article artikle
    ) {
<<<<<<< HEAD
        return null;
=======

>>>>>>> fredrik
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Article> deleteArtikle(
            @PathVariable Long id
    ) {
<<<<<<< HEAD
        return null;
=======

>>>>>>> fredrik
    }
}
