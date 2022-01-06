import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ExploringVulture extends HolsDerGeierSpieler{

    private static int roundCounter = 0, gamesWon = 0;
    private static int myLastMove, lastMoveProgram, opponentPoints, myPoints;
    private static final boolean allowDebug = false;
    private float currentWinRate;
    private static int negFactor = 1, lowFactor = 1, midFactor = 1, highFactor = 1;
    private final int[] considerCardsIndex = {0,1,2,3,-1,-2,-3}; //paying more than n+7 or less than n-2 is generally considered bad
    private final int basicIncrement = 5;
    private float localWinRate = 1;

    private final ArrayList<Integer> centerCardsPlayed = new ArrayList<>();
    private final ArrayList<Integer> myCards = new ArrayList<>();
    private final ArrayList<Integer> myPlayedCards = new ArrayList<>();
    private final ArrayList<Integer> opponentCards = new ArrayList<>();
    private final ArrayList<Integer> opponentPlayedCards = new ArrayList<>();
    //private ArrayList<>


    public ExploringVulture() {
        reset(); // gets called after every game (15-round game)
    }

    public void reset() {
        myCards.clear();
        opponentCards.clear();
        centerCardsPlayed.clear();
        opponentPlayedCards.clear();
        myPlayedCards.clear();
        for (int i = 1; i <= 15; i++) {
            myCards.add(i);
            opponentCards.add(i);
        }
        myPoints = 0;
        opponentPoints = 0;
    }

    // SWITCH BETWEEN ALL POSSIBILITIES !!! make a list and iterate if useful. Add random if no combination works

    public int gibKarte(int centerCard) {
        // init basic variables
        roundCounter++; // keep track of the current round / game -> "be aware of your surroundings"
        centerCardsPlayed.add(centerCard);
        opponentCards.removeIf(name -> name.equals(letzterZug()));

        try {
            lastMoveProgram = centerCardsPlayed.get(centerCardsPlayed.size() - 2);
                } catch (IndexOutOfBoundsException ignored) {
        }

        if (letzterZug() != -99) {
            opponentPlayedCards.add(letzterZug());

            //detects a tie
            if (myLastMove == letzterZug()) {
                centerCard += lastMoveProgram;
            }
        }

        // end of each game management detect win
        if (roundCounter % 15 == 0) {
            myPlayedCards.add(myCards.get(0));
            opponentPlayedCards.add(opponentCards.get(0));
            gamesWon += addWinningPoints(centerCardsPlayed, myPlayedCards, opponentPlayedCards);
            //addWinningPoints will detect a win before the last card was played

            currentWinRate = gamesWon / (float) (roundCounter / 15);

            // every 30th game
            if( roundCounter % (15*30) == 0){

                // adapting when win rate is below 0.5
                if(currentWinRate < 0.5 && currentWinRate < localWinRate){
                    printLine("--------- adapting in round "+roundCounter/15+" ---------------");
                    negFactor = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                    lowFactor = ThreadLocalRandom.current().nextInt(1, 2 + 1);
                    midFactor = ThreadLocalRandom.current().nextInt(1, 2 + 1);
                    highFactor = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                    printLine(negFactor + " " + lowFactor + " " + midFactor + " " + highFactor);
                }
                localWinRate = currentWinRate;
            }

        }

        if(centerCard > 10) {
            return sendCard(Collections.max(myCards));
        }
        if(centerCard < -6) {
            return sendCard(Collections.min(myCards));
        }

        switch (centerCard){ // "real" centerCard

            //neg values
            case -5,-4,-3,-2,-1: {
                switch (negFactor){
                    case 1: {
                        if(myCards.contains(centerCard *-2 + 1)){
                            return sendCard(centerCard *-2+1);
                        } else if(myCards.contains(centerCard *-2+2)) {
                            return sendCard(centerCard *-2+2);
                        } else {
                            return sendRandom();
                        }
                    }
                    case 2: {
                        for (int considerIndex : considerCardsIndex) {
                            if (myCards.contains(centerCard * -1 + considerIndex)) {
                                return sendCard(centerCard * -1 + considerIndex);

                            }
                        }
                    }
                    case 3: {
                        return sendCard(Collections.min(myCards));
                    }
                }

            }

            //low values
            case 4,3,2,1: {
                switch (lowFactor) {
                    case 1 -> {
                        if (myCards.contains(centerCard * 2 + 1)) {
                            return sendCard(centerCard * 2 + 1);
                        } else if (myCards.contains(centerCard * 2 + 2)) {
                            return sendCard(centerCard * 2 + 2);
                        } else {
                            return sendRandom();
                        }
                    }
                    case 2 -> {
                        for (int considerIndex : considerCardsIndex) {
                            if (myCards.contains(centerCard + basicIncrement + considerIndex)) {
                                return sendCard(centerCard + basicIncrement + considerIndex);
                            }
                        }
                    }
                }
            }

            //mid values
            case 5,6,7: {
                switch (highFactor) {
                    case 1 -> {
                        if (myCards.contains(centerCard + 6)) {
                            return sendCard(centerCard + 6);
                        } else {
                            return sendRandom();
                        }
                    }
                    case 2 -> {
                        for (int considerIndex : considerCardsIndex) {
                            if (myCards.contains(centerCard + basicIncrement + considerIndex)) {
                                return sendCard(centerCard + basicIncrement + considerIndex);
                            }
                        }
                    }
                }
            }

            //high values
            case 8,9,10: {
                switch (highFactor){
                    case 1: {
                        if(!opponentCards.contains(centerCard +5) && myCards.contains(centerCard +5)){
                            return sendCard(centerCard +5);
                        } else {
                            return sendCard(Collections.min(myCards));
                        }
                    }
                    case 2: {
                        for (int considerIndex : considerCardsIndex) {
                            if (myCards.contains(centerCard + basicIncrement + considerIndex)) {
                                return sendCard(centerCard + basicIncrement + considerIndex);
                            }
                        }
                    }
                    case 3: {
                        if(myCards.contains(15) && myCards.contains(14)){
                            return sendCard(15);
                        } else{
                            return sendCard(Collections.min(myCards));
                        }
                    }
                }
            }

        }

        return sendRandom();

    }

    private int sendCard(int number){
        myCards.removeIf(name -> name.equals(number));
        myLastMove = number;
        myPlayedCards.add(number);
        return number;
    }

    private int sendRandom(){
        int randindex = new Random().nextInt(myCards.size());
        int n = myCards.get(randindex);
        return sendCard(n);
    }

    private int addWinningPoints(ArrayList<Integer> cc, ArrayList<Integer> my, ArrayList<Integer> opp) {

        myPoints = 0;
        opponentPoints = 0;
        int currentValue = 0;

        for (int i = 0; i < 15; i++) {
            int c1 = cc.get(i), m1 = my.get(i), o1 = opp.get(i);

            currentValue += c1;

            if(m1 != o1){

                //this gets never activated, when there's a tie
                if((currentValue>0 && m1 > o1) || (currentValue < 0 && m1 < o1 )){
                    myPoints += (currentValue);
                }
                if((currentValue > 0 && o1 > m1) || (currentValue < 0 && o1 < m1)){
                    opponentPoints += (currentValue);
                }
                currentValue =0;
            }

        }
        if(myPoints > opponentPoints){
            return 1;
        } else {
            return 0;
        }
    }

    private void printLine(String s){
        //enhanced string output
        if(allowDebug){
            System.out.println("\u001B[32m" + "[VA | "+ roundCounter+"] " + s  + "\u001B[0m");}
    }
}