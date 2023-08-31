package com.kelvin.cardsproject.controllers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kelvin.cardsproject.entities.Card;
import com.kelvin.cardsproject.entities.User;
import com.kelvin.cardsproject.services.CardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/cards")
public class CardController {
	
	private final  CardService cardService;
	
	public CardController(CardService cardService) {
		
		this.cardService=cardService;
		
	

}
	
	 // Endpoint to create a card for the authenticated user
	 @Operation(
	    		description = "This endpoint allows you to create a list of cards for authenticated user",
	    		summary = "End point to create cards for authenticated user"
	    				
	    		     
	    		)
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Successfully created cards"), 
	            @ApiResponse(responseCode = "403", description = "Wrong input values for card")
	    }
	            )
    @PostMapping("/create-cards")
    public String createCards(@RequestBody List<Card> card, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        cardService.createCards(card,user);
        return "Card created successfully";
    }
    
    @Operation(
    		description = "This should return the list of cards for authenticated user"
    				+ " and if user is the admin then returns all cards for all members",
    		summary = "End point to get cards for authenticated user returns all cards if authenticated user is admin"
    				
    		     
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"), 
            @ApiResponse(responseCode = "403", description = "Not found - The cards not found")
    }
            )
    

    @GetMapping("/get/member-cards")
    public List<Card> getCardsForUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Card> cards = cardService.getCardsForUser(user);
        System.out.println(Arrays.asList(cards));
        return cards;
    }
    
    @Operation(
    		description = "Returns a single requested card",
    		summary = "End point to get specific card for authenticated user by passing in the card id as a request Param"
    				
    		     
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"), 
            @ApiResponse(responseCode = "403", description = "Not found - card not found")
    }
            )
    @GetMapping("/get-card/{cardId}")
    public Card getCard(
            Authentication authentication,
            @PathVariable Long cardId) {
        User user = (User) authentication.getPrincipal();
        Card card = cardService.getCard(cardId);
        return card;
    }

    @Operation(
    		description = "This should return the searched card item with the user providing a filtering option of either color"
    				+ " name,status,date of creation. You should also provide a sort criteria ",
    		summary = "End point to search for cards with filter options:name,color,status and date of creation and also to include "
    				+ " how the results will be sorted by including a sort value in the request param"
    				
    		     
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"), 
            @ApiResponse(responseCode = "403", description = "Not found - Check console and your input values and try again")
    }
            )
    @GetMapping("/searchand-sortcards")
    public List<Card> searchAndSortCards(
            Authentication authentication,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt,
            @RequestParam(required = false) String sort) {
        User user = (User) authentication.getPrincipal();
        List<Card> filteredCards = cardService.searchAndSortCards(user, status, color, name, createdAt,sort);
        return filteredCards;
    }
    
    
    @Operation(
    		description = "Should successfully update the card, if the color and description fields are not provided then "
    				+ " the description and color is cleared out and becomes null",
    		summary = "End point to update card for authenticated user by passing the card id as a request param"
    				
    		     
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"), 
            @ApiResponse(responseCode = "403", description = "Not found -  card not found")
    }
            )
    @PutMapping("/update-card/{cardId}")
    public String updateCard(
            Authentication authentication,
            @PathVariable Long cardId,
            @RequestBody Card card) {
        User user = (User) authentication.getPrincipal();
        cardService.updateCard(cardId,card);
        return "Card updated successfully";
    }

    @Operation(
    		description = "This should delete card for authenticated user",
    		summary = "End point to delete card for authenticated user by passing the card id as a request param"
    				
    		     
    		)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"), 
            @ApiResponse(responseCode = "403", description = "Not found - The cards not found")
    }
            )
    @DeleteMapping("/delete-card/{cardId}")
    public String deleteCard(
            Authentication authentication,
            @PathVariable Long cardId) {
        User user = (User) authentication.getPrincipal();
        cardService.deleteCard(user, cardId);
        return "Card deleted successfully";
    }


}
