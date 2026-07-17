package it.matteo.portfolio;

import myshelfiecontroller.*;

/**
 * La classe Main rappresenta il punto di ingresso principale dell'applicazione.
 * Ha il compito di innescare l'inizializzazione dei giocatori, l'estrazione 
 * degli obiettivi comuni e l'esecuzione del loop di gioco attraverso il controller.
 */
public class Main {

	/**
	 * Il metodo main funge da entry point del programma.
	 * Coordina le tre fasi fondamentali della partita invocando i metodi statici del Controller.
	 * * @param args gli argomenti passati da riga di comando
	 */
	public static void main(String[] args) {
		
		Controller.gameSetup();
		Controller.gameStart();
		Controller.gameEnd();
		
	}

}
