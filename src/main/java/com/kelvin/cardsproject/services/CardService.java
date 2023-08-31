package com.kelvin.cardsproject.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kelvin.cardsproject.entities.Card;
import com.kelvin.cardsproject.entities.User;
import com.kelvin.cardsproject.entities.UserRole;
import com.kelvin.cardsproject.repositories.CardRepository;

@Service
public class CardService {
	
	private final  CardRepository cardRepository;
	
	public CardService(CardRepository cardRepository) {
		
		this.cardRepository=cardRepository;
		
	}
	
	public void createCards(List<Card> cards,User user) {
       
		cards.forEach(card ->{
			card.setUser(user);
			
		});
        cardRepository.saveAll(cards);
    }
	
	 public List<Card> getCardsForUser(User user) {
		 if (user.getRole() == UserRole.ADMIN) {
	            return cardRepository.findAll();
	        } else {
	            return cardRepository.findByUser(user);
	        }
	    }

	  

	public List<Card> searchAndSortCards(User user, String status, String color, String name, LocalDateTime createdAt,String sort) {
	    List<Card> filteredCards = cardRepository.findByUser(user);

	    if (status != null) {
	        if (isValidProperty(status, "status")) {
	            filteredCards = filteredCards.stream()
	                    .filter(card -> card.getStatus().equals(status))
	                    .collect(Collectors.toList());
	        } else {
	            throw new IllegalArgumentException("Invalid status: " + status);
	        }
	    }

	    if (color != null) {
	        if (isValidProperty(color, "color")) {
	            filteredCards = filteredCards.stream()
	                    .filter(card -> {
	                        String cardColor = card.getColor();
	                        return cardColor != null && cardColor.equals(color);
	                    })
	                    .collect(Collectors.toList());
	        } else {
	            throw new IllegalArgumentException("Invalid color: " + color);
	        }
	    }


	    if (name != null) {
	        if (isValidProperty(name, "name")) {
	            filteredCards = filteredCards.stream()
	                    .filter(card -> card.getName().equals(name))
	                    .collect(Collectors.toList());
	        } else {
	            throw new IllegalArgumentException("Invalid name: " + name);
	        }
	    }

	    if (createdAt != null) {
	        if (isValidProperty(createdAt, "createdAt")) {
	            filteredCards = filteredCards.stream()
	                    .filter(card -> card.getCreatedAt().toLocalDate().isEqual(createdAt.toLocalDate()))
	                    .collect(Collectors.toList());
	        } else {
	            throw new IllegalArgumentException("Invalid creation date: " + createdAt);
	        }
	    }

	    if (!filteredCards.isEmpty() && sort != null) {
	        filteredCards = sortCards(filteredCards, sort);
	    }

	    if (filteredCards.isEmpty()) {
	        throw new NoSuchElementException("No matching cards found.");
	    }

	    return filteredCards;
	}

	private boolean isValidProperty(Object value, String propertyName) {
	    switch (propertyName) {
	        case "status":
	            return cardRepository.existsByStatus((String) value);
	        case "color":
	            return cardRepository.existsByColor((String) value);
	        case "name":
	            return cardRepository.existsByName((String) value);
	        case "createdAt":
	            return cardRepository.existsByCreatedAt((LocalDateTime) value);
	        default:
	            return false;
	    }
	}

	
	public List<Card> sortCards(List<Card> cards, String sort) {
	    cards.sort((card1, card2) -> {
	        switch (sort) {
	            case "name":
	                return card1.getName().compareTo(card2.getName());
	            case "color":
	                return card1.getColor().compareTo(card2.getColor());
	            case "status":
	                return card1.getStatus().compareTo(card2.getStatus());
	            case "createdAt":
	                return card1.getCreatedAt().compareTo(card2.getCreatedAt());
	            default:
	                return 0; // No sorting
	        }
	    });

	    return cards;
	}

	public void updateCard(Long cardId, Card updatedCard) {
	    try {
	        Card card = cardRepository.findById(cardId)
	                .orElseThrow(() -> new NoSuchElementException("Card not found or user does not have access"));

	        if (updatedCard.getName() != null) {
	            card.setName(updatedCard.getName());
	        }

	        if (updatedCard.getDescription() != null) {
	            card.setDescription(updatedCard.getDescription());
	        } else {
	            card.setDescription(null); // Clear description if null
	        }

	        if (updatedCard.getColor() != null) {
	            if (isValidColor(updatedCard.getColor())) { 
	                card.setColor(updatedCard.getColor());
	            } else {
	                throw new IllegalArgumentException("Invalid color format.");
	            }
	        } else {
	            card.setColor(null); // Clear color if null
	        }

	        if (updatedCard.getStatus() != null) {
	            if (isValidStatus(updatedCard.getStatus())) { // Add status validation logic
	                card.setStatus(updatedCard.getStatus());
	            } else {
	                throw new IllegalArgumentException("Invalid status.");
	            }
	        }

	        cardRepository.save(card);
	        
	    } catch (NoSuchElementException e) {
	        String errorMessage = "Card not found or user does not have access: " + e.getMessage();
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage, e);
	    }
	}

	private boolean isValidColor(String color) {
	    return color.matches("^#([0-9a-fA-F]{6})$");
	}

	private boolean isValidStatus(String status) {
	    // Add status validation logic (e.g., check against valid statuses)
	    return Arrays.asList("To Do", "In Progress", "Done").contains(status);
	}


	
	public Card getCard(Long cardId) {
	    try {
	        return cardRepository.findById(cardId)
	                .orElseThrow(() -> new NoSuchElementException("Card not found or user does not have access"));
	    } catch (NoSuchElementException e) {
	        String errorMessage = "Card not found or user does not have access: " + e.getMessage();
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage, e);
	    }
	}

	
	public void deleteCard(User user, Long cardId) {
	    try {
	        Card card = cardRepository.findById(cardId)
	                .orElseThrow(() -> new NoSuchElementException("Card not found or user does not have access"));

	        cardRepository.delete(card);
	    } catch (NoSuchElementException e) {
	        String errorMessage = "Card not found or user does not have access: " + e.getMessage();
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage, e);
	    }
	}




}
