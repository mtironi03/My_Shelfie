package myshelfiemodel;

import java.util.ArrayList;
import java.security.SecureRandom;
import personal_card.*;
import java.util.Scanner;

/**
 * La classe Player rappresenta un giocatore all'interno della partita.
 * Gestisce lo stato del giocatore (punteggio, libreria, carta personale)
 * e le azioni attive come la scelta delle tessere e il loro posizionamento.
 */
public class Player {
	private int id;
	private String name;
	private int points;
	private boolean chair; // Indica se il giocatore è il "Primo Giocatore" (ha il segnalino sedia)
	private Personal_Card personalCard;
	public Library library;

	/**
	 * Crea un nuovo oggetto Player inizializzandone i parametri fondamentali.
	 * Assegna automaticamente una carta personale casuale tra quelle ancora disponibili.
	 *
	 * @param name                       il nome del giocatore
	 * @param id                         l'identificativo numerico del giocatore
	 * @param chair                      true se il giocatore possiede il segnalino del primo giocatore, false altrimenti
	 * @param arrayPersonalCardAvailable un array booleano che tiene traccia delle carte personali non ancora assegnate
	 */
	public Player(String name, int id, boolean chair, boolean[] arrayPersonalCardAvailable) {
		this.name = name;
		this.chair = chair;
		this.id = id;
		this.points = 0;
		this.library = new Library();
		this.personalCard = getRandomObject(arrayPersonalCardAvailable);
	}

	/**
	 * Seleziona e assegna in modo pseudo-casuale (sicuro) un oggetto Personal_Card 
	 * pescando tra quelle ancora segnate come disponibili nell'array fornito.
	 *
	 * @param arrayPersonalCardAvailable l'array che mappa la disponibilità delle carte personali
	 * @return un'istanza derivata di Personal_Card assegnata al giocatore
	 */
	Personal_Card getRandomObject(boolean[] arrayPersonalCardAvailable) {
		int upperbound = 12;
		int r;
		boolean found = false;
		
		do {
			SecureRandom rand = new SecureRandom(); // Genera un numero casuale crittograficamente sicuro
			r = rand.nextInt(upperbound) + 1; // Genera un valore tra 1 e 12
			
			if (arrayPersonalCardAvailable[r - 1]) {
				found = true;
				arrayPersonalCardAvailable[r - 1] = false; // Segna la carta come assegnata
			}
		} while (!found);

		switch (r) {
			case 1: return new Personal_Card01();
			case 2: return new Personal_Card02();
			case 3: return new Personal_Card03();
			case 4: return new Personal_Card04();
			case 5: return new Personal_Card05();
			case 6: return new Personal_Card06();
			case 7: return new Personal_Card07();
			case 8: return new Personal_Card08();
			case 9: return new Personal_Card09();
			case 10: return new Personal_Card10();
			case 11: return new Personal_Card11();
			case 12: return new Personal_Card12();
			default: return null;
		}
	}

	/**
	 * Restituisce l'identificativo del giocatore.
	 *
	 * @return l'ID del giocatore
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Restituisce i punti vittoria attuali del giocatore.
	 *
	 * @return il punteggio del giocatore
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * Aggiunge un valore al punteggio complessivo del giocatore.
	 *
	 * @param points i punti vittoria da sommare al totale
	 */
	public void addPoints(int points) {
		this.points += points;
	}

	/**
	 * Restituisce il nome del giocatore.
	 *
	 * @return il nome del giocatore in formato stringa
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Imposta questo giocatore come detentore del segnalino "Primo Giocatore".
	 */
	public void setChairTrue() {
		this.chair = true;
	}

	/**
	 * Restituisce una rappresentazione testuale dello stato attuale del giocatore.
	 *
	 * @return una stringa formattata contenente ID, nome, stato della sedia, punti e carta personale
	 */
	@Override
	public String toString() {
		return " Id: " + this.id + "\n Name: " + this.name + "\n Chair: " + this.chair 
				+ "\n Points: " + this.points + "\n Personal Card: " + this.personalCard.getClass().getSimpleName() + "\n";
	}

	/**
	 * Gestisce la logica di interazione con l'utente per la selezione delle tessere dal tabellone.
	 * Verifica che il numero di tessere e le coordinate inserite siano valide e che le tessere 
	 * selezionate formino una linea retta adiacente.
	 *
	 * @param map      l'istanza della mappa centrale del gioco (Living Room)
	 * @param nPlayers il numero totale di giocatori nella partita
	 * @param sc       lo Scanner passato dal Main per gestire l'input utente
	 * @return true se il processo di selezione e inserimento va a buon fine, false in caso di selezione non valida
	 */
	public boolean chooseTile(Map map, int nPlayers, Scanner sc) {
		int choice;

		do {
			System.out.println("Quante tessere desideri pescare? Inserisci un numero da 1 a 3:");
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				choice = -1;
			}

			if (choice > 3 || choice <= 0) {
				System.out.println("Numero non valido! Inserisci un valore compreso tra 1 e 3.\n");
				System.out.println("---------------------------------------");
			}
		} while (choice > 3 || choice <= 0);

		Position[] pos = new Position[choice];
		int x, y;

		for (int i = 0; i < choice; i++) {
			System.out.println("------------------------------------");
			System.out.println("Inserisci le coordinate per la tessera " + (i + 1) + ":");
			
			do {
				System.out.print("\tInserisci la riga (0-8): ");
				try {
					x = Integer.parseInt(sc.nextLine());
				} catch (Exception e) {
					x = -1;
				}
				if (x > 8 || x < 0) {
					System.out.println("Valore non valido! Inserisci un numero tra 0 e 8.\n");
				}
			} while (x > 8 || x < 0);

			do {
				System.out.print("\tInserisci la colonna (0-8): ");
				try {
					y = Integer.parseInt(sc.nextLine());
				} catch (Exception e) {
					y = -1;
				}
				if (y > 8 || y < 0) {
					System.out.println("Valore non valido! Inserisci un numero tra 0 e 8.\n");
				}
			} while (y > 8 || y < 0);
			
			pos[i] = new Position(x, y);

			// Verifica se la singola tessera rispetta la regola del "lato libero"
			if (!map.verifyTile(pos[i])) {
				return false;
			}

			// Verifica l'adiacenza e la linearità (ortogonale) se si pescano più tessere
			if (i > 0) {
				if (!((pos[i].getX() - 1 == pos[i - 1].getX() && pos[i].getY() == pos[i - 1].getY())
						|| (pos[i].getX() + 1 == pos[i - 1].getX() && pos[i].getY() == pos[i - 1].getY())
						|| (pos[i].getY() + 1 == pos[i - 1].getY() && pos[i - 1].getX() == pos[i].getX())
						|| (pos[i].getY() - 1 == pos[i - 1].getY() && pos[i - 1].getX() == pos[i].getX()))) {
					System.out.println("Errore: La tessera selezionata non è adiacente ortogonalmente alla precedente.");
					System.out.println("---------------------------------------");
					return false;
				}

				if (i > 1) {
					if (!(pos[0].getX() == pos[2].getX() || pos[0].getY() == pos[2].getY())) {
						System.out.println("Errore: Le tessere selezionate devono formare una linea retta.");
						System.out.println("---------------------------------------");
						return false;
					}
				}
			}
		}

		// Se la selezione è valida, procede all'inserimento nella libreria
		this.putInLibrary(pos, map, nPlayers, sc);
		return true;
	}

	/**
	 * Calcola i punti vittoria derivanti dal completamento della carta obiettivo personale.
	 *
	 * @return i punti vittoria ottenuti verificando le corrispondenze nella libreria
	 */
	public int verifyPersonalCard() {
		int cont = 0;
		int earnedPoints = 0;
		ArrayList<Tile> targetPositions = personalCard.getColorPositions();
		Tile[][] libraryMatrix = library.getLibrary();
		
		for (int i = 0; i < targetPositions.size(); i++) {
			Position p = targetPositions.get(i).getPosition();
			Color c = targetPositions.get(i).getColor();
			
			if (libraryMatrix[p.getX()][p.getY()] != null) {
				if (libraryMatrix[p.getX()][p.getY()].getColor() == c) {
					cont++;
				}
			}
		}

		switch (cont) {
			case 1: earnedPoints = 1; break;
			case 2: earnedPoints = 2; break;
			case 3: earnedPoints = 4; break;
			case 4: earnedPoints = 6; break;
			case 5: earnedPoints = 9; break;
			case 6: earnedPoints = 12; break;
		}
		
		return earnedPoints;
	}

	/**
	 * Restituisce l'oggetto carta personale assegnato al giocatore.
	 *
	 * @return l'istanza della carta personale (Personal_Card)
	 */
	public Personal_Card getPersonalCard() {
		return personalCard;
	}

	/**
	 * Gestisce la logica di interazione per ordinare le tessere pescate e inserirle 
	 * in una specifica colonna della libreria personale ("caduta di gravità").
	 *
	 * @param p        l'array delle posizioni originarie delle tessere sul tabellone
	 * @param map      il tabellone di gioco
	 * @param nPlayers il numero di giocatori
	 * @param sc       lo Scanner per la lettura dell'input utente
	 */
	public void putInLibrary(Position[] p, Map map, int nPlayers, Scanner sc) {
		
		System.out.println("\nHai pescato le seguenti tessere:");
		for (int i = 0; i < p.length; i++) {
			Tile tile = map.getTile(p[i]);
			switch (tile.getColor()) {
				case BLUE -> System.out.print("\u001B[34m" + tile.getColor() + "\033[0m\t\t");
				case GREEN -> System.out.print("\u001B[32m" + tile.getColor() + "\033[0m\t\t");
				case LIGHT_BLUE -> System.out.print("\u001B[36m" + tile.getColor() + "\033[0m\t\t");
				case PINK -> System.out.print("\u001B[35m" + tile.getColor() + "\033[0m\t\t");
				case WHITE -> System.out.print("\u001B[37m" + tile.getColor() + "\033[0m\t\t");
				case YELLOW -> System.out.print("\u001B[33m" + tile.getColor() + "\033[0m\t\t");
			}
		}
		System.out.println();

		Position[] p1 = new Position[p.length];

		// Scelta dell'ordine di inserimento (dal basso verso l'alto)
		for (int i = 1; i < p.length; i++) {
			int choice;
			do {
				System.out.println("Scegli l'ordine delle " + p.length + " tessere. Quale vuoi posizionare nello slot n." + i + " partendo dal basso? (Inserisci l'indice 1, 2 o 3)");
				try {
					choice = Integer.parseInt(sc.nextLine());
				} catch (Exception e) {
					choice = -1;
				}

				if (choice > 3 || choice <= 0) {
					System.out.println("Valore non valido! Inserisci un numero tra 1 e 3.\n");
				}
			} while (choice > 3 || choice <= 0);
			
			p1[i - 1] = new Position(p[choice - 1].getX(), p[choice - 1].getY());
			p[choice - 1] = null;
		}
		
		for (int i = 0; i < p.length; i++) {
			if (p[i] != null) {
				p1[p.length - 1] = p[i];
			}
		}

		// Scelta della colonna bersaglio nella libreria
		int input = 0;
		do {
			System.out.println("In quale colonna vuoi inserirle? (Inserisci un numero da 1 a 5)");
			try {
				input = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				input = -1;
			}
			if (input > 5 || input <= 0) {
				System.out.println("Numero di colonna non valido!\n");
			}
		} while (input > 5 || input <= 0);

		input = input - 1; // Allineamento all'indice dell'array (0-4)

		// Trova la prima cella libera nella colonna partendo dal basso
		int i = 5; 
		while (i >= 0 && this.library.getTile(new Position(i, input)) != null) {
			i = i - 1;
		}
		
		int cont = 0;
		try {
			// Tenta l'inserimento simulando la caduta (gravità)
			for (int f = 0; f < p1.length; f++) {
				this.library.setTile(new Position(i - f, input), map.getTile(p1[f]));
				cont++;
			}
			// Se l'inserimento ha successo, rimuove effettivamente le tessere dal tabellone
			for (int j = 0; j < p1.length; j++) {
				map.takeTile(p1[j]);
			}
		} catch (Exception e) {
			// Blocco di ripristino (Rollback): se la colonna non ha spazio sufficiente (ArrayIndexOutOfBoundsException), annulla l'inserimento parziale
			for (int f = 0; f < cont; f++) {
				this.library.setTile(new Position(i - f, input), null);
			}
			System.out.println("Spazio insufficiente nella colonna selezionata! L'azione è stata annullata.");
			System.out.println("---------------------------------------");
			
			// Richiede al giocatore di ripetere la scelta
			while (!chooseTile(map, nPlayers, sc));
			return; 
		}
	}

	/**
	 * Verifica se il giocatore è il primo di turno ("Presidente").
	 *
	 * @return true se il giocatore possiede la sedia del primo turno, false altrimenti
	 */
	public boolean isChair() {
		return chair;
	}

	/**
	 * Algoritmo ricorsivo di esplorazione in profondità (DFS - Depth First Search).
	 * Naviga la matrice della libreria partendo da una coordinata specifica per individuare
	 * ed eliminare blocchi di tessere adiacenti dello stesso colore, contando gli elementi del gruppo.
	 *
	 * @param lib   la libreria da ispezionare
	 * @param t1    la posizione (nodo) iniziale o corrente per la verifica
	 * @param c     il colore da confrontare per stabilire l'appartenenza al gruppo
	 * @param count il contatore parziale delle tessere trovate (e rimosse) nel blocco
	 * @return il conteggio totale delle tessere facenti parte del gruppo adiacente ispezionato
	 */
	public int remove_adjacency(Library lib, Position t1, Color c, int count) {
		
		// Controllo Basso
		if (t1.getX() + 1 < Library.ROWS) {
			if (lib.getTile(new Position(t1.getX() + 1, t1.getY())) != null) {
				if (lib.getTile(new Position(t1.getX() + 1, t1.getY())).getColor() == c) {
					count++;
					lib.setTile(new Position(t1.getX() + 1, t1.getY()), null);
					count = remove_adjacency(lib, new Position(t1.getX() + 1, t1.getY()), c, count);
				}
			}
		}
		// Controllo Destra
		if (t1.getY() + 1 < Library.COLS) {
			if (lib.getTile(new Position(t1.getX(), t1.getY() + 1)) != null) {
				if (lib.getTile(new Position(t1.getX(), t1.getY() + 1)).getColor() == c) {
					count++;
					lib.setTile(new Position(t1.getX(), t1.getY() + 1), null);
					count = remove_adjacency(lib, new Position(t1.getX(), t1.getY() + 1), c, count);
				}
			}
		}
		// Controllo Sinistra
		if (t1.getY() - 1 >= 0) {
			if (lib.getTile(new Position(t1.getX(), t1.getY() - 1)) != null) {
				if (lib.getTile(new Position(t1.getX(), t1.getY() - 1)).getColor() == c) {
					count++;
					lib.setTile(new Position(t1.getX(), t1.getY() - 1), null);
					count = remove_adjacency(lib, new Position(t1.getX(), t1.getY() - 1), c, count);
				}
			}
		}
		// Controllo Cella Corrente
		if (lib.getTile(new Position(t1.getX(), t1.getY())) != null
				&& lib.getTile(new Position(t1.getX(), t1.getY())).getColor() == c) {
			lib.setTile(new Position(t1.getX(), t1.getY()), null);
			count++;
		}

		return count;
	}

	/**
	 * Calcola e assegna i punti vittoria di fine partita (Endgame Scoring).
	 * Esegue una scansione completa della libreria per individuare gruppi di tessere 
	 * adiacenti dello stesso colore avvalendosi dell'algoritmo di visita ricorsivo (remove_adjacency).
	 * I punti vengono assegnati secondo la tabella ufficiale del gioco (3 tessere = 2pti, ..., 6+ tessere = 8pti).
	 */
	public void verifyPlanceGoal() {
		// Viene utilizzata una Deep Copy della libreria per distruggerne i dati durante il conteggio 
		// senza alterare lo stato reale della libreria del giocatore.
		Library lib = new Library(this.library);

		int count = 0;
		int goalPoints = 0;
		Color[] colors = { Color.BLUE, Color.PINK, Color.LIGHT_BLUE, Color.GREEN, Color.YELLOW, Color.WHITE };
		
		for (int j = 0; j < colors.length; j++) {
			for (int i = 0; i < Library.ROWS; i++) {
				for (int k = 0; k < Library.COLS; k++) {

					// Verifica sulla libreria originale
					if (this.library.getTile(new Position(i, k)) != null
							&& this.library.getTile(new Position(i, k)).getColor() == colors[j]) {
						count = remove_adjacency(this.library, new Position(i, k), colors[j], count);

						// Sincronizzazione con la copia fittizia (lib) usata per l'algoritmo distruttivo
						if (lib.getTile(new Position(i, k)) != null
								&& lib.getTile(new Position(i, k)).getColor() == colors[j]) {
							count = remove_adjacency(lib, new Position(i, k), colors[j], count);
						}
						
						// Assegnazione punti in base all'entità del raggruppamento trovato
						if (count == 3) {
							goalPoints = 2;
						} else if (count == 4) {
							goalPoints = 3;
						} else if (count == 5) {
							goalPoints = 5;
						} else if (count >= 6) {
							goalPoints = 8;
						}
						
						this.points += goalPoints;
						count = 0; // Reset dei contatori per il prossimo gruppo
						goalPoints = 0;
					}
				}
			}
		}
	}
}
