package treenipaivakirja;

import java.util.List;

/**
 * @author Eeli
 * @version 1 Mar 2023
 *
 */
public class Treenipaivakirja {
    private final Paivat paivat = new Paivat();
    /* TODO: Tämä tulossa
    private final Treenit treenit = new Treenit();
    */
    private final Tulokset tulokset = new Tulokset();
    
        
    /**
     * Palautaa treenipaivakirjan paivat
     * @return paiva määrän
     */
    public int getPaivia() {
        return paivat.getlkm();
    }
    
    /**
     * Palauttaa i:n paivan
     * @param i monesko paiva palautetaan
     * @return viite i:teen paivaan
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Paiva annaPaiva(int i) throws IndexOutOfBoundsException{
        return paivat.anna(i);
    }
    
    /**
     * Haetaan kaikki jäsen harrastukset
     * @param paiva päivä jolle tuloksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin tuloksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Treenipaivakirja treenipaivakirja = new Treenipaivakirja();
     *  Paiva otus1 = new Paiva(), otus2 = new Paiva(), otus3 = new Paiva();
     *  otus1.rekisteroi(); otus2.rekisteroi(); otus3.rekisteroi();
     *  int id1 = otus1.getTunnusNro();
     *  int id2 = otus2.getTunnusNro();
     *  Tulos pitsi11 = new Tulos(id1); treenipaivakirja.lisaa(pitsi11);
     *  Tulos pitsi12 = new Tulos(id1); treenipaivakirja.lisaa(pitsi12);
     *  Tulos pitsi21 = new Tulos(id2); treenipaivakirja.lisaa(pitsi21);
     *  Tulos pitsi22 = new Tulos(id2); treenipaivakirja.lisaa(pitsi22);
     *  Tulos pitsi23 = new Tulos(id2); treenipaivakirja.lisaa(pitsi23);
     *  
     *  List<Tulos> loytyneet;
     *  loytyneet = treenipaivakirja.annaTulokset(otus3);
     *  loytyneet.size() === 0; 
     *  loytyneet = treenipaivakirja.annaTulokset(otus1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = treenipaivakirja.annaTulokset(otus2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == pitsi21 === true;
     * </pre> 
     */
    public List<Tulos> annaTulokset(Paiva paiva) {
        return tulokset.annaTulokset(paiva.getTunnusNro());
    }

    /**
     * TODO: Tulossa / Kesken
     * 
     * Poistaa paivista,(ja muista) ne joilla on nro. 
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako paivaa poistettiin
     */
    public int poista(int nro) {
        return 0;
    }
    
    /**
     * Lisää treenipaivakirjaan uuden paivan
     * @param paiva lisättävä päivä
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
     * Listään uusi tulos treenipaivakirjaan
     * @param tul lisättävä tulos 
     */
    public void lisaa(Tulos tul) {
        tulokset.lisaa(tul);
    }

    
    /**
     * TODO: Sama idea kuin paiva lisaa
     * @param tulos Tulos
     */
    public void tulos(Tulos tulos){
        //Alustava treenin lisäys
        tulokset.lisaa(tulos);
    }
    
    
    /*
    //TODO: Saako metodeja kuormitettua eli tässä esim lisaa mutta toinen ottaa Paiva tyyppiä ja toinen Treeni tyyppiä
    // Vastaus joo?
    public void lisaa(Treeni treeni)throws SailoException {
        //Alustava treenin lisäys
        treenit.lisaa(treeni);
    }
    
    /**
     * Lukee paivan tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    /**
     * @param nimi nimi
     * @throws SailoException poikkeuskäsittelijä...
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
                Paiva paiva = treeniKerta.annaPaiva(i);
                System.out.println("Jäsen paikassa: " + i);
                paiva.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }   
    }
}
