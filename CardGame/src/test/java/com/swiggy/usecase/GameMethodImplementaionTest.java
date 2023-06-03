package com.swiggy.usecase;

import org.junit.jupiter.api.BeforeEach;

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
	
	
	@BeforeEach
	public void setUpTest() {
		
		this.playerService = new PlayerServiceImplementation();
		this.deckService = new DeckServiceImplementation();
		
		this.deck = new Deck();
		
		deckService.shuffle(deck.getDeckOfCards());
		
		totalPlayers = new Player[2];
		
		totalPlayers[0] = new Player("Tanmay", new Card[52]);
		totalPlayers[1] = new Player("Harish", new Card[52]);
		
		for(int player=0; player<totalPlayers.length; player++) {
			
			// giving 5-cards initially to eachPlayer
			
			for(int card=0; card<5; card++) {
				totalPlayers[player].getCardsInHand()[totalPlayers[player].getIndexOfNextNewCard()] = deckService.getTopCard(deck.getDeckOfCards());
				totalPlayers[player].setIndexOfNextNewCard(totalPlayers[player].getIndexOfNextNewCard()+1);
			}
		}
	}
	
}
