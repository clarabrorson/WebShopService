package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * @Author Jafar
 * Repository interface för att hantera databasoperationer för användare.
 * Innehåller metoder för att hämta användare från databasen.
 * Används av UserService-klassen.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
