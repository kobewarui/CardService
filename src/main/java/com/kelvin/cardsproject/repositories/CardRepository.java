package com.kelvin.cardsproject.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kelvin.cardsproject.entities.Card;
import com.kelvin.cardsproject.entities.User;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
	
	List<Card> findByUser(User user);
	List<Card> findByColor(String color);
	List<Card> findByStatus(String status);
	List<Card> findByName(String name);
	List<Card> findByCreatedAt(LocalDateTime createdAt);
	
	boolean existsByStatus(String status);

    boolean existsByColor(String color);

    boolean existsByName(String name);

    boolean existsByCreatedAt(LocalDateTime createdAt);
	Optional<Card> findById(Long cardId);

}
