import java.util.ArrayList;
import java.util.List;

public class scribble2 {

    public static void main(String[] args) {

        List<ArrayList<Integer>> opponentPattern = new ArrayList<>();

        int index = 0;

        for (int i = -5; i <=10 ; i++) {
            for (int j = 1; j <=15 ; j++) {
                opponentPattern.add(new ArrayList<>());
                opponentPattern.get(index).add(i);
                opponentPattern.get(index).add(j);
                opponentPattern.get(index).add(0);
                index ++;
            }
        }

        int indexTransfer = 0;
        // add new entry
        int centerCard = 10;
        int opponentCard = 15;
        // -5 verweist auf die ersten 15, -4 auf 16-30, -3 auf 31 - 45
        if(centerCard<0){
            indexTransfer = (5+centerCard)*15 + opponentCard -1;
        } else {
            indexTransfer = (5+centerCard)*15 + opponentCard -1;
        }



        System.out.println(indexTransfer);

        opponentPattern.get(indexTransfer).set(2,1);

        printPattern(opponentPattern);


    }
    public static void printPattern(List<ArrayList<Integer>> opponentPattern){

        for (ArrayList<Integer> integers : opponentPattern) {
            System.out.println(integers);
        }

    }
}
