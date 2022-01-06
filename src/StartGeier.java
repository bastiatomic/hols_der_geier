import java.util.ArrayList;

public class StartGeier {
    static int gamesAmount = 1000;
    static float timePast;
    static int vicPoints1 = 0, vicPoints2 = 0;

    public static void main(String[] args) throws Exception {

        timePast = System.nanoTime();

        ArrayList<HolsDerGeierSpieler> botList = new ArrayList<>();

        //botList.add(new VultureAgent());
        botList.add(new ExploringVulture());
        //botList.add(new RandomBot());
        //botList.add(new DenisPavlov());
        botList.add(new DenisPavlov2());
        //botList.add(new OneVulture());
        /*botList.add(new RosaHase());
        botList.add(new OneVulture());
        botList.add(new Sascha());
        botList.add(new FlosBot());
        botList.add(new ThreeMonkeys());
        botList.add(new ChristopherTabea());
        botList.add(new AltenhofSpiessBot());*/

        for (int n = 1; n < botList.size(); n++){

            for (int i = 0; i < gamesAmount; i++) {
                HolsDerGeier hdg = new HolsDerGeier();
                hdg.neueSpieler(botList.get(0),botList.get(n));


                hdg.ganzesSpiel();

            }

        }
        System.out.println("player1: " + vicPoints1 +" | " + vicPoints2 +" :player2");
        System.out.println("[Finished in " + (System.nanoTime() - timePast)/1_000_000_000 + " sec.]");


    }

    public static void addWinningPoints(int points1, int points2){
        if(points1 > points2){
            vicPoints1 ++;
        } else if (points2 > points1) {
            vicPoints2++;
        }

    }

}
