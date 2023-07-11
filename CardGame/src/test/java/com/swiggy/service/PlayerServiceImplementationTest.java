package com.swiggy.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.swiggy.model.Card;
import com.swiggy.model.Deck;
import com.swiggy.model.Player;

@TestInstance(Lifecycle.PER_CLASS)
public class PlayerServiceImplementationTest {
	
	private Player currentPlayer;
	private DeckService deckService;
	private Deck deck;
	
	
	@BeforeEach
	public void setUpTest() {
		
		deck = new Deck();
		deckService = new DeckServiceImplementation();
		
		// shuffling the deck
		deckService.shuffle(deck.getDeckOfCards());
		
		currentPlayer = new Player("Rahul", new Card[52]);
		
		// giving 5-cards initially
		for(int card=0; card<5; card++) {
			currentPlayer.getCardsInHand()[currentPlayer.getIndexOfNextNewCard()] = deckService.getTopCard(deck.getDeckOfCards());
			currentPlayer.setIndexOfNextNewCard(currentPlayer.getIndexOfNextNewCard()+1);
		}
	}
	
	
	@Test
	public void testPickCard() {
		// last valid card value should not be null
		assertTrue(currentPlayer.getCardsInHand()[currentPlayer.getIndexOfNextNewCard()-1]!=null, "next index to pick card is not correct");
		
		// index of next new card should be null
		assertTrue(currentPlayer.getCardsInHand()[currentPlayer.getIndexOfNextNewCard()]==null, "next index value is not correct");
	}
	
	
	@Test
	public void testThrowCardOnDiscardPile() {
		
		//case-1: if throwing last card from Card[] cardsInHand
		
		// check if nextIndexValue is null (true)
		assertTrue(currentPlayer.getCardsInHand()[currentPlayer.getIndexOfNextNewCard()]==null,"next index value is wrong, new pickCard position mismatch");
		
		// check if last index of valid card is not null (true);
		assertTrue(currentPlayer.getCardsInHand()[currentPlayer.getIndexOfNextNewCard()-1] != null, "position of last valid card in playerHand is mismatched");
	
		
		// case-2 : if throwing card from middle of valid cards arrays
		
		if(currentPlayer.getCardsInHand()[currentPlayer.getIndexOfNextNewCard()] == null) {
			
			int sizeOfValidCards = currentPlayer.getIndexOfNextNewCard();
			
			for(int card=0; card<sizeOfValidCards; card++) {
				
				// checking if there is null values in between
				// if not null, means valid cards are available to throw
				
				assertTrue(currentPlayer.getCardsInHand()[card] != null, "There is null values in between of valid cards of player hand");
			}	
		}
	}
	
	
	@Test
	public void testHasWon() {
		
		// this should be false as in starting of game every player has some valid cards
		assertFalse(currentPlayer.getCardsInHand()[0] == null, "Player does not get initials cards to start game");
		
	}
	
}
