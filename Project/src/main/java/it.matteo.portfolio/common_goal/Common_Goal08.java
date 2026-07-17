package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;
import myshelfiemodel.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.8.
 * Requisito: Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti. 
 * Righe diverse possono avere combinazioni diverse di tipi di tessere.
 */
public class Common_Goal08 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal08 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal08(int nPlayers) {
		super(nPlayers);
		this.description = "Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti. Righe diverse possono avere combinazioni diverse di tipi di tessere.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria di tessere da verificare
	 * @return true se sono presenti almeno 4 righe complete con al massimo 3 colori diversi, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		int validRowsCount = 0;

		// Iterazione sulle righe della libreria
		for (int row = 0; row < Library.ROWS; row++) {
			boolean isRowFull = true;
			
			// Utilizzo di un Set per tracciare automaticamente i colori univoci presenti nella riga
			Set<Color> distinctColors = new HashSet<>();

			// Analisi delle tessere (colonne) della riga corrente
			for (int col = 0; col < Library.COLS; col++) {
				Tile tile = library.getTile(new Position(row, col));
				
				if (tile != null) {
					// Il Set memorizza solo colori unici, ignorando automaticamente i duplicati
					distinctColors.add(tile.getColor());
				} else {
					// Se troviamo uno spazio vuoto, la riga non è completa
					isRowFull = false;
					break;
				}
			}

			// Se la riga è piena e contiene al massimo 3 colori distinti (1, 2 o 3 tipi)
			if (isRowFull && distinctColors.size() <= 3) {
				validRowsCount++;
			}

			// L'obiettivo richiede almeno 4 righe valide per essere superato
			if (validRowsCount >= 4) {
				return true;
			}
		}

		return false;
	}
}
