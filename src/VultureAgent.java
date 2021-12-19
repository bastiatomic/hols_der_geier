
import java.util.ArrayList;
import java.util.Collections;

import java.util.Random;

public class VultureAgent extends HolsDerGeierSpieler {

    private static int gameCounter;
    private static int myLastMove, lastMoveProgram, opponentPoints, myPoints;
    private static final boolean allowDebug = false;
    private static boolean secondStrategy = false, thirdStrategy = false, fourthStrategy = false;

    private final ArrayList<Integer> myCards = new ArrayList<>();
    private final ArrayList<Integer> opponentCards = new ArrayList<>();
    private boolean switcher = true;
    private int gamesWon = 0;

    private final ArrayList<Integer> centerCardsPlayed = new ArrayList<>();

    private final ArrayList<Integer> opponentPlayedCards = new ArrayList<>();
    private final ArrayList<Integer> myPlayedCards = new ArrayList<>();
    //lastMoveProgram keeps track of the REAL value we play for (will sometimes reset, hopefully)


    public VultureAgent() {
        reset(); // gets called after every game (15-round game)
    }

    public void reset() {
        myCards.clear();
        opponentCards.clear();
        centerCardsPlayed.clear();
        for (int i = 1; i <= 15; i++) {
            myCards.add(i);
            opponentCards.add(i);
        }
        myPoints = 0;
        opponentPoints = 0;
    }

    public int gibKarte(int centerCard){
        gameCounter ++; // keep track of the current round / game -> "be aware of your surroundings"
        centerCardsPlayed.add(centerCard);
        opponentCards.removeIf(name -> name.equals(letzterZug()));
        int finalCenterCard = centerCard; // whenever I need the current played card
        // SDV has to be on top

        try{
            lastMoveProgram = centerCardsPlayed.get(centerCardsPlayed.size()-2); //vorletzter Zug
        } catch (IndexOutOfBoundsException ignored){}

        if(letzterZug() != -99) {
            opponentPlayedCards.add(letzterZug());
            printLine("----- Analysis -----");
            printLine("finalCenterCard: " + finalCenterCard);
            printLine("Was last a tie? " + (myLastMove == letzterZug()));
            printLine("My last move: " + myLastMove);

            printLine("----- Current move behavior preparation -----");
            printLine("current centerCard: " + centerCard);
            if(myLastMove == letzterZug()){
                printLine("Last move was a tie. So I'll add the last centerCard to the current one");
                centerCard += lastMoveProgram;
                printLine("Current centerCard: " + centerCard);
            }

        }

        //current work
        if(gameCounter % 15 == 0){ // calculate wheter I won this round or not
            System.out.println(centerCardsPlayed);
            myPlayedCards.add(myCards.get(0)); opponentPlayedCards.add(opponentCards.get(0));
            System.out.println(myPlayedCards);
            System.out.println(opponentPlayedCards);
            gamesWon += addWinningPoints(centerCardsPlayed, myPlayedCards, opponentPlayedCards);

        }

        if(gameCounter % 15 == 0){ // this is the call in the last round of each game

            printLine("End of game results is going to be: " + myPoints + " | " + opponentPoints);

            printLine("GamesWon: " + gamesWon);
            System.out.println("[VA] ["+gameCounter+"]" +" From " + gameCounter/15 +" games I won " + gamesWon +" ("+(float) gamesWon/(gameCounter/15)+")" );


            // adaptive behavior at the last round of each game
            if(gameCounter/15 == 15 && (float) gamesWon/(float) (gameCounter/15) < 0.5){ // played bad after 20 rounds
                System.out.println("\u001B[32m" +"[VA] adapting  ..." + "\u001B[0m");
                secondStrategy = true; // adapting to "small RH"
            }
            if(gameCounter/15 == 30 && (float) gamesWon/(float) (gameCounter/15) < 0.5){ // played bad after 40 games
                System.out.println("\u001B[32m" +"[VA] adapting again ..." + "\u001B[0m");
                thirdStrategy = true; // changing to considerIndex
            }
            if(gameCounter/15 == 45 && (float) gamesWon/(float) (gameCounter/15) < 0.5){ // played bad after 60 rounds
                System.out.println("\u001B[32m" +"[VA] adapting fourth plan ..." + "\u001B[0m");
                fourthStrategy = true; // completing changing to RH plan
            }
        }

        // smart behavior // exchangeable blocks, based on winCounter; if I lose too much with one block, I exchange them
        //printLine("We play for " + smartDetectionValue); // summed up centerCard

        if(centerCard > 10){
            //veryhighCounter ++;
            return sendCard(Collections.max(myCards));
        }

        if(!thirdStrategy && !fourthStrategy){
            switch (centerCard){

                // negative value: should prevent
                case -5,-4,-3,-2,-1 -> {
                    if(secondStrategy){
                        if(switcher) {
                            System.out.println("Switching neg strategy!");
                            System.out.println(gameCounter);
                            switcher = false;
                        }

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
            final int negativeModifier = -1; // lower values spend too much on negative cards
            //testing the effect of a random choice, if considerCardsIndex finds nothing

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
            case 1,2,3,4,5 -> {
                if(myCards.contains(centerCard *2 + 1)){
                    return sendCard(centerCard *2+1);
                } else if (myCards.contains(centerCard *2+2)) {
                    return sendCard(centerCard *2+2);
                } else {
                    return sendRandom();
                }
            }
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

    private void printLine(String s){
        if(allowDebug){
            System.out.println("\u001B[32m" + "[VA] ["+gameCounter+"] " + s + "\u001B[0m");
        }
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

        int myPoints = 0;
        int opponentPoints = 0;
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
        System.out.println(myPoints + " | " + opponentPoints);
        if(myPoints > opponentPoints){
            return 1;
        } else {
            return 0;
        }
    }
}
