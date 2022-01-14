public class BasicGeier extends HolsDerGeierSpieler {

    public void reset () {
    }

    public int gibKarte(int naechsteKarte) {
        if (naechsteKarte < 0)
            return naechsteKarte + 6;
        return naechsteKarte + 5;

    }


}
