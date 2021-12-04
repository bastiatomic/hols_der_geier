import java.util.ArrayList;

/*init program to start main framework
*/
public class StartGeier {
    static int pointsPlayer1;
    static int pointsPlayer2;
    static int gameIndex;

    public static void main(String[] args) {

        ArrayList<HolsDerGeierSpieler> botList = new ArrayList<>();
        botList.add(new RosaHase());
        botList.add(new OneVulture());
        botList.add(new OneVulture2());
        botList.add(new RandomBot()); // all against this bot
        botList.add(new Sascha());
        botList.add( new FlosBot());
        botList.add(new ThreeMonkeys());
        botList.add(new ChristopherTabea());
        botList.add(new SvGeier());


        for (int n = 1; n < botList.size(); n++){

            for (gameIndex = 0; gameIndex < 100; gameIndex++) {
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
