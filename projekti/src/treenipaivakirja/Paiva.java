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
    
    
        public Paiva() {
            // TODO Auto-generated constructor stub
        }
        
        
    private static class Paivamaara{
        int pp;
        int kk;
        int vv;
        
        public Paivamaara() {
            this.pp = 0;
            this.kk = 0;
            this.vv = 2000;
        }
        
        
        public String toString(Paivamaara pv){
            return "" + pp + kk + vv;
        }

    }
    
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
/*
    public String toString(Paivamaara pv) {
        
        int pp = Paivamaara.pp;
        int kk = Paivamaara.;
        int vv = Paivamaara.;
        return Paivamaara.toString();
        
    }
*/
    
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    public static void main(String args[]) {
        Paiva eka = new Paiva();
        eka.rekisteroi();
        
        eka.tulosta(System.out);


    }
}
