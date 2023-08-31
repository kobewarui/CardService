package com.kelvin.cardsproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kelvin.cardsproject.entities.User;
import com.kelvin.cardsproject.entities.UserRole;
import com.kelvin.cardsproject.repositories.UserRepository;

@SpringBootApplication
public class CardsProjectApplication {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CardsProjectApplication.class, args);
	}
	
    @Bean
	
	CommandLineRunner runner() {
		
		return args ->{
			
	  
	        userRepository.save(new User("admin@gmail.com", passwordEncoder.encode("adminpassword"), UserRole.ADMIN));
	        userRepository.save(new User("memberOne@gmail.com", passwordEncoder.encode("memberOnepassword"), UserRole.MEMBER));
	        userRepository.save(new User("memberTwo@gmail.com", passwordEncoder.encode("memberTwopassword"), UserRole.MEMBER));
	        userRepository.save(new User("memberThree@gmail.com", passwordEncoder.encode("memberThreepassword"), UserRole.MEMBER));
	        userRepository.save(new User("memberFour@gmail.com", passwordEncoder.encode("memberFourpassword"), UserRole.MEMBER));
	        userRepository.save(new User("memberFive@gmail.com", passwordEncoder.encode("memberSixpassword"), UserRole.MEMBER));
	        
			
		};
		
	}

}
