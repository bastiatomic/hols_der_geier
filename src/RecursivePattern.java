// for evaluation, I need 6 variables, 3x the current list to
import java.util.ArrayList;

public class RecursivePattern {

    public static int counter;
    public static int counter2;
    public static ArrayList<Integer> centerCards = new ArrayList<>();
    public static ArrayList<Integer> userCards = new ArrayList<>();
    public static ArrayList<Integer> opponentCards = new ArrayList<>();
    public static int maxValue = 0;

    public static void main(String[] args){


        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        ArrayList<Integer> c = new ArrayList<>();

        fillCards(centerCards);fillCards(userCards);fillCards(opponentCards);

        System.out.println(recursion(a,b,c, 3));
        System.out.println("all unique values counter: " + counter);
        System.out.println("last branch counter: " + counter2);
        System.out.println("MaxValue: " + maxValue);


    }

    public static int recursion(ArrayList<Integer> n1, ArrayList<Integer> m1,ArrayList<Integer> k1, int depth){

        if (depth == 0){
            counter2 ++; // count only the end branches / results
            //n1,m1,k1 are the "position value" // Sebastian Lague minimax
            return evaluateChange(n1,m1,k1); // return evaluation number here !!!
        }


        for (int centerCard : centerCards){
            for (int userCard: userCards){
                for (int opponentCard : centerCards) {
                    counter++; // counting all unique occurrences

                    ArrayList<Integer> n = new ArrayList<>(n1);
                    ArrayList<Integer> m = new ArrayList<>(m1);
                    ArrayList<Integer> k = new ArrayList<>(k1);
                    n.add(centerCard);
                    m.add(userCard);
                    k.add(opponentCard);

                    //evaluateChange(userCards, m);
                    maxValue = Math.max(maxValue, recursion(n, m,k, depth - 1));

                }
            }
        }

        return maxValue;

    }


    public static void fillCards(ArrayList<Integer> cards){

        for (int i = 1; i <= 3 ; i++) {
            cards.add(i);

        }

    }

    public static int evaluateChange( ArrayList<Integer> centerCards1, ArrayList<Integer> userCards1, ArrayList<Integer> opponentCards1){

        //System.out.println(newUserCards1 + " " + newUserCards3 + " " + newUserCards2);
        // + for userCards1, - for OpponentCards1,
        int evaulationValue = 0;
        for(int entry : userCards1){
            evaulationValue += (entry*2);
        }

        return evaulationValue;




    }

}
