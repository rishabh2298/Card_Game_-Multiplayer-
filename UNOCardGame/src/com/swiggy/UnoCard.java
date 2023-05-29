package com.swiggy;

public class UnoCard {
	
	private String color;
	private int value;
	private boolean isSpecialCard;
	private String specialCardValue;

	
	// constructor for normal cards
	
	public UnoCard(String color, int value) {
		this.color = color;
		this.value = value;
		isSpecialCard = false;
		specialCardValue = null;
	}
	
	
	// constructor for special card +2(draw-two), +4(draw-four), reverse, skip, wild cards
	
	public UnoCard(String color, String specialCardValue) {
		this.color = color;
		this.value = 0;
		this.isSpecialCard = true;
		this.specialCardValue = specialCardValue;
	}


	public String getColor() {
		return color;
	}


	public int getValue() {
		return value;
	}


	public boolean isSpecialCard() {
		return isSpecialCard;
	}


	public String getSpecialCardValue() {
		return specialCardValue;
	}


	// To print cards in shape
	
	@Override
	public String toString() {

		String[] cardLayout = {" ----- ", "|     |","|     |"," ----- "}; 
		
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<cardLayout.length; i++) {
			if(i==1) {
				result.append("| "+this.getColor()+" |"+" ");
			}
			else if(i==2) {
				if(isSpecialCard) {
					result.append("| "+this.getSpecialCardValue()+" |"+" ");
				}
				else {
					result.append("| "+this.getValue()+" |"+" ");
				}
			}
			else {
				result.append(cardLayout[i]+" ");
			}
			// for new line
			result.append("\n");
		}
		
		return result.toString();
	}
	
}
