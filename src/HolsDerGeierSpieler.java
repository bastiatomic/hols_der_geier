import java.io.IOException;
public abstract class HolsDerGeierSpieler {

    private int nummer;
    private HolsDerGeier hdg;


    public int getNummer() {
        return nummer;
    }
    public HolsDerGeier getHdg()  {
        return hdg;
    }

    public int letzterZug() {
        return hdg.letzterZug(nummer);
    }

    public void register(HolsDerGeier hdg, int nummer) {
        this.hdg=hdg;
        this.nummer=nummer;
    }

    public abstract void reset();
    public abstract int gibKarte(int naechsteKarte) throws IOException;
}
