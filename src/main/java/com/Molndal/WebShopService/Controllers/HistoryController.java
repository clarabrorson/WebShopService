package com.Molndal.WebShopService.Controllers;

import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webshop/historik")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

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
