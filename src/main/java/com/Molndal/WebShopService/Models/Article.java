package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int cost;
    private String description;

<<<<<<< HEAD
=======
    @ManyToOne
    @JoinColumn(name = "history_id")
    private History history;

>>>>>>> fredrik
}
