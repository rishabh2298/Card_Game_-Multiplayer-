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
public class DeckServiceImplementationTest {

	private Deck deck;
	private DeckService deckService;
	private Player currentPlayer;
	
	
	@BeforeEach
	public void setUpTest() {
		
		this.deck = new Deck();
		currentPlayer = new Player("Rahul", new Card[52]);
		
		this.deckService = new DeckServiceImplementation();

		// giving 5-cards initially to currentPlayer
		for(int card=0; card<5; card++) {
			currentPlayer.getCardsInHand()[currentPlayer.getIndexOfNextNewCard()] = deckService.getTopCard(deck.getDeckOfCards());
			currentPlayer.setIndexOfNextNewCard(currentPlayer.getIndexOfNextNewCard()+1);
		}
	}
	
	
	@Test
	public void testShuffle() {
		
		// calling shuffle method to shuffle all cards
		deckService.shuffle(deck.getDeckOfCards());
		
		// case-1 : if first card is same after shuffle as previous
		
		// It should not have first card = suit: Diamonds , face: Ace; after shuffle
		
		assertTrue(((deck.getDeckOfCards()[0].getSuit().equals("Diamonds") && !deck.getDeckOfCards()[0].getFace().equals("  Ace   ")) 
				|| (!deck.getDeckOfCards()[0].getSuit().equals("Diamonds")) && deck.getDeckOfCards()[0].getFace().equals("  Ace   "))
				|| (!deck.getDeckOfCards()[0].getSuit().equals("Diamonds")) && !deck.getDeckOfCards()[0].getFace().equals("  Ace   "), "Shuffling of cards fails, Please check shuffle logic");
		
		
		// It should not have last card = suit: Spades, face : Jack;  after shuffle
		
		assertTrue(((deck.getDeckOfCards()[0].getSuit().equals(" Spades ") && !deck.getDeckOfCards()[0].getFace().equals("  Jack  ")) 
				|| (!deck.getDeckOfCards()[0].getSuit().equals(" Spades ")) && deck.getDeckOfCards()[0].getFace().equals("  Jack  "))
				|| (!deck.getDeckOfCards()[0].getSuit().equals(" Spades ")) && !deck.getDeckOfCards()[0].getFace().equals("  Jack  "), "Shuffling of cards fails, Please check shuffle logic");
		
		
		/*
		 *  checking for any null values in deck of cards,
		 *  if found null values then allotment of cards to deck was failed
		 *  
		 *  correct range of valid cards = 52(total length) - 5 (alloted to current player) = 47
		 */
		
		for(int card=0; card<47; card++) {
			assertTrue(deck.getDeckOfCards()[card] != null, "There is null value in between of deckOFCards, Please check constructor of Deck Class [ Deck() ]");
		}
		
	}
	
	
	@Test
	public void testGetTopCard() {

		// Comparing top card with peek card if both are same then getTopCard is working perfectly fine
		
		Card topCard = deckService.getPeekCard(deck.getDeckOfCards());
		
		for(int validCard=deck.getDeckOfCards().length-1; validCard>=0; validCard--) {
			
			// this means it is a top card from deckOfCards
			
			if(deck.getDeckOfCards()[validCard] != null) {
				 
				assertTrue(deck.getDeckOfCards()[validCard].getSuit().equals(topCard.getSuit()) 
						&& deck.getDeckOfCards()[validCard].getFace().equals(topCard.getFace()), "Top Card and Peek Card mismatched, Check PeekCard and topCard logics");
				
				return;
			}
		}
	}
	
	
	@Test
	public void testGetPeekCard() {
		// Comparing peek card with top card from getTopCard which return topMostCard of deck
		// if both are same then getPeekCard is working perfectly fine
		
		Card peekCard = deckService.getPeekCard(deck.getDeckOfCards());
		
		for(int validCard=deck.getDeckOfCards().length-1; validCard>=0; validCard--) {
			
			// this means it is a top card from deckOfCards
			
			if(deck.getDeckOfCards()[validCard] != null) {
				 
				assertTrue(deck.getDeckOfCards()[validCard].getSuit().equals(peekCard.getSuit()) 
						&& deck.getDeckOfCards()[validCard].getFace().equals(peekCard.getFace()), "Top Card and Peek Card mismatched, Check PeekCard and topCard logics");
				
				return;
			}
		}
	}
	
	
	@Test
	public void testHasWon() {
		
		/*
		 *  This will return true only if current player cardsInHand will have no valid
		 *  cards left,
		 */
		
		assertFalse(currentPlayer.getCardsInHand()[0]==null, "Check for alloting cards to currentPlayer, as in starting of game no player can won the game");
	}
	
}
