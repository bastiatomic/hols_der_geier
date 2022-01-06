

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Diese Geierklasse versucht durch Speicherung der Karten und deren H�ufigkeiten, die Strategie des Gegenspielers
 * zu erlernen oder zumindest so gut es geht zu durchschauen, um somit immer die passende Konterkarte auszuspielen
 *
 */
public class FlosBot extends HolsDerGeierSpieler {

    // Enth�lt die Informationen aus allen Runden und Spielen geb�ndelt. Wird nie resettet
    private HashMap< Integer, HashMap< Integer, Integer > > enemyPattern = new HashMap< Integer, HashMap< Integer, Integer > >();
    // Enh�lt rundenspezifische Informationen, welche nach jedem Spiel gel�scht werden
    private HashMap< Integer, HashMap< Integer, Integer > > lastRoundsPattern = new HashMap< Integer, HashMap< Integer, Integer > >();

    // Beinhaltet meine noch verf�gbaren Spielerkarten
    private ArrayList< Integer > myCards;
    // Enth�lt die noch nicht gespielten Geier-/M�usekarten
    private ArrayList< Integer > buzzardCards;
    // Enth�lt eine Unterliste aus myCards mit dem Inhalt [1-8]
    private ArrayList< Integer > lowEndCards;
    // Enth�lt eine Unterliste aus myCards mit dem Inhalt [9-15]
    private ArrayList< Integer > highEndCards;

    private int games = 0, lastBuzzardCard = -99, pointsToWin = 0, myNumber, oppNumber, wins = 0, looses = 0, shift = 0;

    // Speichert f�r beide Spieler die jeweiligen Rundenpunkten
    private int[] playerPoints;

    // Regelt, ob allgemein aus myCards gespielt oder die Unterteilung in lowEndCards und highEndCards stattfindet
    private boolean activateFineSelect = false;

    @Override
    public void reset() {
        // F�llen der ArrayLists
        this.myCards = fillList( new ArrayList<Integer>(), 1, 15, 0 );
        this.buzzardCards = fillList( new ArrayList<Integer>(), -5, 10, 0 );
        this.lowEndCards = fillList( new ArrayList<Integer>(), 1, 8, 0 );
        this.highEndCards = fillList( new ArrayList<Integer>(), 9, 15, 0 );

        // Abgleichen und anpassen der Daten aus der letzten Runde mit den Gesamtdaten
        synchronizeEnemyData( this.lastRoundsPattern, this.enemyPattern );

        // Gibt es nach 5 Spielen noch keinen Sieger, wird die zu legende Karte in der n�chsten Runde erh�ht
        if( this.games == 5 && this.wins == this.looses ) shift++;
        // Sollte es nach weiteren 5 Spielen nicht mehr unentschieden stehen, wird die �nderung r�ckg�ngig gemacht
        if( ( this.games % 5 == 0 ) && this.wins != this.looses && shift > 0 ) shift = 0;

        // Hochz�hlen des Spielz�hlers
        if( this.games == 10 ) this.games = 0;
        this.games++;

        // Zur�cksetzen von Werten
        this.pointsToWin = 0;
        this.lastBuzzardCard = -99;
        this.playerPoints = new int[] { 0, 0 };
        this.myNumber = getNummer();
        this.oppNumber = getNummer() ^ 1;
    }

    @Override
    public int gibKarte( int buzzardCard ) {
        if( this.lastBuzzardCard > -6 ) {
            // In allen Runde, mit Ausnahme der Ersten, werden die Daten des letzten Durchgangs gespeichert
            learnEnemysStrategy( this.enemyPattern, this.lastBuzzardCard );
            learnEnemysStrategy( this.lastRoundsPattern, this.lastBuzzardCard );
            // Sollte mein Bot hinten liegen, wird die Unterteilung in lowEndCards und highEndCards aktiviert
            if( this.playerPoints[ myNumber ] < this.playerPoints[ oppNumber ] ) this.activateFineSelect = true;
        }
        // Anpassen der aktuellen Rundenpunkte um zu sehen, welcher Spieler gerade f�hrend ist
        updatePoints( buzzardCard );
        this.lastBuzzardCard = buzzardCard;
        return chooseStrategy( buzzardCard );
    }

    /**
     * Behandelt Ausnahmef�lle und entscheidet, welche Strategie gespielt wird
     *
     * @param buzzardCard Die aktuelle Geier-/M�usekarte, um welche gespielt wird
     * @return Die errechnete Spielerkarte
     */
    private int chooseStrategy( int buzzardCard ) {
        // Ausnahmef�lle
        if( ( this.pointsToWin > 0 && buzzardCard < 0 ) || ( this.pointsToWin < 0 && buzzardCard > 0 ) || ( this.pointsToWin == 0 )
                || ( this.pointsToWin > -2 && buzzardCard < 2 ) )
            return playCard( this.myCards.get( 0 ) );
        if( this.pointsToWin > 10 ) return playCard( this.myCards.get( this.myCards.size() - 1 ) );
        if( this.pointsToWin >= -5 && this.pointsToWin <= 10 ) buzzardCard = this.pointsToWin;
        if( this.pointsToWin != buzzardCard )
            if( this.pointsToWin >= -5 && this.pointsToWin <= 10 && this.pointsToWin != 0 ) buzzardCard = this.pointsToWin;

        // W�hrend des erstens Spiels wird eine statische Strategie gespielt, bei allen anderen danach eine dynamische
        int cardToPlay = ( this.games > 1 ) ? playDynamicStrategy( buzzardCard ) : playStaticStrategy( buzzardCard );
        return playCard( cardToPlay );
    }

    /**
     * Errechnet eine Spielerkarte in einem statischen Muster abh�ngig von der �bergebenen Geier-/M�usekarte
     *
     * @param buzzardCard Die aktuelle Geier-/M�usekarte, um welche gespielt wird als
     * @return Eine ausgew�hlte Spielerkarte
     */
    private int playStaticStrategy( int buzzardCard ) {
        return buzzardCard < 0 ? buzzardCard + 6 : buzzardCard + 5;
    }

    /**
     * Errechnet, aus den aktuellen Rundenbedingungen und den erlernten Informationen
     * zur Spieltaktik des Gegners, die Karte mit der h�chsten Gewinnwahrscheinlichkeit
     *
     * @param buzzardCard Die aktuelle Geier-/M�usekarte, um welche gespielt wird
     * @return die Berechnete optimale Karte f�r diesen Zug
     */
    private int playDynamicStrategy( int buzzardCard ) {
        // Bekommen der gesammelten Daten zu der aktuellen Geier-/M�usekarte
        HashMap< Integer, Integer > cardSet = this.enemyPattern.get( buzzardCard );
        if( cardSet == null ) {
            // Sollten noch keine Daten vorhanden sein, setzte einen Standartwert
            cardSet = new HashMap< Integer, Integer >();
            cardSet.put( buzzardCard, playStaticStrategy( buzzardCard ) );
        }
        // Ermitteln der Karte mit h�chster Legeh�ufigkeit des Gegners
        int enemysCard = getMaxCard( cardSet );
        int cardToPlay = enemysCard + ( 1 + this.shift );
        // �berpr�fen ob die ermittelte Karte zu weit von den Standartwerten abweicht und ggf. anpassen des Wertes
        int alternativeCard = playStaticStrategy( buzzardCard );
        if( alternativeCard > ( cardToPlay + 5 ) ) cardToPlay = alternativeCard;
        return cardToPlay;
    }

    /**
     * Entfernt die Spielerkarte aus der jeweils richtigen Liste
     *
     * @param cardToPlay Die berechnete Karte, welche gespielt werden soll
     * @return Die berechnete Karte, welche gespielt werden soll
     */
    private int playCard( int cardToPlay ) {
        // Auswahl des Kartensatzes, von welchem die n�chste Karte gew�hlt wird
        ArrayList< Integer > cardSetToPickFrom = this.activateFineSelect ? ( this.pointsToWin < 10 ? this.lowEndCards : this.highEndCards ) : this.myCards;
        // Sollte die ermittelte Karte schon gespielt worden sein, wird die n�chste verf�gbare ausgew�hlt
        if( !this.myCards.contains( cardToPlay ) ) cardToPlay = findNextValidCard( cardSetToPickFrom, cardToPlay, this.pointsToWin > 4 || this.pointsToWin < -5 );

        // Enth�lt lowEndCards die aktuelle Karte, wird diese aus der Liste entfernt
        if( this.lowEndCards.contains( cardToPlay ) ) this.lowEndCards.remove( this.lowEndCards.indexOf( cardToPlay ) );
        // Sollte highEndCards die aktuelle Karten enthalten, wird diese aus der Liste gel�scht
        if( this.highEndCards.contains( cardToPlay ) ) this.highEndCards.remove( this.highEndCards.indexOf( cardToPlay ) );
        // Zur�ckgeben und l�schen der aktuellen Karte aus der Liste myCards
        return this.myCards.remove( this.myCards.indexOf( cardToPlay ) );
    }

    /**
     * Aktualisiert nach jeder Runde die aktuelle Punktzahl jedes Spielers und addiert, bei
     * einem Unentschieden, die Punkte der vorherigen Runde
     *
     * @param buzzardCard Die aktuelle Geier-/M�usekarte, um welche gespielt wird
     */
    private void updatePoints( int buzzardCard ) {
        // Handelt es sich um negative Punkte, ja/nein ?
        boolean negative = this.lastBuzzardCard < 0;
        // Anpassen der Rundenpunkte, sollte es einen gewinner gegeben haben
        if( lastMove( this.myNumber ) > lastMove( this.oppNumber ) ) this.playerPoints[ negative ? this.oppNumber : this.myNumber ] += this.pointsToWin;
        else if( lastMove( this.myNumber ) < lastMove( this.oppNumber ) ) this.playerPoints[ negative ? this.myNumber : this.oppNumber ] += this.pointsToWin;
        // Ging die letzte Runde untentschieden aus, werden die Punkte f�r die n�chste Runde addiert
        if( lastMove( this.myNumber ) == lastMove( this.oppNumber ) && ( this.lastBuzzardCard > -99 ) )
            if( this.pointsToWin == 0 || this.pointsToWin != this.lastBuzzardCard) this.pointsToWin = this.lastBuzzardCard + buzzardCard;
            else this.pointsToWin += buzzardCard;
        else this.pointsToWin = buzzardCard;
        // Entfernen der Geier-/M�usekarte aus der Liste der noch nicht gelegten Karten
        this.buzzardCards.remove( this.buzzardCards.indexOf( buzzardCard ) );
        if( this.buzzardCards.isEmpty() ) {
            // Beim Spielende wird ein Sieg oder eine Niederlage hinzugef�gt
            if( this.playerPoints[ this.myNumber ] > this.playerPoints[ this.oppNumber ] ) this.wins++;
            if( this.playerPoints[ this.myNumber ] < this.playerPoints[ this.oppNumber ] ) this.looses++;
        }
    }

    /**
     * Holt die vom Gegner in der letzten Runde gespielt Karte und ordnet sie einer
     * Geier-/M�usekarte zu und passt ggf. die H�ufigkeit an
     *
     * @param pattern Eine Kartensammlung mit H�ufigkeit, in welche die neue Information eingetragen werden soll
     * @param lastBuzzCard Die Geier-/M�usekarte aus der letzten Runde
     */
    private void learnEnemysStrategy( HashMap<Integer, HashMap<Integer, Integer>> pattern, int lastBuzzCard ) {
        if( lastBuzzCard < -5 ) return;
        // Erstellen einer neuen HashMap ohne Inhalt
        HashMap<Integer, Integer> playedCard = new HashMap<Integer, Integer>();
        // Sollte bereits ein Eintrag zu der Geier-/M�usekarte existieren, wird die HashMap �berschrieben
        if( pattern.containsKey( lastBuzzCard ) ) playedCard = pattern.get( lastBuzzCard );
        // Bekommen der Spielerkarte des Gegners aus der letzten Runde
        int enemyCard = lastMove( this.oppNumber );
        // Initialisieren eines neuen Kartenz�hlers
        int cardCounter = 0;
        // Sollte zu der ermittelten Spielerkarte bereits ein Eintrag vorhanden sein, wird der Z�hler angepasst
        if( playedCard.containsKey( enemyCard ) ) cardCounter = playedCard.get( enemyCard );
        // Speichern der Spielerkarte mit der angepassten H�ufigkeit
        playedCard.put( enemyCard, cardCounter + 1 );
        // Speichern der Karte mit H�ufigkeit und Zuordnung zu einer Geier-/M�usekarte
        pattern.put( lastBuzzCard, playedCard );
    }

    /**
     * Vergleicht die Daten aus zwei verschiedenen Informationss�tzen und entfernt ungleichheiten
     *
     * @param newData Kartensammlung mit H�ufigkeit
     * @param oldData Kartensammlung mit H�ufigkeit
     */
    private void synchronizeEnemyData( HashMap<Integer, HashMap<Integer, Integer>> newData, HashMap<Integer, HashMap<Integer, Integer>> oldData ) {
        if( newData.isEmpty() ) return;
        for( int gKeys : oldData.keySet() ) {
            // Enth�lt die newData noch keine Information zu dem aktuellen Schl�ssel -> �berspringen
            if( !newData.containsKey( gKeys ) ) continue;

            // Holen der Daten aus beiden HashMaps mit dem aktuellen Schl�ssel
            HashMap<Integer, Integer> oldPattern = oldData.get( gKeys );
            HashMap<Integer, Integer> newPattern = newData.get( gKeys );

            // Ermitteln der Karte mit der h�chsten H�ufigkeit von beiden Datens�tzen
            int oldMax = getMaxCard( oldPattern ), newMax = getMaxCard( newPattern );
            if( oldMax != newMax )
                // Sollten sich die Werte unterscheiden, wird der Datensatz der oldData angepasst
                oldData.put( gKeys, newPattern );
        }
        // Die Daten der newData werden gel�scht
        newData.clear();
    }

    /**
     * Durchsucht das �bergebene Kartenset nach verf�gbaren Karten und gibt die n�chste verf�gbare zur�ck
     *
     * @param cardSet Eine Kartenliste in welcher nach der n�chsten Karte gesucht werden soll
     * @param currentCard Die aktuell errechnete, optimale Spielerkarte
     * @param add Ob in der Liste aufsteigend oder absteigend nach der n�chsten verf�gbaren Karten gesucht werden soll
     * @return Die n�chste noch nicht gelegte, und damit g�ltige, Spielerkarte.
     */
    private int findNextValidCard( ArrayList<Integer> cardSet, int currentCard, boolean add ) {
        // Sollte das ausgew�hlte Kartenset leer sein, wird aus allen Karten eine verf�gbare gew�hlt
        if( cardSet.isEmpty() ) return findNextValidCard( this.myCards, currentCard, add );
        // Wurde eine verf�gbare Karten gefunden, wird diese zur�ckgegeben
        if( cardSet.contains( currentCard ) ) return currentCard;
        // Ist die aktuelle Karte gr��er als die h�chte Verf�gbare, wird absteigend gesucht
        if( currentCard > cardSet.get( cardSet.size() - 1 ) ) return findNextValidCard( cardSet, currentCard - 1, add );
        // Ist die aktuelle Karte kleiner als die h�chste Verf�gbare, wird aufsteigend gesucht
        if( currentCard < cardSet.get( 0 ) ) return findNextValidCard( cardSet, currentCard + 1, add );
        // Sollte die aktuelle Karten mitten drin liegen, wird je nach 'add' entweder absteigend oder aufsteigend gesucht
        return findNextValidCard( cardSet, currentCard + ( add ? 1 : -1 ), add);
    }

    /**
     * F�llt eine ArrayList mit Integern
     *
     * @param list Eine ArrayList<Integer>, welche bef�llt werden soll
     * @param start Startwert
     * @param end Endwert
     * @param exception Ausnahmefall, welcher nicht in die Liste eingetragen wird
     * @return Die �bergebene ArrayList<Integer> mit gew�nschtem Inhalt
     */
    private ArrayList<Integer> fillList( ArrayList<Integer> list, int start, int end, int exception ) {
        for( int i = start; i <= end; i++ )
            if( i != exception ) list.add( i );
        return list;
    }

    /**
     * Holt den letzten Zug eines Spielers
     *
     * @param playerNumber Eine Spielernummer [0,1]
     * @return Den letzten Zug des Spielers mit der �bergebenen Spielernummer
     */
    public int lastMove( int playerNumber ) {
        return getHdg().letzterZug( playerNumber );
    }

    /**
     * Berechnet aus einem Kartensatz die Karte, welche am H�ufigsten gelegt wurde
     *
     * @param cardSet Eine Sammlung aus den gelegten gegnerischen Spielerkarten und deren H�ufigkeit
     * @return Die Karte mit der h�chsten Auftrittswahrscheinlichkeit
     */
    private int getMaxCard( HashMap<Integer, Integer> cardSet ) {
        return Collections.max( cardSet.entrySet(), Map.Entry.comparingByValue() ).getKey();
    }

}