package com.Molndal.WebShopService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
/**
 * Representerar en användare i webbshop-applikationen.
 * Implementerar Spring Security UserDetails-gränssnittet för autentisering och auktorisering.
 *
 * @Author Jafar Hussein
 */

@Table(name="roles")
@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    /**
     * Mängd roller (auktoriteter) tilldelade användaren.
     */
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_role_junction",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;
    /**
     * Mängd inköpshistorik kopplad till användaren.
     */
    @OneToMany(mappedBy = "user")
    private Set<History> histories;
    /**
     * Kundvagn kopplad till användaren.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    /**
     * Standardkonstruktor nödvändig för CommandLineRunner-funktionalitet.
     * Initialiserar auktoritetsuppsättningen som en tom HashSet.
            */
    public User() {
        super();
        this.authorities = new HashSet<Role>();
    }

    /**
     * Parametriserad konstruktor för att skapa en användare med specificerade detaljer.
     *
     * @param id          Unik identifierare för användaren.
     * @param username    Unikt användarnamn för användaren.
     * @param password    Lösenord kopplat till användarens konto.
     * @param authorities Mängd roller (auktoriteter) tilldelade användaren.
     */
    public User(Long id, String username, String password, Set<Role> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    /**
     * Returnerar de auktoriteter som tilldelats användaren.
     *
     * @return Mängden roller (auktoriteter) tilldelade användaren.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * Indikerar om användarens konto har förfallit.
     *
     * @return true, eftersom kontot aldrig förfaller.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Indikerar om användaren inte är låst ute.
     *
     * @return true, eftersom användarkontot aldrig låses.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Indikerar om användarens autentiseringsuppgifter (lösenord) inte har förfallit.
     *
     * @return true, eftersom autentiseringsuppgifterna aldrig förfaller.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Indikerar om användaren är aktiverad.
     *
     * @return true, eftersom alla användare är aktiverade.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
