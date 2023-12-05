package com.Molndal.WebShopService;

import com.Molndal.WebShopService.Models.Role;
import com.Molndal.WebShopService.Models.User;
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

}
