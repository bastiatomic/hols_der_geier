import java.util.ArrayList;
import java.util.Arrays;

class Scribble {
    public static void main(String[] args) {

        ArrayList<int[]> gameTree = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            float n1 = (float) Math.random()*100;
            float m1 =  (float) Math.random()*100;
            float k1 =  (float) Math.random()*100;

            int n = (int) n1;
            int m = (int) m1;
            int k = (int) k1;
            gameTree.add(new int[]{n,m,k});
        }

        for (int[] n : gameTree){
            System.out.println(Arrays.toString(n));
        }

    }
}