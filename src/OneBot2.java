import java.util.*;

public class OneBot2 extends Main{

    ArrayList<Integer> considerCards = new ArrayList<>();
    int [] indexList = {0,1,-1,2,-2};
    int negPosFactor;

    public int decideCard(int centerCardsChoice, ArrayList<Integer> userCards, ArrayList<Integer> TwoBotUserCards){
        int botChoice;
        considerCards.clear(); // prevent considerCards from piling up

        //populate considerCards
        for (int index2 : indexList) {
            if (centerCardsChoice < 0) {
                negPosFactor = (centerCardsChoice * -3) + 5;
            } else {
                negPosFactor = 5;
            }
            int index3 = centerCardsChoice + negPosFactor + index2;
            if (userCards.contains(index3)) {
                considerCards.add(index3);
            }
        }

        //case: considerCards are empty -> return first element of userCards (WIP)
        if (considerCards.isEmpty()){
            botChoice = userCards.get(0);
            userCards.remove(userCards.get(0));
            return  botChoice;
        }

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

        //return fitting considerCards (from perfect -> good)
        for (int i = 0; i < considerCards.size(); i++) {
            if(userCards.contains(considerCards.get(0))){
                userCards.remove(considerCards.get(0));
                return considerCards.get(0);
            } else {
                considerCards.remove(0);
            }

        }

        //case: no valid solution found (exception handling) -> return random item from userCards
        botChoice = userCards.get(0);
        return botChoice;

    }
}
