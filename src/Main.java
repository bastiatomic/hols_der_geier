import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {

        HashSet<Integer> firstUserCards = new HashSet<>();
        HashSet<Integer> secondUserCards = new HashSet<>();
        ArrayList<Integer> centerCards = new ArrayList<>();
        int firstUserCollectedCards = 0;
        int secondUserCollectedCards = 0;
        Scanner input1 = new Scanner(System.in);

        // fill hashSets
        fillUserCards(firstUserCards);
        fillUserCards(secondUserCards);
        fillCenterCards(centerCards);

        //game loop
        while (centerCards.size() != 0) {
            // centralCards
            System.out.println("[LOG] CenterCards: " + centerCards);
            int index = new Random().nextInt(centerCards.size());
            int centerCardsChoice = centerCards.get(index);
            centerCards.remove(index);
            System.out.println("centerCardsChoice: " + centerCardsChoice);

            // get user input
            System.out.print("prompt input of firstUser: ");
            int firstUserChoice = input1.nextInt();
            System.out.println("Valid? " + firstUserCards.contains(firstUserChoice));
            firstUserCards.remove(firstUserChoice);

            System.out.print("prompt input of secondUser: ");
            int secondUserChoice = input1.nextInt();
            System.out.println("Valid? " + secondUserCards.contains(secondUserChoice));
            secondUserCards.remove(secondUserChoice);

            // winning condition checker
            if (centerCardsChoice > 0 && firstUserChoice > secondUserChoice) {
                firstUserCollectedCards += centerCardsChoice;
            } else if (centerCardsChoice > 0 && firstUserChoice < secondUserChoice){
                secondUserCollectedCards += centerCardsChoice;
            } else if (centerCardsChoice < 0 && firstUserChoice > secondUserChoice){
                secondUserCollectedCards += centerCardsChoice;
            } else {
                firstUserCollectedCards += centerCardsChoice;
            }
            System.out.println("[LOG] firstUser: " + firstUserCollectedCards + " | secondUser: " + secondUserCollectedCards);
        }
    }
    public static void fillUserCards(HashSet<Integer> array){
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
}