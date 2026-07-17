package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;
import myshelfiemodel.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.5.
 * Requisito: Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti. 
 * Colonne diverse possono avere combinazioni diverse di tipi di tessere.
 */
public class Common_Goal05 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal05 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal05(int nPlayers) {
		super(nPlayers);
		this.description = "Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti. Colonne diverse possono avere combinazioni diverse di tipi di tessere.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria da verificare
	 * @return true se sono presenti almeno 3 colonne complete con al massimo 3 colori diversi, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		int validColumnsCount = 0;

		// Iterazione sulle colonne della libreria
		for (int col = 0; col < Library.COLS; col++) {
			boolean isColumnFull = true;
			
			// Utilizzo di un Set per collezionare e contare automaticamente i colori univoci
			Set<Color> distinctColors = new HashSet<>();

			// Analisi delle tessere (righe) della colonna corrente
			for (int row = 0; row < Library.ROWS; row++) {
				Tile tile = library.getTile(new Position(row, col));
				
				if (tile != null) {
					// Il Set aggiunge il colore. Se è già presente, viene semplicemente ignorato.
					distinctColors.add(tile.getColor());
				} else {
					// Se troviamo anche solo uno spazio vuoto, la colonna non è completa
					isColumnFull = false;
					break;
				}
			}

			// Se la colonna è piena e contiene un massimo di 3 colori distinti (1, 2 o 3 tipi)
			if (isColumnFull && distinctColors.size() <= 3) {
				validColumnsCount++;
			}

			// L'obiettivo richiede esattamente 3 colonne valide
			if (validColumnsCount >= 3) {
				return true;
			}
		}

		return false;
	}
}
