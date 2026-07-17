package common_goal;

import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Color;
import myshelfiemodel.Tile;

/**
 * Classe concreta che rappresenta l'Obiettivo Comune n.2.
 * Requisito: Quattro tessere dello stesso tipo posizionate ai quattro angoli della libreria.
 */
public class Common_Goal02 extends Common_Goal {

	/**
	 * Costruisce l'oggetto Common_Goal02 configurando i gettoni in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori della partita
	 */
	public Common_Goal02(int nPlayers) {
		super(nPlayers);
		this.description = "Quattro tessere dello stesso tipo ai quattro angoli della libreria.";
	}

	/**
	 * Verifica se l'obiettivo è stato raggiunto all'interno della libreria analizzata.
	 *
	 * @param library la libreria del giocatore da esaminare
	 * @return true se i quattro angoli contengono tessere dello stesso colore, false altrimenti
	 */
	@Override
	public boolean verify_goal(Library library) {
		// Utilizzo delle costanti di Library per evitare i "Magic Numbers" (es. 5 e 4 hardcoded)
		int maxRow = Library.ROWS - 1;
		int maxCol = Library.COLS - 1;

		// Estrazione delle tessere ai quattro estremi della matrice
		Tile topLeft = library.getTile(new Position(0, 0));
		Tile topRight = library.getTile(new Position(0, maxCol));
		Tile bottomLeft = library.getTile(new Position(maxRow, 0));
		Tile bottomRight = library.getTile(new Position(maxRow, maxCol));

		// Se anche solo un angolo è vuoto (null), l'obiettivo non è raggiunto
		if (topLeft == null || topRight == null || bottomLeft == null || bottomRight == null) {
			return false;
		}

		// Isoliamo il colore della tessera in alto a sinistra come riferimento
		Color targetColor = topLeft.getColor();

		// Verifichiamo in modo lineare che gli altri tre angoli condividano lo stesso colore
		return topRight.getColor() == targetColor &&
		       bottomLeft.getColor() == targetColor &&
		       bottomRight.getColor() == targetColor;
	}
}
