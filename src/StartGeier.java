/*
ToDo: compare the gamepattern array with "bot behavior.gsheet"
ToDo: iterate over each entry of gamePattern and make a list of the 15 with the centerCard at index [0]
ToDo: write a max() function to determine all possibilites, write printLine, wehn >=0.5
 */

import java.util.ArrayList;

public class StartGeier {
    static int pointsPlayer1;
    static int pointsPlayer2;
    static int gameIndex;
    static int gamesAmount = 1000;

    public static void main(String[] args) {
        //pattern is too vague, u need to adapt to the bot because u
        // can't write a function to "Rule Them All"

        ArrayList<HolsDerGeierSpieler> botList = new ArrayList<>();
        //botList.add(new RosaHase());
        botList.add(new VultureAgent());
        //botList.add(new KillerBot());
        //botList.add(new NoahRuben());
       botList.add(new RandomBot());
       botList.add(new OneVulture());
        botList.add(new Sascha());
        botList.add(new FlosBot());
        botList.add(new ThreeMonkeys());
        botList.add(new ChristopherTabea());
        botList.add(new SvGeier());
        botList.add(new AltenhofSpiessBot());
        //botList.add(new VultureAgent());


        for (int n = 1; n < botList.size(); n++){

            for (gameIndex = 0; gameIndex < gamesAmount; gameIndex++) {
                HolsDerGeier hdg = new HolsDerGeier();

                hdg.neueSpieler(botList.get(0),botList.get(n));

                try{
                    hdg.ganzesSpiel();
                } catch (Exception e){
                    System.out.println("Log: Fehler;");
                    System.out.println(e);
                }

            }
            System.out.println(botList.get(0) + " " + pointsPlayer1 + " | " + pointsPlayer2 +" "+ botList.get(n));
            pointsPlayer2 = 0;
            pointsPlayer1 = 0;

        }


    }
    public void addWinningPoint(int one, int two){
        if (one == 1){
            pointsPlayer1 +=1;
        } else {
            pointsPlayer2 +=1;
        }
    }
}
