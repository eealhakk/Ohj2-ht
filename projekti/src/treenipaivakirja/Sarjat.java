/**
 * 
 */
package treenipaivakirja;

import java.util.ArrayList;

/**
 * @author antti
 * @version Mar 9, 2023
 *
 */
public class Sarjat {
    private static final int MAX_SARJOJA = 15;
    private int              lkm           = 0;
    private ArrayList<Sarja>    alkioS  = new ArrayList<Sarja>(MAX_SARJOJA);

    private String           tiedostonNimi = "";
    
    public Sarjat() {
        //
    }
    
    /**
     * @param sarja sarja
     * @throws SailoException poikkeus
     */
    public void lisaa(Sarja sarja) throws SailoException {
        if (lkm >= alkioS.size()) throw new SailoException("Liikaa alkioita");
        alkioS.set(lkm, sarja);
        lkm++;
    }
    
    /**
     * Palauttaa viitteen i jäseneen.
     * @param i minkä indexin jäsen halutaan
     * @return viite jäseneen jonka idx on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Sarja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkioS.get(i);
    }
    
    
    /**
     * Lukee sarjat tiedostosta.  //TODO: Kesken
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/sarjat.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Tallentaa sarjat tiedostoon.  //TODO: Kesken
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna()throws SailoException {
        throw new SailoException("Ei osata vielä tallentaa!" + tiedostonNimi);
    }
    
    /**
     * Palauttaa treenipaivakirjan sarjojen lukumäärän
     * @return paivien lukumäärä
     */
    public int getlkm() {
        return lkm;
    }
    
    
    
    /**
     * @param args aarargghh
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    }

}
