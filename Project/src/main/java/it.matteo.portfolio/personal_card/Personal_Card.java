package personal_card;

import java.util.ArrayList;
import java.util.List;

import myshelfiemodel.Library;
import myshelfiemodel.Position;
import myshelfiemodel.Tile;

/**
 * Classe astratta che rappresenta una carta Obiettivo Personale.
 * Gestisce le posizioni e i colori bersaglio richiesti all'interno della libreria del giocatore.
 */
public abstract class Personal_Card {
	
	// Lista di tessere che definiscono le coordinate e i colori bersaglio sulla libreria.
	protected ArrayList<Tile> targetPositions = new ArrayList<>();

	/**
	 * Restituisce la lista delle tessere che definiscono le posizioni e i colori bersaglio.
	 *
	 * @return La lista delle tessere (posizioni e colori) della carta personale.
	 */
	public ArrayList<Tile> getColorPositions() {
		return this.targetPositions;
	}

	/**
	 * Genera una rappresentazione visiva della carta personale.
	 * Crea una libreria virtuale temporanea, vi posiziona le tessere bersaglio 
	 * e ne esegue la stampa a schermo.
	 */
	public void visualPersonalCard() {
		Library virtualLibrary = new Library();
		for (Tile targetTile : targetPositions) {
			virtualLibrary.setTile(targetTile.getPosition(), targetTile); 
		}
		virtualLibrary.visualLibrary();
	}

	/**
	 * Calcola i punti conseguiti confrontando la libreria reale del giocatore 
	 * con i requisiti spaziali e cromatici della carta Obiettivo Personale.
	 *
	 * @param playerLibrary La libreria del giocatore da valutare.
	 * @return Il punteggio totale ottenuto (1, 2, 4, 6, 9 o 12 punti) in base alle corrispondenze esatte.
	 */
	public int calculatePoints(Library playerLibrary) {
		int matches = 0;

		// Verifica ogni singola tessera richiesta dalla carta
		for (Tile targetTile : targetPositions) {
			Position targetPos = targetTile.getPosition();
			Tile libraryTile = playerLibrary.getTile(targetPos);
			
			// Se nella libreria è presente una tessera nella posizione corretta e il colore coincide col bersaglio
			if (libraryTile != null && libraryTile.getColor() == targetTile.getColor()) {
				matches++;
			}
		}

		// Assegnazione dei punti in base al regolamento ufficiale utilizzando una switch expression
		return switch (matches) {
			case 1 -> 1;
			case 2 -> 2;
			case 3 -> 4;
			case 4 -> 6;
			case 5 -> 9;
			case 6 -> 12;
			default -> 0; // 0 corrispondenze comportano 0 punti
		};
	}
}
