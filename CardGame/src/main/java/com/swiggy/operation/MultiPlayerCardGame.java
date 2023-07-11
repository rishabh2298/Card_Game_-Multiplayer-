package com.swiggy.operation;

import com.swiggy.usecase.Game;

public class MultiPlayerCardGame {
	
	public static void main(String[] args) {
		
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		System.out.println();
		System.out.println("          WELCOME  TO  CARDS  GAME");
		System.out.println();
		System.out.println("ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
		System.out.println();
		
		Game game = new Game();
		
		// This will start the game
		
		game.startGame();
	}

}
