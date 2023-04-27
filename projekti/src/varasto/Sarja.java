package varasto;
import java.io.*;


/**
 * @author Antti ja eeli
 * @version Mar 9, 2023
 *
 */
public class Sarja {
    
    private int            tunnusNro        = 0;
    private int sarjat;
    private int toistot;
    private double paino;
    private static int     seuraavaNro      = 1;
    
    /**
     * Alustaa sarjan tiedot tyhjäksi
     */
    public Sarja() {
        //
    }
    

    

    /**
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", this.tunnusNro) + " " + this.toistot + "x" + this.paino);
    }
    
    
    /**
     * @param os Tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa sarjalle seuraavan rekisterinumeron.
     * @return sarjan uusi tunnusNro
     * @example
     * <pre name="test">
     *   Sarja sarja1 = new Sarja();
     *   sarja1.getTunnusNro() === 0;
     *   sarja1.rekisteroi();
     *   Sarja sarja2 = new Sarja();
     *   sarja2.rekisteroi();
     *   int n1 = sarja1.getTunnusNro();
     *   int n2 = sarja2.getTunnusNro();
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
     * 
     */
    public void vastaaSarja() {
        paino = ranL(25,100);
        toistot = ranL(2, 12);
    }
   
    
    /**
     * testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Sarja sarja = new Sarja();
        Sarja sarja2 = new Sarja();
        sarja.rekisteroi();
        sarja2.rekisteroi();
        
        sarja.tulosta(System.out);
        sarja2.tulosta(System.out);
        
        sarja.vastaaSarja(); //sarja.taytasarjaTiedoilla();
        sarja.tulosta(System.out);
        
        sarja2.vastaaSarja(); //sarja.taytasarjaTiedoilla();
        sarja2.tulosta(System.out);


        }

}
