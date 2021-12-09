
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Pattern2 {

    //global values (not affected by recursive behavior!)
    static double counter = 0;
    static double counter2 = 0;
    static double maxEval = -99;

    static ArrayList<Integer> centerCards = new ArrayList<>();
    static ArrayList<Integer> userCards = new ArrayList<>();
    static ArrayList<Integer> opponentCards = new ArrayList<>();

    //static List<ArrayList<Integer>> globalGameTree = new ArrayList<>();

    public static void main(String[] args) {

        ArrayList<Integer> gameTree = new ArrayList<>();

        centerCards.add(1);
        //centerCards.add(2);
        //centerCards.add(3);

        for (int i = 6; i <= 7 ; i++) {
            userCards.add(i);
        }
        for (int i = 8; i <=9 ; i++) {
            opponentCards.add(i);

        }

        double sum = iterateOverAll(gameTree, 2);

        DecimalFormat formatter = new DecimalFormat("#,###");

        System.out.println(formatter.format(sum));

       System.out.println("counter2: " + counter2);
       System.out.println("counter1: " + counter);
    }

    public static double iterateOverAll(ArrayList<Integer> currentDepth, int depth) {
        if (depth == 0) {
            counter2 ++;
            float currentHigh = evaluatePattern(currentDepth);
            System.out.println(currentDepth);
            //System.out.println(currentDepth.size());
            //System.out.println(currentHigh);

            return currentHigh; // return at the end of each branch
        } else {
            for (int centerCard : centerCards) { // for EACH child of position
                for (int userCard : userCards) {
                    for (int opponentCard : opponentCards) {
                        //System.out.println(centerCard + " " + userCard +" " + opponentCard);
                        counter ++;

                        maxEval = -99;

                        currentDepth.add(centerCard);
                        currentDepth.add(userCard);
                        currentDepth.add(opponentCard);

                        double eval = iterateOverAll(currentDepth, depth-1);
                        maxEval = Math.max(maxEval, eval);


                    }
                }
            }
        }
        return maxEval; // return at the end of the entire recursion
    }

    public static float evaluatePattern(ArrayList<Integer> pattern){
        //System.out.println(pattern);
        int sum = 0;

        for (int entry : pattern){
            sum += entry;
        }

        return sum;
    }
}

