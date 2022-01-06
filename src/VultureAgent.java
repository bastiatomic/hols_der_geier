import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*

considerCardsIndex has negModifier = -1

neg case:
    myCards.contains(centerCard *-2 + 1) or myCards.contains(centerCard *-2 + 2 or random
    collection.min()
    considerCardsIndex

low case:
    collection.min()
    considerCardsIndex

mid case (5,6,7):
    5 -> 10, 6 -> 13, 7 -> 14, else collection.min()
    considerCardsIndex

high case:
    n+5 if I have n+4 else collection.min()
    considerCardsIndex
    if enemy doesn't have n+5, then play n+5, else collection.min()

very high case:
    collection.max()

iterate simple with +1 over each possibility // ExploringVulture
1111    000
2222    001
3113    010
1221    011
2112    100
3223    101
 */

public class VultureAgent extends HolsDerGeierSpieler {

    private static int roundCounter = 0, gamesWon = 0;
    private static int myLastMove, lastMoveProgram, opponentPoints, myPoints;
    private static boolean secondStrategy = false, thirdStrategy = false, fourthStrategy = false;
    private static final boolean allowDebug = true;

    private final ArrayList<Integer> centerCardsPlayed = new ArrayList<>();
    private final ArrayList<Integer> myCards = new ArrayList<>();
    private final ArrayList<Integer> myPlayedCards = new ArrayList<>();
    private final ArrayList<Integer> opponentCards = new ArrayList<>();
    private final ArrayList<Integer> opponentPlayedCards = new ArrayList<>();

    public VultureAgent() {
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

    // sSWITCH BETWEEN ALL POSSIBILITIES !!! make a list and iterate if usefull. Add random if no combination works

    public int gibKarte(int centerCard){
        // init basic variables
        roundCounter ++; // keep track of the current round / game -> "be aware of your surroundings"
        centerCardsPlayed.add(centerCard);
        opponentCards.removeIf(name -> name.equals(letzterZug()));

        try{
            lastMoveProgram = centerCardsPlayed.get(centerCardsPlayed.size()-2);
        } catch (IndexOutOfBoundsException ignored){}

        if(letzterZug() != -99) {
            opponentPlayedCards.add(letzterZug());

            //detects a tie
            if(myLastMove == letzterZug()) {
                centerCard += lastMoveProgram;
            }
        }

        // end of each game management detect win
        if(roundCounter % 15 == 0) {
            myPlayedCards.add(myCards.get(0));
            opponentPlayedCards.add(opponentCards.get(0));
            gamesWon += addWinningPoints(centerCardsPlayed, myPlayedCards, opponentPlayedCards);
            //addWinningPoints will detect a win before the last card was played

            // adapting when win rate is below 0.5
            float currentWinRate =  gamesWon / (float) (roundCounter / 15);

            if (roundCounter / 15 == 15 && currentWinRate < 0.5) { // played bad after 20 rounds
                printLine("adapting  second plan ...");
                secondStrategy = true; // adapting to better negative behavior
            }
            if (roundCounter / 15 == 30 && currentWinRate < 0.5) { // played still bad after 40 games
                printLine("adapting third plan ...");
                thirdStrategy = true; // changing to considerIndex
            }
            if (roundCounter / 15 == 45 && currentWinRate < 0.5) { // played still bad after 60 rounds
                printLine( "adapting fourth plan ...");
                fourthStrategy = true; // changing behavior when opponent has card X
            }
        }

        if(roundCounter/15 == 1000){
            printLine("GG: " + gamesWon);
            roundCounter = 0;
            gamesWon = 0;
            secondStrategy = false;
            thirdStrategy = false;
            fourthStrategy = false;
        }

        // chosse the blocks and the general behavior
        if(centerCard > 10){
            return sendCard(Collections.max(myCards));
        }

        if(!thirdStrategy && !fourthStrategy){
            switch (centerCard){

                // negative cards -> should prevent
                case -5,-4,-3,-2,-1 -> {
                    if(secondStrategy){
                        if(myCards.contains(centerCard *-2 + 1)){
                            return sendCard(centerCard *-2+1);
                        } else if(myCards.contains(centerCard *-2+2)) {
                            return sendCard(centerCard *-2+2);
                        } else {
                            return sendRandom();
                        }

                    } else {
                        return sendCard(Collections.min(myCards));
                    }

                }

                // low value -> don't care
                case 1,2,3,4 -> {
                    return sendCard(Collections.min(myCards));
                }
                // mid-value -> nice to have
                case 5 -> {
                    if(myCards.contains(10)){
                        return sendCard(10);
                    } else{
                        return sendCard(Collections.min(myCards));
                    }
                }
                case 6 -> {
                    if(myCards.contains(13)){
                        return sendCard(13);
                    } else{
                        return sendCard(Collections.min(myCards));
                    }
                }
                case 7 -> {
                    if(myCards.contains(14)){
                        return sendCard(14);
                    } else{
                        return sendCard(Collections.min(myCards));
                    }
                }
                // high-value -> I have to get them
                case 8 -> {
                    if(myCards.contains(13) && myCards.contains(12)){
                        return sendCard(13);
                    } else{
                        return sendCard(Collections.min(myCards));
                    }
                }
                case 9 -> {
                    if(myCards.contains(14) && myCards.contains(13)){
                        return sendCard(14);
                    } else{
                        return sendCard(Collections.min(myCards));
                    }
                }

                case 10 -> {
                    if(myCards.contains(15) && myCards.contains(14)){
                        return sendCard(15);
                    } else{
                        return sendCard(Collections.min(myCards));
                    }
                }
            }
        } else if (thirdStrategy && !fourthStrategy){
            int[] considerCardsIndex = {0,1,2,3,-1,-2,-3}; //paying more than n+7 or less than n-2 is generally considered bad
            final int basicIncrement = 5; // basic n+5 mechanic
            final int negativeModifier = -1;

            for (int considerIndex : considerCardsIndex){
                //case: positive behavior pattern
                if (centerCard > 0 && myCards.contains(centerCard + basicIncrement + considerIndex)) {
                    return sendCard(centerCard + basicIncrement + considerIndex);

                    //case: negative behavior pattern
                } else if (centerCard < 0 && myCards.contains(centerCard * negativeModifier + considerIndex)) {
                    return sendCard(centerCard * negativeModifier + considerIndex);

                }
            }

        } else { // fourth plan
            if(centerCard<0) {centerCard *=-1;}
            switch(centerCard){

                // neg and low case
                case 1,2,3,4,5 -> {
                    if(myCards.contains(centerCard *2 + 1)){
                        return sendCard(centerCard *2+1);
                    } else if (myCards.contains(centerCard *2+2)) {
                        return sendCard(centerCard *2+2);
                    } else {
                        return sendRandom();
                    }
                }
                // mid case
                case 6 -> {
                    if(myCards.contains(13)){
                        return sendCard(13);
                    }
                }
                case 8,7 -> {
                    if(myCards.contains(centerCard +7)){
                        return sendCard(centerCard +7);
                    } else if (myCards.contains(centerCard -5)){
                        return sendCard(centerCard -5);
                    } else {
                        return sendRandom();
                    }
                }
                case 10,9 -> {
                    if(!opponentCards.contains(centerCard +5) && myCards.contains(centerCard +5)){
                        return sendCard(centerCard +5);
                    } else if (myCards.contains(centerCard -8)){
                        return sendCard(centerCard -8);
                    } else {
                        return sendRandom();
                    }
                }
            }

        }

        return sendRandom(); // if everything fails, use random
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

    private static void printLine(String s){
        //enhanced string output
        if(allowDebug){
        System.out.println("\u001B[32m" + "[VA | "+ roundCounter/15+"] " + s  + "\u001B[0m");}
    }
}