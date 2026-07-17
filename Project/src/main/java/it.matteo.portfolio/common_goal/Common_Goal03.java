package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;
import myshelfiemodel.Color;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.3.
 * Requisito: Quattro gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso tipo. 
 * Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
 */
public class Common_Goal03 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal03 configurando i gettoni in base al numero di giocatori.
	 * * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal03(int nPlayers) {
		super(nPlayers);
		this.description = "Quattro gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso tipo. Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria di tessere da verificare
	 * @return true se sono presenti almeno 4 gruppi isolati composti da esattamente 4 tessere, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		// Estrazione della matrice di gioco per passarla al metodo della classe padre
		Tile[][] grid = library.getLibrary();
		
		// Matrice di supporto per non conteggiare più volte le stesse tessere (visitata)
		boolean[][] visited = new boolean[Library.ROWS][Library.COLS];
		int countGroupsOfFour = 0;

		for (int i = 0; i < Library.ROWS; i++) {
			for (int j = 0; j < Library.COLS; j++) {
				Tile tile = grid[i][j];
				
				// Se troviamo una tessera valida e non ancora analizzata
				if (tile != null && !visited[i][j]) {
					Color clusterColor = tile.getColor();
					
					// Chiamata all'algoritmo ricorsivo ereditato per calcolare la dimensione esatta del gruppo.
					// L'algoritmo valuterà autonomamente la forma (L, T, quadrato o linea).
					int clusterSize = checkClusterSize(grid, i, j, clusterColor, visited);
					
					// Il gruppo deve essere composto da ESATTAMENTE quattro tessere adiacenti
					if (clusterSize == 4) {
						countGroupsOfFour++;
					}
				}
			}
		}

		// Restituisce true solo se sono stati trovati almeno 4 gruppi validi
		return countGroupsOfFour >= 4;
	}
}
