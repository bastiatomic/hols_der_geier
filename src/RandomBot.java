import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBot extends HolsDerGeierSpieler {

    // this variable never gets cleared with reset()
    List<ArrayList<Integer>> opponentPattern = new ArrayList<>();
    List<ArrayList<Integer>> currentHighs = new ArrayList<>();
    List<ArrayList<Integer>> fullOpponentPattern = new ArrayList<>();
    // currentHigh = [index, cC, oC, amountPlayedThisMove]

    private final ArrayList<Integer> nochNichtGespielt = new ArrayList<>();
    private final ArrayList<Integer> opponentCards = new ArrayList<>();
    private final ArrayList<Integer> centerCards = new ArrayList<>();
    private boolean allowInit = true;
    private int roundCounter;
    private int lastCenterCard = 0;
    private int userLastCard = 0;
    private final ArrayList<Float> myEvaluations = new ArrayList<>();
    private final ArrayList<Float> opponentEvaluations = new ArrayList<>();

    public RandomBot() {
        reset(); // gets called after every game (15-round game)
    }

    public void reset() {
        nochNichtGespielt.clear();
        opponentCards.clear();
        centerCards.clear();
        for (int i = 1; i <= 15; i++) {
            nochNichtGespielt.add(i);
        }
    }

    public int gibKarte(int centerCard) throws IOException {
        roundCounter ++;
        centerCards.add(centerCard);

        //init opponentPattern arrays
        if (letzterZug() == -99 && allowInit){
            int index = 0;

            for (int i = -5; i <=10 ; i++) {
                for (int j = 1; j <=15 ; j++) {
                    opponentPattern.add(new ArrayList<>());
                    opponentPattern.get(index).add(i);
                    opponentPattern.get(index).add(j);
                    opponentPattern.get(index).add(0);
                    index ++;
                }
            }


            for (int i = 0; i < 15; i++) {
                currentHighs.add(new ArrayList<>());
                currentHighs.get(i).add(i);
                if (i < 5){
                    currentHighs.get(i).add(i-5);
                } else {
                    currentHighs.get(i).add(i-4);
                }
                currentHighs.get(i).add(0);
                currentHighs.get(i).add(0);
                //eg. [5, -3, 6, 50]
                // index = 5, cC = -3, oC = 6, amount = 50;


            }

            allowInit = false;
        }

        //grab opponent
        if (letzterZug() != -99) {
            opponentCards.add(letzterZug());
            int centerCard2 = centerCards.get(centerCards.size() - 2);
            int opponentCard = opponentCards.get(opponentCards.size() - 1);

            //case: evaluatePattern
            //System.out.println(lastCenterCard + " " + userLastCard + " " + letzterZug());
            myEvaluations.add(getEvaluation(lastCenterCard, userLastCard, letzterZug()));
            opponentEvaluations.add(getEvaluation(lastCenterCard, letzterZug(), userLastCard));




            // add the card to matrix here
            int indexTransfer;
            indexTransfer = (5+centerCard2)*15 + opponentCard -1;

            int n = opponentPattern.get(indexTransfer).get(2) + 1;
            opponentPattern.get(indexTransfer).set(2,n);
        }


        // write your code here
        /*
        iterate over length(opponentPattern) but with 15 increment each time
        find high of each index, THEN proceed to the next index (-5,...+10)
        */

        int localHigh = 0;
        int localHighoC = 0;
        int i;
        for (i = 0; i < 15 ; i++) {
            if(opponentPattern.get(i).get(2) > localHigh){
                localHigh = opponentPattern.get(i).get(2);
                localHighoC = opponentPattern.get(i).get(1);
            }
        }
        currentHighs.get(i/15).set(1, localHigh);
        currentHighs.get(i/15).set(2, localHighoC);


        //end of round mechanic
        if(roundCounter == ((StartGeier.gamesAmount)*15)){
            roundCounter = 0;
            // System.out.println(myEvaluations);
            //System.out.println(opponentEvaluations);

            float sum1 = 0f;
            float sum2 = 0f;
            for (Float entry : myEvaluations){
                sum1 = sum1 + entry;
            }
            for (Float entry : opponentEvaluations){
                sum2 = sum2 + entry;
            }
            //System.out.println(sum1);
            //System.out.println(sum2);
        }

        // actual giving feedback to mainframe
        int randindex = new Random().nextInt(nochNichtGespielt.size());
        int n = nochNichtGespielt.get(randindex);
        nochNichtGespielt.removeIf(name -> name.equals(n));

        lastCenterCard = centerCard;
        userLastCard = n;
        return n;
    }
    public static void printPattern(List<ArrayList<Integer>> pattern){

        System.out.println("----------Printing-----------");
        for (ArrayList<Integer> integers : pattern) {
            if (integers.get(2) != 0) {
                System.out.println(integers);
            }
        }
        System.out.println("-----------Printing done----------------");
    }
    public static float getEvaluation(int centerCard, int userCard, int opponentCard){
        float cost1, gain1, chance1;
        float bias = 3/120f;

        chance1 = 0;

        cost1 = -userCard/120f;

        if(userCard>opponentCard){
            gain1 = centerCard;
        } else {
            gain1 = 0;
        }

        if(!((userCard >= centerCard-2 && userCard <= centerCard+7) || (userCard==1) || (userCard==2))){
            chance1 = -centerCard;
        }

        return cost1 + gain1 +bias+ chance1;
    }
}