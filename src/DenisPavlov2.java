import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class DenisPavlov2 extends HolsDerGeierSpieler {
    private final List<Integer> myNotPlayedCards = new ArrayList<>();
    private final List<Integer> enemyNotPlayedCards = new ArrayList<>();
    private final List<Integer> notPlayedPointCards = new ArrayList<>();

    public DenisPavlov2() {
        reset();
    }

    public void reset() {
        enemyNotPlayedCards.clear();
        myNotPlayedCards.clear();

        for (int i = 1; i <= 15; i++) {
            enemyNotPlayedCards.add(i);
            myNotPlayedCards.add(i);
        }

        notPlayedPointCards.clear();
        for (int i = -5; i <= 10; i++) {
            if (i == 0) {
                continue;
            }

            notPlayedPointCards.add(i);
        }
    }

    public int gibKarte(int nextPointCard) {
        notPlayedPointCards.removeIf(card -> card == nextPointCard);

        int lastTurn = letzterZug();
        enemyNotPlayedCards.removeIf(card -> card == lastTurn);

        return myTurn(nextPointCard);
    }

    private int myTurn(int nextPointCard) {
        int cardToRemove = 0;

        int myCardsCount = myNotPlayedCards.size();
        if (myCardsCount == 1) {
            cardToRemove = myNotPlayedCards.get(0);
        } else if (Arrays.asList(-5, -4, -3, -2, -1, 1, 2, 3).contains(nextPointCard)) {
            cardToRemove = removeMinNotPlayedCard();
        } else if (Arrays.asList(4, 5).contains(nextPointCard)) {
            cardToRemove = removeCardFirstSpecialCase(nextPointCard);
        } else if (Arrays.asList(6, 7, 8).contains(nextPointCard)) {
            cardToRemove = removeCard(Collections.max(myNotPlayedCards));
        } else if (Arrays.asList(9, 10).contains(nextPointCard)) {
            cardToRemove = removeCardSecondSpecialCase(nextPointCard);
        }

        return cardToRemove;
    }

    private int removeCardFirstSpecialCase(int nextPointCard) {
        int cardToRemove;

        int maxNotPlayedCard = Collections.max(notPlayedPointCards);
        if (maxNotPlayedCard == nextPointCard) {
            cardToRemove = nextPointCard * 3;
            if (cardIsExist(cardToRemove)) {
                return removeCard(cardToRemove);
            }

            cardToRemove = nextPointCard * 3 - 1;
            if (cardIsExist(cardToRemove)) {
                return removeCard(cardToRemove);
            }
        }

        cardToRemove = nextPointCard * 3 - 2;
        if (cardIsExist(cardToRemove)) {
            return removeCard(cardToRemove);
        }

        cardToRemove = nextPointCard * 3 - 3;
        if (cardIsExist(cardToRemove)) {
            return removeCard(cardToRemove);
        }

        return removeMinNotPlayedCard();
    }

    private int removeCardSecondSpecialCase(int nextPointCard) {
        int cardToRemove = nextPointCard + 5;
        if (cardIsExist(cardToRemove)
                && enemyCardNotExist(14)
                && enemyCardNotExist(15)) {
            return removeCard(cardToRemove);
        }

        return removeMinNotPlayedCard();
    }

    private int removeMinNotPlayedCard() {
        int minNotPlayedCard = Collections.min(myNotPlayedCards);
        return removeCard(minNotPlayedCard);
    }

    private boolean cardIsExist(int number) {
        return myNotPlayedCards.contains(number);
    }

    private boolean enemyCardNotExist(int number) {
        return !enemyNotPlayedCards.contains(number);
    }

    private int removeCard(int cardToRemove) {
        myNotPlayedCards.removeIf(card -> card == cardToRemove);
        return cardToRemove;
    }
}