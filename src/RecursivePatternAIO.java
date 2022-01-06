// for evaulation I need 6 LISTS, 3 with the cards available and 3 with the cards already played
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RecursivePatternAIO {

    public static int counter;
    public static int counter2;
    public static ArrayList<Integer> centerCards = new ArrayList<>();
    public static ArrayList<Integer> userCards = new ArrayList<>();
    public static ArrayList<Integer> opponentCards = new ArrayList<>();
    public static List<ArrayList<Integer>> maxValue = new ArrayList<>();
    public static List<ArrayList<Integer>> localValue = new ArrayList<>();
    public static HashMap<Integer, ArrayList<Integer>> considerMap = new HashMap<>();
    public static int globalArrayLength = 9;

    public static void main(String[] args){

        List<ArrayList<Integer>> x1 = new ArrayList<>();




        for (int i = -5; i <= 10; i++) {
            if(i != 0){
                considerMap.put(i,new ArrayList<>(Arrays.asList(Math.abs(i)+5,Math.abs(i)+4, Math.abs(i)+3)));
            }

        }

        for (int i = 0; i < globalArrayLength; i++) {
            maxValue.add(new ArrayList<>());
            maxValue.get(i).add(0);
            localValue.add(new ArrayList<>());
            localValue.get(i).add(0);
        }

        fillCards(centerCards);fillCards(userCards);fillCards(opponentCards);
        fillRecursiveLists(x1);
        System.out.println("print init x1: " + x1);
        fillCards(x1.get(1));fillCards(x1.get(2));
        //x1.get(3).add(0);
        fillCenterCards(x1.get(0));
        //position.get(3) = maxValue für recursion

        System.out.println("print modified x1: " + x1);

        System.out.println(recursion(x1, 2));
        System.out.println("all unique values counter: " + counter);
        System.out.println("last branch counter: " + counter2);
        System.out.println("MaxValue: " + maxValue);

    }

    public static List<ArrayList<Integer>> recursion(List<ArrayList<Integer>> position, int depth){

        if (depth == 0){
            //counter2 ++; // count only the end branches / results
            //n1,m1,k1 are the "position value" // Sebastian Lague minimax
            return evaluatePosition(position); // return evaluation number here !!!
        }

        //position.get(0).set(0,considerMap.get())
        // centerCards müssen lokal bleiben


        // remove all unique occurances
        for (int centerCard : position.get(0)){
            for (int userCard: position.get(1)){
                for (int opponentCard : position.get(2)) {
                    counter++; // counting all unique occurrences

                    //System.out.println(position);

                    // fill all lists, remove the given three values (index 0,1,2)
                    List<ArrayList<Integer>> x2 = new ArrayList<>();
                   // System.out.println(centerCard +" " + userCard + " " + opponentCard);
                    allignList(x2, position, centerCard, userCard, opponentCard);

                    try {
                        localValue = recursion(x2, depth - 1);
                        if(localValue.get(3).get(0) > maxValue.get(3).get(0)){
                            maxValue = localValue;
                        }
                    } catch (IndexOutOfBoundsException ignored){
                    }


                }
            }
        }

        return position;

    }

    public static void fillCards(ArrayList<Integer> cards){

        for (int i = 1; i <= 15 ; i++) {
            cards.add(i);

        }

    }

    public static List<ArrayList<Integer>> evaluatePosition( List<ArrayList<Integer>> position){

        counter2 ++;

        if (position.get(4).size() != 0){
            int localSum = 0;
            for(int entry : position.get(4))
                localSum += entry;
            position.get(3).add(localSum);

        } else {
            position.get(3).add(0);
        }

        return position;
    }

    public static void fillRecursiveLists(List<ArrayList<Integer>> list){
        for (int i = 0; i < globalArrayLength; i++) {
            list.add(new ArrayList<>());
        }
    }

    public static void allignList(List<ArrayList<Integer>> childList, List<ArrayList<Integer>> parentList, int r1, int r2, int r3) {

        for (int i = 0; i < globalArrayLength; i++) {
            childList.add(new ArrayList<>(parentList.get(i)));
        }

        childList.get(0).removeIf(name -> name.equals(r1));
        childList.get(1).removeIf(name -> name.equals(r2));
        childList.get(2).removeIf(name -> name.equals(r3));

        childList.get(6).add(r1);
        childList.get(7).add(r2);
        childList.get(8).add(r3);

        //calculate position[3],position[4],position[5]

        if((r1 > 0 && r2 > r3)  || (r1 < 0 && r2 < r3)){
            childList.get(4).add(r1);
            childList.get(5).add(0);

        } else {
            childList.get(5).add(r1);
            childList.get(4).add(0);
        }

        //System.out.println(parentList +" | " + childList);

    }

    public static void fillCenterCards(ArrayList<Integer> list){
        for (int i = -5; i <= 10 ; i++) {
            if (i != 0){
                list.add(i);
            }

        }

    }
}
