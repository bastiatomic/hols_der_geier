import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Bastian Hesse
 * @version 3.2 (10.01.2022) [latest]
 **/

/* CORE IDEA (README)
der Bot kopiert ständig das aktuelle Spiel in die ArrayList currentGameCopy.
Jetzt kopiert er einmalig die Züge des Gegners in die activeGameHashMap, spielt diese und kontrolliert im 10-Spiel-Zyklus,
ob diese kopierten Züge siegreich sind. Falls nein, nimmt er sich die aktuell mitgeschnittenen Züge von currentGameCopy
und spielt diese. Der Kontroll-Zyklus läuft von neuem.
"copyPasta" bezeichnet die verwendete Strategie.
"centerCard" bezieht sich auf die "Geier-Karte". */

public class CopyPastaBot_Bastian_Hesse extends HolsDerGeierSpieler {

    private final ArrayList<int[]> currentGameCopy = new ArrayList<>();
    private HashMap<Integer, Integer> activeGameHashMap = new HashMap<>();
    private int lastCenterCard, lastMyCard, myChoice;
    private int roundCounter = 0, lastGamesWinAmount = 0;

    // the framework will call reset() by themselves
    public void reset() {
        currentGameCopy.clear();
    }

    public int gibKarte(int centerCard) {
        roundCounter++;

        // the copyPasta mode starts after the first game.
        if (activeGameHashMap.size() != 0) {
            myChoice = activeGameHashMap.get(centerCard);
        } else {

            // first games handling
            if (centerCard < 0) {
                myChoice = centerCard + 6;
            } else {
                myChoice = centerCard + 5;
            }
        }

        // copy data into maps and lists
        fillCurrentGameCopy();
        lastCenterCard = centerCard;
        lastMyCard = myChoice; // this is important to receive the correct choice of the bot

        //in every last round of a game, the bot calculates victory or defeat and the WinAmount
        if (roundCounter % 15 == 0) {
            lastGamesWinAmount += addWinningPoint(currentGameCopy);

            if (roundCounter % (15 * 10) == 0) {
                if (lastGamesWinAmount <= 5) {
                    //extract currentGameCopy values into gameHashMap
                    activeGameHashMap = fillActiveGameHashMap(currentGameCopy);
                }
                lastGamesWinAmount = 0;
            }
        }
        return myChoice;
    }

    private void fillCurrentGameCopy() {
        if (letzterZug() != -99) { // every round except each first round of a game.
            currentGameCopy.add(new int[]{lastCenterCard, lastMyCard, letzterZug()});
        }

        /*calculate the sum of all centerCards and cards played by the opponent.
        With this knowledge we can calculate the LAST card of the opponent without an ArrayList e.g. "opponentPlayedCards". */
        if (roundCounter % 15 == 0) {
            int keySum = 0, valueSum = 0;
            for (int[] n : currentGameCopy) {
                keySum += n[0];
                valueSum += n[2];
            }
            // calculate last bot & centerCard movement
            currentGameCopy.add(new int[]{40 - keySum, myChoice, 120 - valueSum});
        }
    }

    private HashMap<Integer, Integer> fillActiveGameHashMap(ArrayList<int[]> list1) {
        for (int[] n : list1) {
            activeGameHashMap.put(n[0], n[2]);
        }

        return activeGameHashMap;
    }

    private int addWinningPoint(ArrayList<int[]> list1) {
        int myPoints = 0, opponentPoints = 0, currentValue = 0;

        for (int[] n : list1) {
            int c1 = n[0], m1 = n[1], o1 = n[2];

            currentValue += c1;

            if (m1 != o1) {

                //this gets never activated, when there's a tie
                if ((currentValue > 0 && m1 > o1) || (currentValue < 0 && m1 < o1)) {
                    myPoints += (currentValue);
                }
                if ((currentValue > 0 && o1 > m1) || (currentValue < 0 && o1 < m1)) {
                    opponentPoints += (currentValue);
                }
                currentValue = 0;
            }
        }
        if (myPoints > opponentPoints) {
            return 1;
        } else {
            return 0;
        }
    }

}