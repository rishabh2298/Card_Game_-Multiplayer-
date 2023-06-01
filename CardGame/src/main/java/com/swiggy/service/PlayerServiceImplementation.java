package com.swiggy.service;

import com.swiggy.model.Card;
import com.swiggy.model.Player;


public class PlayerServiceImplementation implements PlayerService {

	
	public void pickCard(Player currentPlayer, Card card) {
		
		int locationOfNewCard = currentPlayer.getIndexOfNextNewCard();
		
		// allotting new card to players hand
		currentPlayer.getCardsInHand()[locationOfNewCard] = card;
		
		// updating next index where new card will be added
		currentPlayer.setIndexOfNextNewCard(locationOfNewCard + 1);
	}

	
	public Card throwCardOnDiscardPile(Player currentPlayer, int indexOfCard) {

		Card removedCard = currentPlayer.getCardsInHand()[indexOfCard];
		
		/*  removing selected card from hands of player
		 *  and shifting remaining cards to it's correct position
		 *  so that there will be no null values between valid cards
		 */
		
		/* case-1 : if last card is chosen from array of cards
		 * then make it null and need not to shift it to correct position
		 * as there is no invalid cards(null values) in between cards array of player 
		 */
		
		if(currentPlayer.getCardsInHand()[indexOfCard + 1] == null) {
			
			currentPlayer.getCardsInHand()[indexOfCard] = null;
			
			// updating index of next new card (which has to be null) to add new card
			
			currentPlayer.setIndexOfNextNewCard(indexOfCard);
			
			return removedCard;
		}
		
		// case-2 : if card is chosen from middle of array of cards
		
		while(currentPlayer.getCardsInHand()[indexOfCard + 1] != null ) {		// until next card != null
			
			currentPlayer.getCardsInHand()[indexOfCard] = currentPlayer.getCardsInHand()[indexOfCard + 1];
			
			indexOfCard++;
		}

		// updating index of next new card (which has to be null) to add new card

		currentPlayer.setIndexOfNextNewCard(indexOfCard + 1);
		
		return removedCard;
	}

	
	public boolean hasWon(Player currentPlayer) {
		
		// return true if(cards[] cardsInHand.[0th] value == null), else false (not won)
		
		return currentPlayer.getCardsInHand()[0] == null;
	}

	
	public void showCurrentPlayerCards(Player currentPlayer) {
		
		String[] cardLayout = {"======== ","| |","| |","======== "};

		for(int layout=0; layout<cardLayout.length; layout++) {
			
			int sizeOfValidCards = currentPlayer.getIndexOfNextNewCard();

			StringBuilder result = new StringBuilder();
			Card[] allCards = currentPlayer.getCardsInHand();
			
			for(int card=0; card<sizeOfValidCards; card++) {
				if(layout==1) {
					result.append("|"+allCards[card].getSuit()+"| ");
				}
				else if(layout==2) {
					result.append("|"+allCards[card].getFace()+"| ");
				}
				else {
					result.append(" "+cardLayout[layout]+" ");
				}
			}
			
			System.out.println(result.toString());
		}
		
	}
	

	public void hideOtherPlayerCards(Player currentPlayer) {

		String[] cardLayout = {" ======== ","|        |","|        |"," ======== "};
		
		StringBuilder result = new StringBuilder();
		
		for(int layout=0; layout<cardLayout.length; layout++) {
			
			int sizeOfValidCards = currentPlayer.getIndexOfNextNewCard();
			
			for(int cards=0; cards<sizeOfValidCards; cards++) {
				result.append(cardLayout[layout]+" ");
			}
			
			result.append("\n");
		}
		
		System.out.println(result.toString());
	}

}
