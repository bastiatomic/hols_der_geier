import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ResetDatabase {

    public static void main(String[] args) throws IOException {

        Scanner input1 = new Scanner(System.in);
        System.out.println("Are you sure you want to reset the ENTIRE database? (y/n)");
        String answer = input1.nextLine();

        if (answer.equals("y")) {

            FileWriter writer = new FileWriter("database.csv", false);
            writer.append("id");
            writer.append(',');
            writer.append("game");
            writer.append(',');
            writer.append("round");
            writer.append(',');
            writer.append("revealedCard");
            writer.append(',');
            writer.append("OneBotChoice");
            writer.append(',');
            writer.append("TwoBotChoice");
            writer.append(',');
            writer.append('\n');

            writer.flush();
            writer.close();

            System.out.println("[LOG] Database cleared and populated with default headers.");
        }
    }

}
