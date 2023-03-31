package com.training.security;

import com.training.security.model.UserEntity;
import com.training.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository) {
		return (args -> {
			List<UserEntity> userEntities = userRepository.findAll();
			log.info("UserEntity records: ");
			log.info("---------------");
			log.info(userEntities.toString());
			log.info("");
		});
	}
}
