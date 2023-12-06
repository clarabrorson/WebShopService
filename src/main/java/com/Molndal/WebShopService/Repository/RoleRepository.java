package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * @author Clara Brorson
 * Interface for RoleRepository
 * The extends JpaRepository gives us access to methods for CRUD operations
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByAuthority(String authority);
}
