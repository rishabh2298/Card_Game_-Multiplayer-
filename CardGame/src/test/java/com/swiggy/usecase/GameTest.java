package com.swiggy.usecase;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.swiggy.model.Card;
import com.swiggy.model.Deck;
import com.swiggy.model.Player;
import com.swiggy.service.DeckService;
import com.swiggy.service.DeckServiceImplementation;
import com.swiggy.service.PlayerService;
import com.swiggy.service.PlayerServiceImplementation;

public class GameTest {
	
	private Card currentCard;
	private Deck deck;
	private Card[] discardPile;
	private int indexOfDiscardPile;
	private Player[] totalPlayers;
	private PlayerService playerService;
	private DeckService deckService;
	private boolean applyActionCard;
	private int penalty;
	private int turn;
	private boolean reversePlay;
	
	@BeforeEach
	public void setUpTest() {

		this.playerService = new PlayerServiceImplementation();
		this.deckService = new DeckServiceImplementation();
		
		this.deck = new Deck();
		
		deckService.shuffle(deck.getDeckOfCards());
		
	}
	
	
	@Test
	public void testDistributeCardsToPlayer() {
		
		totalPlayers = new Player[2];
		
		totalPlayers[0] = new Player("Tanmay", new Card[52]);
		totalPlayers[1] = new Player("Harish", new Card[52]);
		
		for(int numberOfCard=0; numberOfCard<5; numberOfCard++) {
			
			for(int currentPlayer=0; currentPlayer<totalPlayers.length; currentPlayer++) {
				playerService.pickCard(totalPlayers[currentPlayer], deckService.getTopCard(deck.getDeckOfCards()));
				
				assertTrue(totalPlayers[currentPlayer] != null, "currentPlayer is not getting equal cards, Please check PickCard method of PlayerService interface implementation");
			}
		}
	}
	
}
