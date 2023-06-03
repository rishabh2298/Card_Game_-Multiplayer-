package com.swiggy.usecase;

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
	
	
	@Test
	public void testGameOver() {
		
		// this is a private method inside game class
		// this method will check if any of the palyer has won the game or not
		
		for(int player=0; player<totalPlayers.length; player++) {
			
			for(int card=0; card<totalPlayers[player].getCardsInHand().length; card++) {
				
				// this will execute if 1st and next card of player is null
				// means he is the winner
				if(card==0) {
					Assertions.assertNotNull(totalPlayers[player].getCardsInHand()[card], "This player must have winner, but there is some logic error in cardInHands implementation of player");
					Assertions.assertNotNull(totalPlayers[player].getCardsInHand()[card+1], "This player must have winner, but there is some logic error in cardInHands implementation of player");
				}
				// only not-valid cards available to can break out this inner loop
				else {
					break;
				}
			}
		}
	}
	
}
