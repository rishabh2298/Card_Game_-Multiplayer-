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
	private boolean applyActionCard;
	private int penalty;
	private int turn;
	private boolean reversePlay;
	private Scanner scanner;
	private GameMethod gameMethod;

	// setting initial game-setup
	
	public Game() {
		
		// initializing game method object to perform card-rules methods
		this.gameMethod = new GameMethodImplementation();
		
		// to get access to required methods
		this.playerService = new PlayerServiceImplementation();
		
		this.deckService = new DeckServiceImplementation();
		
		// creating Card[] deck.getDeckOfCards() as constructor built
		deck = new Deck();
		
//		deck.getDeckOfCards() = deck.getdeck.getDeckOfCards()();
		
		// shuffle before distributing to each player
		deckService.shuffle(deck.getDeckOfCards());
		
		// get starting card before first player chance.
		currentCard = gameMethod.getInitialCard(deck);

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
		gameMethod.distributeCardsToPlayer(deck, totalPlayers);
		
	}
	
	
	// starting of game (take care of current player both in normal/reverse case)
	
	public void startGame() {
		
		// this will help to start with first player always
		turn = totalPlayers.length;
		
		while(!gameMethod.gameOver(totalPlayers)) {
			
			System.out.println();
			System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooopoooooo");
			System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooopoooooo");
			System.out.println();
			
			int playerTurn = turn % totalPlayers.length;
			playGame(totalPlayers[playerTurn]);
			
			// if turn(0)-- is in case of (reverse play);
			if(turn == -1) {
				turn = totalPlayers.length - 1;
			}
			
			// if skip one player (ACE) when (turn=0) to turn=-2 from method call
			else if(turn == -2) {
				turn = totalPlayers.length - 2;
			}
			
			/*
			 *  if turn was skipped from turn=0 to turn=-2 (due to ACE)
			 *  and at same time (reversePlay=true) then turn-- will make
			 *  turn = -3;
			 */
			else if(turn == -3) {
				if(totalPlayers.length == 2) {
					turn = 0;	// for 1st player as (3 skipped - 2,1,2)
				}
				
				// for greater than 2 player
				else {	
					turn = totalPlayers.length - 3;
				}
			}
			
			System.out.println();
			System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooopoooooo");
			System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooopoooooo");
			System.out.println();
		}
		
	}
	
	
	// this method contains main process of game
	
	private void playGame(Player currentPlayer) {
		
		// if deck.getDeckOfCards() are empty then game is draw
		
		if(deckService.isEmpty(deck.getDeckOfCards())){
			printBoundry();
			System.out.println("Match is Draw, As deck.getDeckOfCards() is finished.......");
			printBoundry();
			
			System.out.println("Want to start new Game? (y/n)");
			String choice = scanner.next();
			
			if(choice.equalsIgnoreCase("y")) {
				new Game().startGame();
				return;
			}
			else {
				System.out.println("Thankyou to play...........");
				return;
			}
		}
		
		
		// to design output much readable
		printBoundry();
		
		System.out.println(currentPlayer.getPlayerName()+" It's your turn, Current Card on Discard Pile is :-\n"+ currentCard);
		
		printBoundry();
		
		gameMethod.showCardsToCurrentPlayer(currentPlayer, totalPlayers);
		
		printBoundry();
		

		/*
		 *  if current card is any Action Card (Ace, King, Queen, Jack) 
		 */
		
		if(currentCard.isSpecialCard()) {
		
			if(currentCard.getFace().equals("  Ace   ")) {
				
				// Your turn is get Skipped
				if(reversePlay) {
					turn--;
				}
				else {
					turn++;
				}
				
				// for second next player this will be (true)
				if(applyActionCard) {
					
					gameMethod.nextCardIfCurrentCardIsActionCard(currentPlayer, currentCard, discardPile, indexOfDiscardPile, deck);
					
					this.applyActionCard = false;
					
					// chance of next player
					return;
				}
				
				this.applyActionCard = true;
				
				// Chance of next player
				return;
			}
			else if(currentCard.getFace().equals("  King  ")) {
				
				if(applyActionCard) {
					applyActionCard = false;
					gameMethod.nextCardIfCurrentCardIsActionCard(currentPlayer, currentCard, discardPile, indexOfDiscardPile, deck);
					
					if(reversePlay) turn--;
					else turn++;
					
					return;
				}
				
				// reverse the flow of playerTurns
				if(reversePlay) {
					reversePlay = false;
					turn = turn + 2;
				}
				else {
					reversePlay = true;
					turn = turn - 2;
				}
				
				this.applyActionCard = true;
				
				// Chance of next player;
				return;
			}
			else if(currentCard.getFace().equals(" Queen  ")) {
				
				// draw 2 cards from deck.getDeckOfCards() (penalty = 2)
				penalty = 2;
				
				/*
				 * current player draw 2 cards and throw a valid(non-action) card if available
				 * otherwise his chance get skipped as penalty got fined already;
				 * 
				 * same applies for next player until valid card found
				 */
				gameMethod.penaltyGotApplied(currentPlayer, penalty, deck, reversePlay, turn, currentCard, discardPile, indexOfDiscardPile);
				
				return;
			}
			else if(currentCard.getFace().equals("  Jack  ")) {
				
				// draw 4 cards from deck.getDeckOfCards() (penalty = 4)
				penalty = 4;
				
				gameMethod.penaltyGotApplied(currentPlayer, penalty, deck, reversePlay, turn, currentCard, discardPile, indexOfDiscardPile);
				
				return;
			}

		}
		
		
		// for Normal current card
		selectAnyNextCard(currentPlayer);
		
		if(reversePlay) turn--;
		else turn++;
	}
	
	
	private void selectAnyNextCard(Player currentPlayer){

		System.out.println("Choose card to throw on Discard Pile");
		int selectedCard = scanner.nextInt();
		selectedCard--;
		
		while(selectedCard < 0 || selectedCard>=currentPlayer.getIndexOfNextNewCard()) {
			System.out.println("Please Enter valid card Number");
			selectedCard = scanner.nextInt();
			selectedCard--;
		}
		
		if(!gameMethod.hasCounterCard(currentPlayer, currentCard)) {
			
			/*
			 *  As not have any valid card to throw on discard Pile, So have to draw new card
			 *  from deckOfCards
			 */

			gameMethod.drawACardFromDeck(currentPlayer, deck);
			
			// next player chance after drawn a card
			return;
		}
		
		// until select valid cards from hand
		
		while(!gameMethod.isValidCard(currentPlayer, selectedCard, currentCard)){
			System.out.println("Choose valid card to throw on Discard Pile,");
			selectedCard = scanner.nextInt();
			selectedCard--;
			
			while(selectedCard < 0 || selectedCard >= currentPlayer.getIndexOfNextNewCard()) {
				System.out.println("Please Enter valid card Number");
				selectedCard = scanner.nextInt();
				selectedCard--;
			}
		}
		
		// updating current card for next player
		
		currentCard = playerService.throwCardOnDiscardPile(currentPlayer, selectedCard);
		
		discardPile[indexOfDiscardPile++] = currentCard;
	}
	

	// To print boundary
	// This will make output more vision smooth
	
	private void printBoundry() {
		System.out.println("#############################################################################################################");
	}

}
