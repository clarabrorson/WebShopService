package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/artiklar")
public class ArtiklarController {

    @GetMapping("")
    private ResponseEntity<List<Article>> getArtiklar() {

    }

    @GetMapping("/{id}")
    private ResponseEntity<Article> getOneArtikle(
            @PathVariable Long id
    ) {

    }

    @PostMapping("")
    private ResponseEntity<Article> addNewArtikle(
            @RequestBody Article artikle
    ) {

    }

    @PatchMapping("/{id}")
    private ResponseEntity<Article> updateArtikle(
            @PathVariable Long id,
            @RequestBody Article artikle
    ) {

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Article> deleteArtikle(
            @PathVariable Long id
    ) {

    }
}
