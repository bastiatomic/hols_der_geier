import java.util.*;

public class OneBot2 extends Main{

    HashMap<Integer, Integer> considerationMap = new HashMap<>();
    HashSet<Integer> centerCardsPlayed = new HashSet<>();

    public int decideCard(int centerCardsChoice, ArrayList<Integer> userCards, ArrayList<Integer> TwoBotUserCards){
        int botChoice;

        //fill HashMap //standard library // KEY = centerCards, VALUE = OneBotCards
        for (int i= -5; i<=10; i++){
            if (i != 0 && !centerCardsPlayed.contains(i)){
                if(i>0){
                    considerationMap.put(i, i+5);
                }else{
                    considerationMap.put(i, (i*-2+5));
                }
            }
        }
        //case: can I win with this centerCardChoice?
        // ...

        //case: centerCardsChocie = 0
        if (centerCardsChoice == 0){
            return Collections.min(considerationMap.values());
        }

        //case: centerCardsChoice > 10
        if (centerCardsChoice > 10){
            return  Collections.max(considerationMap.values());
        }

        printLine("Mechanic","OneBot " + considerationMap + "   (" + centerCardsChoice + ")");

        // decide based on standard library
        if (considerationMap.containsKey(centerCardsChoice)) {
            botChoice = considerationMap.get(centerCardsChoice);
            considerationMap.remove(centerCardsChoice);
        } else {
            botChoice = 0;
        }


        centerCardsPlayed.add(centerCardsChoice);
        return botChoice;

    }
}
