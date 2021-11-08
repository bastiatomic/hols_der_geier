import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Integer> centerCards = new ArrayList<>();
        Scanner input1 = new Scanner(System.in);
        boolean running = true;
        Human OneBot = new Human();
        Human TwoBot = new Human();

        // fill hashSets
        fillCenterCards(centerCards);


        //game loop
        while (centerCards.size() != 0 && running) {

            // centerCards
            printLine("Log", "CenterCards: " + centerCards, "\u001B[33m");
            int index = new Random().nextInt(centerCards.size());
            int centerCardsChoice = centerCards.get(index);
            centerCards.remove(index);
            printLine("Game", "Revealed number: " + centerCardsChoice, "\u001B[32m");

            //bot decisions based on their behavior patterns defined in their classes


            // winner checker


            //overview after the round (or whole game, if winner checker defines a winner


            //mechanics
            printLine("Mechanic", "Procced? (true, false)", "\u001B[31m");
            running = input1.nextBoolean();
        }

    }
    public static void fillUserCards(ArrayList<Integer> array){
        for (int i = 0; i<= 15; i++){
            array.add(i);
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
    public static void printLine(String prefix, String text, String color){
        System.out.println(color + "[" + prefix + "] " + text + "\u001B[0m");
    }
}