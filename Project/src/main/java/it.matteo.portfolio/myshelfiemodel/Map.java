package myshelfiemodel;

import java.util.Random;

/**
 * La classe Map rappresenta la plancia Soggiorno del gioco My Shelfie.
 * Gestisce la disposizione iniziale delle tessere in base al numero di giocatori,
 * la validazione della pesca e il ripristino automatico del tabellone.
 */
public class Map {

	public static final int SIZE = 9;
	private final Tile[][] grid = new Tile[SIZE][SIZE];

	// Il sacchetto contiene inizialmente 22 tessere per ciascuno dei 6 tipi (132 totali)
	private int blueTiles = 22;
	private int whiteTiles = 22;
	private int lightBlueTiles = 22;
	private int pinkTiles = 22;
	private int yellowTiles = 22;
	private int greenTiles = 22;

	/**
	 * Matrice di configurazione ufficiale della plancia Soggiorno (Simmetria Rotazionale).
	 * 0 = Spazio non utilizzabile (fuori dal tabellone)
	 * 2 = Spazio disponibile con 2, 3 o 4 giocatori
	 * 3 = Spazio con 3 puntini (disponibile con almeno 3 giocatori)
	 * 4 = Spazio con 4 puntini (disponibile solo con 4 giocatori)
	 */
	private static final int[][] BOARD_LAYOUT = {
			{0, 0, 0, 3, 4, 0, 0, 0, 0},
			{0, 0, 0, 2, 2, 4, 0, 0, 0},
			{0, 0, 3, 2, 2, 2, 3, 0, 0},
			{0, 4, 2, 2, 2, 2, 2, 2, 3},
			{4, 2, 2, 2, 2, 2, 2, 2, 4},
			{3, 2, 2, 2, 2, 2, 2, 4, 0},
			{0, 0, 3, 2, 2, 2, 3, 0, 0},
			{0, 0, 0, 4, 2, 2, 0, 0, 0},
			{0, 0, 0, 0, 4, 3, 0, 0, 0}
	};

	/**
	 * Costruisce la mappa di gioco e la configura in base al numero di giocatori.
	 *
	 * @param nPlayers il numero di giocatori (2, 3 o 4)
	 */
	public Map(int nPlayers) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int requiredPlayers = BOARD_LAYOUT[i][j];
				
				// Se lo spazio richiede più giocatori di quelli presenti o è 0, viene bloccato
				if (requiredPlayers == 0 || nPlayers < requiredPlayers) {
					grid[i][j] = new Tile(); // Tessera segnaposto (vuota/bloccata)
				} else {
					grid[i][j] = null; // Spazio di gioco valido, inizialmente vuoto
				}
			}
		}
		fillMap();
	}

	/**
	 * Ripristina il Soggiorno pescando nuove tessere Oggetto dal sacchetto
	 * e piazzandole casualmente in tutti gli spazi vuoti validi.
	 */
	public void fillMap() {
		Random rand = new Random();

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (grid[i][j] == null && getTotalRemainingTiles() > 0) {
					boolean tileAssigned = false;

					while (!tileAssigned) {
						int randomColor = rand.nextInt(6);
						tileAssigned = tryAssignTile(i, j, randomColor);
					}
				}
			}
		}
	}

	/**
	 * Tenta di assegnare una tessera di un colore specifico alla coordinata indicata.
	 * Riduce correttamente il contatore del sacchetto tramite decremento (--).
	 */
	private boolean tryAssignTile(int row, int col, int colorIndex) {
		switch (colorIndex) {
			case 0 -> { if (blueTiles > 0) { blueTiles--; grid[row][col] = new Tile(new Position(row, col), Color.BLUE); return true; } }
			case 1 -> { if (whiteTiles > 0) { whiteTiles--; grid[row][col] = new Tile(new Position(row, col), Color.WHITE); return true; } }
			case 2 -> { if (lightBlueTiles > 0) { lightBlueTiles--; grid[row][col] = new Tile(new Position(row, col), Color.LIGHT_BLUE); return true; } }
			case 3 -> { if (pinkTiles > 0) { pinkTiles--; grid[row][col] = new Tile(new Position(row, col), Color.PINK); return true; } }
			case 4 -> { if (yellowTiles > 0) { yellowTiles--; grid[row][col] = new Tile(new Position(row, col), Color.YELLOW); return true; } }
			case 5 -> { if (greenTiles > 0) { greenTiles--; grid[row][col] = new Tile(new Position(row, col), Color.GREEN); return true; } }
		}
		return false;
	}

	/**
	 * Verifica se la mappa contiene ancora tessere adiacenti.
	 * Se non ci sono tessere adiacenti, esegue automaticamente il ripristino del Soggiorno.
	 *
	 * @return true se il Soggiorno è stato ripristinato, false se si può continuare a giocare
	 */
	public boolean verifyMap() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (isPlayableTile(i, j)) {
					// Controllo ortogonale: verifica se c'è una tessera adiacente a destra o in basso
					boolean hasAdjacentRight = (j + 1 < SIZE) && isPlayableTile(i, j + 1);
					boolean hasAdjacentBottom = (i + 1 < SIZE) && isPlayableTile(i + 1, j);

					if (hasAdjacentRight || hasAdjacentBottom) {
						return false; // Trovata almeno una coppia adiacente, nessun ripristino
					}
				}
			}
		}
		fillMap(); // Tutte le tessere sono isolate: scatta il riempimento automatico
		return true;
	}

	/**
	 * Verifica se una tessera è pescabile secondo la regola del "lato libero".
	 * Una tessera è valida se è presente e se almeno uno dei suoi 4 lati non tocca un'altra tessera.
	 *
	 * @param position la posizione della tessera da verificare
	 * @return true se la tessera ha almeno un lato libero, false altrimenti
	 */
	public boolean verifyTile(Position position) {
		int x = position.getX();
		int y = position.getY();

		if (!isPlayableTile(x, y)) {
			System.out.println("Selezione non valida: nessuna tessera presente.");
			return false;
		}

		// Una tessera ha un lato libero se lo spazio adiacente è null, fuori tabellone o non valido (-1)
		boolean hasFreeSide = isSideFree(x - 1, y) || // Alto
				              isSideFree(x + 1, y) || // Basso
				              isSideFree(x, y - 1) || // Sinistra
				              isSideFree(x, y + 1);   // Destra

		if (!hasFreeSide) {
			System.out.println("Selezione non valida: la tessera non ha lati liberi.");
		}

		return hasFreeSide;
	}

	/**
	 * Verifica se un lato specifico della coordinata indicata risulta libero.
	 *
	 * @param r l'indice di riga
	 * @param c l'indice di colonna
	 * @return true se il lato è fuori dai confini, vuoto o non occupato da una tessera valida
	 */
	private boolean isSideFree(int r, int c) {
		if (r < 0 || r >= SIZE || c < 0 || c >= SIZE) {
			return true;
		}
		return grid[r][c] == null || grid[r][c].getPosition().getX() == -1;
	}

	/**
	 * Restituisce la tessera presente alla posizione specificata.
	 *
	 * @param p la posizione da controllare
	 * @return l'oggetto Tile corrispondente alla posizione
	 */
	public Tile getTile(Position p) {
		return this.grid[p.getX()][p.getY()];
	}

	/**
	 * Rimuove una tessera dal tabellone in seguito alla sua pescata, impostando lo spazio a null.
	 *
	 * @param position la posizione della tessera da rimuovere
	 */
	public void takeTile(Position position) {
		this.grid[position.getX()][position.getY()] = null;
	}

	/**
	 * Restituisce una copia della griglia di gioco attuale per preservare l'incapsulamento.
	 *
	 * @return una matrice bidimensionale clone del tabellone
	 */
	public Tile[][] getMap() {
		Tile[][] copy = new Tile[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			System.arraycopy(this.grid[i], 0, copy[i], 0, SIZE);
		}
		return copy;
	}

	/**
	 * Verifica se la cella contiene una tessera giocabile e valida (coordinata diversa da -1).
	 */
	private boolean isPlayableTile(int row, int col) {
		return grid[row][col] != null && grid[row][col].getPosition().getX() != -1;
	}

	/**
	 * Calcola il numero totale di tessere ancora presenti nel sacchetto.
	 */
	private int getTotalRemainingTiles() {
		return blueTiles + whiteTiles + lightBlueTiles + pinkTiles + yellowTiles + greenTiles;
	}

	/**
	 * Mostra a schermo una rappresentazione testuale a colori del tabellone Soggiorno.
	 * Utilizza i codici ANSI per mappare visivamente i colori delle tessere sulla CLI.
	 */
	public void visualmap() {
		System.out.print("\t");
		for (int j = 0; j < SIZE; j++) System.out.print(j + "\t");
		System.out.println("\n");

		for (int i = 0; i < SIZE; i++) {
			System.out.print(i + "\t");
			for (int j = 0; j < SIZE; j++) {
				if (isPlayableTile(i, j)) {
					switch (grid[i][j].getColor()) {
						case BLUE -> System.out.print("\u001B[34mB\033[0m\t");
						case GREEN -> System.out.print("\u001B[32mG\033[0m\t");
						case LIGHT_BLUE -> System.out.print("\u001B[36mL\033[0m\t");
						case PINK -> System.out.print("\u001B[35mP\033[0m\t");
						case WHITE -> System.out.print("\u001B[37mW\033[0m\t");
						case YELLOW -> System.out.print("\u001B[33mY\033[0m\t");
					}
				} else {
					System.out.print("---\t");
				}
			}
			System.out.println();
		}
	}
}
