package com.Molndal.WebShopService.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnore
    private Set<Article> purchasedArticles = new HashSet<>();



    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private int totalCost;

    @Override
    public boolean equals(Object o) { // kontrollerar om två objekt är lika
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return id != null && id.equals(history.id);
    }

    @Override
    public int hashCode() { // returnerar ett unikt hashcode för varje objekt
        return id != null ? id.hashCode() : 0;

    }
}
