package kanta;
import javax.swing.SwingConstants;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kenttä kokonaislukujen tallentamiseksi
 */
public class IntKentta extends PerusKentta  {
    private int arvo;

    /**
     * Alustetaan kenttä kysymyksellä.
     * @param kysymys joka näytetään kenttää kysyttäessä.
     */
    public IntKentta(String kysymys) { super(kysymys); }


    /**
     * @return kentän arvo kokonaislukuna
     */
    public int getValue() { return arvo; }


    /**
     * Asetetaan kentän arvo kokonaislukuna
     * @param value asetettava kokonaislukuarvo
     */
    public void setValue(int value) { arvo = value; }


    /** 
     * Kentän arvo merkkijonona.
     * @return kenttä merkkijonona
     * @example
     * <pre name="test">
     *  IntKentta kentta = new IntKentta("määrä");
     *  kentta.aseta("12") === null;
     *  kentta.toString() === "12";
     * </pre>
     */
    @Override
    public String toString() { return ""+arvo; }



    /**
     * Asetetaan kentän arvo merkkijonosta.  Jos jono 
     * kunnollinen, palautetaan null.  Jos jono ei
     * kunnollinen int-syöte, palautetaan virheilmoitus ja
     * kentän alkuperäinen arvo jää voimaan.
     * @param jono kentään asetettava arvo mekrkijonona
     * @return null jos hyvä arvo, muuten virhe.  
     * @see kanta.PerusKentta#aseta(java.lang.String)
     * 
     * @example
     * <pre name="test">
     * IntKentta kentta = new IntKentta("määrä");
     * kentta.aseta("12") === null; kentta.getValue() === 12;
     * kentta.aseta("k") === "Ei kokonaisluku (k)"; kentta.getValue() === 12; 
     * </pre>
     */
    @Override
    public String aseta(String jono) {
        StringBuffer sb = new StringBuffer(jono);
        try {
            setValue(Mjonot.erotaEx(sb,' ',0)); 
            return null;
        }
        catch (NumberFormatException ex) {
            return "Ei kokonaisluku (" + jono + ")"; 
        }
    }

    /**
     * Palauttaa kentän tiedot vertailtavana merkkijonona
     * @return vertailtava merkkijono kentästä
     * @example
     * <pre name="test">
     * IntKentta kentta = new IntKentta("määrä");
     * kentta.aseta("12");  kentta.getAvain() === "        12";
     * kentta.aseta("1");   kentta.getAvain() === "         1";
     * kentta.aseta("999"); kentta.getAvain() === "       999";
     *   
     * </pre>
     */
    @Override
    public String getAvain() { 
        return Mjonot.fmt(arvo, 10); 
    }

    /**
     * @return syväkopio oliosta
     * @example
     * <pre name="test">
     *   #THROWS CloneNotSupportedException
     *   IntKentta kentta = new IntKentta("määrä");
     *   kentta.aseta("12");
     *   IntKentta klooni = kentta.clone();
     *   kentta.getValue() === klooni.getValue();
     *   kentta.aseta("13");
     *   kentta.getValue() === 13;
     *   klooni.getValue() === 12;
     * </pre>
     */
    @Override
    public IntKentta clone() throws CloneNotSupportedException {
        return (IntKentta)super.clone();
    }


    /**
     * @return vertailee kahta oliota keskenään
     */
    @Override
    public int compareTo(Kentta k) {
        if ( !(k instanceof IntKentta) ) return super.compareTo(k);
        return getValue() - ((IntKentta)k).getValue();
    }


    /**
     * @return vaakasuuntainen sijainti kentälle
     */
    @Override
    public int getSijainti() {
        return SwingConstants.RIGHT;        
    }
}
