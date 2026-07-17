package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;
import myshelfiemodel.Color;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.7.
 * Requisito: Cinque tessere dello stesso tipo (colore) che formano una linea diagonale continua.
 */
public class Common_Goal07 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal07 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal07(int nPlayers) {
		super(nPlayers);
		this.description = "Cinque tessere dello stesso tipo che formano una diagonale.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 * Ispeziona le quattro possibili traiettorie diagonali da 5 tessere permesse dalla griglia.
	 *
	 * @param library la libreria di tessere da verificare
	 * @return true se è presente almeno una diagonale valida di 5 tessere, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		int lastColIndex = Library.COLS - 1;

		// Controllo delle due diagonali possibili in direzione discendente destra ↘ (step riga +1, step colonna +1)
		if (checkDiagonal(library, 0, 0, 1, 1)) return true;
		if (checkDiagonal(library, 1, 0, 1, 1)) return true;

		// Controllo delle due diagonali possibili in direzione discendente sinistra ↙ (step riga +1, step colonna -1)
		if (checkDiagonal(library, 0, lastColIndex, 1, -1)) return true;
		if (checkDiagonal(library, 1, lastColIndex, 1, -1)) return true;

		return false;
	}

	/**
	 * Metodo helper che analizza la presenza di una traiettoria diagonale rettilinea 
	 * composta da 5 tessere dello stesso colore.
	 *
	 * @param library  la libreria del giocatore
	 * @param startRow la riga di partenza della diagonale
	 * @param startCol la colonna di partenza della diagonale
	 * @param rowStep  l'incremento o decremento della riga per ogni passo (+1 o -1)
	 * @param colStep  l'incremento o decremento della colonna per ogni passo (+1 o -1)
	 * @return true se la diagonale di 5 tessere dello stesso colore è interamente presente, false altrimenti
	 */
	private boolean checkDiagonal(Library library, int startRow, int startCol, int rowStep, int colStep) {
		// Otteniamo la prima tessera per stabilire il colore di riferimento
		Tile firstTile = library.getTile(new Position(startRow, startCol));
		
		// Se la casella di partenza è vuota, la diagonale non può essere formata
		if (firstTile == null) {
			return false;
		}
		
		Color targetColor = firstTile.getColor();

		// Controlliamo le successive 4 tessere lungo il vettore di movimento
		for (int i = 1; i < 5; i++) {
			int nextRow = startRow + (i * rowStep);
			int nextCol = startCol + (i * colStep);
			
			Tile currentTile = library.getTile(new Position(nextRow, nextCol));
			
			// Se incontriamo una cella vuota o un colore discordante, la traiettoria fallisce
			if (currentTile == null || currentTile.getColor() != targetColor) {
				return false;
			}
		}

		// Se il ciclo si completa senza interruzioni premature, la diagonale è valida
		return true;
	}
}
