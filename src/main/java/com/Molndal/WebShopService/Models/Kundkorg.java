package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;

@Entity
public class Kundkorg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
}
