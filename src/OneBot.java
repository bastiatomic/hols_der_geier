import java.util.ArrayList;
import java.util.HashMap;

public class OneBot extends Main{

    public int decideCard(int centerCardsChoice, ArrayList<Integer> userCards, ArrayList<Integer> TwoBotUserCards){

        ArrayList<Integer> considerationCards = new ArrayList<>();
        HashMap<Integer,Integer> k = new HashMap<>();
        k.put(-7, 21);
        k.put(-6, 18);
        k.put(-5, 15);
        k.put(-4, 12);
        k.put( -3, 9);
        k.put(-2, 6);
        k.put(-1, 3);
        k.put(1, 6);
        k.put(2, 7);


        // get considerationCards
        if (centerCardsChoice > 0) {
            for (int i = centerCardsChoice - 6; i < centerCardsChoice + 6; i++) {
                if (userCards.contains(i)) {
                    considerationCards.add(i);
                }
            }
        } else {
            for (int i = centerCardsChoice-2; i<= centerCardsChoice+2; i++){
                if (userCards.contains(k.get(i))) {
                    considerationCards.add(k.get(i));
                }
            }
        }

        // no fitting value found
        if (considerationCards.size()==0){
            return userCards.get(0);
        }
        printLine("Mechanic", "OneBotUserCards: " + OneBotUserCards);
        printLine("Mechanic", "OneBot consCards " + considerationCards);

        // is n+5 in myCards?; chose the highest possible cards
        int botChoice = considerationCards.get(considerationCards.size()-1);
        userCards.remove(userCards.size()-1);

        //can I win the game with the currentCardsCHoice?
        /* no */

        //can I outbid TwoBot by {n-4 ... n-1} ?
        /* no */




        return botChoice;

    }
}
