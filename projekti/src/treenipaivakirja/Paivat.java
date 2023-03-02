package treenipaivakirja;

/**
 * @author Eeli
 * @version 1 Mar 2023
 *
 */
public class Paivat {
    private static final int MAX_PAIVIA   = 5;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Paiva            alkiot[]      = new Paiva[MAX_PAIVIA];


    /**
     * Muodostaja
     */
    public Paivat() {
        // Atribuuttien oma alustus riittänee
    }
    
    /**
     * @param paiva päivä luokka
     * @throws SailoException poikkeus
     */
    public void lisaa(Paiva paiva) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = paiva;
        lkm++;
    }
    

    /**
     * Palauttaa viitteen i jäseneen.
     * @param i minkä indexin jäsen halutaan
     * @return viite jäseneen jonka idx on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Paiva anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
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
     * Tallentaa paivat tiedostoon.  //TODO: Kesken
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna()throws SailoException {
        throw new SailoException("Ei osata vielä tallentaa!" + tiedostonNimi);
    }
    
    /**
     * Palauttaa treenipaivakirjan paivien lukumäärän
     * @return paivien lukumäärä
     */
    public int getlkm() {
        return lkm;
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Paivat jasenet = new Paivat();

        Paiva sali_Treeni = new Paiva(), juoksu_Treeni = new Paiva();
        sali_Treeni.rekisteroi();
        sali_Treeni.vastaaEsimerkkiTreeni();
        juoksu_Treeni.rekisteroi();
        juoksu_Treeni.vastaaEsimerkkiTreeni();

        try {
            jasenet.lisaa(sali_Treeni);
            jasenet.lisaa(juoksu_Treeni);

            System.out.println("============= Paivat testi =================");

            for (int i = 0; i < jasenet.getlkm(); i++) {
                Paiva jasen = jasenet.anna(i);
                System.out.println("Jäsen nro: " + i);
                jasen.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }




}
