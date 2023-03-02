package treenipaivakirja;

import java.io.*;

/**
 * @author Eeli
 * @version 1 Mar 2023
 *
 */
public class Paiva {
        private int                 tunnusNro;
        private final Paivamaara    paivamaara       = new Paivamaara();
        private String              treeninTyyppi    = "";
        private String              luontipv         = "";
        private String              muokattuViimeksi = "";
        
        private static int seuraavaNro    = 1;
    
    
        /**
         * Muodostaja
         */
        public Paiva() {//==
            // TODO Auto-generated constructor stub
        }
        
        
    private static class Paivamaara{
        private int pp;
        private int kk;
        private int vv;
        
        public Paivamaara() {
            this.pp = 0;
            this.kk = 0;
            this.vv = 2000;
        }
        
        //Setit
        public void setpp(int pp) {
            this.pp = pp;
        }
        
        public void setkk(int ikk) {
            this.kk = ikk;
        }
        
        public void setvv(int ivv) {
            this.vv = ivv;
        }
        
        public void setPv(int pvm, int kk, int vuosi) {
            this.pp = pvm;
            this.kk = kk;
            this.vv = vuosi;
        }
        //Setit loppuu
        
        @Override
        public String toString(){
            String jono = String.format("%02d",pp) + 
                    "." + String.format("%02d",kk) + 
                    "." + String.format("%04d",vv);
            return jono;
        }
        


    }//==
    
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
     * @param pv päivä
     * @return päivä merkkijonona
     */
    public String toString(Paivamaara pv){
        return pv.toString();
    }
    
    /**
     * @return rekisteröi uuden alkion
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    //Setit
    /**
     * @param ipp asettaa päivän
     */
    public void setpp(int ipp) {
        paivamaara.setpp(ipp);
    }
    
    /**
     * @param ikk asettaa kuukauden
     */
    public void setkk(int ikk) {
        paivamaara.setkk(ikk);
    }
    
    /**
     * @param ivv asettaa vuoden
     */
    public void setvv(int ivv) {
        paivamaara.setvv(ivv);
    }
    
    /**
     * Asettaa päivämäärän
     * @param pvm päivä
     * @param kk kuukausi
     * @param vv vuosi
     */
    public void setPv(int pvm, int kk, int vv) {
        paivamaara.setPv(pvm,kk,vv);
    }
    //Setit loppuu
    

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot paivalle.
     */
    public void vastaaEsimerkkiTreeni() {
        int ipp = ranL(1,31);
        int ikk = ranL(1,12);
        int ivv = ranL(2000,2023);
        
        paivamaara.setpp(ipp);
        paivamaara.setpp(ikk);
        paivamaara.setpp(ivv);
        treeninTyyppi = "Kuntosali";
        luontipv = "1.1.2000";
        muokattuViimeksi = "2.2.2000";
    }
    

    /**
     * @param out output
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3));
        out.println("pvm:  " + paivamaara.toString() + "\n" +
                    "luontipv  " + "( " + luontipv + " )" + "\n" +
                    "muokattuViimeksi " + "( " +  muokattuViimeksi + " )");
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
        Paiva eka = new Paiva();
        eka.rekisteroi();
        eka.setPv(1,3,2023);
        
        eka.tulosta(System.out);


    }
}
