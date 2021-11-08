import java.util.ArrayList;

public class Human {

    private int victoryPoints;
    private ArrayList<Integer> userCards = new ArrayList<>();

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void addUserCards(int entry) {
        this.userCards.add(entry);
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public ArrayList getUserCards() {
        return userCards;
    }
    public void addStarterCards(){
        for (int i = 1; i<=15; i++){
            this.userCards.add(i);
        }
    }
}
