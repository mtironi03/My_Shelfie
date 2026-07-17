package common_goal;

import myshelfiemodel.Color;
import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.10.
 * Requisito: Due righe formate ciascuna da 5 diversi tipi di tessere.
 */
public class Common_Goal10 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal10 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal10(int nPlayers) {
		super(nPlayers);
		this.description = "Due righe formate ciascuna da 5 diversi tipi di tessere.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria di tessere da verificare
	 * @return true se sono presenti almeno 2 righe complete formate da 5 tessere di colore diverso, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		int validRowsCount = 0;

		// Iterazione sulle righe della libreria
		for (int row = 0; row < Library.ROWS; row++) {
			boolean isRowFull = true;
			
			// Il Set memorizzerà in automatico solo i colori univoci
			Set<Color> distinctColors = new HashSet<>();

			// Iterazione sulle colonne per esplorare l'intera riga
			for (int col = 0; col < Library.COLS; col++) {
				Tile tile = library.getTile(new Position(row, col));
				
				if (tile != null) {
					distinctColors.add(tile.getColor());
				} else {
					// Se troviamo uno spazio vuoto, la riga non è completa
					isRowFull = false;
					break; 
				}
			}

			// Se la riga è piena (nessun null) e contiene esattamente 5 colori distinti
			if (isRowFull && distinctColors.size() == 5) {
				validRowsCount++;
			}

			// L'obiettivo richiede almeno 2 righe valide
			if (validRowsCount >= 2) {
				return true;
			}
		}
		
		return false;
	}
}
