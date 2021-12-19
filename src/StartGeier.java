import java.util.ArrayList;

public class StartGeier {
    static int gameIndex;
    static int gamesAmount = 1;
    static float timePast;
    static int vicPoints1 = 0, vicPoints2 = 0;

    public static void main(String[] args) throws Exception {

        timePast = System.nanoTime();

        ArrayList<HolsDerGeierSpieler> botList = new ArrayList<>();

        //botList.add(new RandomBot());

        botList.add(new VultureAgent());
        //botList.add(new DenisPavlov());
        //botList.add(new DenisPavlov2());
        //botList.add(new OneVulture());
        /*botList.add(new VultureAgent());*/
        //botList.add(new RosaHase());
        /*botList.add(new OneVulture());
        botList.add(new Sascha());
        botList.add(new FlosBot());
        botList.add(new ThreeMonkeys());
        botList.add(new ChristopherTabea());*/
        botList.add(new AltenhofSpiessBot());

        for (int n = 1; n < botList.size(); n++){

            for (gameIndex = 0; gameIndex < gamesAmount; gameIndex++) {
                HolsDerGeier hdg = new HolsDerGeier();

                hdg.neueSpieler(botList.get(0),botList.get(n));

                hdg.ganzesSpiel();

            }

        }
        System.out.println(vicPoints1 +" " + vicPoints2);
        System.out.println("[Finished in " + (System.nanoTime() - timePast)/1_000_000_000 + "sec.]");


    }

    public void addWinningPoints(int points1, int points2){
        if(points1 > points2){
            vicPoints1 ++;
        } else {
            vicPoints2++;
        }

    }

}
