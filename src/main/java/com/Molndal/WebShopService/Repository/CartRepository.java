package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Detta interface är en JPA-repository som används för att utföra operationer på tabellen carts.
 * @author Clara Brorson
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = {"user", "user.username"})
    Optional<Cart> findCartById(Long cartId);
    Cart findByUser(User currentUser);
}
