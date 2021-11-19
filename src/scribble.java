import java.util.ArrayList;
import java.util.Arrays;

public class scribble {

    public static void main(String[] args) {

        ArrayList<Integer> userCards = new ArrayList<>
                (Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));

        ArrayList<Integer> considerCards = new ArrayList<>();
        int centerCardChoice = 8;
        int index;
        int negPosFactor;
        System.out.println("--------------------------------");
        // 10 -> 10, 11, 9, 12, 8}
        // 0 1 -1 2 -2
        // -5 -> 15,14,13
        // -4 -> 12,11,10
        int [] indexList = {0,1,-1,2,-2};

        for (int index2 : indexList){
            if (centerCardChoice <0){
                negPosFactor = (centerCardChoice * -3) + 5;
            } else{
                negPosFactor = 5;
            }
            // fill considerCards with knowledge
            int index3 = centerCardChoice + negPosFactor + index2;
            if (userCards.contains(index3)) {
                considerCards.add(index3);
            }
        }
        System.out.println(centerCardChoice +"  "+ considerCards);
        considerCards.clear();


        System.out.println("----------------");


    }

}

