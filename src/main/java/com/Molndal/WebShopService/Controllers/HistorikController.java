package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.Historik;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/historik")
public class HistorikController {

    @GetMapping("")
    private ResponseEntity<List<Historik>> getAllHistorik() {

    }

    @GetMapping("/{id}")
    private ResponseEntity<List<Historik>> getOneUserHistorik(
            @PathVariable Long userId
    ) {

    }

    @PostMapping("")
    private ResponseEntity<Historik> addNewHistorik(
            @RequestBody Historik artikle
    ) {

    }
}
