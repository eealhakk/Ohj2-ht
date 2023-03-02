/**
 * 
 */
package treenipaivakirja;
import java.io.*;


/**
 * @author Antti
 * @version Mar 2, 2023
 *
 */
public class Treeni {
    
    private int         tunnusNro       = 0;
    private String      treeninTyyppi   = "";
    private String         paivamaara      = "";
    
    private static int  seuraavaNro     = 1;
    
    /**
     * Alustaa treenin tiedot tyhjäksi
     */
    public Treeni() {
        //
    }
    

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
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", this.tunnusNro) + " " + this.treeninTyyppi + " " + this.paivamaara);
    }
    
    
    /**
     * @param os Tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa treenille seuraavan rekisterinumeron.
     * @return treenin uusi tunnusNro
     * @example
     * <pre name="test">
     *   Treeni treeni1 = new Treeni();
     *   treeni1.getTunnusNro() === 0;
     *   treeni1.rekisteroi();
     *   treeni treeni2 = new treeni();
     *   treeni2.rekisteroi();
     *   int n1 = treeni1.getTunnusNro();
     *   int n2 = treeni2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */

    public int rekisteroi() {
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    
    /**
     * 
     */
    public void vastaaTreeni() {
        treeninTyyppi = "Kuntosali";
        paivamaara = "2.3.2023";
    }
   
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Treeni treeni = new Treeni();
        Treeni treeni2 = new Treeni();
        treeni.rekisteroi();
        treeni2.rekisteroi();
        
        treeni.tulosta(System.out);
        treeni2.tulosta(System.out);
        
        treeni.vastaaTreeni(); //treeni.taytaTreeniTiedoilla();
        treeni.tulosta(System.out);
        
        treeni2.vastaaTreeni(); //treeni.taytaTreeniTiedoilla();
        treeni2.tulosta(System.out);


        }

}
