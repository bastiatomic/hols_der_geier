import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class scribble {

    static ArrayList<Integer> userCards = new ArrayList<>();
    static ArrayList<Integer> centerCards = new ArrayList<>();

    public static void main(String[] args) {
        int counter = 0;

        for (int i = 1; i < 15; i++) {
            userCards.add(i);
        }
        for (int i = -5; i <= 10 ; i++) {
            if (i !=0){
                centerCards.add(i);
            }
        }

        HashMap<Integer, HashMap<Integer, Integer>> opponentPattern = new HashMap<>();
        // centerCard, (opponentCard, probability)

        int probability = 0;

        for (int i = -5; i <= 10; i++) {
            if (i != 0){
                opponentPattern.put(i, new HashMap<>());
                for (int j = 1; j <= 15 ; j++) {
                    opponentPattern.get(i).put(j, probability);
                }
            }
        }
        for (int entry = -5; entry <= 10; entry++){
            //System.out.println(entry);
           // System.out.println(opponentPattern.get(entry));

        }

        for (int i = 1; i <= 100; i++) {
            int rCenterCard = getCenterCard();
            int rUserCard = getUserCard();
            int n = opponentPattern.get(rCenterCard).get(rUserCard)+1;
            opponentPattern.get(rCenterCard).put(rUserCard, n);
        }

        for (int entry = -5; entry <= 10; entry++){
            System.out.println("cC " + entry + ": " + opponentPattern.get(entry));

        }
    }
    public static int getCenterCard(){
        int index = new Random().nextInt(centerCards.size());
        return centerCards.get(index);

    }
    public static int getUserCard(){
        int index = new Random().nextInt(userCards.size());
        return userCards.get(index);

    }
}

