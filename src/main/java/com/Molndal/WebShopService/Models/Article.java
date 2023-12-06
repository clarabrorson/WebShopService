package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int cost;
    private String description;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private History history;


}
