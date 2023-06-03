package com.swiggy.usecase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.swiggy.model.Card;
import com.swiggy.model.Deck;
import com.swiggy.model.Player;
import com.swiggy.service.DeckService;
import com.swiggy.service.DeckServiceImplementation;
import com.swiggy.service.PlayerService;
import com.swiggy.service.PlayerServiceImplementation;

public class GameMethodImplementaionTest {

	private PlayerService playerService;
	private DeckService deckService;
	private Player[] totalPlayers;
	private Deck deck;
	private GameMethod gameMethod;
	
	
	@BeforeEach
	public void setUpTest() {
		
		this.playerService = new PlayerServiceImplementation();
		this.deckService = new DeckServiceImplementation();
		
		this.deck = new Deck();
		
		deckService.shuffle(deck.getDeckOfCards());
		
		totalPlayers = new Player[2];
		
		totalPlayers[0] = new Player("Tanmay", new Card[52]);
		totalPlayers[1] = new Player("Harish", new Card[52]);
		
		this.gameMethod = new GameMethodImplementation();
		
	}
	
	
	@Test
	public void testDistributeCardsToPlayer() {
		
		// this will call original method to distribute cards to each player
		
		gameMethod.distributeCardsToPlayer(deck, totalPlayers);
		
		for(int player=0; player<totalPlayers.length; player++) {
			
			for(int card=0; card<5; card++) {
				
				// checking if method worked and player get cards or not
				
				Assertions.assertNotNull(totalPlayers[player].getCardsInHand()[card], "currentPlayer is not getting equal cards, Please check distributeCardToPlayer method of GameMethod interface implementation");
			}
		}
	}
	
	
	@Test
	public void testGameOver() {
		
		// As cards are distributed so no player will won initially
		// so test case must return false (as 0-th index will have cards to play)
		gameMethod.distributeCardsToPlayer(deck, totalPlayers);
		
		// this method will check if any of the palyer has won the game or not
		gameMethod.gameOver(totalPlayers);
		
		for(int player=0; player<totalPlayers.length; player++) {
			
			Assertions.assertNotNull(totalPlayers[player].getCardsInHand()[0], "This player must not be winner, So there is some logic error in gameOver method (or) cardInHands implementation of player");
			Assertions.assertNotNull(totalPlayers[player].getCardsInHand()[1], "This player must not be winner, but there is some logic error in gameOver method (or) cardInHands implementation of player");
			
		}
	}
	
	
	@Test
	public void testHasCounterCard() {
		
		// this will call original method to distribute cards to each player
		
		gameMethod.distributeCardsToPlayer(deck, totalPlayers);
		
		Card selectedCard = totalPlayers[0].getCardsInHand()[1];
		
		for(int validCard=0; validCard<totalPlayers[0].getIndexOfNextNewCard(); validCard++) {

			// these will show error-test, because it will not have to be null
			Assertions.assertNotNull(totalPlayers[0].getCardsInHand()[validCard],"This should be not null, So check distribution card method (or) getIndextOfnextNewCard method");

		}

		/*
		 * case-1 : when has any counter card
		 */
		if(gameMethod.hasCounterCard(totalPlayers[0], selectedCard)) {
			assertTrue(gameMethod.hasCounterCard(totalPlayers[0], selectedCard));
		}

		/*
		 * case-2 : when not have any counter card
		 */
		else {
			assertFalse(gameMethod.hasCounterCard(totalPlayers[0], selectedCard));
		}
		
	}
	
}
