package personal_card;

import myshelfiemodel.Color;
import myshelfiemodel.Tile;
import myshelfiemodel.Position;

/**
 * La classe Personal_Card02 rappresenta una specifica carta Obiettivo Personale. 
 * Estende la classe Personal_Card e implementa una configurazione predefinita delle
 * posizioni delle tessere sulla libreria.
 */
public class Personal_Card02 extends Personal_Card {
	
	/**
	 * Costruttore di Personal_Card02. 
	 * Inizializza la configurazione predefinita delle posizioni delle tessere sulla libreria.
	 */
	public Personal_Card02() {
		// Allineato con la variabile 'targetPositions' della classe padre Personal_Card
		targetPositions.add(new Tile(new Position(1, 3), Color.LIGHT_BLUE));
		targetPositions.add(new Tile(new Position(3, 2), Color.YELLOW));
		targetPositions.add(new Tile(new Position(2, 4), Color.WHITE));
		targetPositions.add(new Tile(new Position(3, 0), Color.GREEN));
		targetPositions.add(new Tile(new Position(4, 1), Color.PINK));
		targetPositions.add(new Tile(new Position(0, 4), Color.BLUE));
	}
}
