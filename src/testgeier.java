import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Geier.
 *
 * @author (Alejandro Punal Clement)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class testgeier extends HolsDerGeierSpieler {
    /**
     /**
     * Hier definieren Sie den Konstruktor fuer Objekte Ihrer Klasse (falls Sie einen eigenen brauchen) Geier
     */
    private final ArrayList<Integer> nichtGespielte = new ArrayList<>();
    private final ArrayList<Integer> gegnerNichtGespielt = new ArrayList<>();
    private final ArrayList<Integer> nichtGespieltePunkte = new ArrayList<>();
    ArrayList<Integer> bestimmteRandomZahlen = new ArrayList<Integer>();

    public testgeier() {
        reset ();

    }

    public void reset () {
        gegnerNichtGespielt.clear();
        nichtGespielte.clear();
        for (int i = 1; i <= 15; i++) {
            gegnerNichtGespielt.add(i);
            nichtGespielte.add(i);
        }

        nichtGespieltePunkte.clear();
        for (int i = -5; i <= 10; i++)
            if (i != 0) {
                nichtGespieltePunkte.add(i);
            }
        bestimmteRandomZahlen.clear();
        bestimmteRandomZahlen.add(11);
        bestimmteRandomZahlen.add(4);
        bestimmteRandomZahlen.add(7);
        bestimmteRandomZahlen.add(6);
        bestimmteRandomZahlen.add(5);
        bestimmteRandomZahlen.add(3);
    }

    public int gibKarte(int naechsteKarte) {
        nichtGespieltePunkte.removeIf(name -> name.equals(naechsteKarte));
        int lastTurn = letzterZug();
        gegnerNichtGespielt.removeIf(name -> name.equals(lastTurn));
        return meinZug(naechsteKarte);

    }

    private int meinZug(int naechsteKarte) {
        int kartenZaehler = nichtGespielte.size();
        if (kartenZaehler == 1) {
            return nichtGespielte.get(0);
        }
        //liste erstellen


        switch (naechsteKarte) { //Logik von meinem Bot
            case -5: if (existCard(10))
                return karteAusDeckLoeschen(10);
            case -4: if (existCard(9))
                return karteAusDeckLoeschen(9);
            case -3: if (existCard(8))
                return karteAusDeckLoeschen(8);
            case -2: if (existCard(2))
                return karteAusDeckLoeschen(2);
            case -1: if (existCard(1))
                return karteAusDeckLoeschen(1);
            case 1,2,3,4,5,6: return chooseRandomSpecificCard();
            case 7: if (existCard(12))
                return karteAusDeckLoeschen(12);
            case 8: if (existCard(13))
                return karteAusDeckLoeschen(13);
            case 9: if (existCard(14))
                return karteAusDeckLoeschen(14);
            case 10: if (existCard(15))
                return karteAusDeckLoeschen(15);

            default: return zufaelligeKarte();

        }

    }
    private int chooseRandomSpecificCard() {
        int cardsCount = bestimmteRandomZahlen.size();
        int index = (int) (Math.random() * cardsCount);
        int value = bestimmteRandomZahlen.get(index);
        bestimmteRandomZahlen.removeIf(name -> name.equals(value));
        return value;
    }
    private boolean existCard(int number) {
        return nichtGespielte.contains(number);
    }
    private int karteAusDeckLoeschen(int nummer) {
        nichtGespielte.removeIf(name -> name.equals(nummer));
        return nummer;

    }
    private int zufaelligeKarte() {
        int myCardsCount = nichtGespielte.size();
        if (myCardsCount == 1) {
            return nichtGespielte.get(0);
        }

        int randomCard = (int) (Math.random() * myCardsCount);
        int valueIndex = nichtGespielte.get(randomCard);
        return karteAusDeckLoeschen(valueIndex);
    }
}