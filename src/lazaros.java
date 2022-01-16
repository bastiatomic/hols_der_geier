import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Geier.
 *
 * @author (Lazi)
 * @version 27.514,b
 */
public class lazaros extends HolsDerGeierSpieler {
    /**
     /**
     * Hier definieren Sie den Konstruktor fuer Objekte Ihrer Klasse (falls Sie einen eigenen brauchen) Geier
     */
    private final ArrayList<Integer> nichtGespielte = new ArrayList<>();
    private final ArrayList<Integer> myCards3 = new ArrayList<>();
    ArrayList<Integer> randomNumbers2 = new ArrayList<>();

    public lazaros() {
        reset ();

    }

    public void reset () {
        nichtGespielte.clear();
        for (int i = 1; i <= 15; i++) {
            nichtGespielte.add(i);
        }

        myCards3.clear();
        for (int i = -5; i <= 10; i++)
            if (i != 0) {
                myCards3.add(i);
            }
        randomNumbers2.clear();randomNumbers2.add(11);randomNumbers2.add(4);randomNumbers2.add(7);randomNumbers2.add(6);randomNumbers2.add(5);randomNumbers2.add(3);
    }

    public int gibKarte(int naechsteKarte) {
        myCards3.removeIf(name -> name.equals(naechsteKarte));

        int kartenZaehler = nichtGespielte.size();
        if (kartenZaehler == 1) {
            return nichtGespielte.get(0);
        }

        switch (naechsteKarte) { //Logik von meinem Bot
            case -5: if (existCard(8))
                return karteAusDeckLoeschen(8);
            case -4: if (existCard(9))
                return karteAusDeckLoeschen(10);
            case -3: if (existCard(10))
                return karteAusDeckLoeschen(8);
            case -2: if (existCard(2))
                return karteAusDeckLoeschen(2);
            case -1: if (existCard(1))
                return karteAusDeckLoeschen(1);
            case 1,2,3,4,5,6: return chooseRandomSpecificCard();
            case 7,8,9,10: return naechsteKarte + 5;

            default: return zufaelligeKarte();

        }

    }
    private int chooseRandomSpecificCard() {
        int cardsCount = randomNumbers2.size();
        int index = (int) (Math.random() * cardsCount);
        int value = randomNumbers2.get(index);
        randomNumbers2.removeIf(name -> name.equals(value));
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