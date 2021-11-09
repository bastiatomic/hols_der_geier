import java.util.ArrayList;
import java.util.Random;

public class DataDrivenBot extends Main{

    public int victoryPoints = 0;
    public ArrayList<Integer> userCards = new ArrayList<>();

    public int decideCard(){

        int index2 = new Random().nextInt(userCards.size());
        int botChoice = userCards.get(index2); // get a card based on the given index
        userCards.remove(index2); // remove the card based on the given index

        return botChoice;

    }
    public void populateBotCards(){
        for (int i = 1; i <= 15; i++){
            this.userCards.add(i);
        }
    }

}
