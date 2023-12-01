package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Artikel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/artiklar")
public class ArtiklarController {

    @GetMapping("")
    private ResponseEntity<List<Artikel>> getArtiklar() {

    }

    @GetMapping("/{id}")
    private ResponseEntity<Artikel> getOneArtikle(
            @PathVariable Long id
    ) {

    }

    @PostMapping("")
    private ResponseEntity<Artikel> addNewArtikle(
            @RequestBody Artikel artikle
    ) {

    }

    @PatchMapping("/{id}")
    private ResponseEntity<Artikel> updateArtikle(
            @PathVariable Long id,
            @RequestBody Artikel artikle
    ) {

    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Artikel> deleteArtikle(
            @PathVariable Long id
    ) {

    }
}
