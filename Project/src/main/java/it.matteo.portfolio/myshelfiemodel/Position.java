package myshelfiemodel;

import java.util.Objects;

/**
 * La classe Position rappresenta una coordinata spaziale immutabile all'interno 
 * di un sistema bidimensionale (x, y).
 */
public class Position {

	// L'utilizzo di campi 'final' garantisce l'immutabilità della classe, 
	// favorendo la thread-safety e l'aderenza ai principi di clean code.
	private final int x;
	private final int y;

	/**
	 * Costruisce un oggetto Position con le coordinate specificate.
	 *
	 * @param x la coordinata dell'asse delle ascisse (riga)
	 * @param y la coordinata dell'asse delle ordinate (colonna)
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Costruisce un oggetto Position con coordinate predefinite impostate a (-1, -1).
	 * Viene tipicamente utilizzato per rappresentare una posizione non ancora assegnata, 
	 * vuota o non valida all'interno del tabellone.
	 */
	public Position() {
		this.x = -1;
		this.y = -1;
	}

	/**
	 * Restituisce la coordinata x (riga).
	 *
	 * @return la coordinata x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Restituisce la coordinata y (colonna).
	 *
	 * @return la coordinata y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Verifica l'uguaglianza logica tra questo oggetto e un altro oggetto.
	 * Due posizioni sono considerate uguali se e solo se condividono le medesime coordinate x e y.
	 *
	 * @param o l'oggetto da confrontare con questa istanza
	 * @return true se gli oggetti sono logicamente equivalenti, false altrimenti
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Position position = (Position) o;
		return this.x == position.x && this.y == position.y;
	}

	/**
	 * Calcola il valore di hash per l'oggetto, basandosi sulle coordinate x e y.
	 * Questo metodo è fondamentale per garantire il corretto funzionamento dell'oggetto 
	 * all'interno di collezioni basate su hash (es. HashSet, HashMap).
	 *
	 * @return il valore di hash calcolato
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * Restituisce una rappresentazione testuale della posizione.
	 * Particolarmente utile per le operazioni di logging e debugging del software.
	 *
	 * @return una stringa nel formato "Position{x=..., y=...}"
	 */
	@Override
	public String toString() {
		return "Position{" + "x=" + x + ", y=" + y + '}';
	}
}
