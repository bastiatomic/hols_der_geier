import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bastian Hesse
 * @version 3.1 (10.01.2022, 18:12)
 **/

/*
CORE IDEA
der Bot spielt immer die Züge des Gegners aus der vorausgegangenen Runde. That's it. No magic at all.
"copyPasta" bezeichnet die verwendete Strategie.
"centerCard" bezieht sich auf die "Geier-Karte".
 */

//ToDo: I write some cards in two different arrays/HashMaps. One for copying the opponent and one for analysing my win

public class CopyPasta_MVP extends HolsDerGeierSpieler {

    private final HashMap<Integer, Integer> gameHashMap = new HashMap<>(); //cC, oC
    private final HashMap<Integer, Integer> gameHashMapCurrentRun = new HashMap<>(); //cC, oC
    private final ArrayList<int[]> currentGameTree = new ArrayList<>(); //cC, mC, oC
    private int roundCounter = 0, lastGamesWinAmount;
    private int lastCenterCard, lastMyCard, myChoice;

    // the framework will call reset() by themselves
    public void reset() {
        gameHashMap.clear(); // just clear it as a safety measure
        currentGameTree.clear();
    }

    public int gibKarte(int centerCard) {
        roundCounter++;

        // the copyPasta mode starts after the first game.
        if (gameHashMapCurrentRun.size() != 0) {
            myChoice = gameHashMapCurrentRun.get(centerCard);
        } else {

            // first game handling
            if (centerCard < 0) {
                myChoice = centerCard + 6;
            } else {
                myChoice = centerCard + 5;
            }
        }

        // copy data into maps and lists
        fillGameHashMap();
        lastCenterCard = centerCard;
        lastMyCard = myChoice; // this is important to receive the correct choice of the bot

        //in every last round of a game, the bot calculates victory or defeat and the WinAmount
        if (roundCounter % 15 == 0) {
            lastGamesWinAmount += addWinningPoint(currentGameTree);

            if (roundCounter % (15 * 10) == 0) {
                if (lastGamesWinAmount <= 5) {
                    gameHashMapCurrentRun.putAll(gameHashMap); // deep copy hashmap
                }
                lastGamesWinAmount = 0;

            }
        }

        return myChoice;
    }

    private void fillGameHashMap() {
        if (letzterZug() != -99) { // every round except each first round of a game.
            gameHashMap.put(lastCenterCard, letzterZug());
            currentGameTree.add(new int[]{lastCenterCard, lastMyCard, letzterZug()});
        }

        /*calculate the sum of all centerCards and cards played by the opponent.
        With this knowledge we can calculate the last card of the opponent without an ArrayList e.g. "opponentPlayedCards". */
        if (roundCounter % 15 == 0) {
            int keySum = 0, valueSum = 0;
            for (Map.Entry<Integer, Integer> entry : gameHashMap.entrySet()) {
                keySum += entry.getKey();
                valueSum += entry.getValue();
            }
            // calculate last bot & centerCard movement
            gameHashMap.put(40 - keySum, 120 - valueSum);
            currentGameTree.add(new int[]{40 - keySum, myChoice, 120 - valueSum});
        }
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