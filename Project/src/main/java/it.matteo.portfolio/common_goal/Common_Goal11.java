package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;
import myshelfiemodel.Color;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.11.
 * Requisito: Cinque tessere dello stesso tipo (colore) che formano una "X".
 */
public class Common_Goal11 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal11 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal11(int nPlayers) {
		super(nPlayers);
		this.description = "Cinque tessere dello stesso tipo che formano una X.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 * Scansiona la libreria cercando il potenziale vertice superiore sinistro di una sotto-matrice 3x3.
	 *
	 * @param library la libreria di tessere da verificare.
	 * @return true se è presente almeno una conformazione a "X" valida, false altrimenti.
	 */
	@Override
	public boolean verify_goal(Library library) {
		// Una forma a X richiede una sotto-matrice di dimensioni 3x3.
		// Calcoliamo i limiti massimi di scansione per il punto di ancoraggio (top-left)
		int maxRowStart = Library.ROWS - 3; // 6 - 3 = 3
		int maxColStart = Library.COLS - 3; // 5 - 3 = 2

		for (int r = 0; r <= maxRowStart; r++) {
			for (int c = 0; c <= maxColStart; c++) {
				if (isXShape(library, r, c)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Verifica se esiste una forma a X omogenea partendo dall'ancoraggio (r, c).
	 * Il centro geometrico della struttura si trova in (r + 1, c + 1).
	 */
	private boolean isXShape(Library library, int r, int c) {
		// Analisi del centro geometrico della X
		Tile centerTile = library.getTile(new Position(r + 1, c + 1));
		if (centerTile == null) {
			return false;
		}
		
		Color targetColor = centerTile.getColor();

		// Coordinate relative ai 4 angoli della sotto-matrice 3x3
		int[][] cornerOffsets = {
			{0, 0}, // In alto a sinistra
			{0, 2}, // In alto a destra
			{2, 0}, // In basso a sinistra
			{2, 2}  // In basso a destra
		};

		// Verifica la validità e la corrispondenza cromatica di ciascun angolo
		for (int[] offset : cornerOffsets) {
			Tile cornerTile = library.getTile(new Position(r + offset[0], c + offset[1]));
			if (cornerTile == null || cornerTile.getColor() != targetColor) {
				return false;
			}
		}

		return true;
	}
}
