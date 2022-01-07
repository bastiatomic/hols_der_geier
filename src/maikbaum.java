import java.util.ArrayList;

public class maikbaum extends HolsDerGeierSpieler {

    int gameCounter = 0;
    int botCounter1 = 0;
    int botCounter2 = 0;
    int Zug1 = 0;
    int Zug2 = 0;
    int Zug3 = 0;
    int Zug4 = 0;
    int unetnschieden = 0;
    int zuege= 0;


    private ArrayList<Integer> nochNichtGespielt = new ArrayList<Integer>();


    @Override
    public void reset() {

    }

    @Override
    public int gibKarte(int naechsteKarte) {
        //zählt Runden
        gameCounter++;


       /* if (zuege[0]==zuege[1]){
            unetnschieden++;
            //System.out.println("Unentschieden:"+unetnschieden);
        }*/

        //Runden-1/15%2 ergibt die gerade und ungerade Rundenanzahl
        //ein großes Statement,dass abfragt, ob eine gerade oder ungerade gespielt wird
        if (((gameCounter - 1) / 15) % 2 == 0) {

            //System.out.println("Ungerade");
            //zählt welcher Bot spielt
            botCounter1++;
            int bot1 = ((botCounter1 - 1) / 15) % 2;

            //System.out.println(botCounter1);
            //System.out.println(bot1);


            if (bot1 == 0) {

                //Nur zum Test, um zu sehen welcher Bot spielt
                Zug1++;
                //System.out.println("Zug1:"+Zug1);
                //System.out.println(gameCounter);
                switch (naechsteKarte) {
                    case -5:
                        return 1;
                    case -4:
                        return 2;
                    case -3:
                        return 3;
                    case -2:
                        return 4;
                    case -1:
                        return 5;
                    case 1:
                        return 6;
                    case 2:
                        return 7;
                    case 3:
                        return 8;
                    case 4:
                        return 9;
                    case 5:
                        return 10;
                    case 6:
                        return 11;
                    case 7:
                        return 12;
                    case 8:
                        return 13;
                    case 9:
                        return 14;
                    case 10:
                        return 15;
                    default:
                        return 15;
                }


            } else Zug2++;
            //System.out.println("Zug2:"+Zug2);
            //System.out.println(gameCounter);
            if (bot1 != 0) {
                switch (naechsteKarte) {
                    case -5:
                        return 5;
                    case -4:
                        return 4;
                    case -3:
                        return 3;
                    case -2:
                        return 2;
                    case -1:
                        return 1;
                    case 1:
                        return 7;
                    case 2:
                        return 8;
                    case 3:
                        return 10;
                    case 4:
                        return 9;
                    case 5:
                        return 13;
                    case 6:
                        return 12;
                    case 7:
                        return 11;
                    case 8:
                        return 14;
                    case 9:
                        return 6;
                    case 10:
                        return 15;
                    default:
                        return 15;
                }
            }
        } else if (((gameCounter-1) / 15) % 2 != 0)  {

            //System.out.println("Gerade");

            botCounter2++;
            int bot2 = ((botCounter2 - 1) / 15) % 2;

            //System.out.println(botCounter2);
            //System.out.println(bot2);

            if (bot2 == 0) {

                Zug3++;
                //System.out.println("Zug3:"+Zug3);
                //System.out.println(gameCounter);
                switch (naechsteKarte) {
                    case -5:
                        return 6;
                    case -4:
                        return 3;
                    case -3:
                        return 1;
                    case -2:
                        return 4;
                    case -1:
                        return 2;
                    case 1:
                        return 5;
                    case 2:
                        return 9;
                    case 3:
                        return 7;
                    case 4:
                        return 12;
                    case 5:
                        return 10;
                    case 6:
                        return 14;
                    case 7:
                        return 8;
                    case 8:
                        return 13;
                    case 9:
                        return 11;
                    case 10:
                        return 15;
                    default:
                        return 15;
                }


            } else if (bot2 != 0) {

                Zug4++;
                //System.out.println("Zug4:"+Zug4);
                //System.out.println(gameCounter);
                switch (naechsteKarte) {
                    case -5:
                        return 3;
                    case -4:
                        return 1;
                    case -3:
                        return 2;
                    case -2:
                        return 5;
                    case -1:
                        return 4;
                    case 1:
                        return 7;
                    case 2:
                        return 9;
                    case 3:
                        return 6;
                    case 4:
                        return 10;
                    case 5:
                        return 12;
                    case 6:
                        return 13;
                    case 7:
                        return 11;
                    case 8:
                        return 14;
                    case 9:
                        return 15;
                    case 10:
                        return 8;
                    default:
                        return 15;

                }
            }
        }



        return 100000;
    }

}