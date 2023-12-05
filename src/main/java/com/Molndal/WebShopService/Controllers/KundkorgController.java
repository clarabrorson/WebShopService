package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Kundkorg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webshop/kundkorg")
public class KundkorgController {

    @GetMapping("")
    private ResponseEntity<Kundkorg> getKundkorg() {

    }

    @PostMapping("")
    private ResponseEntity<Kundkorg> addNewArtikleToKundkorg(
            @RequestBody Article artikle
    ) {

    }

    @PatchMapping("/{antal}")
    private ResponseEntity<Kundkorg> updateArtikleCount(
            @PathVariable int antal,
            @RequestBody Article artikle
    ) {

    }

    @DeleteMapping("")
    private ResponseEntity<Kundkorg> deleteArtikleFromKundkorg(
            @RequestBody Article artikel
    ) {

    }
}
