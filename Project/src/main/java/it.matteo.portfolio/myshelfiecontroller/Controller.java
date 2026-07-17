package myshelfiecontroller;

import common_goal.*;
import myshelfiemodel.*;

import java.security.SecureRandom;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * La classe Controller gestisce l'intero flusso e la logica della partita.
 * Coordina l'inizializzazione del gioco, l'alternanza dei turni e il 
 * calcolo del punteggio finale.
 */
public class Controller {
	
	private static int nPlayers;
	private static Player[] players; // Array contenente i giocatori della partita
	private static int idInitialPlayer;
	private static Map m;
	private static Common_Goal[] common_goals;
	private static final Scanner sc = new Scanner(System.in);
	
	/**
	 * Fase 1: Inizializzazione della partita.
	 * Richiede il numero di giocatori, i loro nomi, assegna la sedia del "Primo Giocatore",
	 * distribuisce le carte personali, inizializza il tabellone ed estrae gli obiettivi comuni.
	 */
	public static void gameSetup() {
		// Inizializza l'array per tenere traccia delle carte personali disponibili
		boolean[] arrayPersonalCardAvailable = new boolean[12];
		for (int i = 0; i < 12; i++) {
			arrayPersonalCardAvailable[i] = true;
		}

		do {
			System.out.print("Inserisci il numero di giocatori (da 2 a 4): ");
			try {
				nPlayers = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				nPlayers = -1;
			}

			if (nPlayers > 4 || nPlayers <= 1) {
				System.out.println("Numero non valido! Deve essere compreso tra 2 e 4.\n");
				System.out.println("---------------------------------------");
			}
		} while (nPlayers > 4 || nPlayers <= 1);

		players = new Player[nPlayers];
		SecureRandom rand = new SecureRandom();
		
		// Estrazione casuale del primo giocatore (sedia)
		idInitialPlayer = rand.nextInt(nPlayers);
		
		for (int i = 0; i < nPlayers; i++) { 
			System.out.print("Nome del Giocatore " + (i + 1) + ": ");
			String name = sc.nextLine();
			boolean chair = (idInitialPlayer == i); // Assegna true solo se l'indice corrisponde all'estrazione
			players[i] = new Player(name, i + 1, chair, arrayPersonalCardAvailable);
		}
		
		System.out.println("\nGiocatori iscritti:");
		for (int i = 0; i < nPlayers; i++) {
			System.out.println("-------------------------------------");
			System.out.println(players[i].toString());
		}
		System.out.println("-------------------------------------");
		System.out.println("Premi INVIO per continuare...");
		sc.nextLine();
		
		m = new Map(nPlayers);
		common_goals = new Common_Goal[2];
		Common_Goal.extractCommonGoal(common_goals, nPlayers);
		
		System.out.println("Obiettivo Comune 1: \t" + common_goals[0].getDescription());
		System.out.println("Obiettivo Comune 2: \t" + common_goals[1].getDescription());
		System.out.println("Premi INVIO per iniziare la partita...");
		sc.nextLine();
	}

	/**
	 * Fase 2: Svolgimento della partita (Game Loop).
	 * Gestisce l'alternanza dei turni, visualizza lo stato del gioco, elabora
	 * le scelte dei giocatori e verifica il completamento degli obiettivi comuni.
	 */
	public static void gameStart() {
		// Array per tracciare se un giocatore ha già riscosso i punti degli obiettivi comuni
		boolean[] hasCompletedGoal1 = new boolean[nPlayers];
		boolean[] hasCompletedGoal2 = new boolean[nPlayers];
		
		boolean isPlaying = true;
		int currentPlayerIndex = idInitialPlayer;
		int idFirstPlayerToFinish = -1; // Traccia chi completa la libreria per primo
		
		while (isPlaying || idFirstPlayerToFinish != -1) {
			
			// Condizione di terminazione: la partita finisce quando il turno torna al primo giocatore
			// dopo che qualcuno ha riempito la libreria.
			if (players[currentPlayerIndex].isChair() && idFirstPlayerToFinish != -1) {
				break;
			} 
			
			// Workaround per "pulire" la console stampando righe vuote
			for (int j = 0; j < 100; ++j) System.out.println();
						
			System.out.println("È il turno di " + players[currentPlayerIndex].getName());
			System.out.println("-------------------------------------");
			System.out.println("Tabellone (Soggiorno):");
			m.visualmap();
			System.out.println("-------------------------------------");
			waitForSeconds(3);
			
			System.out.println("La tua Libreria:");
			System.out.println();
			System.out.println(" 1  2  3  4  5 ");
			System.out.println();
			players[currentPlayerIndex].library.visualLibrary();
			System.out.println("-------------------------------------");
			waitForSeconds(3);
			
			System.out.println("Il tuo Obiettivo Personale:");
			players[currentPlayerIndex].getPersonalCard().visualPersonalCard();
			System.out.println("-------------------------------------");
			waitForSeconds(3);
			
			System.out.print("Obiettivo Comune 1:");
			System.out.println("\t" + common_goals[0].getDescription());
			System.out.print("Obiettivo Comune 2:");
			System.out.println("\t" + common_goals[1].getDescription());
			System.out.println("-------------------------------------");
			
			// Esegue la scelta della tessera finché non risulta valida
			boolean isChoiceValid;
			do {
				isChoiceValid = players[currentPlayerIndex].chooseTile(m, nPlayers, sc);
			} while (!isChoiceValid);
			
			// Verifica il completamento del primo Obiettivo Comune
			if (!hasCompletedGoal1[currentPlayerIndex]) {
				boolean resultCommonGoal = common_goals[0].verify_goal(players[currentPlayerIndex].library);
				if (resultCommonGoal) {
					players[currentPlayerIndex].addPoints(common_goals[0].givePoints());
					System.out.println("Hai completato l'obiettivo: " + common_goals[0].getClass().getSimpleName() + "!");
					hasCompletedGoal1[currentPlayerIndex] = true;	
				}
			}
			
			// Verifica il completamento del secondo Obiettivo Comune
			if (!hasCompletedGoal2[currentPlayerIndex]) {
				boolean resultCommonGoal = common_goals[1].verify_goal(players[currentPlayerIndex].library);
				if (resultCommonGoal) {
					players[currentPlayerIndex].addPoints(common_goals[1].givePoints());
					System.out.println("Hai completato l'obiettivo: " + common_goals[1].getClass().getSimpleName() + "!");
					hasCompletedGoal2[currentPlayerIndex] = true;
				}
			}
			
			System.out.println("La tua Libreria aggiornata:");
			players[currentPlayerIndex].library.visualLibrary();
			System.out.println("-------------------------------------");
			waitForSeconds(3);
			
			// Verifica se il tabellone necessita di essere riempito
			if (m.verifyMap()) {
				System.out.println("-------------------------------------");
				System.out.println("Tabellone ripristinato automaticamente!");
				System.out.println("-------------------------------------");
				waitForSeconds(3);
			}
			
			// Verifica se il giocatore ha completato la propria libreria
			if (players[currentPlayerIndex].library.isFull() && isPlaying) {
				System.out.println("-------------------------------------");
				System.out.println("Hai completato la tua libreria per primo! Ottieni 1 punto bonus.");
				System.out.println("La partita terminerà alla fine del giro corrente.");
				System.out.println("-------------------------------------");
				waitForSeconds(3);
				players[currentPlayerIndex].addPoints(1);
				isPlaying = false; // Innesca la condizione di fine partita
				idFirstPlayerToFinish = currentPlayerIndex;
			}
			
			currentPlayerIndex++;
			currentPlayerIndex = currentPlayerIndex % nPlayers; // Gestione ciclica dei turni
			
			System.out.println("Premi INVIO per passare al prossimo giocatore...");
			sc.nextLine();
		}
	}

	/**
	 * Fase 3: Calcolo del punteggio finale e determinazione del vincitore.
	 * Somma i punti derivanti dagli obiettivi personali e dagli schemi di adiacenza 
	 * nella libreria per ciascun partecipante.
	 */
	public static void gameEnd() {
		for (int i = 0; i < nPlayers; i++) {
			players[i].addPoints(players[i].verifyPersonalCard());
			players[i].verifyPlanceGoal();
		}
		
		System.out.println("LA PARTITA È TERMINATA. ECCO I RISULTATI FINALI:");
		waitForSeconds(1);
		
		for (int i = 0; i < nPlayers; i++) {
			System.out.println("---------------------------------------");
			waitForSeconds(1);
			System.out.println("Giocatore: " + players[i].getName());
			waitForSeconds(1);
			System.out.println("Punti: " + players[i].getPoints());
		}
		
		waitForSeconds(1);
		System.out.println("---------------------------------------");
		
		String winnerName = "";
		int pointsMax = -1;
		
		for (int i = 0; i < nPlayers; i++) {
			if (players[i].getPoints() > pointsMax) {
				pointsMax = players[i].getPoints();
				winnerName = players[i].getName();
			}
		}
		
		System.out.println("IL VINCITORE È " + winnerName.toUpperCase() + " CON " + pointsMax + " PUNTI!");
		
		// Chiusura dello Scanner solo a fine programma per prevenire memory leak ed evitare di chiudere System.in prematuramente
		sc.close(); 
	}

	/**
	 * Mette in pausa l'esecuzione del thread per il numero specificato di secondi.
	 * Utilizzato per scandire i tempi di lettura dell'output a console.
	 *
	 * @param seconds il numero di secondi di attesa
	 */
	public static void waitForSeconds(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Ripristina lo stato di interruzione del thread
		}
	}

}
