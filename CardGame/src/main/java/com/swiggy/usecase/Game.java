package com.swiggy.usecase;

import java.util.Scanner;

import com.swiggy.model.Card;
import com.swiggy.model.Deck;
import com.swiggy.model.Player;
import com.swiggy.service.DeckService;
import com.swiggy.service.DeckServiceImplementation;
import com.swiggy.service.PlayerService;
import com.swiggy.service.PlayerServiceImplementation;

public class Game {
	
	private Card currentCard;
	private Deck deck;
	private Card[] discardPile;
	private int indexOfDiscardPile;
	private Player[] totalPlayers;
	private PlayerService playerService;
	private DeckService deckService;
	private int penalty;
	private int playerTurn;
	private boolean reversePlay;
	private Scanner scanner;
	

	// setting initial game-setup
	
	public Game() {
		
		// to get access to required methods
		this.playerService = new PlayerServiceImplementation();
		
		this.deckService = new DeckServiceImplementation();
		
		// creating Card[] deckOfCards as constructor built
		deck = new Deck();
		
		// get starting card before first player chance.
		currentCard = getInitialCard();
		
		// shuffle before distributing to each player
		deckService.shuffle(deck.getDeckOfCards());
		
		// maximum possible size of discard pile
		discardPile = new Card[52];
		
		// allotting first card to discard pile to start the game
		discardPile[indexOfDiscardPile++] = currentCard;
		
		scanner = new Scanner(System.in);
		
		
		// Number Of player want's to play (as Multiplayer game);
		
		System.out.println("Enter Number of players");
		int numberOfPlayer = scanner.nextInt();
		
		while(numberOfPlayer<2 || numberOfPlayer>4) {
			System.out.println("Player must be 2,3 or 4");
			System.out.println("Please select valid number of players");
			numberOfPlayer = scanner.nextInt();
		}
		
		// storing players into Player[] totalPlayer

		totalPlayers = new Player[numberOfPlayer];
		
		for(int player=0; player<numberOfPlayer; player++) {
			System.out.println("Enter player-"+(player+1)+" name");
			String playerName = scanner.next();
			totalPlayers[player] = new Player(playerName, new Card[52]);
		}
		
		// Distributing 5-cards initially to each players
		distributeCardsToPlayer();
		
	}
	
	
	// starting of game (take care of current player both in normal/reverse case)
	
	public void startGame() {
		
		playerTurn = totalPlayers.length;
		
		while(!gameOver(totalPlayers)) {
			
		}
		
	}
	
	
	private boolean gameOver(Player[] totalPlayers) {
		
		for(Player currentPlayer : totalPlayers) {
			
			if(playerService.hasWon(currentPlayer)) {

				System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
				System.out.println("Congratulations" +" "+ currentPlayer.getPlayerName());
				System.out.println("You have won the GAME.......");
				System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
				
				scanner.close();
				
				return true;
			}
		}
		
		return false;
	}

	
	private Card getInitialCard() {
		// shuffle until top will be number card
		// to start the game
		
		Card initialCard = deckService.getPeekCard(deck.getDeckOfCards());
		
		while(initialCard.isSpecialCard()) {
			deckService.shuffle(deck.getDeckOfCards());
			initialCard = deckService.getPeekCard(deck.getDeckOfCards());
		}
		
		initialCard = deckService.getTopCard(deck.getDeckOfCards());
		
		return initialCard;
	}
	
	
	// distributing 5 cards to each palyers
	
	private void distributeCardsToPlayer() {
		
		for(int numberOfCard=0; numberOfCard<5; numberOfCard++) {
			
			for(int currentPlayer=0; currentPlayer<totalPlayers.length; currentPlayer++) {
				playerService.pickCard(totalPlayers[currentPlayer], deckService.getTopCard(deck.getDeckOfCards()));
			}
		}
		
	}
	
}
