package com.swiggy.model;

public class DeckOfCards {
	
	private Card[] deckOfCards;
	private static final int numberOfCards = 52;
	
	/*
	 *  When constructor get called all the variable gets it's required values
	 *  deckOfCards get initialized with 52 default cards
	 */
	
	public DeckOfCards() {
		
		this.deckOfCards = new Card[numberOfCards];
		
		String[] allSuits = {"Diamonds", "  Clubs ", " Hearts "," Spades "};
		
		String[] allFaces = {"  Ace   ", "  Two   "," Three  ","  Four  ","  Five  ","  Six   "," Seven  "," Eight  ","  Nine  "
				,"  Ten   ","  King  "," Queen  ","  Jack  "};
		
		int index = 0;
		
		// Allotting each combinations of card to deckOfCards
		
		for(int suit=0; suit<allSuits.length; suit++) {
			for(int face=0; face<allFaces.length; face++) {
				// In case of Number cards (Non-Action cards)
				if(face<10) {
					deckOfCards[index++] = new Card(allSuits[suit], allFaces[face]);
				}
				// In case of Special cards (Action cards)
				else {
					deckOfCards[index++] = new Card(true, allSuits[suit], allFaces[face]);
				}
			}
		}
		
		// Total cards 52 (normal cards(40) + action cards(12))
	}

	
	public Card[] getDeckOfCards() {
		return deckOfCards;
	}

}
