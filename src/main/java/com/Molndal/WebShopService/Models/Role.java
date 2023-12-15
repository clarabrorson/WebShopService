package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Den här klassen används för att skapa roller för användare.
 * Den implementerar GrantedAuthority för att kunna användas av Spring Security.
 * @author Clara Brorson
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

    public Role() {super();}

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(Integer roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
