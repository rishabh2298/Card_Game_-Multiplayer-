package com.swiggy.usecase;

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
	
	
	
	
}
