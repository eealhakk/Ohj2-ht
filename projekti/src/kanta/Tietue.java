package kanta;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Rajapinta tietueelle johon voidaan taulukon avulla rakentaa 
 * "attribuutit".
 * @author Eeli ja Antti
 * @example
 */
public interface Tietue {

    
    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *   #import treenipaivakirja.Tulos;
     *   Tulos tul = new Tulos();
     *   tul.getKenttia() === 5;
     * </pre>
     */
    public abstract int getKenttia();


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Tulos tul = new Tulos();
     *   tul.ekaKentta() === 2;
     * </pre>
     */
    public abstract int ekaKentta();

    /**
     * @param k Mikä kenttä halutaan
     * @return k.s kenttä
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   IntKentta kentta = (IntKentta)har.getKentta(3);   
     *   kentta.getValue() === 1949;   
     * </pre>
     */
    public abstract Kentta getKentta(int k);

    
    /**
     * @return palautetaan viite koko kenttätaulukkoon.
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Kentta[] kentat  = har.getKentat();   
     *   ((IntKentta)(kentat[4])).getValue() === 22;   
     * </pre>
     */
    public abstract Kentta[] getKentat();

    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Tulos tul = new Tulos();
     *   tul.getKysymys(2) === "ala";
     * </pre>
     */
    public abstract String getKysymys(int k);
    
    /**
     * @param k minkä kentän kysymys halutaan asettaa
     * @param kysymys kentän kysymys
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.getKysymys(2) === "ala";
     *   har.setKysymys(2,"aihe");
     *   har.getKysymys(2) === "aihe";
     * </pre>
     */
    public void setKysymys(int k, String kysymys);
    
    /**
     * @return kaikkien näytettävien kysymysten otsikot merkkijonotaulukkona
     * @example
     * <pre name="test">
     * #import java.util.Arrays;
     *   Harrastus har = new Harrastus();
     *   Arrays.toString(har.getOtsikot()) =R= "\\[ala, aloitusvuosi, h/vko.*";
     * </pre>
     */
    public abstract String[] getOtsikot();


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Tulos tul = new Tulos();
     *   tul.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   tul.anna(0) === "2";   
     *   tul.anna(1) === "10";   
     *   tul.anna(2) === "Kalastus";   
     *   tul.anna(3) === "1949";   
     *   tul.anna(4) === "22";   
     * </pre>
     */
    public abstract String anna(int k);
    
    /**
     * Kentän sisältö kokonaislukuna.  Jos ei ole IntKentta,
     * niin -1.
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö kokonaislukuna.  
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   har.annaInt(0) === 2;   
     *   har.annaInt(1) === 10;   
     *   har.annaInt(2) === -1;   
     *   har.annaInt(3) === 1949;   
     *   har.annaInt(4) === 22;   
     * </pre>
     */
    public abstract int annaInt(int k);

    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Tulos tul = new Tulos();
     *   tul.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
     *   tul.aseta(3,"1940")  === null;
     *   tul.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
     *   tul.aseta(4,"20")    === null;
     * </pre>
     */
    public abstract String aseta(int k, String s);


    /**
     * Tehdään identtinen klooni tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Tulos tul = new Tulos();
     *   tul.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Object kopio = tul.clone();
     *   kopio.toString() === tul.toString();
     *   tul.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(tul.toString()) === false;
     *   kopio instanceof Tulos === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;
    
    /**
     * Tulostetaan tietueen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public abstract void tulosta(PrintWriter out);


    /**
     * Tulostetaan tietueen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public abstract void tulosta(OutputStream os);


    /**
     * Tulostetaan tietueen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public abstract void tulosta(PrintStream out);


    /**
     * Antaa tietueelle seuraavan rekisterinumeron.
     * @return tietueen uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Harrastus pitsi1 = new Harrastus();
     *   pitsi1.getTunnusNro() === 0;
     *   pitsi1.rekisteroi();
     *   int n1 = pitsi1.getTunnusNro();
     *   Harrastus pitsi2 = new Harrastus();
     *   pitsi2.rekisteroi() === n1 + 1;
     *   int n2 = pitsi2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public abstract int rekisteroi();


    /**
     * Palautetaan tietueen oma id
     * @return tietueen id
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.getTunnusNro() === 2;
     * </pre>
     */
    public abstract int getTunnusNro();


    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tulos Tulos = new Tulos();
     *   Tulos.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Tulos.toString()    =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     * </pre>
     */
    @Override
    public abstract String toString();
    
    //=========================================
    //Poista ylimääräset
    //=========================================
    

    /**
     * Selvitää tietueen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta tietueen tiedot otetaan
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.getJasenNro() === 10;
     *   harrastus.toString()     =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     *   
     *   harrastus.rekisteroi();
     *   int n = harrastus.getTunnusNro();
     *   harrastus.parse(""+(n+20));
     *   harrastus.rekisteroi();
     *   harrastus.getTunnusNro() === n+20+1;
     *   harrastus.toString()     =R= "" + (n+20+1) + "\\|10\\|\\|1949\\|22.*";
     * </pre>
     */
    public abstract void parse(String rivi);

    
    /**
     * @return seuraava vapaa id-numero
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.rekisteroi();
     *   harrastus.getSeuraavaNro() === harrastus.getTunnusNro()+1;
     * </pre>
     */
    public int getSeuraavaNro();

    
    /**
     * Antaa k:n kentän sisällön avain-merkkijonona
     * jonka perusteella voi lajitella
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   har.getAvain(0) === "         2";
     *   har.getAvain(1) === "        10";
     *   har.getAvain(2) === "KALASTUS";
     *   har.getAvain(20) === "";
     * </pre>
     */
    public abstract String getAvain(int k);

    
    /**
     * Tutkii onko tietueen tiedot samat kuin parametrina tuodun tietueen tiedot
     * @param tietue tietue johon verrataan
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Harrastus har1 = new Harrastus();
     *   har1.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Harrastus har2 = new Harrastus();
     *   har2.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Harrastus har3 = new Harrastus();
     *   har3.parse("   3   |  12  |   Kalastus  | 1949 | 22 t ");
     *   
     *   
     *   har1.equals(har2) === true;
     *   har2.equals(har1) === true;
     *   har1.equals(har3) === false;
     *   har3.equals(har2) === false;
     * </pre>
     */ 
    @Override
    public abstract boolean equals(Object tietue); 

    
    /**
     * @param k mistä kentästä
     * @return vaakasuuntainen sijainti kentälle
     * @example
     * <pre name="test">
     * #import javax.swing.SwingConstants;
     *  IntKentta k1 = new IntKentta("määrä");
     *  k1.getSijainti() === SwingConstants.RIGHT; 
     * </pre>
     */
    int getSijainti(int k);
}
