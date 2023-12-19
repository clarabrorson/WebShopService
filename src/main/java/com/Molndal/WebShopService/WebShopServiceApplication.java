package com.Molndal.WebShopService;
import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.Role;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import com.Molndal.WebShopService.Repository.RoleRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * Detta är huvudklassen för applikationen.
 */
@SpringBootApplication
public class WebShopServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(WebShopServiceApplication.class, args);
	}

	/**
	 * Denna metod används för att lägga till initial data i databasen om den är tom.
	 * @param roleRepository
	 * @param userRepository
	 * @param passwordEncoder
	 * @param articleRepository
	 */
		@Bean
		CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ArticleRepository articleRepository) {
			return args -> {
				//Kontrollerar om en roll med auktoriteten "ADMIN" redan finns i databasen.
				// Om den finns, avslutas metoden tidigt och inget mer görs.
				if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

				//Skapar en ny Role-instans med auktoriteten "ADMIN" och sparar den i databasen.
				Role adminRole = roleRepository.save(new Role("ADMIN"));

				//Skapar en ny Role-instans med auktoriteten "USER" och sparar den i databasen.
				roleRepository.save(new Role("USER"));

				//Skapar en uppsättning av roller och lägger till adminRole i uppsättningen.
				Set<Role> roles = new HashSet<>();
				roles.add(adminRole);

				//Skapar en ny User-instans med användarnamnet "admin", ett krypterat lösenord och rollerna.
				//Användaren sparas i databasen genom UserRepository.
				User admin = new User(1L, "admin", passwordEncoder.encode("password"), roles);
				userRepository.save(admin);

				//Skapar några artiklar och sparar i databasen
				Article article = new Article("Banan", 10, "En gul frukt", 50);
				Article article2 = new Article("Äpple", 5, "En röd frukt", 20);
				Article article3 = new Article("Apelsin", 7, "En orange frukt", 30);
				Article article4 = new Article("Päron", 8, "En grön frukt", 40);
				Article article5 = new Article("Kiwi", 9, "En brun frukt", 60);
				Article article6 = new Article("Mango", 11, "En gul frukt", 70);
				Article article7 = new Article("Ananas", 12, "En gul frukt", 80);
				Article article8 = new Article("Vattenmelon", 13, "En grön frukt", 90);
				Article article9 = new Article("Citron", 14, "En gul frukt", 100);
				Article article10 = new Article("Papaya", 15, "En gul frukt", 110);

				articleRepository.save(article);
				articleRepository.save(article2);
				articleRepository.save(article3);
				articleRepository.save(article4);
				articleRepository.save(article5);
				articleRepository.save(article6);
				articleRepository.save(article7);
				articleRepository.save(article8);
				articleRepository.save(article9);
				articleRepository.save(article10);

			};
	}
}
