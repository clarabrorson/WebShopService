package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private Set<Article> purchasedArticles;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
