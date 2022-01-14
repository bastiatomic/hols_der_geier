public class BotNouri extends HolsDerGeierSpieler {


    // Hier definiere ich den Konstruktor fuer Objekte von meiner Klasse
    public BotNouri() {
    }

    // Hier erzeuge ich ein neues Objekt meiner Helper-Klasse
    Helper helper = new Helper();

    // Alle Arraylists werden durch diese Methode am Start einer neuen Runde zurückgesetzt
    public void reset() {
        helper.clearAll();
    }

    public int gibKarte(int geierCard) {

        int card;
        helper.setLastCardEnemy(letzterZug());
        helper.getMyLastCard();
        int differencePoints = helper.difference();
        helper.getRemainCards().remove(helper.getRemainCards().indexOf(geierCard));
        if (letzterZug() != -99)
            helper.getEnemyHand().remove(helper.getEnemyHand().indexOf(letzterZug()));

        if (geierCard > 0 && helper.getLastCardEnemy() == -99) {
            card = geierCard + 6;
            helper.test(card);
            helper.setMyLastCard(card);
            helper.setScoreOfLastRound(geierCard);
        } else if (geierCard < 0 && helper.getLastCardEnemy() == -99) {
            card = (geierCard * (-2)) + 3;
            helper.setMyLastCard(card);
            helper.setScoreOfLastRound(geierCard);
        } else {
            if (geierCard > 0) {
                if (differencePoints <= -10) {
                    card = geierCard + 8;
                    card = helper.test(card);
                } else if (differencePoints > -10 && differencePoints <= -5) {
                    card = geierCard + 7;
                    card = helper.test(card);
                } else if (differencePoints > -5 && differencePoints <= 5) {
                    card = geierCard + 6;
                    card = helper.test(card);
                } else if (differencePoints > 5 && differencePoints <= 10) {
                    card = geierCard + 5;
                    card = helper.test(card);
                } else{
                    card = geierCard + 3;
                    card = helper.test(card);
                }
            } else {
                if(differencePoints < 0) {
                    card = geierCard * -2;
                    card = helper.test(card);
                }else {
                    card = (geierCard * -2  -2);
                    card = helper.test(card);
                }
            }
            helper.setScoreOfLastRound(geierCard);
            helper.setMyLastCard(card);
        }

        helper.setLastGeierCard(geierCard);
        card = helper.test(card);
        helper.getMyHand().remove(helper.getMyHand().indexOf(card));
        return card;
    }
}
