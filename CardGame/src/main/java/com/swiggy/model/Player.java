package com.swiggy.model;

public class Player {
	
	private int playerId;
	private String playerName;
	private Card[] cardsInHand;
	private int indexOfNextNewCard;
	
	public Player(String playerName, Card[] playerCards) {
		this.playerId = generatePlayerId(6);
		this.playerName = playerName;
		this.cardsInHand = playerCards;
		
		// Allotting next index value for new drawn card
		
		for(int validCard=0; validCard<cardsInHand.length; validCard++) {
			if(cardsInHand[validCard] == null) {
				indexOfNextNewCard = validCard;
				break;
			}
		}
	}
	
	// This will generate unique Id for each players;
	
	private int generatePlayerId(int size) {
		
		StringBuilder playerId = new StringBuilder();

		int[] numbers = {0,1,2,3,4,5,6,7,8,9};
		
		for(int idSize=0; idSize<size; idSize++) {
			playerId.append(numbers.length * Math.random());
		}
		
		return Integer.parseInt(playerId.toString());
	}
	

	public int getPlayerId() {
		return playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Card[] getCardsInHand() {
		return cardsInHand;
	}

	public int getIndexOfNextNewCard() {
		return indexOfNextNewCard;
	}
	
	
	// to update next index of player card which is null 
	
	public void setIndexOfNextNewCard(int indexOfNextNewCard) {
		this.indexOfNextNewCard = indexOfNextNewCard;
	}

	
	public String toString() {
		return "Player Id = "+this.playerId+"\nPlayer Name = "+this.playerName;
	}
	
}
