package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int cost;
    private String description;

    //La till quantity variabel för att kunna uppdatera antal i cart
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private History history;

    //Metod för att uppdatera antal i cart. Behövs för CartService och för att kunna uppdatera antal i databasen
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
