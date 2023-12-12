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


@SpringBootApplication
public class WebShopServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebShopServiceApplication.class, args);
	}

		//Denna metod definierar en bean för CommandLineRunner, som används för att köra specifik kod vid applikationens uppstart.
		//Här används den för att lägga till initial data i databasen om den är tom.
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


				//Skapar en article och sparar den i databasen
				Article article = new Article(1L, "Banan", 10, "En gul frukt", 50);
				articleRepository.save(article);
			};
	}

}
