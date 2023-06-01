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
		
		// this will help to start with first player always
		turn = totalPlayers.length;
		
		while(!gameOver(totalPlayers)) {
			
			int playerTurn = turn % totalPlayers.length;
			playGame(totalPlayers[playerTurn]);
			
//			if(reversePlay) {
//				turn--;
//			}
//			else {
//				turn++;
//			}
			
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
		}
		
	}
	
	
	// this method contains main process of game
	
	private void playGame(Player currentPlayer) {
		
		// if deckOfCards are empty then game is draw
		
		if(deckService.isEmpty(deck.getDeckOfCards())){
			printBoundry();
			System.out.println("Match is Draw, As deckOfCards is finished.......");
			printBoundry();
			
			System.out.println("Want to start new Game? (y/n)");
			String choice = scanner.next();
			
			if(choice.equalsIgnoreCase("y")) {
				deck = new Deck();
				startGame();
				return;
			}
			else {
				System.out.println("Thankyou to play...........");
				return;
			}
		}
		
		
		// to design output much readable
		printBoundry();
		
		System.out.println(currentPlayer+"It's you turn, Current Card on Discard Pile is :-"+ currentCard);
		
		printBoundry();
		
		showCardsToCurrentPlayer(currentPlayer);
		
		printBoundry();
		
		
		// if there is penalty then current player has to draw cards, and his chance got skipped
		// In case of currentCard = Jack, Queen
		
		if(penalty != 0) {
//			System.out.println("Since current card is Action Card, So you have to draw="+penalty+" from deck of cards");
//
//			while(penalty-- >0) {
//				
//				Card newCard = deckService.getTopCard(deck.getDeckOfCards());
//				playerService.pickCard(currentPlayer, newCard);
//				
//				System.out.println("You have picked card :-"+ newCard);
//				confirmToContinue();
//				
//				// while taking cards from deckOFCards it get's empty, then
//				
//				if(deckService.isEmpty(deck.getDeckOfCards())){
//					printBoundry();
//					System.out.println("Match is Draw, As deckOfCards is finished.......");
//					printBoundry();
//					
//					System.out.println("Want to start new Game? (y/n)");
//					String choice = scanner.next();
//					
//					if(choice.equalsIgnoreCase("y")) {
//						deck = new Deck();
//						startGame();
//						return;
//					}
//					else {
//						System.out.println("Thankyou to play...........");
//						return;
//					}
//				}
//			}
//			
//			/*
//			 * Throwing valid card for next player as he/she can't throw same card/any action cards
//			 * So, Checking it has validCard to play then provide chance, otherwise
//			 * skipped his chance
//			 */
////
////			if(!checkForDifferentValidCard(currentPlayer)){
////				System.out.println("You don't have valid card to play, So your chance is skipped");
////			}
////			else {
////				System.out.println("Choose card to throw on Discard Pile");
////				int selectedCard = scanner.nextInt();
////				
////				while(isAnyActionCard(currentPlayer, selectedCard) || !isDifferentSuitCard(currentPlayer, selectedCard)){
////					System.out.println("Choose valid card to throw on Discard Pile,\nCan't be action/different suit card");
////					selectedCard = scanner.nextInt();
////				}
////				
////				// updating current card for next player
////				
////				currentCard = playerService.throwCardOnDiscardPile(currentPlayer, selectedCard);
////				
////				discardPile[indexOfDiscardPile++] = currentCard;
////				
////			}
//			
//			if(reversePlay) {
//				turn--;
//			}
//			else {
//				turn++;
//			}
//			
//			// need to stop, for providing next chance to next player
//			return;
		}
		

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
					
					nextCardIfCurrentCardIsActionCard(currentPlayer);
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
//					selectNextCard(currentPlayer);
				}
				
				// reverse the flow of playerTurns
				if(reversePlay) {
					reversePlay = false;
					turn++;
				}
				else {
					reversePlay = true;
					turn--;
				}
				
				this.applyActionCard = true;
				
				// Chance of next player;
				return;
			}
			else if(currentCard.getFace().equals(" Queen  ")) {
				
				// draw 2 cards from deckOfCards (penalty = 2)
				
				penaltyGotApplied(currentPlayer);
			}
			else if(currentCard.getFace().equals("  Jack  ")) {
				
			}

		}
		
	}
	
	
	private void penaltyGotApplied(Player currentPlayer) {
		System.out.println("Since current card is Action Card, So you have to draw="+penalty+" from deck of cards");

		while(penalty-- >0) {
			
			Card newCard = deckService.getTopCard(deck.getDeckOfCards());
			playerService.pickCard(currentPlayer, newCard);
			
			System.out.println("You have picked card :-"+ newCard);
			confirmToContinue();
			
			// while taking cards from deckOFCards it get's empty, then
			
			if(deckService.isEmpty(deck.getDeckOfCards())){
				printBoundry();
				System.out.println("Match is Draw, As deckOfCards is finished.......");
				printBoundry();
				
				System.out.println("Want to start new Game? (y/n)");
				String choice = scanner.next();
				
				if(choice.equalsIgnoreCase("y")) {
					deck = new Deck();
					startGame();
					return;
				}
				else {
					System.out.println("Thankyou to play...........");
					return;
				}
			}
		}
		
		/*
		 * Throwing valid card for next player as he/she can't throw same card/any action cards
		 * So, Checking it has validCard to play then provide chance, otherwise
		 * skipped his chance
		 */
		
		nextCardIfCurrentCardIsActionCard(currentPlayer);
		
		if(reversePlay) {
			turn--;
		}
		else {
			turn++;
		}
		
		// need to stop, for providing next chance to next player
		return;
	}
	
	
	/*
	 * Throwing valid card for next player as he/she can't throw same card/any action cards
	 * So, Checking it has validCard to play then provide chance, otherwise
	 * skipped his chance
	 */
	
	private void nextCardIfCurrentCardIsActionCard(Player currentPlayer) {

		if(!checkForDifferentValidCard(currentPlayer)){
			System.out.println("You don't have valid card to play, So your chance is skipped");
		}
		else {
			System.out.println("Choose card to throw on Discard Pile");
			int selectedCard = scanner.nextInt();
			
			while(isAnyActionCard(currentPlayer, selectedCard) || !isDifferentSuitCard(currentPlayer, selectedCard)){
				System.out.println("Choose valid card to throw on Discard Pile,\nCan't be action/different suit card");
				selectedCard = scanner.nextInt();
			}
			
			// updating current card for next player
			
			currentCard = playerService.throwCardOnDiscardPile(currentPlayer, selectedCard);
			
			discardPile[indexOfDiscardPile++] = currentCard;
			
		}
	}
	
	
	private boolean checkForDifferentValidCard(Player currentPlayer) {

		for(Card card : currentPlayer.getCardsInHand()) {
			
			if(card == null) {
				break;
			}
			
			// it might be action card
			if(card.getSuit().equals(currentCard.getSuit())) {
				if(!card.isSpecialCard()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	// return true if selected card is different suit card
	
	private boolean isDifferentSuitCard(Player currentPlayer, int selectedCard) {
		
		if(currentPlayer.getCardsInHand()[selectedCard].getSuit().equals(currentCard.getSuit()))
			return false;
		
		return true;
	}
	
	/*
	 *  return true, if any of the actions cards (Ace, Jack, Queen, King) Or null value
	 *  return false, if not an action card
	 */
	
	private boolean isAnyActionCard(Player currentPlayer, int selectedCard) {

		if(currentPlayer.getCardsInHand()[selectedCard].isSpecialCard())
			return true;
		else if(currentPlayer.getCardsInHand()[selectedCard] == null)
			return true;
		
		return false;
	}
	
	
	// This will print only cards of current player
	
	private void showCardsToCurrentPlayer(Player currentPlayer) {
		
		for(Player player : totalPlayers) {
			
			if(player.getPlayerId() == currentPlayer.getPlayerId()) {
				playerService.showCurrentPlayerCards(currentPlayer);
			}
			else {
				playerService.hideOtherPlayerCards(player);
			}
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
	
	
	private void confirmToContinue() {
		System.out.println("Enter (any key) to continue");
		scanner.next();
	}
	
	
	private void printBoundry() {
		System.out.println("#############################################################################################################");
	}

}
