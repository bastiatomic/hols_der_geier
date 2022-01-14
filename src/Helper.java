import java.util.ArrayList;

public class Helper {
    // Attribute: Arraylisten von den übrigen Geier-Karten, den übrigen Karten des Gegners und die übrigen Karten meiner Hand
    private ArrayList<Integer> remainCards =new ArrayList<Integer>();
    private ArrayList<Integer> enemyHand =new ArrayList<Integer>();
    private ArrayList<Integer> myHand=new ArrayList<Integer>();

    // Variablen zum Berechnen des Punktestandes
    private int lastGeierCard;
    private int lastCardEnemy;
    private int myLastCard;
    private int enemyScore;
    private int myScore;
    private int scoreOfLastRound;
    public boolean even = false;
    public int evenScore = 0;
    public int differenceForTest = 0;

    // getter
    public ArrayList<Integer> getRemainCards() {
        return remainCards;
    }
    public ArrayList<Integer> getEnemyHand() {
        return enemyHand;
    }
    public ArrayList<Integer> getMyHand() {
        return myHand;
    }
    public int getLastGeierCard() {
        return lastGeierCard;
    }
    public int getLastCardEnemy() {
        return lastCardEnemy;
    }
    public int getMyLastCard() {
        return myLastCard;
    }
    public int getEnemyScore() {
        return enemyScore;
    }
    public int getMyScore() {
        return myScore;
    }
    public int getScoreOfLastRound() {
        return scoreOfLastRound;
    }

    // setter
    public void setRemainCards(ArrayList<Integer> remainCards) {
        this.remainCards = remainCards;
    }
    public void setEnemyHand(ArrayList<Integer> enemyHand) {
        this.enemyHand = enemyHand;
    }
    public void setMyHand(ArrayList<Integer> myHand) {
        this.myHand = myHand;
    }
    public void setLastGeierCard(int lastGeierCard) {
        this.lastGeierCard = lastGeierCard;
    }
    public void setLastCardEnemy(int lastCardEnemy) {
        this.lastCardEnemy = lastCardEnemy;
    }
    public void setMyLastCard(int myLastCard) {
        this.myLastCard = myLastCard;
    }
    public void setScoreOfLastRound(int scoreOfRound) {
        this.scoreOfLastRound += scoreOfRound;
    }
    // Aufaddieren des Punktestandes von mir und meinem Gegner
    public void setEnemyScore(int lastGeierCard) {
        this.enemyScore += lastGeierCard;
    }
    public void setMyScore(int lastGeierCard) {
        this.myScore += lastGeierCard;
    }

    // diese Methode setzt die ArrayListen auf die notwendige Größe mit den notwendigen Karten
    public void clearAll(){
        getRemainCards().clear();
        for (int i=-5;i<=10;i++) {
            if(i == 0)
                continue;
            getRemainCards().add(i);
        }

        getEnemyHand().clear();
        for (int i=1;i<=15;i++)
            getEnemyHand().add(i);

        getMyHand().clear();
        for (int i=1;i<=15;i++)
            getMyHand().add(i);

        setMyScore(0);
        setEnemyScore(0);
    }
    // berechnet die differenz zwischen meinem und dem gegner punktestand
    public int difference(){

        if (lastCardEnemy == myLastCard){
            even = true;
            evenScore = getScoreOfLastRound();
        } else{
            if (lastGeierCard > 0){
                if (myLastCard > lastCardEnemy){
                    setMyScore(scoreOfLastRound);
                    setScoreOfLastRound(-getScoreOfLastRound());
                } else{
                    setEnemyScore(scoreOfLastRound);
                    setScoreOfLastRound(-getScoreOfLastRound());
                }
            } else{
                if (myLastCard > lastCardEnemy){
                    setEnemyScore(getScoreOfLastRound());
                    setScoreOfLastRound(-getScoreOfLastRound());
                } else{
                    setMyScore(getScoreOfLastRound());
                    setScoreOfLastRound(-getScoreOfLastRound());
                }
            }
            if (even){
                even = false;
                setScoreOfLastRound(-evenScore-getScoreOfLastRound());
            }
        }
        differenceForTest = myScore - enemyScore;
        return differenceForTest;
    }
    public int test(int card){
        boolean found = false;
        boolean outOfBouncePlus = false, outOfBounceMinus = false;
        int i = card;
        if (i == 0)
            i += 1;
        if (card > getMyHand().get(getMyHand().size() - 1)) {
            card = getMyHand().get(getMyHand().size() - 1);
            //System.out.println("card before X: " + card);
            return card;
        }
        if (getMyHand().indexOf(card) == -1 && differenceForTest >= 0){
            while(true){
                ////System.out.println("while1" + i);
                --i;
                if(i <= 0){
                    outOfBounceMinus = true;
                    break;
                }
                if (getMyHand().indexOf(i) != -1){
                    found = true;
                    break;
                }
            }
        } else if (!getMyHand().contains(card) && differenceForTest < 0){
            while(true){
                ////System.out.println("while2");
                ++i;
                if (i == 16) {
                    //System.out.println("Found error! Do sth else");
                    outOfBouncePlus = true;
                    break;
                }
                if (getMyHand().indexOf(i) != -1){
                    found = true;
                    break;
                }
            }
        }
        if (outOfBounceMinus){
            i = card;
            while(true){
                ////System.out.println("while1.1");
                ++i;
                if (getMyHand().indexOf(i) != -1){
                    found = true;
                    break;
                }
            }
        } else if (outOfBouncePlus){
            i = card;
            while(true) {
                ////System.out.println("while2.1");
                --i;
                if (getMyHand().indexOf(i) != -1) {
                    found = true;
                    break;
                }
            }
        }

        //System.out.println("difference: " + differenceForTest);

        if (found)
            return getMyHand().get(getMyHand().indexOf(i));
        else {
            return card;
        }
    }
}

