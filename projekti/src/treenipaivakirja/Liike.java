package treenipaivakirja;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Eeli
 * @version 1 Mar 2023
 *
 */
public class Liike {
        private int                 tunnusNro;
        private String              liikkeenNimi;
        
        private static int seuraavaNro    = 1;
    
    
        /**
         * Muodostaja
         
        public Liikkee() {//==
            // TODO Auto-generated constructor stub
        }
        */
        
        
    public Liike() {
        //
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
     * Apumetodi, jolla saadaan täytettyä testiarvot Liikkeelle.
     */
    public void vastaaEsimerkkiLiiketyyppi() {
        ArrayList<String> x = new ArrayList<String>(Arrays.asList("Mave","Penkki","Ylätalja"));
        liikkeenNimi = x.get(ranL(1,3));
    }
    
    
    /**
     * @param liike päivä
     * @return päivä merkkijonona
     */
    public String toString(Liike liike){
        return liike.toString();
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
        out.println(String.format("%03d", tunnusNro, 3));
        out.println("Liikkeen nimi: " + liikkeenNimi + "numero:  " + tunnusNro);
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
        Liike eka = new Liike();
        eka.rekisteroi();
        
        eka.tulosta(System.out);
    }

    /**
     * @return Tunnus numero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
}
