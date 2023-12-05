package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Article;
<<<<<<< HEAD
import com.Molndal.WebShopService.Models.Cart;
=======
import com.Molndal.WebShopService.Models.Kundkorg;
>>>>>>> fredrik
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
<<<<<<< HEAD
    private ResponseEntity<Cart> addNewArtikleToKundkorg(
=======
    private ResponseEntity<Kundkorg> addNewArtikleToKundkorg(
>>>>>>> fredrik
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
<<<<<<< HEAD
    private ResponseEntity<Cart> deleteArtikleFromKundkorg(
            @RequestBody Article article
=======
    private ResponseEntity<Kundkorg> deleteArtikleFromKundkorg(
            @RequestBody Article artikel
>>>>>>> fredrik
    ) {
        return null;
    }
}
