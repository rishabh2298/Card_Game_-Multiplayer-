package com.swiggy.service;

import com.swiggy.model.Card;
import com.swiggy.model.Player;

public interface PlayerService {
	
	public abstract void pickCard(Player currentPlayer, Card card);
	
	public abstract Card throwCardOnDiscardPile(Player currentPlayer, int indexOfCard);
	
	public abstract boolean hasWon(Player currentPlayer); 
	
	public abstract void showCurrentPlayerCards(Player currentPlayer);
	
	public abstract void hideOtherPlayerCards(Player currentPlayer);
	
}
