import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static int centerCardsChoice;
    public static final int calculateDepth = 5;
    public static final int gamesAmount = 1;
    public static int OneBotVictoryPoints = 0;
    public static int TwoBotVictoryPoints = 0;
    public static ArrayList<Integer> OneBotUserCards = new ArrayList<>();
    public static ArrayList<Integer> TwoBotUserCards = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        // how many rounds? ability to run n unique gaes
        for (int game = 1; game <= gamesAmount; game++) {

            ArrayList<Integer> centerCards = new ArrayList<>();
            //Scanner input1 = new Scanner(System.in);
            //boolean running = true;
            OneBot OneBot = new OneBot();
            RandomBot TwoBot = new RandomBot();
            int currentRound = 1;

            // fill hashSets
            fillCenterCards(centerCards);
            populateBotCards(OneBotUserCards);
            populateBotCards(TwoBotUserCards);


            //game loop
            while (centerCards.size() != 0 && currentRound <= calculateDepth) {

                // centerCards
                int index = new Random().nextInt(centerCards.size());
                centerCardsChoice = centerCards.get(index); // get a card based on the given index
                centerCards.remove(index); // remove the card based on the given index
                printLine("Log", "CenterCards: " + centerCards);
                printLine("Game", "Round " + (15 - centerCards.size()) + " | Revealed number: " + centerCardsChoice);

                //bot decisions based on their behavior patterns defined in their classes
                int currentOneBotDecision = OneBot.decideCard(centerCardsChoice, OneBotUserCards,TwoBotUserCards);
                //printLine("Bot", "OneBot card list: " + OneBot.userCards);
                printLine("Bot", "OneBot decision: " + currentOneBotDecision);

                int currentTwoBotDecision = TwoBot.decideCard(centerCardsChoice, TwoBotUserCards,OneBotUserCards);
                //printLine("Bot", "TwoBot card list: " + TwoBot.userCards);
                printLine("Bot", "TwoBot decision: " + currentTwoBotDecision);

                // winner checker
                int winner = winnerChecker(centerCardsChoice, currentOneBotDecision, currentTwoBotDecision);
                if (winner == 1) {
                    OneBotVictoryPoints += centerCardsChoice;
                    printLine("Game", "OneBot gained " + centerCardsChoice + " points.");
                } else {
                    TwoBotVictoryPoints += centerCardsChoice;
                    printLine("Game", "TwoBot gained " + centerCardsChoice + " points.");
                }

                //overview after the round (or whole game, if winner checker defines a winner
                printLine("Game", "OneBot points: " + OneBotVictoryPoints + " | TwoBot points: " + TwoBotVictoryPoints);

                // write result to csv
                writeToCSV("empty_id",game, currentRound, centerCardsChoice, currentOneBotDecision, currentTwoBotDecision);


                //mechanics
                //printLine("Mechanic", "Procced? (enter or 'stop')");
                //end handling of each round
                currentRound += 1;
                //running = false;
                //running = ! input1.nextLine().equals("stop");
            }
            // end of each game

            writeToCSV("end_round", 0, 0, 0, OneBotVictoryPoints, TwoBotVictoryPoints);
            if (OneBotVictoryPoints > TwoBotVictoryPoints) {
                printLine("Game", "OneBot won.");
            } else {
                printLine("Game", "TwoBot won.");
            }
            System.out.println(game);

            // game is terminated
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

    public static void populateBotCards(ArrayList<Integer> cards){
        for (int i = 1; i <= 15; i++){
            cards.add(i);
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
            //printLine("Mechanic", "First case");
            return 1;
        } else if (revealedCard > 0 && SecondBot > FirstBot) {
            //printLine("Mechanic", "Second case");
            return 2;
        } else if (revealedCard < 0 && FirstBot > SecondBot) {
            //printLine("Mechanic", "Third case");
            return 2;
        } else {
            //printLine("Mechanic", "Fourth case");
            return 1;
        }

    }

    public static void writeToCSV(String id, int game, int currentRound, int centerCardsChoice, int currentOneBotDecision, int currentTwoBotDecision) throws IOException {

        printLine("Mechanic", "Writing results to csv");
        FileWriter writer = new FileWriter("database.csv", true);
        writer.append(String.valueOf(id));
        writer.append(',');
        writer.append(String.valueOf(game));
        writer.append(',');
        writer.append(String.valueOf(currentRound));
        writer.append(',');
        writer.append(String.valueOf(centerCardsChoice));
        writer.append(',');
        writer.append(String.valueOf(currentOneBotDecision));
        writer.append(',');
        writer.append(String.valueOf(currentTwoBotDecision));
        writer.append(',');
        writer.append('\n');

        writer.flush();
        writer.close();

    }

}