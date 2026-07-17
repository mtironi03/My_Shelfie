package myshelfiemodel;

/**
 * La classe Tile rappresenta una tessera del gioco.
 * Ogni tessera è caratterizzata da una posizione (sulla plancia o nella libreria)
 * e da un colore specifico.
 */
public class Tile {

    private Position position;
    private Color color;

    /**
     * Costruisce un oggetto Tile con la posizione e il colore specificati.
     *
     * @param position la posizione occupata dalla tessera
     * @param color    il colore associato alla tessera
     */
    public Tile(Position position, Color color) {
        this.position = position;
        this.color = color;
    }

    /**
     * Costruttore di default.
     * Crea una tessera con una posizione iniziale vuota e senza alcun colore (null).
     */
    public Tile() {
        this.position = new Position();
        this.color = null; // Esplicitato per una migliore leggibilità del codice
    }

    /**
     * Restituisce il colore della tessera.
     *
     * @return il colore della tessera
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Restituisce la posizione attuale della tessera.
     *
     * @return l'oggetto Position associato alla tessera
     */
    public Position getPosition() {
        return this.position;
    }
}
