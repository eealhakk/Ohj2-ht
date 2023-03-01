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
        private int pp;
        private int kk;
        private int vv;
        
        public Paivamaara() {
            this.pp = 0;
            this.kk = 0;
            this.vv = 2000;
        }
        
        
        @Override
        public String toString(){
            String jono = String.format("%02d",pp) + "." + String.format("%02d",kk)
                             + "." + String.format("%04d",vv);
            return jono;
        }
        
        public void setPv(int pvm, int kk, int vuosi) {
            this.pp = pvm;
            this.kk = kk;
            this.vv = vuosi;
        }

    }
    
    public String toString(Paivamaara pv){
        return pv.toString();
    }
    
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    public void setPv(int pvm, int kk, int vuosi) {
        paivamaara.setPv(pvm,kk,vuosi);
    }
    
/*
    public String toString(Paivamaara pv) {
        
        int pp = Paivamaara.pp;
        int kk = Paivamaara.;
        int vv = Paivamaara.;
        return Paivamaara.toString();
        
    }
*/
    /*
     *         private int                 tunnusNro;
        private final Paivamaara    paivamaara       = new Paivamaara();
        private String              treeninTyyppi    = "";
        private String              luontipv         = "";
        private String              muokattuViimeksi = "";
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3));
        out.println("pvm:  " + paivamaara.toString() + "\n" +
                    "luontipv  " + "( " + luontipv + " )" + "\n" +
                    "muokattuViimeksi " + "( " +  muokattuViimeksi + " )");
    }

    
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    public static void main(String args[]) {
        Paiva eka = new Paiva();
        eka.rekisteroi();
        eka.setPv(1,3,2023);
        
        eka.tulosta(System.out);


    }
}
