package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Tile;
import myshelfiemodel.Position;
import myshelfiemodel.Color;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.1.
 * Requisito: Sei gruppi separati formati ciascuno da esattamente due tessere 
 * adiacenti dello stesso tipo. I colori dei diversi gruppi possono differire tra loro.
 */
public class Common_Goal01 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal01 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal01(int nPlayers) {
		super(nPlayers);
		this.description = "Sei gruppi separati formati ciascuno da esattamente due tessere adiacenti dello stesso tipo. I gruppi possono essere di colori differenti.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria del giocatore da esaminare
	 * @return true se sono presenti almeno 6 gruppi isolati composti da esattamente 2 tessere, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		// Matrice della libreria recuperata tramite copia per ispezione autonoma
		Tile[][] grid = library.getLibrary();
		boolean[][] visited = new boolean[Library.ROWS][Library.COLS];
		int countGroupsOfTwo = 0;

		for (int i = 0; i < Library.ROWS; i++) {
			for (int j = 0; j < Library.COLS; j++) {
				Tile tile = grid[i][j];
				
				// Se troviamo una tessera reale non ancora visitata
				if (tile != null && !visited[i][j]) {
					Color clusterColor = tile.getColor();
					
					// Riutilizza l'algoritmo Flood-Fill ereditato dalla classe padre Common_Goal
					int clusterSize = checkClusterSize(grid, i, j, clusterColor, visited);
					
					// Regola ufficiale: il gruppo deve essere isolato e composto da ESATTAMENTE due tessere
					if (clusterSize == 2) {
						countGroupsOfTwo++;
					}
				}
			}
		}

		// L'obiettivo è superato se si contano almeno 6 gruppi distinti da due tessere
		return countGroupsOfTwo >= 6;
	}
}
