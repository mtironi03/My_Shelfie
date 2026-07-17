package myshelfiemodel;

/**
 * La classe Library rappresenta la libreria personale di un giocatore.
 * È strutturata come una griglia bidimensionale di tessere (Tile) e presenta
 * dimensioni fisse standard pari a 6 righe e 5 colonne.
 */
public class Library {

	// Definizione delle costanti per evitare i "numeri magici" e favorire la manutenibilità
	public static final int ROWS = 6;
	public static final int COLS = 5;

	private final Tile[][] grid = new Tile[ROWS][COLS]; // Matrice di tessere rappresentante la griglia della libreria

	/**
	 * Costruttore predefinito. Crea una libreria inizialmente vuota.
	 * In Java, gli elementi di una matrice di oggetti vengono automaticamente inizializzati a null.
	 */
	public Library() {
		
	}

	/**
	 * Costruttore di copia (Deep Copy).
	 * Crea una nuova istanza di Library duplicando accuratamente lo stato di un'altra libreria.
	 *
	 * @param other la libreria da copiare
	 */
	public Library(Library other) {
		if (other == null) return;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				Tile originalTile = other.grid[i][j]; 
				if (originalTile != null) {
					this.grid[i][j] = new Tile(originalTile.getPosition(), originalTile.getColor());
				} else {
					this.grid[i][j] = null;
				}
			}
		}
	}

	/**
	 * Restituisce una copia della matrice della libreria per preservare l'incapsulamento.
	 *
	 * @return una matrice bidimensionale di tessere che rappresenta lo stato attuale della libreria
	 */
	public Tile[][] getLibrary() {
		Tile[][] copy = new Tile[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			System.arraycopy(this.grid[i], 0, copy[i], 0, COLS);
		}
		return copy;
	}

	/**
	 * Restituisce la tessera situata alla posizione specificata.
	 *
	 * @param p la posizione della tessera da recuperare
	 * @return l'oggetto Tile in quella posizione, oppure null se la cella è vuota
	 */
	public Tile getTile(Position p) {
		if (p == null || isOutOfBounds(p.getX(), p.getY())) {
			return null;
		}
		return this.grid[p.getX()][p.getY()];
	}

	/**
	 * Inserisce una tessera nella posizione specificata della libreria.
	 *
	 * @param p    la posizione in cui collocare la tessera
	 * @param tile la tessera da inserire
	 */
	public void setTile(Position p, Tile tile) {
		if (p != null && !isOutOfBounds(p.getX(), p.getY())) {
			this.grid[p.getX()][p.getY()] = tile;
		}
	}

	/**
	 * Visualizza la libreria a console, mostrando le tessere presenti con i rispettivi colori tramite codici ANSI.
	 */
	public void visualLibrary() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				Tile tile = grid[i][j];
				if (tile != null) {
					// Utilizzo di uno switch statement per ottimizzare la leggibilità e la gestione dei colori dei caratteri
					switch (tile.getColor()) {
						case BLUE -> System.out.print("\u001B[34m B \033[0m");
						case GREEN -> System.out.print("\u001B[32m G \033[0m");
						case LIGHT_BLUE -> System.out.print("\u001B[36m L \033[0m"); 
						case PINK -> System.out.print("\u001B[35m P \033[0m");
						case WHITE -> System.out.print("\u001B[37m W \033[0m");
						case YELLOW -> System.out.print("\u001B[33m Y \033[0m");
						default -> System.out.print(" ? ");
					}
				} else {
					System.out.print(" 0 ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Verifica se la libreria è completamente piena.
	 *
	 * @return true se tutte le celle sono occupate da una tessera, false altrimenti
	 */
	public boolean isFull() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (this.grid[i][j] == null) {
					return false; // Interrompe immediatamente il controllo non appena rileva un alloggiamento vuoto
				}
			}
		}
		return true;
	}

	/**
	 * Metodo di utilità interno per verificare se le coordinate fornite eccedono i limiti della matrice.
	 *
	 * @param row l'indice di riga da verificare
	 * @param col l'indice di colonna da verificare
	 * @return true se la posizione si trova fuori dai confini della libreria, false altrimenti
	 */
	private boolean isOutOfBounds(int row, int col) {
		return row < 0 || row >= ROWS || col < 0 || col >= COLS;
	}
}
