package treenipaivakirja;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author antti ja eeli
 * @version Mar 9, 2023
 *
 */
public class Tulokset implements Iterable<Tulos>{
    private String tiedostonNimi = "";
    
    /** Taulukko harrastuksista */
    private final Collection<Tulos> alkiot        = new ArrayList<Tulos>();

    
    
    /**
     * Alustetaan tulos alustavasti tyhjäksi
     */
    public Tulokset() {
        //
    }
    
    /**
     * Lisää uuden tuloksen tietorakenteeseen.  Ottaa tuloksen omistukseensa.
     * @param tul lisättävä tulos.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Tulos tul) {
        alkiot.add(tul);
    }


    /**
     * Lukee tulokset tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".tul";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa tulokset tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    @Override
    public Iterator<Tulos> iterator() {
        return alkiot.iterator();
    }
    
    //========
    
    /**
     * Haetaan kaikki paiva :n tulokset
     * @param tunnusnro paivan tunnusnumero jolle tuloksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin tuloksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Tulokset tulokset = new Tulokset();
     *  Tulos pitsi21 = new Tulos(2); tulokset.lisaa(pitsi21);
     *  Tulos pitsi11 = new Tulos(1); tulokset.lisaa(pitsi11);
     *  Tulos pitsi22 = new Tulos(2); tulokset.lisaa(pitsi22);
     *  Tulos pitsi12 = new Tulos(1); tulokset.lisaa(pitsi12);
     *  Tulos pitsi23 = new Tulos(2); tulokset.lisaa(pitsi23);
     *  Tulos pitsi51 = new Tulos(5); tulokset.lisaa(pitsi51);
     *  
     *  List<Tulos> loytyneet;
     *  loytyneet = tulokset.annaTulokset(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = tulokset.annaTulokset(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = tulokset.annaTulokset(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == pitsi51 === true;
     * </pre> 
     */
    public List<Tulos> annaTulokset(int tunnusnro) {
        List<Tulos> loydetyt = new ArrayList<Tulos>();
        for (Tulos har : alkiot)
            if (har.getPaivaNro() == tunnusnro) {
                loydetyt.add(har);
            }
        return loydetyt;
    }

    
    //========
    


    /**
     * @param args eikäytö
     */
    public static void main(String[] args) {
    // TODO Auto-generated method stub
        Tulokset harrasteet = new Tulokset();
        Tulos pitsi1 = new Tulos();
        pitsi1.vastaaTulos(2);
        Tulos pitsi2 = new Tulos();
        pitsi2.vastaaTulos(1);
        Tulos pitsi3 = new Tulos();
        pitsi3.vastaaTulos(2);
        Tulos pitsi4 = new Tulos();
        pitsi4.vastaaTulos(2);

        harrasteet.lisaa(pitsi1);
        harrasteet.lisaa(pitsi2);
        harrasteet.lisaa(pitsi3);
        harrasteet.lisaa(pitsi2);
        harrasteet.lisaa(pitsi4);

        System.out.println("============= Tulokset testi =================");

        List<Tulos> harrastukset2 = harrasteet.annaTulokset(2);

        for (Tulos har : harrastukset2) {
            System.out.print(har.getPaivaNro() + " ");
            har.tulosta(System.out);
        }

    }



}
