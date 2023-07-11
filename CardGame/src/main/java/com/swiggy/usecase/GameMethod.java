package com.swiggy.usecase;

import com.swiggy.model.Card;
import com.swiggy.model.Deck;
import com.swiggy.model.Player;

public interface GameMethod {

	public abstract void selectAnyNextCard(Player currentPlayer, Card currentCard, Card[] discardPile, int indexOfDiscardPile, Deck deck);
	
	public abstract boolean hasCounterCard(Player currentPlayer, Card currentCard);
	
	public abstract boolean isValidCard(Player currentPlayer, int selectedCard, Card currentCard);
	
	public abstract void penaltyGotApplied(Player currentPlayer, int penalty, Deck deck, boolean reversePlay, int turn, Card currentCard, Card[] discardPile, int indexOfDiscardPile);
	
	public abstract void nextCardIfCurrentCardIsActionCard(Player currentPlayer, Card currentCard, Card[] discardPile, int indexOfDiscardPile, Deck deck);
	
	public abstract void drawACardFromDeck(Player currentPlayer, Deck deck);
	
	public abstract boolean checkForDifferentNonActionCard(Player currentPlayer, Card currentCard);
	
	public abstract boolean isDifferentSuitCard(Player currentPlayer, int selectedCard, Card currentCard);
	
	public abstract boolean isAnyActionCard(Player currentPlayer, int selectedCard);
	
	public abstract void showCardsToCurrentPlayer(Player currentPlayer, Player[] totalPlayers);
	
	public abstract boolean gameOver(Player[] totalPlayers);
	
	public abstract Card getInitialCard(Deck deck);
	
	public abstract void distributeCardsToPlayer(Deck deck, Player[] totalPlayers);
	
}
