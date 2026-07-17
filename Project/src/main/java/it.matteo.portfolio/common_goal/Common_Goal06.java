package common_goal;

import myshelfiemodel.Color;
import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.6.
 * Requisito: Otto tessere dello stesso tipo (colore) in qualsiasi posizione della libreria. 
 * Non ci sono restrizioni sulla disposizione spaziale di queste tessere.
 */
public class Common_Goal06 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal06 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal06(int nPlayers) {
		super(nPlayers);
		this.description = "Otto tessere dello stesso tipo. Non ci sono restrizioni sulla posizione di queste tessere.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria di tessere da verificare
	 * @return true se sono presenti almeno 8 tessere dello stesso colore, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		// Array dimensionato dinamicamente in base al numero di colori disponibili
		int[] colorCounts = new int[Color.values().length];

		for (int r = 0; r < Library.ROWS; r++) {
			for (int c = 0; c < Library.COLS; c++) {
				Tile tile = library.getTile(new Position(r, c));
				
				if (tile != null) {
					// .ordinal() converte il colore nell'indice corrispondente dell'array (es. PINK=0, BLUE=1...)
					int colorIndex = tile.getColor().ordinal();
					colorCounts[colorIndex]++;
					
					// Controllo Immediato: verifichiamo solo il colore appena incrementato (Early Exit)
					if (colorCounts[colorIndex] >= 8) {
						return true; // Obiettivo raggiunto!
					}
				}
			}
		}
		
		// Se terminiamo la scansione di tutta la libreria senza mai raggiungere quota 8
		return false;
	}
}
