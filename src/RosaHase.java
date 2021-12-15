import java.util.ArrayList;
import java.util.Random;


// i want the +10!!!
public class RosaHase extends HolsDerGeierSpieler
{
    private final ArrayList<Integer> opponentCards=new ArrayList<>();
    private final ArrayList<Integer> userCards=new ArrayList<>();

    public RosaHase()
    {
        reset();
    }
    
    public void reset() {
        opponentCards.clear();
        userCards.clear();
        for (int i=15;i>0;i--) {
            opponentCards.add(i);
            userCards.add(i);
        }
    }

    public int gibKarte(int centerCard) {
 
        if (letzterZug() !=-99) {
            opponentCards.removeIf(name -> name.equals(letzterZug()));
        }
        //System.out.println(opponentCards);

        int currentChoice = 99;

        //Da es für uns keien Rolle spielt, ob eine 1 oder eine -1 liegt werden
        //hier alle negativen Werte durch *(-1) zu positiven umgewandelt
        if (centerCard < 0) {
            centerCard *= -1;
        }

        //Hier wird die currentChoice für alle Karten von -5 bis 5 bestimmt
        if (centerCard <= 5){

            if (userCards.contains(centerCard*2+1)) {
                currentChoice = centerCard*2+1;
                //das else dient dazu den Fall abzufangen, falls der erste schon gespielt wurde
            } else {
                currentChoice = centerCard*2+2;
            }

        }else{

            // wenn ich noch 15 habe, aber der Gegner nicht, snacke ich mir die 10 (Outbid opponent)
            if (centerCard == 10 && !opponentCards.contains(15) && userCards.contains(15) ){
                currentChoice = 15; //System.out.println("bin im 10 fall");
            }else if (centerCard ==10 &&userCards.contains(2) ){
                currentChoice = 2;
            }

            //selbe Fall wie bei der 10 nur mit 14 und 1
            if (centerCard == 9 && !opponentCards.contains(14) && userCards.contains(14) ){
                currentChoice = 14; //System.out.println("bin im 9 fall");
            }else if (centerCard ==9 && userCards.contains(1) ){
                currentChoice = 1;
            }

            //Normal wird bei der 8 die 15 gespielt, nur wenn der oben genannte
            //Fall eingetreten ist das bei der 10 die 15 gespielt wird muss hier
            //die 2 gespielt werden
            if (centerCard == 8 && userCards.contains(15)){
                currentChoice = 15; //System.out.println("bin im 8 fall");
            }else if(centerCard == 8 && userCards.contains(2)){
                currentChoice = 2;
            }

            //Selbe Fall wie bei der 8 nur für 14 und 1
            if (centerCard == 7 && userCards.contains(14)){
                currentChoice = 14;//System.out.println("bin im 7 fall");
            }else if(centerCard == 7 && userCards.contains(1)){
                currentChoice = 1;
            }
            if (centerCard == 6) {
                currentChoice = 13;
            }
        }

        // sth went wrong, handling it here
        if(currentChoice > 15){
            int index = new Random().nextInt(userCards.size());
            //System.out.println("Chossing random");
            currentChoice = userCards.get(index);
        }

        int finalAusgabe = currentChoice;
        userCards.removeIf(name -> name.equals(finalAusgabe));

        //System.out.println("[Rosa Hase ] " +"cC: "+ centerCard2+ " | choice:  "+ currentChoice);
        //System.out.println(currentRound + " | " +centerCard+  " | " + currentChoice +" [RosaHase] ");
        return currentChoice;
    }
}

