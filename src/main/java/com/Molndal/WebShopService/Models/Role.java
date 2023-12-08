package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Clara Brorson
 * This class is a model for the role of the user.
 * It implements GrantedAuthority from Spring Security.
 */
@Entity
@Data
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer roleId;

    private String authority;

    //Lade till konstruktor för att CommandLineRunner skulle funka
    public Role() {super();}

    //Lade till konstruktor för att CommandLineRunner skulle funka
    public Role(String authority) {
        this.authority = authority;
    }

    //Lade till konstruktor för att CommandLineRunner skulle funka
    public Role(Integer roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }

    //Ändrade från null till this.authority
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
