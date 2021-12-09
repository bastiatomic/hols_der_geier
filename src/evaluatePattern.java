import java.util.ArrayList;
import java.util.List;

public class evaluatePattern {

    public static void main(String[] args) {

        // [7,4,1], [10,15,1], [6,11,5], [9,14,13]
        List<ArrayList<Integer>> pattern = new ArrayList<>();

        pattern.add(new ArrayList<>());
        pattern.add(new ArrayList<>());
        pattern.add(new ArrayList<>());
        pattern.add(new ArrayList<>());


        pattern.get(0).add(0, 7);
        pattern.get(0).add(1, 4);
        pattern.get(0).add(2, 1);

        pattern.get(1).add(0, 10);
        pattern.get(1).add(1, 15);
        pattern.get(1).add(2, 1);

        pattern.get(2).add(0, 6);
        pattern.get(2).add(1, 11);
        pattern.get(2).add(2, 5);

        pattern.get(3).add(0,9);
        pattern.get(3).add(1,14);
        pattern.get(3).add(2,13);


        for (ArrayList<Integer> entry : pattern){
            System.out.println(getEvaluation(entry.get(0), entry.get(2), entry.get(1)));
        }

        //System.out.println(getEvaluation(10,15,6));


    }
    public static float getEvaluation(int centerCard, int userCard, int opponentCard){
        float cost1, cost2, gain1, gain2, chance1, chance2;
        float bias = 3/120f;

        chance1 = 0;

        cost1 = -userCard/120f;
        cost2 = -opponentCard/120f;

        if(userCard>opponentCard){
            gain1 = centerCard;
            gain2 = 0;
        } else {
            gain1 = 0;
            gain2 = centerCard;
        }

        if(!((userCard >= centerCard-2 && userCard <= centerCard+7) || (userCard==1) || (userCard==2))){
            chance1 = -centerCard;
        }
        if(!((opponentCard >= centerCard-2 && opponentCard <= centerCard+7) || (opponentCard==1) || (opponentCard==2))){
            chance2 = -centerCard;
        }

        return cost1 + gain1 +bias+ chance1;
    }

}
