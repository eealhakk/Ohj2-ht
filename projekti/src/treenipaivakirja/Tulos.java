package treenipaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;

public class Tulos {

    private int      tunnusNro;
    private Liike    liike;//Viite liike olioon
    private Sarja    sarja;//Viite sarja olioon
    
    private static int seuraavaNro    = 1;   

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
     * Apumetodi, jolla saadaan täytettyä testiarvot paivalle.
     */
    public void vastaaEsimerkkiTulos() {
        //liike = Liike.vastaaEsimerkkiLiiketyyppi()
    }
    
    
    /**
     * @return rekisteröi uuden alkion
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    
    /**
     * @param out output
     */
    public void tulosta(PrintStream out) {
        //
    }
    
    
    /**
     * @param os -
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        //
    }
    
    /**
     * @return Tunnus numero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
}
