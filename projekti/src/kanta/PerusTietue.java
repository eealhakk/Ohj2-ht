package kanta;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tietue joka osaa mm. itse huolehtia tunnus_nro:staan.
 * Oikean tietueen tekemiseksi tämä on perittävä ja lisättävä mm.
 * Kentta-taulukko. 0's kenttä on aina id-numero.
 *
 * @example
 * <pre name="testJAVA">
 * public static class Harrastus extends PohjaTietue {
 *     public Harrastus() { 
 *       super(new Kentta[]{
 *                  new IdKentta("id","harrastusID_testPerustietue"),
 *                  new IntKentta("jäsenId"),
 *                  new JonoKentta("ala"),
 *                  new IntKentta("aloitusvuosi"),
 *                  new IntKentta("h/vko")
 *            },2); 
 *     }
 *     @Override public Harrastus clone() throws CloneNotSupportedException {return (Harrastus)super.clone(); }
 *     public int getJasenNro() { return ((IntKentta)getKentta(1)).getValue(); }
 * }    
 * </pre>
 */
public abstract class PerusTietue implements Cloneable,Tietue  {
   
    /**
     * @param i seuraava id-numero jota tästä lähiten käytetään
     */
     @SuppressWarnings("unused") 
     protected void setSeuraavaNro(int i) {
         // perusmallissa ei tee mitään
     }
    
    
    /**
     * Asetetaan kentät.  Käytetään lähinnä kloonaamisessa
     * @param kentat uudet kentät tietueelle
     */
    abstract protected void setKentat(Kentta[] kentat);

    
    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.getKenttia() === 5;
     * </pre>
     */
    @Override
    public int getKenttia() {
        return getKentat().length;
    }


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.ekaKentta() === 2;
     * </pre>
     */
    @Override
    public int ekaKentta() {
        return 1;
    }


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
    @Override
    public Kentta getKentta(int k) {
        return getKentat()[k];
    }

    
    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.getKysymys(2) === "ala";
     * </pre>
     */
    @Override
    public String getKysymys(int k) {
        try {
            return getKentta(k).getKysymys();
        } catch (Exception ex) {
            return "Ääliö";
        }
    }


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
    @Override
    public void setKysymys(int k, String kysymys) {
        try {
            getKentta(k).setKysymys(kysymys);
        } catch (Exception ex) {
            // ei käsitellä
        }
    }

    
    /**
     * @return kaikkien näytettävien kysymysten otsikot merkkijonotaulukkona
     * @example
     * <pre name="test">
     * #import java.util.Arrays;
     *   Harrastus har = new Harrastus();
     *   Arrays.toString(har.getOtsikot()) =R= "\\[ala, aloitusvuosi, h/vko.*";
     * </pre>
     */
    @Override
    public String[] getOtsikot() {
        int n = getKenttia() - ekaKentta();
        String[] otsikot = new String[n];
        for (int i=0,k=ekaKentta(); i<n; i++,k++) 
            otsikot[i] = getKysymys(k);
        return otsikot;        
    }
    
    
    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   har.anna(0) === "2";   
     *   har.anna(1) === "10";   
     *   har.anna(2) === "Kalastus";   
     *   har.anna(3) === "1949";   
     *   har.anna(4) === "22";   
     * </pre>
     */
    @Override
    public String anna(int k) {
        try {
            return getKentta(k).toString();
        } catch (Exception ex) {
            return "";
        }
    }


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
    @Override
    public int annaInt(int k) {
        Kentta kentta = getKentta(k);
        if ( !(kentta instanceof IntKentta) ) return -1;
        return ((IntKentta)kentta).getValue();
    }
    
    
    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
     *   har.aseta(3,"1940")  === null;
     *   har.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
     *   har.aseta(4,"20")    === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String s) {
        try {
            String virhe = getKentta(k).aseta(s.trim());
            if ( virhe == null && k == 0 ) setTunnusNro(getTunnusNro());
            if ( virhe == null ) return virhe;
            return getKysymys(k) +": " + virhe;
        } catch (Exception ex) {
            return "Virhe: " + ex.getMessage();
        }
    }


    /**
     * Tehdään identtinen klooni tietueesta
     * @return Object kloonattu tietue
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Object kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     *   kopio instanceof Harrastus === true;
     * </pre>
     */
    @Override
    public Tietue clone() throws CloneNotSupportedException { 
        PerusTietue uusi;
        uusi = (PerusTietue)super.clone();
        uusi.setKentat(getKentat().clone());

        for (int k = 0; k < getKenttia(); k++)
            uusi.getKentat()[k] = getKentta(k).clone();
        return uusi;
    }
    

    /**
     * Tulostetaan tietueen tiedot
     * @param out tietovirta johon tulostetaan
     */
    @Override
    public void tulosta(PrintWriter out) {
        String erotin = "";
        for (int k=ekaKentta(); k<getKenttia(); k++) {
            out.print(erotin + anna(k));
            erotin = " ";
        }
        out.println();
    }


    /**
     * Tulostetaan tietueen tiedot
     * @param os tietovirta johon tulostetaan
     */
    @Override
    public void tulosta(OutputStream os) {
        tulosta(new PrintWriter(os, true)); // ilman autoflushia ei mitään
        // tulostu!
    }
    

    /**
     * Tulostetaan tietueen tiedot
     * @param out tietovirta johon tulostetaan
     */
    @Override
    public void tulosta(PrintStream out) {
        tulosta(new PrintWriter(out, true)); // ilman autoflushia ei mitään
                                             // tulostu!
    }

    
    /**
     * Antaa tietueelle seuraavan rekisterinumeron
     * jollei numeroa jo ole
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
     *   pitsi1.rekisteroi();
     *   pitsi1.getTunnusNro() === n1;
     * </pre>
     */
    @Override
    public int rekisteroi() {
        return setTunnusNro(getSeuraavaNro());
    }


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
    @Override
    public int getTunnusNro() {
        return ((IntKentta)(getKentta(0))).getValue();
    }


    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private int setTunnusNro(int nr) {
        IntKentta k = ((IntKentta)(getKentta(0)));
        k.setValue(nr);
        if (nr >= getSeuraavaNro()) setSeuraavaNro(nr + 1);
        return k.getValue();
    }
    

    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.toString()    =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     * </pre>
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
     }


    /**
     * Selvittää tietueen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta tietueen tiedot otetaan
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.getJasenNro() === 10;
     *   harrastus.toString()     =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     *   
     *   harrastus.rekisteroi(); // ei vaikuta koska tunnusnumero on jo
     *   int n = harrastus.getTunnusNro();
     *   n === 2;
     *   harrastus.parse(""+(n+20));
     *   harrastus.toString()     =R= "" + (n+20) + "\\|10\\|\\|1949\\|22.*";
     *   harrastus = new Harrastus(); // tällä ei ole numeroa
     *   harrastus.rekisteroi(); // tämä saa edellistä yhtä suuremman
     *   harrastus.getTunnusNro() === n+20+1;
     * </pre>
     */
    
    @Override
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }

    
    /**
     * Antaa k:n kentän sisällön avain-merkkijonona
     * jonka perusteella voi lajitella
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     *
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
    @Override
    public String getAvain(int k) {
        try {
            return getKentta(k).getAvain();
        } catch (Exception ex) {
            return "";
        }
    }


    /**
     * Tutkii onko jäsenen tiedot samat kuin parametrina tuodun jäsenen tiedot
     * @param obj tietue johon verrataan
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
     *   har1.equals(har2) === true;
     *   har2.equals(har1) === true;
     *   har1.equals(har3) === false;
     *   har3.equals(har2) === false;
     *   har3.equals(null) === false;
     * </pre>
     */
    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof Tietue)  ) return false;
        Tietue tietue = (Tietue)obj;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(tietue.anna(k)) ) return false;
        return true;
    }

    
    @Override
    public int hashCode() {
        int hc = 0;
        for (int k = 0; k < getKenttia(); k++)
            hc += anna(k).hashCode();
        return hc;
    }

    
    /**
     * @param k mistä kentästä
     * @return vaakasuuntainen sijainti kentälle
     * @example
     * <pre name="test">
     * #import javax.swing.SwingConstants;
     *  Harrastus har = new Harrastus();
     *  har.getSijainti(3) === SwingConstants.RIGHT; 
     * </pre>
     */
    @Override
    public int getSijainti(int k) {
        Kentta kentta = getKentta(k);
        if ( kentta == null ) return 0;
        return kentta.getSijainti();
    }
    
}
