package personal_card;

import myshelfiemodel.Color;
import myshelfiemodel.Tile;
import myshelfiemodel.Position;

/**
 * Classe concreta che rappresenta la carta Obiettivo Personale n.1.
 * Estende la classe Personal_Card e definisce la specifica configurazione segreta 
 * di tessere (posizioni e colori) che il giocatore deve soddisfare nella propria libreria.
 */
public class Personal_Card01 extends Personal_Card {

	/**
	 * Costruisce l'oggetto Personal_Card01.
	 * Inizializza la combinazione predefinita di posizioni e colori richiesti dalla carta.
	 */
	public Personal_Card01() {
		// Allineato con la variabile 'targetPositions' della classe padre Personal_Card
		targetPositions.add(new Tile(new Position(0, 1), Color.LIGHT_BLUE));
		targetPositions.add(new Tile(new Position(2, 1), Color.YELLOW));
		targetPositions.add(new Tile(new Position(3, 3), Color.WHITE));
		targetPositions.add(new Tile(new Position(4, 4), Color.GREEN));
		targetPositions.add(new Tile(new Position(5, 0), Color.PINK));
		targetPositions.add(new Tile(new Position(5, 2), Color.BLUE));
	}
}
