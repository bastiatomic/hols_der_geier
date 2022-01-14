import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Bastian Hesse
 * @version 3.1 (10.01.2022)
 **/

/* CORE IDEA
der Bot spielt immer die Züge des Gegners aus der vorausgegangenen Runde. That's it. No magic at all.
"copyPasta" bezeichnet die verwendete Strategie.
"centerCard" bezieht sich auf die "Geier-Karte". */

public class CopyPasta_Bastian_Hesse extends HolsDerGeierSpieler {

    private final ArrayList<int[]> currentGameList = new ArrayList<>();
    private HashMap<Integer, Integer> gameHashMapCurrentRun = new HashMap<>();
    private int lastCenterCard, lastMyCard, myChoice;
    private int roundCounter = 0, lastGamesWinAmount = 0;

    // the framework will call reset() by themselves
    public void reset() {
        currentGameList.clear();
    }

    public int gibKarte(int centerCard) {
        roundCounter++;

        // the copyPasta mode starts after the first game.
        if (gameHashMapCurrentRun.size() != 0) {
            myChoice = gameHashMapCurrentRun.get(centerCard);
        } else {

            // first games handling
            if (centerCard < 0) {
                myChoice = centerCard + 6;
            } else {
                myChoice = centerCard + 5;
            }
        }

        // copy data into maps and lists
        fillCurrentGameList();
        lastCenterCard = centerCard;
        lastMyCard = myChoice; // this is important to receive the correct choice of the bot

        //in every last round of a game, the bot calculates victory or defeat and the WinAmount
        if (roundCounter % 15 == 0) {
            lastGamesWinAmount += addWinningPoint(currentGameList);

            if (roundCounter % (15 * 10) == 0) {
                if (lastGamesWinAmount <= 5) {
                    //extract currentGameList values into gameHashMap
                    gameHashMapCurrentRun = fillCurrentRun(currentGameList);
                }
                lastGamesWinAmount = 0;

            }
        }

        return myChoice;
    }

    private void fillCurrentGameList() {
        if (letzterZug() != -99) { // every round except each first round of a game.
            currentGameList.add(new int[]{lastCenterCard, lastMyCard, letzterZug()});
        }

        /*calculate the sum of all centerCards and cards played by the opponent.
        With this knowledge we can calculate the last card of the opponent without an ArrayList e.g. "opponentPlayedCards". */
        if (roundCounter % 15 == 0) {
            int keySum = 0, valueSum = 0;
            for (int[] n : currentGameList) {
                keySum += n[0];
                valueSum += n[2];
            }
            // calculate last bot & centerCard movement
            currentGameList.add(new int[]{40 - keySum, myChoice, 120 - valueSum});
        }
    }

    private HashMap<Integer, Integer> fillCurrentRun(ArrayList<int[]> list1) {
        for (int[] n : list1) {
            gameHashMapCurrentRun.put(n[0], n[2]);
        }

        return gameHashMapCurrentRun;
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