package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Artikel;
import com.Molndal.WebShopService.Models.Kundkorg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/kundkorg")
public class KundkorgController {

    @GetMapping("")
    private ResponseEntity<Kundkorg> getKundkorg() {

    }

    @PostMapping("")
    private ResponseEntity<Kundkorg> addNewArtikleToKundkorg(
            @RequestBody Artikel artikle
    ) {

    }

    @PatchMapping("/{antal}")
    private ResponseEntity<Kundkorg> updateArtikleCount(
            @PathVariable int antal,
            @RequestBody Artikel artikle
    ) {

    }

    @DeleteMapping("")
    private ResponseEntity<Kundkorg> deleteArtikleFromKundkorg(
            @RequestBody Artikel artikel
    ) {

    }
}
