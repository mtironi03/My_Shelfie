package personal_card;

import myshelfiemodel.Color;
import myshelfiemodel.Tile;
import myshelfiemodel.Position;

/**
 * Classe concreta che rappresenta la carta Obiettivo Personale n.10.
 * Estende la classe Personal_Card e definisce la specifica configurazione segreta 
 * di tessere (posizioni e colori) che il giocatore deve soddisfare nella propria libreria.
 */
public class Personal_Card10 extends Personal_Card {
	
	/**
	 * Costruisce l'oggetto Personal_Card10. 
	 * Inizializza la combinazione predefinita di posizioni e colori richiesti dalla carta.
	 */
	public Personal_Card10() {
		// Allineato con la variabile 'targetPositions' della classe padre Personal_Card
		targetPositions.add(new Tile(new Position(5, 4), Color.LIGHT_BLUE));
		targetPositions.add(new Tile(new Position(4, 1), Color.YELLOW));
		targetPositions.add(new Tile(new Position(3, 0), Color.WHITE));
		targetPositions.add(new Tile(new Position(2, 3), Color.GREEN));
		targetPositions.add(new Tile(new Position(0, 3), Color.PINK));
		targetPositions.add(new Tile(new Position(1, 1), Color.BLUE));
	}
}
