package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;

@Entity
public class Historik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private User user;
}
