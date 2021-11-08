import java.util.ArrayList;

public class RandomBot extends Main{

    public int victoryPoints = 0;
    public ArrayList<Integer> userCards = new ArrayList<>();

    public void populateUserCards(){
        for (int i = 1; i <= 15; i++){
            this.userCards.add(i);
        }
    }

}
