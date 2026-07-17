# My Shelfie - Backend Core Application

Benvenuto nel repository di **My Shelfie**, un'applicazione backend sviluppata in Java che implementa l'intero set di regole, le logiche di punteggio e le strutture dati dell'omonimo gioco da tavolo. Il progetto è stato ingegnerizzato seguendo rigorosamente i principi della programmazione orientata agli oggetti (OOP), garantendo pulizia del codice, alta manutenibilità e predisposizione a future estensioni architetturali (come interfacce CLI/GUI o moduli di rete).

---

## Stack Tecnologico

* **Linguaggio:** Java 22
* **Build Tool:** Maven 3.9.16
* **Testing Framework:** JUnit Jupiter 5.10.2
* **Database Connector:** MySQL Connector/J 8.3.0

---

## Architettura Software e Scelte di Design

Il cuore dell'applicazione si concentra sulla solidità del dominio di gioco e sulla separazione netta delle responsabilità:

* **Ereditarietà e Polimorfismo:** Le logiche di assegnazione dei punti sono state isolate e incapsulate. Gli obiettivi di gioco si dividono nelle classi astratte `Common_Goal` (Obiettivi Comuni) e `Personal_Card` (Obiettivi Personali). Le singole carte concrete ereditano da queste classi base, implementando algoritmi di verifica geometrica, spaziale e cromatica in modo atomico e indipendente.
* **Modern Java Syntax:** Per il calcolo dei punteggi basato sulle corrispondenze e sui requisiti soddisfatti è stato fatto largo uso delle *switch expression* (sintassi a freccia `->`). Questa scelta riduce la verbosità del codice, elimina i classici *code smell* legati a lunghe catene di condizionali e migliora la leggibilità complessiva.
* **Algoritmi di Scansione della Griglia:** La verifica dello stato della libreria del giocatore (`Library`) adotta algoritmi di pattern matching bidimensionale. Attraverso scansioni ottimizzate e finestre mobili sulla matrice di tessere, il sistema individua formazioni specifiche (linee, colonne, sotto-matrici o pattern a "X") riducendo al minimo la computational complexity e l'allocazione di memoria ridondante.
* **Incapsulamento e Manutenibilità:** Lo stato di ogni entità (`Tile`, `Position`, `Color`) è protetto da un solido livello di incapsulamento. I vincoli dimensionali della plancia e della libreria sono legati a costanti statiche, azzerando l'utilizzo di *magic numbers*.

---

## Struttura del Progetto

Il codice sorgente è organizzato in pacchetti coerenti che riflettono il dominio del problema:

* **src/main/java/myshelfiemodel/**: Modelli di dominio (Library, Tile, Position, Color, ecc.)
* **src/main/java/common_goal/**: Logiche di verifica degli Obiettivi Comuni
* **src/main/java/personal_card/**: Inizializzazione e calcolo punti delle Carte Personali
* **src/test/java/**: Suite di test unitari (JUnit 5) per la validazione delle logiche

---

## Come Iniziare

### Prerequisiti
* **Java Development Kit (JDK):** Versione 22 o superiore.
* **Apache Maven:** Versione 3.9.16 o superiore.

### Installazione e Compilazione

1. Clona il repository locale:
   ```bash
   git clone https://github.com/mtironi03/My_Shelfie.git
   ```

2. Vai nella directory contenente il file pom.xml


3. Compila il progetto e scarica le dipendenze tramite Maven:
   ```bash
   mvn clean compile 
   ```
4. Esegui il build completo generando il pacchetto distribuibile:
   ```bash
   mvn clean package
   ```

5. Avvia l'applicazione tramite il file JAR generato:
   ```bash
   java -jar target/nome-del-file-generato.jar
   ```

---

## Standard di Sviluppo
* **Documentazione Javadoc:** Ogni classe, costruttore e metodo pubblico è interamente documentato in italiano formale secondo lo standard Javadoc, specificando responsabilità, parametri (`@param`) e valori di ritorno (`@return`).
* **Clean Code:** Codice scritto nel rispetto delle convenzioni di naming Java, con forte coesione interna ai pacchetti e basso accoppiamento tra le componenti.
