package com.swiggy;

import java.util.ArrayList;
import java.util.Scanner;

public class UnoPlayer {
	
	private String playerId;
	private String playerName;
	private ArrayList<UnoCard> playerCards;

	
	public UnoPlayer(String playerName) {
		this.playerId = generatePlayerId(6);		// give uniqe ID for player
		this.playerName = playerName;
		playerCards = new ArrayList<>();
	}
	
	
	
	// to generate player Id of defined size;

	private String generatePlayerId(int size) {
		
		StringBuilder result = new StringBuilder();
		
		int[] arr = {0,1,2,3,4,5,6,7,8,9};
		for(int i=0; i<size; i++) {
			result.append(Math.random() * arr.length);
		}
		
		return result.toString(); 
	}



	public String getPlayerId() {
		return playerId;
	}



	public String getPlayerName() {
		return playerName;
	}


	//	This is used to check if player has any valid cards to play.
	
	public ArrayList<UnoCard> getPlayerCards() {
		return playerCards;
	}
	
	
	// To add current card to player if satisfies the condition
	
	public void pickCard(UnoCard card) {
		playerCards.add(card);
	}
	
	
	// This method is use to select and throw card from playerCards by player
	
	public void throwCardOnPile(int index) {
		playerCards.remove(index);
	}
	
	
	// To call (UNO) if last card left
	
	public void sayUno() {
		Scanner scanner = new Scanner(System.in);
		if(playerCards.size() == 1) {
			System.out.println("....UNO....");
			System.out.println("Press Enter to continue....");
			scanner.next();
		}
		scanner.close();
	}
	
	
	// To check if player has won the game or not
	
	public boolean hasWon() {
		return playerCards.size() == 0;
	}
	
	
	/*
	 * To print all the player cards 
	 * This method is to make output more graphically attractive
	 */
	
//	public void showCards() {
//		
//		String[] cardLayout = {" ----- ","|     |"," ----- ","|     |"};
//		
//		for(int i=0; i<cardLayout.length; i++) {
//			
//			StringBuilder card = new StringBuilder();
//			
//			for(int j=0; j<playerCards.size(); j++) {
//				if(i==1)
//			}
//		}
//		
//	}
	
}
