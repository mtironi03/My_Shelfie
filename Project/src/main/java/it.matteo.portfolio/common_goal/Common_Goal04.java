package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Color;
import myshelfiemodel.Tile;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.4.
 * Requisito: Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2.
 * Le tessere dei due gruppi devono essere dello stesso tipo (colore).
 */
public class Common_Goal04 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal04 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal04(int nPlayers) {
		super(nPlayers);
		this.description = "Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2. Le tessere dei due gruppi devono essere dello stesso tipo.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 * Scansiona la libreria alla ricerca di sotto-matrici 2x2 omogenee e non sovrapposte.
	 *
	 * @param library la libreria di tessere da verificare.
	 * @return true se l'obiettivo è stato raggiunto, false altrimenti.
	 */
	@Override
	public boolean verify_goal(Library library) {
		Tile[][] grid = library.getLibrary();
		int[] squaresCount = new int[Color.values().length];
		
		// Matrice di supporto per marcare le tessere che fanno già parte di un quadrato 2x2 convalidato
		boolean[][] partOfSquare = new boolean[Library.ROWS][Library.COLS];

		// Ci fermiamo a ROWS - 1 e COLS - 1 perché controlliamo il blocco (i+1, j+1)
		for (int i = 0; i < Library.ROWS - 1; i++) {
			for (int j = 0; j < Library.COLS - 1; j++) {
				
				Tile t1 = grid[i][j];
				Tile t2 = grid[i][j + 1];
				Tile t3 = grid[i + 1][j];
				Tile t4 = grid[i + 1][j + 1];

				// 1. Verifichiamo che nessuna delle 4 caselle sia vuota
				if (t1 != null && t2 != null && t3 != null && t4 != null) {
					Color targetColor = t1.getColor();

					// 2. Verifichiamo che tutte e 4 le tessere siano dello stesso colore
					if (t2.getColor() == targetColor && t3.getColor() == targetColor && t4.getColor() == targetColor) {
						
						// 3. REGOLA DI SEPARAZIONE: Nessuna delle 4 tessere deve essere già stata usata per un altro quadrato
						if (!partOfSquare[i][j] && !partOfSquare[i][j + 1] && 
							!partOfSquare[i + 1][j] && !partOfSquare[i + 1][j + 1]) {
							
							// Marchiamo le 4 tessere come occupate
							partOfSquare[i][j] = true;
							partOfSquare[i][j + 1] = true;
							partOfSquare[i + 1][j] = true;
							partOfSquare[i + 1][j + 1] = true;

							int colorIndex = targetColor.ordinal();
							squaresCount[colorIndex]++;

							// Se troviamo 2 quadrati distinti dello stesso colore, l'obiettivo è raggiunto
							if (squaresCount[colorIndex] >= 2) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}
}
