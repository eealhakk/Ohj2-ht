package treenipaivakirja;

import varasto.Treeni;

import java.util.ArrayList;

/**
 * @author Antti ja Eeli
 * @version Mar 2, 2023
 *
 */


public class Treenit {
    private static final int MAX_TREENEJA   = 10;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private ArrayList<Treeni>            alkiotT      = new ArrayList<Treeni>();


    /**
     * Muodostaja
     */
    public Treenit() {
        // Atribuuttien oma alustus riittänee
    }
    
    /**
     * @param Treeni päivä luokka
     * @throws SailoException poikkeus
     */
    public void lisaa(Treeni Treeni) throws SailoException {
        //if (lkm >= alkiotT.size()) throw new SailoException("Liikaa alkioita");
        alkiotT.add(lkm,Treeni);
        lkm++;
    }
    

    /**
     * Palauttaa viitteen i jäseneen.
     * @param i minkä indexin jäsen halutaan
     * @return viite jäseneen jonka idx on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Treeni anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiotT.get(i);
    }
    
    /**
     * Lukee jäsenistön tiedostosta.  //TODO: Kesken
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    /**
     * Tallentaa Treenit tiedostoon.  //TODO: Kesken
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna()throws SailoException {
        throw new SailoException("Ei osata vielä tallentaa!" + tiedostonNimi);
    }
    
    /**
     * Palauttaa treeniTreenikirjan paivien lukumäärän
     * @return paivien lukumäärä
     */
    public int getlkm() {
        return lkm;
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Treenit treenit = new Treenit();

        Treeni sali_Treeni = new Treeni(), juoksu_Treeni = new Treeni();
        sali_Treeni.rekisteroi();
        sali_Treeni.vastaaEsimerkkiTreenityyppi();
        juoksu_Treeni.rekisteroi();
        juoksu_Treeni.vastaaEsimerkkiTreenityyppi();

        try {
            treenit.lisaa(sali_Treeni);
            treenit.lisaa(juoksu_Treeni);

            System.out.println("============= Treenit testi =================");

            for (int i = 0; i < treenit.getlkm(); i++) {
                Treeni treeni = treenit.anna(i);
                System.out.println("Treeni nro: " + i);
                treeni.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }




}