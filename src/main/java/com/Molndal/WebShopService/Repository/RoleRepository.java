package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Detta interface används för att utföra operationer på tabellen roles.
 * @author Clara Brorson
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByAuthority(String authority);
}
