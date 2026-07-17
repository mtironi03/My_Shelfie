package common_goal;

import myshelfiemodel.Color;
import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.9.
 * Requisito: Due colonne formate ciascuna da 6 diversi tipi di tessere.
 */
public class Common_Goal09 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal09 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal09(int nPlayers) {
		super(nPlayers);
		this.description = "Due colonne formate ciascuna da 6 diversi tipi di tessere.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria di tessere da verificare
	 * @return true se sono presenti almeno 2 colonne complete formate da 6 tessere di colore diverso, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		int validColumnsCount = 0;

		// Iterazione sulle colonne della libreria
		for (int col = 0; col < Library.COLS; col++) {
			boolean isColumnFull = true;
			
			// Il Set memorizzerà in automatico solo i colori univoci
			Set<Color> distinctColors = new HashSet<>();

			// Iterazione sulle righe per esplorare l'intera colonna
			for (int row = 0; row < Library.ROWS; row++) {
				Tile tile = library.getTile(new Position(row, col));
				
				if (tile != null) {
					distinctColors.add(tile.getColor());
				} else {
					// Appena troviamo uno spazio vuoto, sappiamo che la colonna non è completa
					isColumnFull = false;
					break;
				}
			}

			// Se la colonna è piena (nessun null) e contiene esattamente 6 colori distinti
			if (isColumnFull && distinctColors.size() == 6) {
				validColumnsCount++;
			}

			// L'obiettivo richiede almeno 2 colonne valide
			if (validColumnsCount >= 2) {
				return true;
			}
		}
		
		return false;
	}
}
