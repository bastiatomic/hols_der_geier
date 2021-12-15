
import java.util.ArrayList;
import java.util.Collections;

public class DenisPavlov extends HolsDerGeierSpieler {

    private final ArrayList<Integer> notPlayedCards = new ArrayList<>();
    private final ArrayList<Integer> enemyNotPlayedCards = new ArrayList<>();
    private final ArrayList<Integer> pointCardsNotPlayed = new ArrayList<>();

    public DenisPavlov() {
        reset();
    }

    public void reset() {
        enemyNotPlayedCards.clear();
        notPlayedCards.clear();
        for (int i = 1; i <= 15; i++) {
            enemyNotPlayedCards.add(i);
            notPlayedCards.add(i);
        }
        pointCardsNotPlayed.clear();
        for (int i = -5; i <= 10; i++)
            if (i != 0) {
                pointCardsNotPlayed.add(i);
            }
    }

    public int gibKarte(int nextCard) {
        pointCardsNotPlayed.removeIf(name -> name.equals(nextCard));
        int lastMove = letzterZug();
        if (lastMove == -99) {
            return myTurn(nextCard);
        }
        enemyNotPlayedCards.removeIf(name -> name.equals(lastMove));
        return myTurn(nextCard);
    }

    private int myTurn(int nextCard) {
        int cardsCount = notPlayedCards.size();
        if (cardsCount == 1) {
            return notPlayedCards.get(0);
        }

        switch (nextCard) {
            case -1:
            case -2:
            case -3:
            case -4:
            case -5:
            case 1:
            case 2:
                return removeCard(Collections.min(notPlayedCards));
            case 9:
            case 10:
                if (existCard(nextCard + 5) && notExistEnemyCard(15) && notExistEnemyCard(14)) {
                    return removeCard(nextCard + 5);
                } else return removeCard(Collections.min(notPlayedCards));
            case 6:
            case 7:
            case 8:
                return removeCard(Collections.max(notPlayedCards));
            case 5:
            case 4:
                if (existCard(nextCard * 3) && Collections.max(pointCardsNotPlayed).equals(nextCard)) {
                    return removeCard(nextCard * 3);
                } else if (existCard(nextCard * 3 - 1) && Collections.max(pointCardsNotPlayed).equals(nextCard)) {
                    return removeCard(nextCard * 3 - 1);
                } else if (existCard(nextCard * 3 - 2)) {
                    return removeCard(nextCard * 3 - 2);
                } else if (existCard(nextCard * 3 - 3)) {
                    return removeCard(nextCard * 3 - 3);
                } else return removeCard(Collections.min(notPlayedCards));
            case 3:
                if (existCard(nextCard * 3)) {
                    return removeCard(nextCard * 3);
                } else if (existCard(nextCard * 3 - 1)) {
                    return removeCard(nextCard * 3 - 1);
                } else if (existCard(nextCard * 3 - 2)) {
                    return removeCard(nextCard * 3 - 2);
                } else if (existCard(nextCard * 3 - 3)) {
                    return removeCard(nextCard * 3 - 3);
                } else return removeCard(Collections.min(notPlayedCards));
            default:
                return chooseRandomCard();
        }
    }

    private boolean existCard(int number) {
        return notPlayedCards.contains(number);
    }

    private boolean notExistEnemyCard(int number) {
        return !enemyNotPlayedCards.contains(number);
    }

    private int chooseRandomCard() {
        int cardsCount = notPlayedCards.size();
        if (cardsCount == 1) {
            return notPlayedCards.get(0);
        }

        int index = (int) (Math.random() * cardsCount);
        int value = notPlayedCards.get(index);
        return removeCard(value);
    }

    private int removeCard(int number) {
        notPlayedCards.removeIf(name -> name.equals(number));
        return number;
    }
}