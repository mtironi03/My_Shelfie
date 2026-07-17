package personal_card;

import myshelfiemodel.Color;
import myshelfiemodel.Tile;
import myshelfiemodel.Position;

/**
 * Classe concreta che rappresenta la carta Obiettivo Personale n.7.
 * Estende la classe Personal_Card e definisce la specifica configurazione segreta 
 * di tessere (posizioni e colori) che il giocatore deve soddisfare nella propria libreria.
 */
public class Personal_Card07 extends Personal_Card {
	
	/**
	 * Costruisce l'oggetto Personal_Card07. 
	 * Inizializza la combinazione predefinita di posizioni e colori richiesti dalla carta.
	 */
	public Personal_Card07() {
		// Allineato con la variabile 'targetPositions' della classe padre Personal_Card
		targetPositions.add(new Tile(new Position(2, 0), Color.LIGHT_BLUE));
		targetPositions.add(new Tile(new Position(1, 4), Color.YELLOW));
		targetPositions.add(new Tile(new Position(0, 2), Color.WHITE));
		targetPositions.add(new Tile(new Position(5, 0), Color.GREEN));
		targetPositions.add(new Tile(new Position(3, 1), Color.PINK));
		targetPositions.add(new Tile(new Position(4, 3), Color.BLUE));
	}
}
