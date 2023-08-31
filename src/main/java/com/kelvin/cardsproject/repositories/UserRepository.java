package com.kelvin.cardsproject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelvin.cardsproject.entities.User;


public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User>findByEmail(String email);

}
