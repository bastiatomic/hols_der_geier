import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int centerCardsChoice;

    public static void main(String[] args) {

        ArrayList<Integer> centerCards = new ArrayList<>();
        Scanner input1 = new Scanner(System.in);
        boolean running = true;
        RandomBot TwoBot = new RandomBot();
        DataDrivenBot OneBot = new DataDrivenBot();

        // fill hashSets
        fillCenterCards(centerCards);
        TwoBot.populateBotCards();
        OneBot.populateBotCards();


        //game loop
        while (centerCards.size() != 0 && running) {

            // centerCards
            int index = new Random().nextInt(centerCards.size());
            centerCardsChoice = centerCards.get(index); // get a card based on the given index
            centerCards.remove(index); // remove the card based on the given index
            printLine("Log", "CenterCards: " + centerCards);
            printLine("Game", "Revealed number: " + centerCardsChoice);

            //bot decisions based on their behavior patterns defined in their classes
            int currentOneBotDecision = OneBot.decideCard();
            printLine("Bot", "OneBot card list: " + OneBot.userCards);
            printLine("Bot", "OneBot decision: " + currentOneBotDecision);

            int currentTwoBotDecision = TwoBot.decideCard();
            printLine("Bot", "TwoBot card list: " + TwoBot.userCards);
            printLine("Bot", "TwoBot decision: " + currentTwoBotDecision);

            // winner checker
            int winner = winnerChecker(centerCardsChoice, currentOneBotDecision, currentTwoBotDecision);
            if (winner == 1){
                OneBot.victoryPoints += centerCardsChoice;
                printLine("Game", "OneBot gained " + centerCardsChoice + " points.");
            } else {
                TwoBot.victoryPoints += centerCardsChoice;
                printLine("Game", "TwoBot gained " + centerCardsChoice + " points.");
            }

            //overview after the round (or whole game, if winner checker defines a winner
            printLine("Game", "OneBot points: " + OneBot.victoryPoints);
            printLine("Game", "TwoBot points: " + TwoBot.victoryPoints);

            //mechanics
            printLine("Mechanic", "Procced? (enter or 'stop')");

            running = ! input1.nextLine().equals("stop");
        }

    }

    public static void fillCenterCards(ArrayList<Integer> array){
        for (int i = -5; i < 0; i++){
            array.add(i);
        }
        for (int i = 1; i<= 10; i++){
            array.add(i);
        }
    }

    public static void printLine(String prefix, String text){
        String color;
        switch(prefix){
            case "Game" -> color = "\u001B[32m";
            case "Log" -> color = "\u001B[33m";
            case "Mechanic" -> color = "\u001B[31m";
            case "Bot" -> color = "\u001B[34m";
            default -> color = "\u001B[30m";

        }

        System.out.println(color + "[" + prefix + "] " + text + "\u001B[0m");
    }

    public static int winnerChecker(int revealedCard, int FirstBot, int SecondBot) {
        if (FirstBot == SecondBot) {
            printLine("Game", "Same card number! Revealing next card!");
            return 0;
        } else if (revealedCard > 0 && FirstBot > SecondBot) {
            printLine("Mechanic", "First case");
            return 1;
        } else if (revealedCard > 0 && SecondBot > FirstBot) {
            printLine("Mechanic", "Second case");
            return 2;
        } else if (revealedCard < 0 && FirstBot > SecondBot) {
            printLine("Mechanic", "Third case");
            return 2;
        } else {
            printLine("Mechanic", "Fourth case");
            return 1;
        }

    }

}