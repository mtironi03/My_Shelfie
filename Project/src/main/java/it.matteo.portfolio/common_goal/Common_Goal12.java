package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Position;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.12.
 * Requisito: Cinque colonne di altezza crescente o decrescente.
 * A partire dalla prima colonna a sinistra o a destra, ogni colonna successiva 
 * deve essere formata da una tessera in più. Le tessere possono essere di qualsiasi tipo.
 */
public class Common_Goal12 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal12 configurando i gettoni in base al numero di giocatori.
	 * * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal12(int nPlayers) {
		super(nPlayers);
		this.description = "Cinque colonne di altezza crescente o decrescente: a partire dalla prima colonna a sinistra o a destra, ogni colonna successiva deve essere formata da una tessera in più.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria di tessere da verificare
	 * @return true se le altezze delle colonne formano una scala crescente o decrescente, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		int[] colHeights = new int[Library.COLS];

		// Calcoliamo l'altezza di ogni colonna
		for (int col = 0; col < Library.COLS; col++) {
			colHeights[col] = getColumnHeight(library, col);
		}

		// Verifichiamo la progressione crescente (es. 1, 2, 3, 4, 5 oppure 2, 3, 4, 5, 6)
		boolean ascending = true;
		for (int i = 0; i < Library.COLS - 1; i++) {
			if (colHeights[i + 1] != colHeights[i] + 1) {
				ascending = false;
				break;
			}
		}

		// Verifichiamo la progressione decrescente (es. 6, 5, 4, 3, 2 oppure 5, 4, 3, 2, 1)
		boolean descending = true;
		for (int i = 0; i < Library.COLS - 1; i++) {
			if (colHeights[i + 1] != colHeights[i] - 1) {
				descending = false;
				break;
			}
		}

		// L'obiettivo è superato se almeno una delle due progressioni è valida
		return ascending || descending;
	}

	/**
	 * Metodo di supporto che conta il numero di tessere presenti nella colonna specificata.
	 * * @param library la libreria da esaminare
	 * @param col     l'indice della colonna da misurare
	 * @return il numero totale di tessere presenti nella colonna
	 */
	private int getColumnHeight(Library library, int col) {
		int count = 0;
		for (int row = 0; row < Library.ROWS; row++) {
			if (library.getTile(new Position(row, col)) != null) {
				count++;
			}
		}
		return count;
	}
}
