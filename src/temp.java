import java.util.ArrayList;
import java.util.Random;

public class temp {

    public static void main(String[] args) {

        ArrayList<Integer> centerCards = new ArrayList<>();
        centerCards.add(30);
        centerCards.add(50);
        centerCards.add(150);
        centerCards.add(500);

        Integer randomInt = centerCards.get(new Random().nextInt(centerCards.size()));
        System.out.println(randomInt);

        System.out.println(centerCards);
    }
}
