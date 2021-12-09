import java.util.ArrayList;

//minimax explained by Sebastian Lague (YouTube)
public class Patterns {

    static int counter = 0;
    static int counterDepthZero =0;

    public static void main(String[] args) {

        System.out.println(iterateOverAll(2,new int [] {}, 3));

        System.out.println("counterDepthZero: "+ counterDepthZero);


        test1(new int[]{2, 3, 4});
    }

    public static int iterateOverAll(int entryAmount,int [] position, int depth) {
        if (depth == 0) {
            counterDepthZero ++;
            return counter;
        } else {
            for (int i = 0; i < entryAmount; i++) {
                counter ++;
                System.out.println(i);
                //add all int[] position entries to new int [] {} variable
                iterateOverAll(entryAmount, new int[] {i}, depth - 1);
            }

        }
        return counter;
    }
    public static void test1(int[] list){

    }
}
