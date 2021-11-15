import java.util.ArrayList;
import java.util.Random;

public class OneBot extends Main{

    public int decideCard(int centerCardsChoice, ArrayList<Integer> userCards, ArrayList<Integer> TwoBotUserCards){

        int index2 = new Random().nextInt(userCards.size());
        int botChoice = userCards.get(index2); // get a card based on the given index
        userCards.remove(index2); // remove the card based on the given index

        return botChoice;

    }
}
