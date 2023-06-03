package com.swiggy.usecase;

import com.swiggy.model.Card;
import com.swiggy.model.Deck;
import com.swiggy.model.Player;

public interface GameMethod {

	public abstract void selectAnyNextCard(Player currentPlayer);
	
	public abstract boolean hasCounterCard(Player currentPlayer);
	
	public abstract boolean isValidCard(Player currentPlayer, int selectedCard);
	
	public abstract void penaltyGotApplied(Player currentPlayer);
	
	public abstract void nextCardIfCurrentCardIsActionCard(Player currentPlayer);
	
	public abstract void drawACardFromDeck(Player currentPlayer);
	
	public abstract boolean checkForDifferentNonActionCard(Player currentPlayer);
	
	public abstract boolean isDifferentSuitCard(Player currentPlayer, int selectedCard);
	
	public abstract boolean isAnyActionCard(Player currentPlayer, int selectedCard);
	
	public abstract void showCardsToCurrentPlayer(Player currentPlayer);
	
	public abstract boolean gameOver(Player[] totalPlayers);
	
	public abstract Card getInitialCard(Deck deck);
	
	public abstract void distributeCardsToPlayer(Deck deck);
	
}
