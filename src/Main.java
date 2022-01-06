//edge case: in the last round, bot players have the same card value left (tie)
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    static double time = System.nanoTime();

    private static final int calculateDepth = 15; //default = 15
    private static final int gamesAmount = 10; //default 1; if more, then allowLogging is recommended
    private static boolean allowEnding = true;
    private static int letzterZug;
    private static int OneBotWins, TwoBotWins;
    private static final ArrayList<Integer> OneBotCards = new ArrayList<>();
    private static final ArrayList<Integer> TwoBotCards = new ArrayList<>();
    //---------------------------------------------------
    private static boolean allowPrintLine = true; // increased performance when off
    private static final boolean allowLogging = true;
    //---------------------------------------------------

    public static void main(String[] args) throws IOException {

        //ArrayList<HolsDerGeierSpieler> botList = new ArrayList<>();
        //botList.add(new Cerberus());
       // for (HolsDerGeierSpieler opponentBot : botList){}

        // how many rounds? ability to run n unique games
        for (int game = 1; game <= gamesAmount; game++) {

            //15-round-game | prepare variables
            int currentRound = 1;
            int oneBotVictoryPoints = 0;
            int twoBotVictoryPoints = 0;
            int centerCardsChoice = 0;
            letzterZug = -99;
            ArrayList<Integer> centerCards = new ArrayList<>();
            fillCenterCards(centerCards);
            fillUserCards(OneBotCards);
            fillUserCards(TwoBotCards);

            //RandomBot TwoBot = new RandomBot();
            //OneBot TwoBot = new OneBot();
            //SvGeier OneBot = new SvGeier(); -> buggy as hell
            //CerberusOld OneBot = new CerberusOld();
            //ThreeMonkeys TwoBot = new ThreeMonkeys();
            //OneBot OneBot = new OneBot();
            RosaHase TwoBot = new RosaHase();
            RosaHase OneBot = new RosaHase();
            //ChristopherTabea TwoBot = new ChristopherTabea(); -> buggy as hell
            //JaboBrandBot TwoBot = new JaboBrandBot();
            //Sascha TwoBot = new Sascha();
            //IntelligentererGeier TwoBot = new IntelligentererGeier(); // -> 96% win for me

            //15-round-game | loop
            while (centerCards.size() != 0 && currentRound <= calculateDepth) {

                //centerCards
                int revealedNumber = getCenterCard(centerCards);
                centerCardsChoice += revealedNumber;
                printLine("Game", "Round " + (15 - centerCards.size()) + " | Revealed number: " + revealedNumber + " | Played for: " + centerCardsChoice);

                //bot decisions based on their behavior patterns defined in their classes
                int currentOneBotDecision = OneBot.gibKarte(centerCardsChoice);
                //letzterZug = currentOneBotDecision;
                if(OneBotCards.contains(currentOneBotDecision)){
                    OneBotCards.removeIf(name -> name.equals(currentOneBotDecision));
                } else {
                    printLine("Mechanic", "OneBot, This card has been played or is invalid at all! " + currentOneBotDecision);
                    System.exit(-1);
                }
                int currentTwoBotDecision = TwoBot.gibKarte(centerCardsChoice);
                letzterZug = currentTwoBotDecision;
                if(TwoBotCards.contains(currentTwoBotDecision)){
                    TwoBotCards.removeIf(name -> name.equals(currentTwoBotDecision));
                } else {
                    printLine("Mechanic", "TwoBot, This card has been played or is invalid at all! " + currentTwoBotDecision);
                    System.exit(-1);
                }
                printLine("Bot", "OneBot: " + currentOneBotDecision + " | TwoBot: " + currentTwoBotDecision);

                //winner checker
                int winner = winnerChecker(centerCardsChoice, currentOneBotDecision, currentTwoBotDecision);
                switch(winner) {
                    case 1 -> {
                        oneBotVictoryPoints += centerCardsChoice;
                        printLine("Game", "OneBot gained " + centerCardsChoice + " points.");
                        allowEnding = true;
                    }
                    case 2 -> {
                        twoBotVictoryPoints += centerCardsChoice;
                        printLine("Game", "TwoBot gained " + centerCardsChoice + " points.");
                        allowEnding = true;
                    }
                    case 0 -> {
                        printLine("Mechanic", "Bots bots have chosen the same value. Revealing an additional centerCard");
                        allowEnding = false;
                    }
                }

                //overview after the round (or whole game, if winner checker defines a winner)
                printLine("Game", "OneBot points: " + oneBotVictoryPoints + " | TwoBot points: " + twoBotVictoryPoints);

                //write result to csv
                if(allowLogging) {
                    writeToCSV((game * calculateDepth + currentRound), game, currentRound, centerCardsChoice, currentOneBotDecision, currentTwoBotDecision);
                }
                //end handling of each round
                if (allowEnding) {
                    centerCardsChoice = 0;
                    currentRound += 1;
                }
                System.out.println(game);
            }

            //15-round-game | end of round handling
            if (allowLogging) {
                writeToCSV((game * calculateDepth + currentRound), game, currentRound, 0, oneBotVictoryPoints, twoBotVictoryPoints);
            }
            if (oneBotVictoryPoints > twoBotVictoryPoints) {
                printLine("Game", "OneBot won.");
                OneBotWins += 1;
            } else {
                printLine("Game", "TwoBot won.");
                TwoBotWins += 1;
            }

            //System.out.println("(" + game + ")");
            //15-round-game is terminated
        }
        allowPrintLine = true;
        printLine("Game", "OneBot: " + OneBotWins + " | TwoBot: " + TwoBotWins);
        printLine("Mechanic", "[Finished in "+ (System.nanoTime() - time)/1_000_000_000 + " sec]");;

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
        if (allowPrintLine) {
            String color;
            switch (prefix) {
                case "Game" -> color = "\u001B[32m";
                case "Log" -> color = "\u001B[33m";
                case "Mechanic" -> color = "\u001B[31m";
                case "Bot" -> color = "\u001B[34m";
                case "OneBot" -> color = "\u001B[36m";
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

    public static int getCenterCard(ArrayList<Integer> centerCards){
        int randomIndex = new Random().nextInt(centerCards.size());
        int newReveal = centerCards.get(randomIndex); // get a card based on the given index
        centerCards.remove(randomIndex); // remove the card based on the given index
        return newReveal;
    }

    public static int letzteKarte(){
        return letzterZug;
    }

    public static int letzterZug(){
        return letzterZug;
    }

    public static void fillUserCards(ArrayList<Integer> userCards){
        userCards.clear();
        for (int i = 1; i <= 15 ; i++) {
            userCards.add(i);
        }

    }

}