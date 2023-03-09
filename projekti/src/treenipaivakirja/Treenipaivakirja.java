package treenipaivakirja;

/**
 * @author Eeli
 * @version 1 Mar 2023
 *
 */
public class Treenipaivakirja {
    private final Paivat paivat = new Paivat();
    private final Treenit treenit = new Treenit();
        
    /**
     * Palautaa treenipaivakirjan paivat
     * @return paiva määrän
     */
    public int getPaivia() {
        return paivat.getlkm();
    }

    /**
     * TODO: Kesken
     * 
     * Poistaa paivista,(ja muista) ne joilla on nro. 
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako paivaa poistettiin
     */
    public int poista(int nro) {
        return 0;
    }
    
    /**
     * Lisää kerhoon uuden jäsenen
     * @param paiva lisättävä jäsen
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Treenipaivakirja treenipaivakirja = new Treenipaivakirja();
     * Paiva abc = new Paiva(), ggg = new Paiva();
     * abc.rekisteroi(); ggg.rekisteroi();
     * treenipaivakirja.getPaivia() === 0;
     * treenipaivakirja.lisaa(abc); treenipaivakirja.getPaivia() === 1;
     * treenipaivakirja.lisaa(ggg); treenipaivakirja.getPaivia() === 2;
     * treenipaivakirja.lisaa(abc); treenipaivakirja.getPaivia() === 3;
     * treenipaivakirja.getPaivia() === 3;
     * treenipaivakirja.annaPaiva(0) === abc;
     * treenipaivakirja.annaPaiva(1) === ggg;
     * treenipaivakirja.annaPaiva(2) === abc;
     * treenipaivakirja.annaPaiva(3) === abc; #THROWS IndexOutOfBoundsException 
     * treenipaivakirja.lisaa(abc); treenipaivakirja.getPaivia() === 4;
     * treenipaivakirja.lisaa(abc); treenipaivakirja.getPaivia() === 5;
     * treenipaivakirja.lisaa(abc);            #THROWS SailoException
     * </pre>
     */

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
