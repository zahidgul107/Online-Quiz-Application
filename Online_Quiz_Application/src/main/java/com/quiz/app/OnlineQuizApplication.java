package com.quiz.app;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.quiz.app.entity.Role;
import com.quiz.app.entity.User;
import com.quiz.app.enums.ERole;
import com.quiz.app.repository.RoleRepository;
import com.quiz.app.repository.UserRepository;

@SpringBootApplication
public class OnlineQuizApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepository userRepo;

	public static void main(String[] args) {
		SpringApplication.run(OnlineQuizApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Populate roles if they don't exist
		Arrays.stream(ERole.values()).forEach(roleEnum -> {
			if (roleRepo.findByName(roleEnum).isEmpty()) {
				Role role = new Role();
				role.setName(roleEnum);
				roleRepo.save(role);
			}
		});

		if (userRepo.findByUsername("admin").isEmpty()) {
			Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN).orElseGet(() -> {
				Role role = new Role();
				role.setName(ERole.ROLE_ADMIN);
				return roleRepo.save(role);
			});

			User adminUser = new User("ADMINISTRATOR", (long) 1234567891, "admin", "admin@example.com",
					new BCryptPasswordEncoder().encode("admin"), adminRole);
			userRepo.save(adminUser);

			System.out.println("Admin user created");
		} else {
			System.out.println("Admin user already exists");
		}

	}

}
