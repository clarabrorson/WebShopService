package com.Molndal.WebShopService.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Denna klass används för att skapa ett Article-objekt
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

    /**
     * Inköpshistorik kopplad till artikeln.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

