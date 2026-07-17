package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Color;
import myshelfiemodel.Tile;
import java.security.SecureRandom;

/**
 * La classe astratta Common_Goal rappresenta un obiettivo comune del gioco.
 * Gestisce la pila dei gettoni punteggio decrescenti distribuiti in base al numero di giocatori
 * e fornisce gli strumenti algoritmici comuni (come il Flood-Fill) per la verifica dei requisiti.
 */
public abstract class Common_Goal {

	protected String description;
	protected int[] remainingCards = new int[4]; // Mappa i gettoni punteggio disponibili nella pila

	/**
	 * Costruisce un oggetto Common_Goal configurando la pila dei gettoni punteggio
	 * in funzione del numero di partecipanti.
	 *
	 * @param nPlayers il numero di giocatori (2, 3 o 4)
	 */
	public Common_Goal(int nPlayers) {
		this.remainingCards[1] = 4;
		this.remainingCards[3] = 8;
		if (nPlayers > 2) {
			this.remainingCards[2] = 6;
		}
		if (nPlayers == 4) {
			this.remainingCards[0] = 2;
		}
	}

	/**
	 * Algoritmo Flood-Fill ricorsivo e non distruttivo.
	 * Calcola l'estensione di un gruppo (cluster) di tessere adiacenti dello stesso colore.
	 *
	 * @param grid    la matrice di tessere estratta dalla libreria
	 * @param r       la riga corrente da ispezionare
	 * @param c       la colonna corrente da ispezionare
	 * @param color   il colore di riferimento da verificare per l'appartenenza al gruppo
	 * @param visited la matrice booleana di supporto per evitare duplicazioni nelle visite
	 * @return il numero complessivo di tessere adiacenti ortogonalmente che formano il gruppo
	 */
	protected int checkClusterSize(Tile[][] grid, int r, int c, Color color, boolean[][] visited) {
		// 1. Controllo dei confini della libreria (6 righe x 5 colonne)
		if (r < 0 || r >= Library.ROWS || c < 0 || c >= Library.COLS) {
			return 0;
		}
		
		// 2. Condizioni di arresto: cella già visitata, vuota o di colore differente
		if (visited[r][c] || grid[r][c] == null || grid[r][c].getColor() != color) {
			return 0;
		}

		// 3. Contrassegna la cella corrente come visitata
		visited[r][c] = true;

		// 4. Esplorazione ricorsiva nelle 4 direzioni cardinali
		return 1 + checkClusterSize(grid, r + 1, c, color, visited)  // Giù
		         + checkClusterSize(grid, r - 1, c, color, visited)  // Su
		         + checkClusterSize(grid, r, c + 1, color, visited)  // Destra
		         + checkClusterSize(grid, r, c - 1, color, visited); // Sinistra
	}

	/**
	 * Verifica se i requisiti specifici dell'obiettivo comune sono soddisfatti all'interno della libreria.
	 * Metodo astratto che verrà implementato singolarmente dalle classi figlie concrete.
	 *
	 * @param library la libreria del giocatore da esaminare
	 * @return true se l'obiettivo è stato raggiunto, false altrimenti
	 */
	public abstract boolean verify_goal(Library library);

	/**
	 * Restituisce la descrizione testuale dell'obiettivo comune.
	 *
	 * @return la descrizione dell'obiettivo
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Concede i punti vittoria associati all'obiettivo prelevando il gettone dal valore 
	 * più alto rimasto all'interno della pila.
	 *
	 * @return il punteggio assegnato al giocatore (8, 6, 4 o 2), oppure 0 se i gettoni sono esauriti
	 */
	public int givePoints() {
		for (int i = 0; i < 4; i++) {
			if (this.remainingCards[3 - i] != 0) {
				int points = this.remainingCards[3 - i];
				this.remainingCards[3 - i] = 0; // Consuma il gettone in cima alla pila
				System.out.println("Congratulazioni! Hai guadagnato " + points + " punti.");
				return points;
			}
		}
		return 0;
	}

	/**
	 * Estrae in modo casuale due obiettivi comuni unici tra i 12 disponibili nel gioco,
	 * istanziandoli e inserendoli nell'array di destinazione.
	 *
	 * @param common_goals l'array in cui allocare i due obiettivi comuni estratti
	 * @param nPlayers     il numero di giocatori della partita, necessario all'inizializzazione dei gettoni
	 */
	public static void extractCommonGoal(Common_Goal[] common_goals, int nPlayers) {
		SecureRandom rand = new SecureRandom();
		int firstGoal = rand.nextInt(12);
		int secondGoal;
		
		// Garantisce l'estrazione di due obiettivi nettamente distinti
		do {
			secondGoal = rand.nextInt(12);
		} while (secondGoal == firstGoal);

		common_goals[0] = createGoalInstance(firstGoal, nPlayers);
		common_goals[1] = createGoalInstance(secondGoal, nPlayers);
	}

	/**
	 * Metodo di utilità interno (Factory) per mappare l'indice estratto alla corrispondente classe concreta.
	 */
	private static Common_Goal createGoalInstance(int id, int nPlayers) {
		return switch (id) {
			case 0 -> new Common_Goal01(nPlayers);
			case 1 -> new Common_Goal02(nPlayers);
			case 2 -> new Common_Goal03(nPlayers);
			case 3 -> new Common_Goal04(nPlayers);
			case 4 -> new Common_Goal05(nPlayers);
			case 5 -> new Common_Goal06(nPlayers);
			case 6 -> new Common_Goal07(nPlayers);
			case 7 -> new Common_Goal08(nPlayers);
			case 8 -> new Common_Goal09(nPlayers);
			case 9 -> new Common_Goal10(nPlayers);
			case 10 -> new Common_Goal11(nPlayers);
			case 11 -> new Common_Goal12(nPlayers);
			default -> null;
		};
	}
}
