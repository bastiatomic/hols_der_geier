import java.util.*;

public class OneBot2 extends Main{

    ArrayList<Integer> considerCards = new ArrayList<>();
    int [] indexList = {0,1,-1,2,-2};
    int negPosFactor;

    public int decideCard(int centerCardsChoice, ArrayList<Integer> userCards, ArrayList<Integer> TwoBotUserCards){
        int botChoice = 0;
        considerCards.clear();

        //fill HashMap //standard library // KEY = centerCards, VALUE = OneBotCards
        for (int index2 : indexList){
            if (centerCardsChoice <0){
                negPosFactor = (centerCardsChoice * -3) + 5;
            } else{
                negPosFactor = 5;
            }
            // fill considerCards with knowledge
            int index3 = centerCardsChoice + negPosFactor + index2;
            if (userCards.contains(index3)) {
                considerCards.add(index3);
            }
        }
        System.out.println(centerCardsChoice +"  "+ considerCards);

        //case: can I win with this centerCardChoice?
        // ...

        //case: centerCardsChocie = 0
        if (centerCardsChoice == 0){
            botChoice = Collections.min(considerCards);
            userCards.remove((Integer) botChoice);
            return  botChoice;
        }

        //case: centerCardsChoice > 10
        if (centerCardsChoice > 10){
            botChoice = Collections.max(considerCards);
            userCards.remove((Integer) botChoice);
            return  botChoice;
        }

        for (int i = 0; i < considerCards.size(); i++) {
            if(userCards.contains(considerCards.get(0))){
                return considerCards.get(0);
            } else {
                considerCards.remove(0);
            }

        }



        printLine("Mechanic","OneBot " + considerCards + "   (" + centerCardsChoice + ")");

        // decide based on standard library

        return botChoice;

    }
}
