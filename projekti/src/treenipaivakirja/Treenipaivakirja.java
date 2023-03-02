package treenipaivakirja;

/**
 * @author Eeli
 * @version 1 Mar 2023
 *
 */
public class Treenipaivakirja {
    private final Paivat paivat = new Paivat();
        
    /**
     * Palautaa treenipaivakirjan paivat
     * @return paiva määrän
     */
    public int getPaivia() {
        return paivat.getlkm();
    }

    /**
     * Poistaa paivista,(ja muista) ne joilla on nro. TODO: Kesken
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako paivaa poistettiin
     */
    public int poista(int nro) {
        return 0;
    }
    
    public void lisaa(Paiva paiva) throws SailoException {
        paivat.lisaa(paiva);
    }

    /**
     * Palauttaa i:n paivan
     * @param i monesko paiva palautetaan
     * @return viite i:teen paivaan
     * @throws IndexOutOfBoundsException jos i väärin
     */

    public Paiva annaPaiva(int i) {
        return paivat.anna(i);
    }
    
    /**
     * Lukee paivan tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        paivat.lueTiedostosta(nimi);
    }
    
    /**
     * Tallettaa kerhon tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        paivat.tallenna();
        // TODO: -> yritä tallettaa toinen vaikka toinen epäonnistuisi
    }

    /**
     * TODO: Täydennä pääohjelma oikeaksi!
     * Testiohjelma treenipaivakirjasta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Treenipaivakirja treeniKerta = new Treenipaivakirja();

        try {
            // treenipaivakirja.lueTiedostosta("kelmit"); <- ?? paivat.dat?

            Paiva sali_treeni = new Paiva(), sali_treeni2 = new Paiva();
            sali_treeni.rekisteroi();
            sali_treeni.vastaaEsimerkkiTreeni();
            sali_treeni2.rekisteroi();
            sali_treeni2.vastaaEsimerkkiTreeni();

            treeniKerta.lisaa(sali_treeni);
            treeniKerta.lisaa(sali_treeni2);

            System.out.println("============= Treenipaivakirja testi =================");

            for (int i = 0; i < treeniKerta.getPaivia(); i++) {
                Paiva jasen = treeniKerta.annaPaiva(i);
                System.out.println("Jäsen paikassa: " + i);
                jasen.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }

        
    }
}
