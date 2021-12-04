// won't handling edge case: both players reveal same card and the sum of the centerCards is 10+

import java.util.ArrayList;
import java.util.Random; //fuer den Zufallsgeneratr;


/**
 * ThreeMonkeys-Strategie
 * 
 * @author Lemp, Schmidt, Stomberg 
 * @version 20.01.2011
 */
public class ThreeMonkeys extends HolsDerGeierSpieler{
     
	  //Karten die in der Mitte liegen 
      private ArrayList<Integer> nochVerfuegbar= new ArrayList<>();
      
      private ArrayList<Integer> gegnerDeck= new ArrayList<>();
      private ArrayList<Integer> meinDeckRandom= new ArrayList<>();
      private ArrayList<Integer> meinDeckStrategie= new ArrayList<>();

      
      /**
       * Unseren Namen setzen
       */       
      public ThreeMonkeys(){
         reset();
      }
      
      /**
       * Alle verfügbaren Arraylist werden initialisiert und vorbelegt
       */  
      public void reset(){
         nochVerfuegbar.clear();
         gegnerDeck.clear();
         meinDeckRandom.clear();
         meinDeckStrategie.clear();

         for (int i=10;i>-6;i--)
              nochVerfuegbar.add(i);  

         for (int i=6;i>0;i--)
              meinDeckRandom.add(i);
         
         for (int i=15;i>6;i--)
              meinDeckStrategie.add(i);

         for (int i=15;i>0;i--)           
              gegnerDeck.add(i);   
      }

      /**
       * Die eigene Strategie wird hier abgebildet
       * reti = Rückgabewert, für Karte die von uns gelegt wird
       */
      public int gibKarte(int naechsteKarte){
        
         int reti;
         int letzteKarteGegner = letzterZug();
        
         //Prüfung auf Fehlercode
         if (letzteKarteGegner != -99){
            gegnerDeck.remove((Integer) letzteKarteGegner);
        
         // Lösche diese Karte
            // System.out.println("noch verfügbar: " + nochVerfuegbar);
             //System.out.println("nächsteKarte: " + naechsteKarte);
            nochVerfuegbar.removeIf(name -> name.equals(naechsteKarte));
            //nochVerfuegbar.remove(nochVerfuegbar.indexOf(naechsteKarte));
        }
         //Unterscheidung Geier- und Mausekarten
         if (naechsteKarte > 0)
             reti = mausi(naechsteKarte);
         else
             reti = geierei(naechsteKarte);
       
         return reti;
       }
      
      /**
       * Strategie für positive Karten in unserem Deck
       */      
      private int StrategiePlus(int naechsteKarte){
         if ((nochVerfuegbar.indexOf(naechsteKarte)==0)&&(gegnerDeck.get(0)>=meinDeckStrategie.get(0)))
            return meinDeckStrategie.get(meinDeckStrategie.size()-1);  
         
         else if ((nochVerfuegbar.indexOf(naechsteKarte)>nochVerfuegbar.size()/3)&&(gegnerDeck.get(0)<meinDeckStrategie.get(0)))
       	    return meinDeckStrategie.get(0);  
         
         else {Random random = new Random();
            int reti = random.nextInt(meinDeckStrategie.size());
             return meinDeckStrategie.get(reti);}
      }

      /**
       * Strategie für negative Karten in unserem Deck
       */    
      private int StrategieMinus(int naechsteKarte){
         if ((nochVerfuegbar.indexOf(naechsteKarte)==nochVerfuegbar.lastIndexOf(nochVerfuegbar))&&(nochVerfuegbar.indexOf(naechsteKarte)>nochVerfuegbar.size()/2))
        	return meinDeckStrategie.get(0);   //höchste Karte
         else 	{
             if (meinDeckStrategie.size()==1){
                 return meinDeckStrategie.get(0);
             } else {

                 int pick = meinDeckStrategie.size()-1;
                 return meinDeckStrategie.get(pick);}  //niedrigste Karte
             }
      }
      
      /**
       * Auswahl Strategie, wenn Geierkarten in der Mitte liegen
       */          
      private int geierei (int naechsteKarte) {
          
         int reti,zahl;
         Random random = new Random();
          
         if (naechsteKarte < -2) 	
       	    {reti=StrategieMinus(naechsteKarte);
            meinDeckStrategie.remove((Integer) reti);
            return reti;}    
         else{            
            reti = random.nextInt(meinDeckRandom.size());
            zahl=meinDeckRandom.get(reti);meinDeckRandom.remove(reti);
            return zahl;}                       
      }
      
      /**
       * Auswahl Strategie, wenn Mausekarten in der Mitte liegen
       */          
      private int mausi (int naechsteKarte) {
          
         int reti,zahl;
         Random random = new Random();
          
         if (naechsteKarte > 4)
            {reti=StrategiePlus(naechsteKarte);
            meinDeckStrategie.remove((Integer) reti);
            return reti;}
         else{reti = random.nextInt(meinDeckRandom.size());
        	  zahl=meinDeckRandom.get(reti);meinDeckRandom.remove(reti);
              return zahl;}     
       }    

}
