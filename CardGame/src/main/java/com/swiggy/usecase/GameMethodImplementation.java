package com.swiggy.usecase;

import java.util.Scanner;

import com.swiggy.model.Card;
import com.swiggy.model.Deck;
import com.swiggy.model.Player;
import com.swiggy.service.DeckService;
import com.swiggy.service.DeckServiceImplementation;
import com.swiggy.service.PlayerService;
import com.swiggy.service.PlayerServiceImplementation;

public class GameMethodImplementation implements GameMethod {
	
	
	private PlayerService playerService;
	private DeckService deckService;
	private Scanner scanner;
	
	
	public GameMethodImplementation() {
		this.playerService = new PlayerServiceImplementation();
		this.deckService = new DeckServiceImplementation();
		this.scanner = new Scanner(System.in);
	}
	

	@Override
	public void selectAnyNextCard(Player currentPlayer, Card currentCard, Card[] discardPile, int indexOfDiscardPile, Deck deck) {

		System.out.println("Choose card to throw on Discard Pile");
		int selectedCard = scanner.nextInt();
		selectedCard--;
		
		while(selectedCard < 0 || selectedCard>=currentPlayer.getIndexOfNextNewCard()) {
			System.out.println("Please Enter valid card Number");
			selectedCard = scanner.nextInt();
			selectedCard--;
		}
		
		if(!hasCounterCard(currentPlayer, currentCard)) {
			
			/*
			 *  As not have any valid card to throw on discard Pile, So have to draw new card
			 *  from deckOfCards
			 */
			
			drawACardFromDeck(currentPlayer,deck);
			
			// next player chance after drawn a card
			return;
		}
		
		// until select valid cards from hand
		
		while(!isValidCard(currentPlayer, selectedCard, currentCard)){
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

	
	// To check if currentPlayer has any counter card to play if currentCard is NormalCard
	
	@Override
	public boolean hasCounterCard(Player currentPlayer, Card currentCard) {

		// This are numbers of valid cards in hands of currentPlayer
		// as it is 0-based index
		
		int sizeOfCards = currentPlayer.getIndexOfNextNewCard();
		Card[] cardsInHand = currentPlayer.getCardsInHand();
		
		for(int card=0; card<sizeOfCards; card++) {
			
			if(cardsInHand[card] != null) {
				
				// if same suit then can be any card
				if(cardsInHand[card].getSuit().equals(currentCard.getSuit())) {
					return true;
				}
				
				// if different suit then value(rank) of both the cards should be same
				else if(cardsInHand[card].getFace().equals(currentCard.getFace())) {
					return true;
				}

				// if current card = normal card, then can use Action cards
				else if(cardsInHand[card].isSpecialCard()) {
					return true;
				}
			}	
		}
		
		return false;
	}

	

	/*
	 *	If current card is normal card then, Normal card will apply so select card
	 *	i.e., A player can only play a card if it matches either the suit or the 
	 *  rank of the top card on the discard pile (currentCard). 
	 */
	
	@Override
	public boolean isValidCard(Player currentPlayer, int selectedCard, Card currentCard) {

		Card choosenCard = currentPlayer.getCardsInHand()[selectedCard];
		
		if(choosenCard != null) {
			
			// if same suit then can be any card
			if(choosenCard.getSuit().equals(currentCard.getSuit())) {
				return true;
			}
			
			// if different suit then value(rank) of both the cards should be same
			else if(choosenCard.getFace().equals(currentCard.getFace())) {
				return true;
			}
			
			// if current card = normal card, then can use Action cards
			else if(choosenCard.isSpecialCard()) {
				return true;
			}
		}
		
		return false;
	}


	/*
	 *  If has any action card (Queen, Jack) then this method will be called but allotting
	 *  appropriate penalty
	 */
	
	@Override
	public void penaltyGotApplied(Player currentPlayer, int penalty, Deck deck, boolean reversePlay, int turn, Card currentCard, Card[] discardPile, int indexOfDiscardPile) {
		System.out.println("Since current card is Action Card, So you have to draw = "+penalty+" from deck of cards");

		while(penalty-- >0) {
			
			Card newCard = deckService.getTopCard(deck.getDeckOfCards());
			playerService.pickCard(currentPlayer, newCard);
			
			System.out.println("You have picked card :-\n"+ newCard);
			confirmToContinue();
			
			// while taking cards from deck.getDeckOfCards() it get's empty, then
			
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
		}

		/*
		 * Throwing valid card for next player as he/she can't throw same card/any action cards
		 * So, Checking it has validCard to play then provide chance, otherwise
		 * skipped his chance
		 */
		
		nextCardIfCurrentCardIsActionCard(currentPlayer,currentCard,discardPile,indexOfDiscardPile,deck);
		
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

	@Override
	public void nextCardIfCurrentCardIsActionCard(Player currentPlayer, Card currentCard, Card[] discardPile, int indexOfDiscardPile, Deck deck) {

		// check for any different non-action card
		if(!checkForDifferentNonActionCard(currentPlayer,currentCard)){
			
			// This method will make current player to draw a card from deckOfCard
			
			drawACardFromDeck(currentPlayer,deck);
		}
		else {
			System.out.println("Choose card to throw on Discard Pile");
			int selectedCard = scanner.nextInt();
			selectedCard--;
			
			while(selectedCard < 0 || selectedCard>=currentPlayer.getIndexOfNextNewCard()) {
				System.out.println("Please Enter valid card Number");
				selectedCard = scanner.nextInt();
				selectedCard--;
			}
			
			while(isAnyActionCard(currentPlayer, selectedCard) || isDifferentSuitCard(currentPlayer, selectedCard,currentCard)){
				System.out.println("Choose valid card to throw on Discard Pile,\nCan't be action/different suit card");
				selectedCard = scanner.nextInt();
				selectedCard--;
			}
			
			// updating current card for next player
			
			currentCard = playerService.throwCardOnDiscardPile(currentPlayer, selectedCard);
			
			discardPile[indexOfDiscardPile++] = currentCard;
			
		}
	}


	/*
	 * 	This method will let user to draw a new card from deckOfCards and If deckOfCards
	 *  get empty then, It will stop the application by asking for next Game.
	 */
	
	@Override
	public void drawACardFromDeck(Player currentPlayer, Deck deck) {

		System.out.println("You don't have valid card to play,");
		System.out.println("You have to draw a new card");

		Card newCard = deckService.getTopCard(deck.getDeckOfCards());
		playerService.pickCard(currentPlayer, newCard);
		
		System.out.println("You have picked card :-\n"+ newCard);
		confirmToContinue();
		
		// while taking cards from deck.getDeckOfCards() it get's empty, then
		
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
	}


	/*
	 *  it will check for availability non-action card of same suit
	 *  1. action card = no , 
	 *  2. any other number card = yes, or same suit = yes
	 */
	
	@Override
	public boolean checkForDifferentNonActionCard(Player currentPlayer, Card currentCard) {

		for(Card card : currentPlayer.getCardsInHand()) {
			
			if(card == null) {
				break;
			}
			
			// it might be action card,
			if(card.getSuit().equals(currentCard.getSuit())) {
				if(!card.isSpecialCard()) {
					return true;
				}
			}
		}
		
		return false;
	}

	
	// return true if selected card is different suit card
	
	@Override
	public boolean isDifferentSuitCard(Player currentPlayer, int selectedCard, Card currentCard) {

		if(currentPlayer.getCardsInHand()[selectedCard].getSuit().equals(currentCard.getSuit()))
			return false;
		
		return true;
	}
	

	/*
	 *  return true, if any of the actions cards (Ace, Jack, Queen, King) Or null value
	 *  return false, if not an action card
	 */
	
	@Override
	public boolean isAnyActionCard(Player currentPlayer, int selectedCard) {

		if(currentPlayer.getCardsInHand()[selectedCard].isSpecialCard())
			return true;
		else if(currentPlayer.getCardsInHand()[selectedCard] == null)
			return true;
		
		return false;
	}
	
	
	// This will print only cards of current player

	@Override
	public void showCardsToCurrentPlayer(Player currentPlayer, Player[] totalPlayers) {

		for(Player player : totalPlayers) {
			
			if(player.getPlayerId() == currentPlayer.getPlayerId()) {
				playerService.showCurrentPlayerCards(currentPlayer);
			}
			else {
				playerService.hideOtherPlayerCards(player);
			}
		}
		
	}
	
	
	// This will check if any player has won or not yet.
	
	@Override
	public boolean gameOver(Player[] totalPlayers) {

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
	

	// This will provide 1st starting card on deckOfPile to start game
	
	@Override
	public Card getInitialCard(Deck deck) {
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
	
	@Override
	public void distributeCardsToPlayer(Deck deck, Player[] totalPlayers) {

		for(int numberOfCard=0; numberOfCard<5; numberOfCard++) {
			
			for(int currentPlayer=0; currentPlayer<totalPlayers.length; currentPlayer++) {
				playerService.pickCard(totalPlayers[currentPlayer], deckService.getTopCard(deck.getDeckOfCards()));
			}
		}
		
	}
	
	
	
	// These are private methods of this class
	

	private void confirmToContinue() {
		System.out.println("Enter (any key) to continue");
		scanner.next();
	}
	
	
	private void printBoundry() {
		System.out.println("#############################################################################################################");
	}

}
