package com.swiggy.model;

public class Card {

	private String suit;
	private String face;
	private boolean isSpecialCard;
	
	// Constructor for normal(number) cards
	
	public Card(String suit, String face) {
		this.suit = suit;
		this.face = face;
	}
	
	// Constructor for Action cards (Ace, Queens, Kings, Jacks)
	
	public Card(boolean isSpecial, String suit, String face) {
		this(suit, face);
		this.isSpecialCard = isSpecial;
	}

	
	public String getSuit() {
		return suit;
	}

	public String getFace() {
		return face;
	}

	public boolean isSpecialCard() {
		return isSpecialCard;
	}
	
	
	public String toString() {
		
		String[] cardLayout = {"===========","|			|","|			|","==========="};
		
		StringBuilder cardBox = new StringBuilder();
		
		for(int index=1; index<cardLayout.length; index++) {
			if(index==1) {
				cardBox.append("|  "+suit+"  |");
			}
			else if(index==2) {
				cardBox.append("| "+face+" |");
			}
			else {
				cardBox.append(cardLayout[index]);
			}
			
			// to make proper box shape
			cardBox.append("\n");
		}
		
		return cardBox.toString();
	}
	
}
