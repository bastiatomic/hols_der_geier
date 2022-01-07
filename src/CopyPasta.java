import java.util.HashMap;
import java.util.Map;

/*
CORE IDEA
der Bot spielt immer die Züge des Gegners aus der vorausgegangenen Runde. That's it. No magic at all.
 */

public class CopyPasta extends HolsDerGeierSpieler{

    int roundCounter =0;
    HashMap<Integer, Integer> gameTree = new HashMap<>();
    HashMap<Integer, Integer> gameTreeCurrentRun = new HashMap<>();
    static int lastCenterCard;

    public CopyPasta() {
        reset(); // gets called after every game (15-round game)
    }

    public void reset() {
        gameTree.clear();
    }

    public int gibKarte(int centerCard){
        roundCounter ++;
        int choice;

        if(roundCounter > 15){
            choice = gameTreeCurrentRun.get(centerCard);
        } else {

            if(centerCard<0){
                choice = centerCard+6;
            } else {
                choice = centerCard+5;
            }
        }

        fillGameTree();
        lastCenterCard = centerCard;

        return choice;
    }

    public void fillGameTree(){
        if(letzterZug() != -99){
            gameTree.put(lastCenterCard, letzterZug());
        }
        if(roundCounter % 15 == 0){
            //System.out.println(gameTree);
            int keySum = 0, valueSum = 0;
            for (Map.Entry<Integer, Integer> entry : gameTree.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                keySum += key;
                valueSum += value;
            }
            if(40 - keySum != 0){
                gameTree.put(40 - keySum, 120-valueSum);
            }

            //deep copy hashmap
            gameTreeCurrentRun.clear();
            for (Map.Entry<Integer, Integer> entry : gameTree.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                gameTreeCurrentRun.put(key,value);
            }
        }
    }
}