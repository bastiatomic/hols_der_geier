//this bot is buggy as hell

import java.util.ArrayList;
import java.util.Objects;


public class ChristopherTabea extends HolsDerGeierSpieler{
    // instance variables - replace the example below with your own
    private ArrayList<Integer> kartenAufDerHand= new ArrayList<>();
    private ArrayList<Integer> vomGegnerNochNichtGelegt= new ArrayList<>();
    private ArrayList<Integer> schonGespieltGegner= new ArrayList<>();
    private ArrayList<Integer> schonGespieltWir= new ArrayList<>();
    private ArrayList<Integer> schonGespieltGeier= new ArrayList<>();
    private int runde;
    private int p=0;
    private int pZumRechnen=0;


    public void reset(){
        p=0;
        pZumRechnen=0;
        runde=0;
        vomGegnerNochNichtGelegt.clear();
        for (int i=1;i<16;i++)
            vomGegnerNochNichtGelegt.add(i);
        kartenAufDerHand.clear();
        for (int i=1;i<16;i++)
            kartenAufDerHand.add(i);
        schonGespieltWir.clear();
        schonGespieltGegner.clear();
        schonGespieltGeier.clear();
    }

    public int gibKarte (int naechsteKarte)
    {
        schonGespieltGeier.add(naechsteKarte);

        if (runde>0){
            if(vomGegnerNochNichtGelegt.contains(letzterZug()))
            {
                vomGegnerNochNichtGelegt.remove((Integer) letzterZug());
                schonGespieltGegner.add(letzterZug());
            }
        }
        if (runde>0)
            punkteZaehlen();

        //int ret=kartenAufDerHand.remove(kartenAufDerHand.indexOf(auswahlKarte(naechsteKarte)));
        int ret = auswahlKarte(naechsteKarte);
        kartenAufDerHand.removeIf(name -> name.equals(ret));
        schonGespieltWir.add(ret);
        runde++;
        return ret;
    }

    private void punkteZaehlen(){
        if (Objects.equals(schonGespieltWir.get(schonGespieltWir.size() - 1), schonGespieltGegner.get(schonGespieltGegner.size() - 1))) {//unentschieden
            p+=schonGespieltGeier.get(schonGespieltGeier.size()-2);
            pZumRechnen=p+schonGespieltGeier.get(schonGespieltGeier.size()-1);
        }
        else {p=0;
            pZumRechnen=0;
        }
    }

    private int auswahlKarte(int naechsteKarte)
    {
        if (p!=0)//unentschieden
        {
            return taktik(pZumRechnen);
        }
        return taktik(naechsteKarte);
    }

    private int betrag(int geierKarte)
    {
        return Math.abs(geierKarte);
    }


    private int taktik(int geierKarte)
    {
        int karte;
        if (betrag(geierKarte)<6)
        {
            karte=betrag(geierKarte)*2+zufall();
        }
        else if(geierKarte<10)
        {
            karte=geierKarte+5+zufall();
        }
        else
        {
            karte=15;
        }
        return findeNaechsteKarte(karte);
    }

    private int findeNaechsteKarte(int wunschKarte)
    {
        int i=0;
        int ergebnis=0;
        while(wunschKarte - i > 0 || wunschKarte + i < 16)
        {
            if (kartenAufDerHand.contains(wunschKarte+i))
            {
                ergebnis=wunschKarte+i;
                break;
            }
            if (kartenAufDerHand.contains(wunschKarte-i))
            {
                ergebnis=wunschKarte-i;
                break;
            }
            i++;
        }
        return ergebnis;
    }

    private int zufall()
    {
        if (0!=(int)(Math.random()*100)%3)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}





