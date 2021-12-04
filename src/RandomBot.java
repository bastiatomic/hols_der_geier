import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBot extends HolsDerGeierSpieler {

    // this variable never gets cleared with reset()
    List<ArrayList<Integer>> opponentPattern = new ArrayList<>();

    private final ArrayList<Integer> nochNichtGespielt = new ArrayList<>();
    private final ArrayList<Integer> opponentCards = new ArrayList<>();
    private final ArrayList<Integer> centerCards = new ArrayList<>();
    private boolean allowInit = true;
    private int roundCounter;

    public RandomBot() {
        reset(); // gets called after every game (15-round game)
    }

    public void reset() {
        nochNichtGespielt.clear();
        opponentCards.clear();
        centerCards.clear();
        for (int i = 1; i <= 15; i++) {
            nochNichtGespielt.add(i);
        }
    }

    public int gibKarte(int naechsteKarte) throws IOException {
        roundCounter ++;
        centerCards.add(naechsteKarte);

        if (letzterZug() == -99 && allowInit){
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
            allowInit = false;
        } // init opponentPattern array

        //grab opponent
        if (letzterZug() != -99) {
            opponentCards.add(letzterZug());
            int centerCard = centerCards.get(centerCards.size() - 2);
            int opponentCard = opponentCards.get(opponentCards.size() - 1);


            // add the card to matrix here
            int indexTransfer;
            indexTransfer = (5+centerCard)*15 + opponentCard -1;

            int n = opponentPattern.get(indexTransfer).get(2) + 1;
            opponentPattern.get(indexTransfer).set(2,n);


        }

        // detect opponent pattern based on the index of opponentCards and centerCards (they'll help me)

        //System.out.println(centerCards.size()+" "+roundCounter);
        if(centerCards.size() == 15 && roundCounter == (20*15)){
            //printPattern(opponentPattern);
            writeToThreeCSV(opponentPattern);
            //System.out.println("write to csv");
        }

        // actual giving feedback to mainframe
        int randindex = new Random().nextInt(nochNichtGespielt.size());
        int n = nochNichtGespielt.get(randindex);
        nochNichtGespielt.removeIf(name -> name.equals(n));
        return n;
    }
    public static void printPattern(List<ArrayList<Integer>> opponentPattern){

        for (ArrayList<Integer> integers : opponentPattern) {
            if(integers.get(2) != -990){
                System.out.println(integers);}
        }

    }
    public static void writeToThreeCSV(List<ArrayList<Integer>> opponentPattern) throws IOException {
        for (ArrayList<Integer> integers : opponentPattern) {
            FileWriter writer = new FileWriter("database.csv", true);
            writer.append(String.valueOf(integers.get(0)));
            writer.append(',');
            writer.append(String.valueOf(integers.get(1)));
            writer.append(',');
            writer.append(String.valueOf(integers.get(2)));
            writer.append(',');
            writer.append('\n');

            writer.flush();
            writer.close();

        }


    }
}