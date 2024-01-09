package com.Molndal.WebShopService.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Denna klass används för att skapa ett Article-objekt.
 * Klassen används för att skapa en tabell i databasen med hjälp av JPA.
 * Varje artikel har ett id, ett namn, ett pris och en beskrivning.
 * Varje artikel kan ha flera CartItem-objekt kopplade till sig.
 * Varje artikel kan ha flera History-objekt kopplade till sig.
 *
 * @author Fredrik
 */
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

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<CartItem> cartItems;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "history_id")
    @JsonIgnore
    private History history;

    /**
     * Konstruktor för att skapa ett Article-objekt.
     *
     * @param name är namnet på artikeln.
     * @param cost är priset på artikeln.
     * @param description är beskrivningen av artikeln.
     */
    public Article(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    /**
     * Metod för att lägga till en artikel i inköpshistoriken.
     *
     * @param history är inköpshistoriken som artikeln ska läggas till i.
     */
    public void setHistory(History history) {
        if (this.history != null) {

            this.history.getPurchasedArticles().remove(this);
        }
        this.history = history;
        if (history != null) {
            history.getPurchasedArticles().add(this);
        }
    }

    /**
     * Override metod som kollar om ett objekt är en instans av ett Article-objekt och om deras id är lika.
     * @param o är objektet som ska jämföras med.
     * @return true om objekten är lika, annars false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }

    /**
     * Override metod som returnerar hashkoden för ett Article-objekt.
     * @return hashkoden för ett Article-objekt.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

