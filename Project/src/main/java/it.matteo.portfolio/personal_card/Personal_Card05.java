package personal_card;

import myshelfiemodel.Color;
import myshelfiemodel.Tile;
import myshelfiemodel.Position;

/**
 * Classe concreta che rappresenta la carta Obiettivo Personale n.5.
 * Estende la classe Personal_Card e definisce la specifica configurazione segreta 
 * di tessere (posizioni e colori) che il giocatore deve soddisfare nella propria libreria.
 */
public class Personal_Card05 extends Personal_Card {
	
	/**
	 * Costruisce l'oggetto Personal_Card05. 
	 * Inizializza la combinazione predefinita di posizioni e colori richiesti dalla carta.
	 */
	public Personal_Card05() {
		// Allineato con la variabile 'targetPositions' della classe padre Personal_Card
		targetPositions.add(new Tile(new Position(4, 1), Color.LIGHT_BLUE));
		targetPositions.add(new Tile(new Position(0, 0), Color.YELLOW));
		targetPositions.add(new Tile(new Position(2, 2), Color.WHITE));
		targetPositions.add(new Tile(new Position(0, 4), Color.GREEN));
		targetPositions.add(new Tile(new Position(1, 4), Color.PINK));
		targetPositions.add(new Tile(new Position(2, 1), Color.BLUE));
	}
}
