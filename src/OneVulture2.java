import java.util.ArrayList;
import java.util.Random;

public class OneVulture2 extends HolsDerGeierSpieler {

    private static final ArrayList<Integer> userCards = new ArrayList<>();
    private int choice;

    public OneVulture2() {
        reset();
    }

    public void reset() {
        userCards.clear();
        for (int i = 1; i <= 15; i++) {
            userCards.add(i);
        }
    }

    public int gibKarte(int centerCard) {
        //System.out.println(userCards);

        int n = testAvailbility(centerCard);

        if (userCards.contains(n)){
            choice = n;
        } else {
            int randindex = new Random().nextInt(userCards.size());
            choice = userCards.get(randindex);
        }
        //System.out.println("removing: " + choice);
        userCards.removeIf(name -> name.equals(choice));
        return  choice;



    }

    public int testAvailbility(int centerCard) {

        switch (centerCard) {
            case -5, 5 -> {
                return 5 * 2 + 1;
            }
            case -4, 4 -> {
                return 4 * 2 + 1;
            }
            case -3, 3 -> {
                return 3 * 2 + 1;
            }
            case -2, 2 -> {
                return 2 * 2 + 1;
            }
            case -1, 1 -> {
                return 2 + 1;
            }
            case 6 -> {
                return 13;
            }
            case 7, 9 -> {
                return 14;
            }
            case 8, 10 -> {
                return 15;
            }
            default -> {
                int randindex = new Random().nextInt(userCards.size());
                return userCards.get(randindex);
            }
        }
    }
}
