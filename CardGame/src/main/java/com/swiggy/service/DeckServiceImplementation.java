package com.swiggy.service;

import com.swiggy.model.Card;

public class DeckServiceImplementation implements DeckService {
	

	public void shuffle(Card[] deckOfCards) {
		/*
		 *  If shuffle method called after distributing cards, then null values
		 *  might comes in between of valid cards sequence.
		 *  So first check for length of valid cards
		 */
		int originalSizeOfDeck = 0;
		
		for(int validCard=0; validCard<deckOfCards.length; validCard++) {
			if(deckOfCards[validCard] == null) {
				// Since it is 0-based index value
				originalSizeOfDeck = validCard;
				break;
			}
		}

		// if no null value in deckOfCards
		
		if(originalSizeOfDeck == 0) originalSizeOfDeck = 52;
		
		// Now shuffling valid cards
		
		for(int index=0; index<originalSizeOfDeck; index++) {
			// taking random card from deckOfCards
			int randomIndex = (int)Math.floor(originalSizeOfDeck * Math.random());
			
			// swapping currentCard with randomCard
			Card firstCard = deckOfCards[index];
			firstCard = deckOfCards[randomIndex];
			deckOfCards[randomIndex] = firstCard;
		}
	}

	
	public Card getTopCard(Card[] deckOfCards) {
		
		Card topCard = null;
		
		for(int validCard=deckOfCards.length-1; validCard>=0; validCard--) {
			
			// this will be top (valid card) from deckOfCards
			if(deckOfCards[validCard] != null) {
				topCard = deckOfCards[validCard];
				deckOfCards[validCard] = null;
				break;
			}
		}
		
		return topCard;
	}

	
	public Card getPeekCard(Card[] deckOfCards) {
		

		Card topCard = null;
		
		for(int validCard=deckOfCards.length-1; validCard>=0; validCard--) {
			
			// this will be top (valid card) from deckOfCards
			if(deckOfCards[validCard] != null) {
				topCard = deckOfCards[validCard];
				break;
			}
		}
		
		return topCard;
	}
	

	public boolean isEmpty(Card[] deckOfCards) {
		
		// if 0-th card will be null, then deckOfCards will be null
		
		return deckOfCards[0] == null;
	}

}
