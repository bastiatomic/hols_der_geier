import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Diese Klasse verwendet eine simple Strategie.
 * Die Punket Karten werden in 2 Bereiche eingeteilt, einen niedrigen und einen hohen Punktzahl Bereich.
 * Im niedrigen Punktzahl Bereicht (Punkte < 7) wird immer die eigene niedrigste Karte gespielt.
 * Im hohen Punktzahl bereicht wird geprüft ob die eigenen Karten über die Karten vom Gegner sind.
 * Wenn mehr als eine Karte höher ist als die Gegner Karten, wird die eigene höchste Karte gespielt.
 * Wenn nicht wird geprüft ob die Punkte Karte in den höheren 50% der verfügbaren Punkte Karten sind. Wenn Ja dann wird die eigene höchste Karte gespielt.
 * Wenn nicht wird der Index der Punkte Karte genommen und dieser Index wird dann auf die eigenen Karten angewendet um eine Spielkarte zu bekommen.
 *
 * @author Skillkiller
 * @version 2.4
 */
