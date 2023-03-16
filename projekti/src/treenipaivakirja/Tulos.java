package treenipaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author antti ja eeli
 * @version Mar 9, 2023
 *
 */
public class Tulos {
    private int            tunnusNro        = 0;
    //Lisätty hiljattain. onko tarvetta?
    private int            paivaNro;
    private static int     seuraavaNro      = 1;
    private String paiva = "1.1.2000";
    //Näihin viitenumero kyseisen tuloksen liikkeeseen ja sarjaan
    private int liike;
    private int sarja;
    
    /**
     * Toistaiseksi ei tarvetta 
     */
    public Tulos() {
        //
    }
    
    /**
     * Kun tehdään tulos niin määritetään samalla mille päivälle tulos on
     * TODO: Tähän lisätään ehkä myös sarja ja toisto/toistot?
     * @param paiva mikä paiva kyseessä
     */
    public Tulos(int paiva) {
        this.paivaNro = paiva;
        //this.toistoNro = toisto;
        //this.sarjaNro = sarja;
    }
    
    /**
     * @return Tunnus numero
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    /**
     * @return Paiva numero
     */
    public int getPaivaNro() {
        return this.paivaNro;
    }

    /**
     * Tulostaa tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", this.tunnusNro)+ " " + this.paiva + " " + this.liike + "x" + this.sarja);
    }
    
    /**
     * @param os -
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    


    /**
     * Antaa tuloslle seuraavan rekisterinumeron.
     * @return tulosn uusi tunnusNro
     * @example
     * <pre name="test">
     *   tulos tulos1 = new tulos();
     *   tulos1.getTunnusNro() === 0;
     *   tulos1.rekisteroi();
     *   tulos tulos2 = new tulos();
     *   tulos2.rekisteroi();
     *   int n1 = tulos1.getTunnusNro();
     *   int n2 = tulos2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    
  //TODO: Tämän voi luultavasti siirtää muualle
    /**
     * palauttaa random luvun min ja max välistä. (min ja max) ei ole välillä
     * @param min minimi
     * @param max maksimi
     * @return luku
     */
    public int ranL(int min, int max) {
        if (min == max) return min;
        int luku;
        luku = (int) (Math.random() * (max - min) + min);
        return luku;
    }
    

    /**
     * Vastaa esimerkkitulostuksen
     * @param nro päivän numero
     */
    public void vastaaTulos(int nro) {
        paiva = "20.10.2015";
        sarja = ranL(1,6);
        liike = ranL(5,15);     
        paivaNro = nro;
    }
    
    /**
     * testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tulos tulos = new Tulos();
        Tulos tulos2 = new Tulos();
        tulos.rekisteroi();
        tulos2.rekisteroi();
        
        tulos.tulosta(System.out);
        tulos2.tulosta(System.out);
        
        System.out.println("=============");
        
        tulos.vastaaTulos(1); //tulos.taytatulosTiedoilla();
        tulos.tulosta(System.out);
        
        tulos2.vastaaTulos(0); //tulos.taytatulosTiedoilla();
        tulos2.tulosta(System.out);
        }

}

