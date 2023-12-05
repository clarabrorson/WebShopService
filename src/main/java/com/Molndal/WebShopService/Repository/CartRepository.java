package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.Cart;
import com.Molndal.WebShopService.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
