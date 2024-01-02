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
	 * @param roleRepository är ett RoleRepository-objekt som används för att spara roller i databasen.
	 * @param userRepository är ett UserRepository-objekt som används för att spara användare i databasen.
	 * @param passwordEncoder är ett PasswordEncoder-objekt som används för att kryptera lösenord.
	 * @param articleRepository är ett ArticleRepository-objekt som används för att spara artiklar i databasen.
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
				Article article = new Article("Banana", 10, "A yellow fruit", 85);
				Article article2 = new Article("Apple", 20, "A red fruit", 170);
				Article article3 = new Article("Orange", 30, "An orange fruit", 72);
				Article article4 = new Article("Pear", 40, "A green fruit", 56);
				Article article5 = new Article("Pineapple", 50, "A yellow fruit", 11);
				Article article6 = new Article("Strawberry", 60, "A red berry", 50);
				Article article7 = new Article("Blueberry", 70, "A blue berry", 25);
				Article article8 = new Article("Raspberry", 80, "A red berry", 36);
				Article article9 = new Article("Lemon", 90, "A yellow fruit", 45);
				Article article10 = new Article("Kiwi", 100, "A green fruit", 30);
				Article article11 = new Article("Mango", 110, "A yellow fruit", 20);
				Article article12 = new Article("Watermelon", 120, "A green fruit", 10);
				Article article13 = new Article("Grape", 130, "A purple fruit", 15);
				Article article14 = new Article("Cherry", 140, "A red fruit", 18);
				Article article15 = new Article("Peach", 150, "A yellow fruit", 9);
				Article article16 = new Article("Plum", 160, "A purple fruit", 12);
				Article article17 = new Article("Apricot", 170, "An orange fruit", 8);
				Article article18 = new Article("Pomegranate", 180, "A red fruit", 6);
				Article article19 = new Article("Cantaloupe", 190, "A green fruit", 5);
				Article article20 = new Article("Honeydew", 200, "A green fruit", 4);

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
				articleRepository.save(article11);
				articleRepository.save(article12);
				articleRepository.save(article13);
				articleRepository.save(article14);
				articleRepository.save(article15);
				articleRepository.save(article16);
				articleRepository.save(article17);
				articleRepository.save(article18);
				articleRepository.save(article19);
				articleRepository.save(article20);

			};
	}
}
