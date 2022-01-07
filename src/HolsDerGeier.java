import java.util.ArrayList;

public class HolsDerGeier {

    /* Hier stehen die Geier- und Maeusekarten */
    private ArrayList<Integer> nochZuVergebendeGeierKarten=new ArrayList<Integer>();

    /* Hier stehen die vom Computer gespielten Karten */
    private ArrayList<ArrayList<Integer>> gespielteKarten=new ArrayList<ArrayList<Integer>>();

    /* Punktestaende */
    private int punkte;
    private int[] punktstaende=new int[2];
    private static boolean allowDebug = true;

    /* Das ist die Referenz Ihr Objekt */
    private HolsDerGeierSpieler[] spieler;

    /**
     * Hier definieren Sie den Konstruktor fuer Objekte Ihrer Klasse (falls Sie einen eigenen brauchen) HolsDerGeier
     */
    public HolsDerGeier()  {
        gespielteKarten.add(new ArrayList<Integer>());
        gespielteKarten.add(new ArrayList<Integer>());
    }



    /**
     * Neu laden der Karten
     */
    private void ladeSpiel() {
        //  Geier- und Maeusekarten
        nochZuVergebendeGeierKarten.clear();
        for (int i=-5;i<=10;i++)
            if (i!=0) {
                nochZuVergebendeGeierKarten.add(i);
            }
    }


    /**
     *  Spiele zufaellig die naechste Geier- bzw. Maeusekarte
     */
    private int spieleNaechsteKarte() {
        int nochNichtVergeben=nochZuVergebendeGeierKarten.size();
        int index=(int) (Math.random()*nochNichtVergeben);
        int ret=nochZuVergebendeGeierKarten.remove(index);
        return ret;
    }

    /**
     * Hier kann nach dem letzten Zug gefragt werden. Aber diese Methode ist so eigentlich nciht wirklich gelungen.
     */
    public int letzterZug(int nummer) {
        if (gespielteKarten.get(nummer).size()>0)
            return gespielteKarten.get(nummer).get(gespielteKarten.get(nummer).size()-1);
        else
            return -99;
    }

    /**
     * Alles auf Null
     */
    private void reset() {
        punkte=0;
        for (int i=0;i<gespielteKarten.size();i++)
            gespielteKarten.get(i).clear();
        ladeSpiel();
        for (int i=0;i<punktstaende.length;i++)
            punktstaende[i]=0;
        for (int i=0;i<spieler.length;i++)
            spieler[i].reset();

    }

    public void neueSpieler(HolsDerGeierSpieler spieler1,HolsDerGeierSpieler spieler2){
        spieler = new HolsDerGeierSpieler[2];
        spieler[0]=spieler1;
        spieler[1]=spieler2;
        spieler1.register(this,1);
        spieler2.register(this,0);
    }

    /**
     * Starte ein neues Spiel
     */
    public void naechstesSpiel() {
        if (spieler==null)
            printLine("Noch keine Sieler angemeldet!");
        else {
            printLine("===============");
            printLine("= NEUES SPIEL, ES STEHT 0:0 =");
            printLine("===============");
            reset();
        }
    }


    /**
     * Der naechste Spielzug wird ausgefuehrt.
     * Dazu wird:
     *  - Neue Geier- oder Maeusekarte ermittelt
     *  - Zufaellig eine Karte vom Computer gespielt
     *  - Die Spieler werden gefragt nach den Karten gefragt
     *  - Ausgewertet und der Punktestand gefuehrt
     */
    public void naechsterZug() throws Exception {
        if (!nochZuVergebendeGeierKarten.isEmpty()) {

            // naechste Geier- Maeusekarte
            int naechsteKarte=spieleNaechsteKarte();
            punkte+=naechsteKarte;

            int[] zuege=new int[2];

            // die Z�ge der beiden Spieler
            for (int i=0;i<spieler.length;i++) {
                zuege[i]=spieler[i].gibKarte(naechsteKarte);

                // Sicher ist sicher: Haben Sie diese Karten schon einmal gespielt?
                // Wenn ja: Jetzt ist aber Schluss
                // Wenn nein: Ich merke mit die Karte
                if (gespielteKarten.get(i).contains(zuege[i]) )
                    throw new Exception("GESCHUMMELT: Diese Karte wurde bereits gespielt "+i+" "+zuege[i]);


                if ((zuege[i]<1)||(zuege[i]>15))
                    throw new Exception("GESCHUMMELT: Diese Karte gibt es gar nicht");
            }

            // Alle Z�ge fertig, dann eintragen (erst hier wg. Methode naechsterZug
            for (int i=0;i<spieler.length;i++)
                gespielteKarten.get(i).add(zuege[i]);

            // So sieht der aktuelle Zug aus
            printLine("Ausgespielte Karte: "+naechsteKarte);
            printLine("Zug erster Spieler: "+zuege[0]);
            printLine("Zug zweiter Spieler: "+zuege[1]);


            // Wer kriegt die Punkte?

            // L�sung: Es muss zwischen Maeuse- (nachesteKarte>0) und Geierkarten ((nachesteKarte<0) unterschieden werden.
            if (zuege[0]!=zuege[1]) {
                if (punkte>0)
                    if (zuege[0]>zuege[1])
                        punktstaende[0]=punktstaende[0]+punkte;
                    else
                        punktstaende[1]=punktstaende[1]+punkte;
                else
                if (zuege[0]<zuege[1])
                    punktstaende[0]=punktstaende[0]+punkte;
                else
                    punktstaende[1]=punktstaende[1]+punkte;
                punkte=0;
            } else
                printLine("Unentschieden - Punkte wandern in die naechste Runde");
            printLine("Spielstand: "+punktstaende[0]+" : "+punktstaende[1]);
        } else
            printLine("Spiel ist zu Ende. Sie muessen zuerst die Methode neues Siel aufrufen");

    }

    /**
     * Hier kann ein vollstaendiges Spiel durchgefuehrt werden!
     */
    public void ganzesSpiel() throws Exception {
        if (nochZuVergebendeGeierKarten.isEmpty())
            naechstesSpiel();

        while (!nochZuVergebendeGeierKarten.isEmpty()) {
            naechsterZug();
        }
        printLine("End call");
        StartGeier.addWinningPoints(punktstaende[0], punktstaende[1]);
    }

    private static void printLine(String s){
        //enhanced string output
        if(allowDebug){
            System.out.println("\u001B[32m" + "[HDG] " + s  + "\u001B[0m");}
    }

}
