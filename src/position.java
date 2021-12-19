import java.util.ArrayList;
import java.util.List;

class position {

    public static void main(String[] args) {


        List<ArrayList<Integer>> x = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            x.add(new ArrayList<>());

            if (i % 2 != 0){
                x.get(i).add(-1);
            } else {
                x.get(i).add(1);
                x.get(i).add(2);
                x.get(i).add(3);

            }


        }
        System.out.println(x);

    }


}