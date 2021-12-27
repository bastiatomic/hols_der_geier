/*Cerberus patterns:
- the interest of NOT getting -5 is the same as getting +10
- on average, the cost should not exceed n+5 (n*-3 for negative numbers)
- cost: the sum of all my cards are 120 points. spending 9 points for a 1 is considered bad.*/
import java.util.ArrayList;
import java.util.Random;

public class OneVulture extends HolsDerGeierSpieler {

    //basic variables
    private final ArrayList<Integer> userCards = new ArrayList<>();
    private int userChoice;
    int currentRound;

    public OneVulture(){
        reset();
    }

    public void reset(){
        userCards.clear();
        for (int i = 1; i<= 15; i++){
            userCards.add(i);
        }
    }

    public int gibKarte (int centerCardChoice) {
        currentRound++;

        //behavior variables
        int[] considerCardsIndex = {0,-1,-2}; //paying more than n+7 or less than n-2 is generally considered bad
        final int basicIncrement = 5; // basic n+5 mechanic
        final int negativeModifier = -1; // lower values spend too much on negative cards

        for (int considerIndex : considerCardsIndex){
            //case: positive behavior pattern
            if (centerCardChoice > 0 && userCards.contains(centerCardChoice + basicIncrement + considerIndex)) {
                userChoice = centerCardChoice + basicIncrement + considerIndex;
                userCards.removeIf(name -> name.equals(userChoice));

                return userChoice;

            //case: negative behavior pattern
            } else if (centerCardChoice < 0 && userCards.contains(centerCardChoice * negativeModifier + considerIndex)) {
                userChoice = centerCardChoice * negativeModifier + considerIndex;
                userCards.removeIf(name -> name.equals(userChoice));

                return userChoice;
            }
        }

        //case: considerIndex found nothing
        return randomChoice();

    }
    private int randomChoice(){
        int index = new Random().nextInt(userCards.size());
        userChoice = userCards.get(index);
        userCards.removeIf(name -> name.equals(userChoice));
        return userChoice;
    }
}