package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webshop/kundkorg")
public class KundkorgController {

    @GetMapping("")
    private ResponseEntity<Cart> getKundkorg() {
        return null;
    }

    @PostMapping("")
    private ResponseEntity<Cart> addNewArtikleToKundkorg(
            @RequestBody Article artikle
    ) {
        return null;
    }

    @PatchMapping("/{antal}")
    private ResponseEntity<Cart> updateArtikleCount(
            @PathVariable int antal,
            @RequestBody Article artikle
    ) {
        return null;
    }

    @DeleteMapping("")
    private ResponseEntity<Cart> deleteArtikleFromKundkorg(
            @RequestBody Article article

    ) {
        return null;
    }
}
