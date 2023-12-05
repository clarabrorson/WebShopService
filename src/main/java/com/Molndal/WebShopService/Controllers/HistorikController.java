package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.History;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/historik")
public class HistorikController {

    @GetMapping("")
    private ResponseEntity<List<History>> getAllHistorik() {
        return null;
    }

    @GetMapping("/{id}")
    private ResponseEntity<List<History>> getOneUserHistorik(
            @PathVariable Long userId
    ) {
        return null;
    }

    @PostMapping("")
    private ResponseEntity<History> addNewHistorik(
            @RequestBody History artikle
    ) {
        return null;
    }
}
