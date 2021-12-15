import java.util.ArrayList;

public class StartGeier {
    static int pointsPlayer1;
    static int pointsPlayer2;
    static int gameIndex;
    static boolean allowDatabaseAccess = false;
    static int gamesAmount = 1_000;
    static float timePast;
    static int randomCounter;

    public static void main(String[] args) throws Exception {

        timePast = System.nanoTime();

        ArrayList<HolsDerGeierSpieler> botList = new ArrayList<>();


        botList.add(new DenisPavlov());
        //botList.add(new RandomBot());

        //botList.add(new DenisPavlov());
        botList.add(new VultureAgent());
        botList.add(new OneVulture());
        //botList.add(new VultureAgent());
        //botList.add(new RosaHase());
        //botList.add(new PastVultureAgent());


        /*//botList.add(new OneVulture());
        botList.add(new Sascha());
        botList.add(new FlosBot());
        //botList.add(new ThreeMonkeys());
        botList.add(new ChristopherTabea());
        botList.add(new SvGeier());
        botList.add(new AltenhofSpiessBot());*/

        for (int n = 1; n < botList.size(); n++){

            for (gameIndex = 0; gameIndex < gamesAmount; gameIndex++) {
                HolsDerGeier hdg = new HolsDerGeier();

                hdg.neueSpieler(botList.get(0),botList.get(n));

                hdg.ganzesSpiel();

            }
            System.out.println(botList.get(0) + " " + pointsPlayer1 + " | " + pointsPlayer2 +" "+ botList.get(n));
            pointsPlayer2 = 0;
            pointsPlayer1 = 0;

        }
        System.out.println("[Finished in " + (System.nanoTime() - timePast)/1_000_000_000 + "sec.]");


    }
    public static void addWinningPoint(int one, int two){
        if (one == 0 && two == 0){
            System.out.println("both players played the same card everytime");
        } else if(one == 1){
            pointsPlayer1 +=1;
        } else {
            pointsPlayer2 +=1;
        }
    }
}
