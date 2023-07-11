package com.swiggy.service;

import com.swiggy.model.Card;

public interface DeckService {

	public abstract void shuffle(Card[] deckOfCards);
	
	public abstract Card getTopCard(Card[] deckOfCards);
	
	public abstract Card getPeekCard(Card[] deckOfCards);
	
	public abstract boolean isEmpty(Card[] deckOfCards);
	
}
