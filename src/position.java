import java.util.ArrayList;

public class position {

    // these cards have been played!
    ArrayList<Integer> centerCards;
    ArrayList<Integer> userCards;
    ArrayList<Integer> opponentCards;

    public position(ArrayList<Integer> centerCards, ArrayList<Integer> userCards, ArrayList<Integer> opponentCards) {
        this.centerCards = centerCards;
        this.userCards = userCards;
        this.opponentCards = opponentCards;
    }



}
