
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Sir.MoM
 *
 */
public class NoahRuben extends HolsDerGeierSpieler {

    private ArrayList<Integer> nochNichtGespielt = new ArrayList<Integer>();
    private ArrayList<Integer> meineKarten = new ArrayList<Integer>();
    private ArrayList<Integer> dieKartenDesGegners = new ArrayList<Integer>();
    private ArrayList<Integer> diePunktekarten = new ArrayList<Integer>();
    private int[] letzteZuege = new int[2];
    private ArrayList<Integer> letztePunktekarte = new ArrayList<Integer>();
    private int zug = 0;
    private HashMap<Integer, Double> meineKartenBewertung = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> dieKartenBewertungDesGegners = new HashMap<Integer, Double>();

    public NoahRuben() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see game.mainGame.HolsDerGeierSpieler#reset()
     */
    @Override
    public void reset() {
        meineKartenBewertung.clear();
        nochNichtGespielt.clear();
        meineKarten.clear();
        dieKartenDesGegners.clear();
        diePunktekarten.clear();
        dieKartenBewertungDesGegners.clear();
        zug = 0;

        // init nochNichtGespielt, meineKarten, dieKartenDesGegners
        for (int i = 1; i <= 15; i++) {
            nochNichtGespielt.add(i);
            meineKarten.add(i);
            dieKartenDesGegners.add(i);
        }

        // init diePunktekarten
        for (int i = -5; i < 0; i++) {
            diePunktekarten.add(i);
        }

        for (int i = 1; i < 11; i++) {
            diePunktekarten.add(i);
        }

        intiCardValues();
        setDieKartenBewertungDesGegners();
        modifyCardValues();
    }

    /*
     * (non-Javadoc)
     *
     * @see game.mainGame.HolsDerGeierSpieler#gibKarte(int)
     */
    @Override
    public int gibKarte(int pointsThisRound) {
        zug++;
        //.log("###### \t" + (zug - 1) + "\t ######");

        if (zug > 1) {
            fillLetzteZuege();
            logInTabelle(letztePunktekarte.get(letztePunktekarte.size() - 1));
            deleteLastCards();
        }
        if (zug == 15) {
            return getVerbleibendeMeineKarte();
        }

        letztePunktekarte.add(pointsThisRound);
        return useCardValueToCalculateTheCardToPlay(pointsThisRound);
    }

    private int useCardValueToCalculateTheCardToPlay(int cardToEvaluate) {
        //.log("Die Punktekarte: " + cardToEvaluate + " hat diesen Wert " + meineKartenBewertung.get(cardToEvaluate));
        logVerbleibendeKarten();
        Double dieBewertungDesGegners = dieKartenBewertungDesGegners.get(cardToEvaluate);
        Double meineBewertungDieserKarte = meineKartenBewertung.get(cardToEvaluate);

        if (getWhoWon(letzteZuege[0], letzteZuege[1]) == "3" && zug > 1) {

            int resultingPoints = cardToEvaluate + letztePunktekarte.get(letztePunktekarte.size() - 2);

            //.log("[Debug] resultingPoints = cardToEvaluate + letztePunktekarte.get(letztePunktekarte.size() - 1) \t" + cardToEvaluate + " + " + (letztePunktekarte.get(letztePunktekarte.size() - 1)));
            //.log("[Debug] resultingPoints = " + resultingPoints);

            if (resultingPoints > 10) {
                //.log("[Debug] resultingPoints > 10");
                for (Integer key : meineKartenBewertung.keySet()) {
                    if (meineKartenBewertung.get(key) == getHighestCardLeft()) {
                        meineKartenBewertung.replace(key, meineBewertungDieserKarte);
                    }
                }
                //.log("[Debug] return:\t" + getHighestCardLeft());
                return getHighestCardLeft();

            } else if (resultingPoints < -5) {
                //.log("[Debug] resultingPoints < -5");
                for (Integer key : meineKartenBewertung.keySet()) {
                    if (meineKartenBewertung.get(key) == getHighestCardLeft()) {
                        meineKartenBewertung.replace(key, meineBewertungDieserKarte);
                    }
                }
                //.log("[Debug] return:\t" + getHighestCardLeft());
                return getHighestCardLeft();
            }

            if ((resultingPoints > 8 || resultingPoints < -2) && resultingPoints <= 10 && resultingPoints >= -5) {
                //.log("[Debug] über 8 und unter 2");

                if (!(meineKarten.contains(meineKartenBewertung.get(resultingPoints).intValue()))) {
                    //.log("[Debug][DANGER] Berechnung funktioniert nicht!");
                    //.log("[Debug] return:\t" + meineKartenBewertung.get(cardToEvaluate).intValue());
                    return meineKartenBewertung.get(cardToEvaluate).intValue();
                }

                if (resultingPoints == 0) {
                    //.log("[Debug] resultingPoints == 0");
                    //.log("[Debug] return:\t" + meineKartenBewertung.get(cardToEvaluate).intValue());
                    return meineKartenBewertung.get(cardToEvaluate).intValue();

                }

                //Bewertung vor dem Vertauschen!
                String str;
                for (Integer key : meineKartenBewertung.keySet()) {
                    str = "[" + key + "] \t" + meineKartenBewertung.get(key);
                }
                double temp = meineKartenBewertung.get(resultingPoints);
                meineKartenBewertung.replace(resultingPoints, meineKartenBewertung.get(cardToEvaluate));
                meineKartenBewertung.replace(cardToEvaluate, temp);

                //Bewertung nach dem vertauschen
                String str2;
                for (Integer key : meineKartenBewertung.keySet()) {
                    str = "[" + key + "] \t" + meineKartenBewertung.get(key);
                    //.log("[Meine Kartenbewertung V]" + str + "[Meine Kartenbewertung]" + str);

                }

                //.log("[Debug] return:\t" + meineKartenBewertung.get(resultingPoints).intValue());
                return meineKartenBewertung.get(cardToEvaluate).intValue();

            } else {
                //.log("[Debug] 'NORMAL'");
                //.log("[Debug] return:\t" + meineKartenBewertung.get(cardToEvaluate).intValue());
                return meineKartenBewertung.get(cardToEvaluate).intValue();

            }
        }
        //.log("[Debug] return:\t" + meineKartenBewertung.get(cardToEvaluate).intValue());
        return meineKartenBewertung.get(cardToEvaluate).intValue();
    }

    private int getLowestCardLeft() {
        return meineKarten.get(0);
    }

    private int getHighestCardLeft() {
        return meineKarten.get(meineKarten.size() - 1);
    }

    private void deleteLastCards() {
        for (int i = 0; i < meineKarten.size(); i++) {
            int eineKarte = meineKarten.get(i);
            if (eineKarte == letzteZuege[this.getNummer()]) {
                meineKarten.remove(meineKarten.indexOf(eineKarte));
                //.log("[Debug][Zugaufbereitung]" + eineKarte + " wurde aus meineKarten enfernt");
            }
        }

        for (int i = 0; i < dieKartenDesGegners.size(); i++) {
            int eineKarte = dieKartenDesGegners.get(i);
            if (eineKarte == letzteZuege[getOppositeBotNumber()]) {
                dieKartenDesGegners.remove(dieKartenDesGegners.indexOf(eineKarte));
                //.log("[Debug][Zugaufbereitung]" + eineKarte + " wurde aus dieKartenDesGegners enfernt");
            }
        }
    }

    private int getVerbleibendeGegnerKarte() {
        int temp = 0;
        for (Integer eineKarte : dieKartenDesGegners) {
            temp = eineKarte;
        }
        //.log("[Zug 14] Die verbleibende Karte des Gegners: " + temp);
        return temp;
    }

    private int getVerbleibendeMeineKarte() {
        int temp = 0;
        for (Integer eineKarte : meineKarten) {
            temp = eineKarte;
        }
        //.log("[Zug 14] Meine verbleibende Karte: " + temp);
        return temp;
    }

    private int getVerbleibendePunktekarte() {
        int temp = 0;
        for (Integer eineKarte : diePunktekarten) {
            temp = eineKarte;
        }
        //.log("[Zug 14] Die verbleibenden Punktekarte: " + temp);
        return temp;
    }

    private void logInTabelle(int naechsteKarte) {
        //.logTable(naechsteKarte, letzteZuege[0], letzteZuege[1], getWhoWon(letzteZuege[0], letzteZuege[1]));

    }

    private String getWhoWon(int kartenWert1, int kartenWert2) {
        final String IWON = "1";
        final String ILOSE = "0";
        final String TIED = "3";
        final String NOTKNOWN = "GEWINNER KONNTE NICHT ERMITTELT WERDEN";

        if (kartenWert1 != kartenWert2) {
            if (kartenWert1 > kartenWert2) {
                return IWON;
            } else if (kartenWert1 < kartenWert2) {
                return ILOSE;
            }
        } else {
            return TIED;
        }
        return NOTKNOWN;
    }

    private void fillLetzteZuege() {
        if (zug != 1) {
            int meinLetzterZug = this.letzterZug();
            int derLetzteZugDesGegners = getHdg().letzterZug(getOppositeBotNumber());
            //.log("Mein Zug in Runde " + zug + ". : " + meinLetzterZug);
            //.log("Der Zug des Gegners in Runde" + zug + ". : " + derLetzteZugDesGegners);
            letzteZuege[0] = meinLetzterZug;
            letzteZuege[1] = derLetzteZugDesGegners;
        } else {
            //.log("Erste Runde \t\t\t [Zugaufbereitung] nicht möglich");
        }
    }

    private int getOppositeBotNumber() {
        if (this.getNummer() == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private void intiCardValues() {
        meineKartenBewertung.put(-5, (double) 11);
        meineKartenBewertung.put(-4, (double) 10);
        meineKartenBewertung.put(-3, (double) 9);
        meineKartenBewertung.put(-2, (double) 8);
        meineKartenBewertung.put(-1, (double) 7);
        meineKartenBewertung.put(1, (double) 1);
        meineKartenBewertung.put(2, (double) 2);
        meineKartenBewertung.put(3, (double) 3);
        meineKartenBewertung.put(4, (double) 4);
        meineKartenBewertung.put(5, (double) 5);
        meineKartenBewertung.put(6, (double) 6);
        meineKartenBewertung.put(7, (double) 12);
        meineKartenBewertung.put(8, (double) 13);
        meineKartenBewertung.put(9, (double) 14);
        meineKartenBewertung.put(10, (double) 15);
    }

    public HashMap<Integer, Double> getDieKartenBewertungDesGegners() {
        return dieKartenBewertungDesGegners;
    }

    public void setDieKartenBewertungDesGegners() {
        String stringToParse;

        if(dieKartenBewertungDesGegners.isEmpty()) {
            dieKartenBewertungDesGegners.put(-5, (double) 0);
            dieKartenBewertungDesGegners.put(-4, (double) 0);
            dieKartenBewertungDesGegners.put(-3, (double) 0);
            dieKartenBewertungDesGegners.put(-2, (double) 0);
            dieKartenBewertungDesGegners.put(-1, (double) 0);
            dieKartenBewertungDesGegners.put(1, (double) 0);
            dieKartenBewertungDesGegners.put(2, (double) 0);
            dieKartenBewertungDesGegners.put(3, (double) 0);
            dieKartenBewertungDesGegners.put(4, (double) 0);
            dieKartenBewertungDesGegners.put(5, (double) 0);
            dieKartenBewertungDesGegners.put(6, (double) 0);
            dieKartenBewertungDesGegners.put(7, (double) 0);
            dieKartenBewertungDesGegners.put(8, (double) 0);
            dieKartenBewertungDesGegners.put(9, (double) 0);
            dieKartenBewertungDesGegners.put(10, (double) 0);
        }




        for (Integer key : dieKartenBewertungDesGegners.keySet()) {
            String debugNachricht = "[" + key + "] \t" + dieKartenBewertungDesGegners.get(key);
            //.log("[Debug]" + debugNachricht);

        }
    }

    public void logVerbleibendeKarten() {
        String x = "[";
        for (Integer integer : meineKarten) {
            x = x + integer + ", ";
        }

        //.log("[Debug] meine verbleibende Karten" + x + "]");
    }

    private int spieleZufallskarte() {
        int nochVorhanden = meineKarten.size();
        int index = (int) (Math.random() * nochVorhanden);
        int ret = nochNichtGespielt.remove(index);
        return ret;
    }

    private void modifyCardValues() {
        for(int key : meineKartenBewertung.keySet()) {
            if(meineKartenBewertung.get(key) < dieKartenBewertungDesGegners.get(key)) {
                //.log("[Debug] meine Wertung für " + key + "ist geringer als die des Bots" );

                if(key > 8 || key < -2 ) {
                    //.log("[Debug][Warnung] meine Wertung für " + key + "ist geringer als die des Bots" );
                }
            }
        }
    }
}