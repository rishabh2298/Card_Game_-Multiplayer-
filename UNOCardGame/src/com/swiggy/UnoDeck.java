package com.swiggy;

import java.util.ArrayList;
import java.util.Random;

public class UnoDeck {
	
	
	private ArrayList<UnoCard> deckOfCards;
	
	
	// Initialize the deck with the standard set of cards.
	
	public UnoDeck(){
		
		deckOfCards = new ArrayList<>();
		
		int[] numbers = {0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9};
		
		String[] colors = {"Blue","Red","Green","Yellow"};
		
		String[] specialCards = {"draw_two","Skip","Reverse"};
		
		
		// (0(1) + 1-9(2)) * 4 = 76 normal cards
		
		for(String color : colors) {
			for(Integer num : numbers) {
				deckOfCards.add(new UnoCard(color, num));
			}
		}
		
		
		// (3 * 2) * 4 = 24 Special Cards (2 of each color);
		
		for(String color : colors) {
			for(String cardValue: specialCards) {
				deckOfCards.add(new UnoCard(color, cardValue));
				deckOfCards.add(new UnoCard(color, cardValue));
			}
		}
		
		
		// 4*2 = 8 (wild Card + wild Card draw_four)
		
		for(int i=0; i<4; i++) {
			deckOfCards.add(new UnoCard("wild", "wild"));
			deckOfCards.add(new UnoCard("wild", "draw_four"));
		}
		
		
		// Total cards = 108	(76 normal + 24 special + 8 wild)
		
	}
	
	
	// In case if deck of cards gets Empty
	// Replenish the deck when it runs out.
	
	public UnoDeck(ArrayList<UnoCard> stockPile) {
		deckOfCards = stockPile;
	}
	
	
	// Shuffle the cards.
	
	public void shuffle() {
		/*
		 * to shuffle the deckOfCards, using random index 
		 * and swapping value with current index value
		 */
		
		Random random = new Random();
		int N = this.deckOfCards.size();
		
		for(int i=0; i<N; i++) {
			int randomIndex = i + random.nextInt(N - i);
			UnoCard randomCard = this.deckOfCards.get(randomIndex);
			
			this.deckOfCards.remove(randomIndex);
			this.deckOfCards.add(randomIndex, this.deckOfCards.get(i));
			
			this.deckOfCards.remove(i);
			this.deckOfCards.set(i, randomCard);
		}
	}
	
	
	// Pick top card from deck
	
	public UnoCard getTopCard() {
		return this.deckOfCards.remove(this.deckOfCards.size() - 1);
	}
	
	
	// To check for wild card while taking starting card
	
	public UnoCard peek() {
		return this.deckOfCards.get(deckOfCards.size()-1);
	}
	
	
	public boolean isEmpty() {
		return deckOfCards.size() == 0;
	}
	
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		for(UnoCard card : this.deckOfCards) {
			result.append(card);
		}
		
		return result.toString();
	}

}
