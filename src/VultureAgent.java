import java.util.ArrayList;
import java.util.Random;

public class VultureAgent extends HolsDerGeierSpieler {

    private static final ArrayList<Integer> opponentCards=new ArrayList<>();
    private static final ArrayList<Integer> userCards=new ArrayList<>();

    public VultureAgent()
    {
        reset();
    }

    public void reset() {
        opponentCards.clear();
        userCards.clear();
        for (int i=15;i>0;i--) {
            opponentCards.add(i);
            userCards.add(i);
        }
    }

    public int gibKarte(int centerCard) {

        if (letzterZug() !=-99) {
            opponentCards.removeIf(name -> name.equals(letzterZug()));
        }

        //case: convert negative into positive. -1 == +1
        if (centerCard < 0) {
            centerCard *= -1;
        }

        //case: {-5,...,15} (no 0)
        switch (centerCard){

            case 1,2,3,4,5 -> {
                if(testAvailbility(centerCard *2 + 1)){
                    return sendCard(centerCard *2+1);
                } else if (testAvailbility(centerCard *2+2)) {
                    return sendCard(centerCard *2+2);
                } else {
                    return sendRandom();
                }
            }

            case 10,9 -> {
                if(!opponentCards.contains(centerCard +5) && testAvailbility(centerCard +5)){
                    return sendCard(centerCard +5);
                    } else if (testAvailbility(centerCard -8)){
                        return sendCard(centerCard -8);
                    } else {
                    return sendRandom();
                }
            }
            case 8,7 -> {
                if(testAvailbility(centerCard +7)){
                    return sendCard(centerCard +7);
                } else if (testAvailbility(centerCard -5)){
                    return sendCard(centerCard -5);
                } else {
                    return sendRandom();
                }
            }
            case 6 -> {
                if(testAvailbility(6+7)){
                    return sendCard(6+7);
                }
            }
        }

        // case: {-∞,...,-6 | 16,...,∞}
        return sendRandom();
        
    }
    private static boolean testAvailbility(int number){
        return userCards.contains(number);
    }
    private static int sendRandom(){
        int index = new Random().nextInt(userCards.size());
        int choice = userCards.get(index);
        return sendCard(choice);
    }
    private static int sendCard(int number) {
        userCards.removeIf(name -> name.equals(number));
        return number;
    }
}