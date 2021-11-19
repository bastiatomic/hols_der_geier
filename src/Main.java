import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static int centerCardsChoice = 0;
    public static final int calculateDepth = 15; //default = 15
    public static final int gamesAmount = 1;
    public static int OneBotVictoryPoints;
    public static int TwoBotVictoryPoints;
    public static ArrayList<Integer> OneBotUserCards = new ArrayList<>();
    public static ArrayList<Integer> TwoBotUserCards = new ArrayList<>();
    public static boolean running = true;
    public static boolean allowEnding = true;
    //---------------------------------------------------
    public static final boolean testModeActive = false;
    //---------------------------------------------------

    public static void main(String[] args) throws IOException {

        // how many rounds? ability to run n unique gaes
        for (int game = 1; game <= gamesAmount; game++) {

            ArrayList<Integer> centerCards = new ArrayList<>();
            OneBot2 OneBot = new OneBot2();
            RandomBot TwoBot = new RandomBot();
            int currentRound = 1;

            // fill hashSets, clear VictoryPoints
            fillCenterCards(centerCards);
            populateBotCards(OneBotUserCards);
            populateBotCards(TwoBotUserCards);
            OneBotVictoryPoints = 0;
            TwoBotVictoryPoints = 0;

            //game loop
            while (centerCards.size() != 0 && currentRound <= calculateDepth && running) {

                // centerCards
                centerCardsChoice += getCenterCard(centerCards);
                printLine("Mechanic", "sum of centerCards: " + centerCardsChoice);

                //bot decisions based on their behavior patterns defined in their classes
                int currentOneBotDecision = OneBot.decideCard(centerCardsChoice, OneBotUserCards,TwoBotUserCards);
                //printLine("Bot", "OneBot card list: " + OneBot.userCards);

                int currentTwoBotDecision = TwoBot.decideCard(centerCardsChoice, TwoBotUserCards,OneBotUserCards);
                //printLine("Bot", "TwoBot card list: " + TwoBot.userCards);

                printLine("Bot", "OneBot: " + currentOneBotDecision + " | TwoBot: " + currentTwoBotDecision);

                // winner checker
                int winner = winnerChecker(centerCardsChoice, currentOneBotDecision, currentTwoBotDecision);
                switch(winner) {
                    case 1 -> {
                        OneBotVictoryPoints += centerCardsChoice;
                        printLine("Game", "OneBot gained " + centerCardsChoice + " points.");
                        allowEnding = true;
                    }
                    case 2 -> {
                        TwoBotVictoryPoints += centerCardsChoice;
                        printLine("Game", "TwoBot gained " + centerCardsChoice + " points.");
                        allowEnding = true;
                    }
                    case 0 -> {
                        printLine("Mechanic", "Bots bots have chosen the same value. Revealing an additional centerCard");
                        allowEnding = false;
                    }
                }

                //overview after the round (or whole game, if winner checker defines a winner)
                printLine("Game", "OneBot points: " + OneBotVictoryPoints + " | TwoBot points: " + TwoBotVictoryPoints);

                // write result to csv
                writeToCSV((game *calculateDepth+ currentRound),game, currentRound, centerCardsChoice, currentOneBotDecision, currentTwoBotDecision);

                //end handling of each round
                printLine("Log", "current centerCards " + centerCards);
                //running = input1.nextBoolean();
                if (allowEnding) {
                    centerCardsChoice = 0;
                    currentRound += 1;
                }
            }

            // end of each 15-round-game
            writeToCSV((game *calculateDepth+ currentRound), 0, 0, 0, OneBotVictoryPoints, TwoBotVictoryPoints);
            if (OneBotVictoryPoints > TwoBotVictoryPoints) {
                printLine("Game", "OneBot won.");
            } else {
                printLine("Game", "TwoBot won.");
            }
            printLine("Log", String.valueOf(OneBotUserCards));
            printLine("Log", String.valueOf(TwoBotUserCards));
            System.out.println(game);
            TwoBotUserCards.clear();
            OneBotUserCards.clear();

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
        if (!testModeActive) {
            String color;
            switch (prefix) {
                case "Game" -> color = "\u001B[32m";
                case "Log" -> color = "\u001B[33m";
                case "Mechanic" -> color = "\u001B[31m";
                case "Bot" -> color = "\u001B[34m";
                default -> color = "\u001B[30m";

            }
            System.out.println(color + "[" + prefix + "] " + text + "\u001B[0m");
        }
    }

    public static int winnerChecker(int revealedCard, int FirstBot, int SecondBot) {
        if (FirstBot == SecondBot) {
            printLine("Game", "Same card number! Revealing next card!");
            return 0;
        } else if ((revealedCard > 0 && FirstBot > SecondBot) || (revealedCard < 0 && SecondBot > FirstBot)) {
            return 1;
        } else {return 2;}
    }

    public static void writeToCSV(int id, int game, int currentRound, int centerCardsChoice, int currentOneBotDecision, int currentTwoBotDecision) throws IOException {
        if (testModeActive){
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

    public static int getCenterCard(ArrayList<Integer> centerCards){
        int randomIndex = new Random().nextInt(centerCards.size());
        centerCardsChoice = centerCards.get(randomIndex); // get a card based on the given index
        centerCards.remove(randomIndex); // remove the card based on the given index
        //printLine("Log", "CenterCards: " + centerCards);
        printLine("Game", "Round " + (15 - centerCards.size()) + " | Revealed number: " + centerCardsChoice);

        return centerCardsChoice;
    }
}