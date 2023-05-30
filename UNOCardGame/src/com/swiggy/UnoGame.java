package com.swiggy;

import java.util.ArrayList;
import java.util.Scanner;

public class UnoGame {
	
	private UnoCard currentCard;
	private UnoDeck deckOfCards;
	private ArrayList<UnoCard> cardPile;
	private ArrayList<UnoPlayer> noOfPlayers;
	private int penalty;
	private boolean drawCards;
	private int turn;			// for player to give chance to play
	private boolean reversePlay;
	private Scanner scanner;
	
	
	
	// setting initial game-setup
	
	public UnoGame() {

		// create deck of cards (108) as constructor builts
		deckOfCards = new UnoDeck();
		
		// select normal card to start with;
		currentCard = getInitialCard();
		
		// shuffles the deck before distributing to each player;
		deckOfCards.shuffle();

		
		cardPile = new ArrayList<>();
		cardPile.add(currentCard);

		
		scanner = new Scanner(System.in); 

		// No of player want's to play (Multi-Player)
		System.out.println("Enter no of player wants to play");
		int totalPlayers = scanner.nextInt();
		
		while(totalPlayers < 2 || totalPlayers >6) {
			System.out.println("Player can't be less than 2 or greater than 6");
			System.out.println("Please select valid palyer numbers");
			totalPlayers = scanner.nextInt();
		}
		
		noOfPlayers = new ArrayList<>(totalPlayers);
		
		for(int i=0; i<totalPlayers; i++) {
			System.out.println("Enter player Name");
			String name = scanner.next();
			noOfPlayers.add(new UnoPlayer(name));
		}
		
		
		/* In starting of game it move in forward direction 
		*/

		reversePlay = false;
		
		
		// distribute cards to player equally;
		
		distribuiteCardsToPlayer();
		
	}
	
	
	
	// starting of game (take care of current player both in normal/reverse case)
	
	public void startGame() {
		
		// so that always game-start's with 1st player
		// this method will give fair chance to every player
		
		turn = noOfPlayers.size();
		
		while(!gameOver(noOfPlayers)) {
			
			int index = turn % noOfPlayers.size();
			playGame(noOfPlayers.get(index));
			
			if(reversePlay) {
				turn--;
			}
			else {
				turn++;
			}
			
			// while turn-- in case of (reverse play);
			if(turn == -1) {
				turn = noOfPlayers.size() - 1;
			}
			else if(turn == -2) {	// if skip played and current turn is 0 then to avoid (0-2 = -2)
				turn = noOfPlayers.size() - 2;
			}
			
		}
		
	}
	
	
	
	// this method contains main process of game
	
	private void playGame(UnoPlayer currentPlayer) {
		
		printBoundry();
		
		System.out.println(currentPlayer.getPlayerName() + " It's your turn. Current card on Card-Pile is :-\n" + currentCard);
		
		printBoundry();
		
		showBoardToCurrentPlayer(currentPlayer);
		
		printBoundry();

		
		// if previous player has used wild +4 card
		
		if(penalty == 4) {
			
			System.out.println("Since you have penalty so your chance is skipped and you have to take 4 cards from deck");

			for(int i=0; i<penalty; i++) {
				
				UnoCard newCard = deckOfCards.getTopCard();
				currentPlayer.pickCard(newCard);
				System.out.println("You have picked : " + newCard);
				
				waitToConfirm();
				
				if(deckOfCards.isEmpty()) {
					deckOfCards = new UnoDeck(cardPile);
					cardPile.clear();
				}
			}
			
			drawCards = false;
			penalty = 0;
			
			return;
			
		}

		
		/*
		 * if current card is any special
		 */
		
		if(currentCard.isSpecialCard() && !haveAnyCounterCard(currentPlayer)) {
		
			// card = Reverse || draw_two || Skip
			if(!currentCard.getColor().equals("wild")) {
				
				if(currentCard.getSpecialCardValue().equals(" Skip ")) {
					
					// you got skipped;

					return;
				}
				else if(currentCard.getSpecialCardValue().equals("Reverse")) {
					
					// this will take to previous player from startGame() method
					
					if(this.reversePlay) {
						this.reversePlay = false;
					}
					else {
						this.reversePlay = true;
					}
					return;
				}
				// draw_two card for yourself
				else {
					// means you have to draw two cards;
					drawCards = true;
					penalty = 2;
				}
				
			}
			
			// Any of 8-wild cards
			
			else {
				
				if(currentCard.getSpecialCardValue().equals("card")) {
					// it will go down and current player can choose another card from is't hand_Deck;
				}
				else {	// for wild + draw_four;
					drawCards = true;
					penalty = 4;
				}
			}
			
		}
		else if(currentCard.isSpecialCard() && haveAnyCounterCard(currentPlayer)) {
			/*
			 * If current palyer also used wild + 4
			 */
			penalty += 4;
		}
		
		
		/*
		 * in case of special cards
		 * if penalaty is 4 then next player will have this
	     * and current player will have chance to throw one more card
		 */
		
		if(drawCards && penalty != 4) {

			for(int i=0; i<penalty; i++) {
				
				UnoCard newCard = deckOfCards.getTopCard();
				currentPlayer.pickCard(newCard);
				System.out.println("You have picked : " + newCard);
				
				if(deckOfCards.isEmpty()) {
					deckOfCards = new UnoDeck(cardPile);
					cardPile.clear();
				}
			}
			
			drawCards = false;
			penalty = 0;
			
			return;
		}
		
		
		
		/*
		 * if current card is not having same value card, same color
		 * card, any special cards
		 * 
		 * if will run until a valid card is not added to current player cardDeck
		 */
		
		if(!hasSameColorCard(currentPlayer) && !hasSameValueCard(currentPlayer) && !hasSpecialCard(currentPlayer)) {
			System.out.println("You don't have valid card, So pick from top of Deck");
			
			UnoCard newCard = null;
			
			while(penalty!=4 && !hasSameColorCard(currentPlayer) && !hasSameValueCard(currentPlayer) && !hasSpecialCard(currentPlayer)) {
			
				newCard  = deckOfCards.getTopCard();
				currentPlayer.pickCard(newCard);
				System.out.println("You took : " + newCard);
				
				waitToConfirm();
				
				if(deckOfCards.isEmpty()) {
					deckOfCards = new UnoDeck(cardPile);
					cardPile.clear(); 
				}
			}
			
			System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
			
			System.out.println("Now you recived a valid card");
			
			System.out.println("Your cards :");
			
			currentPlayer.showCards();
			
			System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		}
		
		
		
		// current player choose card from it's handCards
		
		System.out.println("Please choose card");
		int index = scanner.nextInt();
		
		waitToConfirm();
		
		while(!isValidCard(currentPlayer, index-1)) {
			
			System.out.println("Invalid Pick, Please choose correct card");
			index = scanner.nextInt();
			
			waitToConfirm();
		}
		
		index--;
		
		UnoCard playedCard = currentPlayer.throwCardOnPile(index);
		
		
		cardPile.add(playedCard);
		
		// top card on pile
		// work as previous card for next player
		
		currentCard = playedCard;
		
		currentPlayer.sayUno();
		
		if(currentCard.isSpecialCard()) {
			
			if(currentCard.getSpecialCardValue().equals("Reverse")){

				if(this.reversePlay) {
					this.reversePlay = false;
				}
				else {
					this.reversePlay = true;
				}
			}
		}
		

		/*
		 * this will help for current palyer to choose another card
		 * if he/she has any wild-card (or) wild-draw_four card
		 */
		
		if(currentCard.getColor().equals("wild")) {
			if(reversePlay) {
				turn++;
			}
			else {
				turn--;
			}
		}
		
		
		// making card pile to new deckOfCards
		// replenishing		if deck isEmpty()
		
		if(deckOfCards.isEmpty()) {
			deckOfCards = new UnoDeck(cardPile);
			cardPile.clear();
		}
			
	}
	
	
	// to check if have any counter card to play
	// in case of currentCard = any special card
	
	private boolean haveAnyCounterCard(UnoPlayer currentPlayer) {
		
		for(UnoCard card : currentPlayer.getPlayerCards()) {
			
			if(card.isSpecialCard()) {
				if(card.getSpecialCardValue().equals("draw_four")) 
					return true;
			}
		}
		
		return false;
	}
	
	
	
	private boolean isValidCard(UnoPlayer currentPlayer, int index) {
		/*
		 * To be valid card either has same value (or) be any special card (or) has same color
		 */
		
		if(index >= 0  && index < currentPlayer.getPlayerCards().size()) {
			
			if(currentPlayer.getPlayerCards().get(index).getColor().equals(currentCard.getColor())) {
				return true;
			}
			else if(currentPlayer.getPlayerCards().get(index).isSpecialCard()) {
				return true;
			}
			else if(currentPlayer.getPlayerCards().get(index).getValue() == currentCard.getValue()) {
				return true;
			}
			else if(penalty == 4) {
				/*
				 * Remark :
				 * 
				 * if penalty = 4, means current player has choosen wild +4
				 * so he will have chance to take-out another card from
				 * his deck, that's why this has to return true;
				 */
				return true;
			}
			
		}
		
		return false;
	}
	
	
	
	// to check for same color card as current card color
	
	private boolean hasSameColorCard(UnoPlayer currentPlayer) {
		
		for(UnoCard card : currentPlayer.getPlayerCards()) {
			
			if(card.getColor().equals(currentCard.getColor())) {
				return true;
			}
			
		}
		
		return false;
	}
	
	
	// to check if player has same value card as current card
	
	private boolean hasSameValueCard(UnoPlayer currentPlayer) {
		
		for(UnoCard card : currentPlayer.getPlayerCards()) {
			
			if(card.getValue() == currentCard.getValue()) {
				return true;
			}
			
		}
		
		return false; 
	}
	
	
	
	// to check if player has any special card
	
	private boolean hasSpecialCard(UnoPlayer currentPlayer){

		for(UnoCard card : currentPlayer.getPlayerCards()) {
			
			if(card.isSpecialCard()) {
				return true;
			}
			
		}
		
		return false;
	}
	
	
	
	// to show board from current player's perspective
	
	private void showBoardToCurrentPlayer(UnoPlayer currentPlayer) {
		
		for(UnoPlayer player : noOfPlayers) {
			
			if(player.getPlayerId().equals(currentPlayer.getPlayerId())) {
				// showing current player cards
				player.showCards();
			}
			else {
				// hiding other players cards
				player.hideCards();
			}
				
		}
		
	}
	
	
	
	// to check weather anybody win the game or not
	
	private boolean gameOver(ArrayList<UnoPlayer> noOfPlayers) {
		
		for(UnoPlayer currentPlayer : noOfPlayers) {
		
			if(currentPlayer.hasWon()) {
				
				System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
				System.out.println("Congratulations" +" "+ currentPlayer.getPlayerName());
				System.out.println("You have won the GAME.......");
				System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
				
				return true;
			}
			
		}
		
		return false; 
	}
	

	// to get first card from deck of cards
	
	private UnoCard getInitialCard() {
		
		// shuffle until top card will be normal card
		
		UnoCard card = deckOfCards.peek();
		
		while(card.isSpecialCard()) {
			deckOfCards.shuffle();
			card = deckOfCards.peek();
		}
		
		return card;
	}
	
	
	
	
	private void distribuiteCardsToPlayer() {
		// start's with 7 cards to each players
		for(int i=0; i<7; i++) {
			for(int j=0; j<noOfPlayers.size(); j++) {
				noOfPlayers.get(j).pickCard(deckOfCards.getTopCard());
			}
		}
	}
	
	
	private void printBoundry() {
		System.out.println("#############################################################################################################");
	}
	
	
	private void waitToConfirm() {
		System.out.println("Press Enter(any char) to continue");
		scanner.next();
	}
	
}
