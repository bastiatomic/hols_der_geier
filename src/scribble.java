import java.util.Scanner;

public class scribble {

    public static void main(String[] args) {
        Scanner input1 = new Scanner(System.in);
        int revealedCard = input1.nextInt();
        int FirstBot = input1.nextInt();
        int SecondBot = input1.nextInt();

            if (FirstBot == SecondBot) {
                System.out.println("same");
            } else if ((revealedCard > 0 && FirstBot > SecondBot) || (revealedCard < 0 && SecondBot > FirstBot)) {
                System.out.println("FirstBot wins");
            }  else {
                System.out.println("Error");
            }

        }

    }

