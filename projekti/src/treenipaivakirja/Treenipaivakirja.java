package treenipaivakirja;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

//import treenipaivakirja.Harrastus;
//import kerho.Jasen;
import treenipaivakirja.Paiva;
import treenipaivakirja.SailoException;

import static java.nio.file.StandardCopyOption.*; 

/**
 * @author Eeli
 * @version 1 Mar 2023
 *
 */
public class Treenipaivakirja {
    //Vaihdetaan toteutus vastaamaan tuloksen toteuttamistapaa
    private Paivat paivat; //TODO: <-----Täydennä
    //SQL //private final Tulokset tulokset = new Tulokset();
    private Tulokset tulokset;
    private String           tiedostonNimi = "x";
    
    /* TODO: Tämä tulossa
    private final Treenit treenit = new Treenit();
    */
        
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
     * @throws SailoException poikkeus
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
    public List<Tulos> annaTulokset(Paiva paiva) throws SailoException {
        return tulokset.annaTulokset(paiva.getTunnusNro());
    }
    
    public Tulos annaTulos(int pvTunnusNro, int tulTunnusNro) throws SailoException {
        return tulokset.annaTulos(pvTunnusNro, tulTunnusNro);
    }
        
    /**
     * Haetaan kaikki jäsen harrastukset
     * @param tulos jäsen jolle harrastuksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin harrastuksiin
     * @throws SailoException jos harrastusten hakeminen tietokannasta epäonnistuu
     * <pre name="test">
     * #THROWS SailoException
     * Jasen aku1 = new Jasen(); aku1.vastaaAkuAnkka(); 
     * kerho.lisaa(aku1);
     * Harrastus har = new Harrastus(); 
     * har.vastaaPitsinNyplays(aku1.getTunnusNro()); 
     * kerho.lisaa(har);
     * kerho.annaHarrastukset(aku1).get(0) === har;
     *
     * Jasen aku2 = new Jasen(); aku2.vastaaAkuAnkka(); 
     * kerho.lisaa(aku2);
     * kerho.annaHarrastukset(aku2).size() === 0;
     * </pre>
     */
    public List<Tulos> annaTulokset(Tulos tulos) throws SailoException {
        return tulokset.annaTulokset(tulos.getTunnusNro());
    }

    /**
     * TODO: Tulossa / Kesken
     * 
     * Poistaa paivista,(ja muista) ne joilla on nro. 
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako paivaa poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
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

    public void lisaa(Paiva paiva) throws SailoException {  //Kesken, paiva pitää täydentää
        paivat.lisaa(paiva);
    }
    
    //Vaihe 7
    /** 
     * Korvaa paivan tietorakenteessa.  Ottaa paivan omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva paiva.  Jos ei löydy, 
     * niin lisätään uutena paivana. 
     * @param paiva lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaKerho();
     *  kerho.etsi("*",0).size() === 2;
     *  kerho.korvaaTaiLisaa(aku1);
     *  kerho.etsi("*",0).size() === 2;
     * </pre>
     */ 
    public void korvaaTaiLisaa(Paiva paiva) throws SailoException { 
        paivat.korvaaTaiLisaa(paiva); 
    }
    
    /** 
     * Korvaa harrastuksen tietorakenteessa.  Ottaa harrastuksen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva harrastus.  Jos ei löydy, 
     * niin lisätään uutena harrastuksena. 
     * @param tulos lisärtävän harrastuksen viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(Tulos tulos) throws SailoException { 
        tulokset.korvaaTaiLisaa(tulos); 
    }


    
    /**
     * Listään uusi tulos treenipaivakirjaan
     * @param tul lisättävä tulos 
     * @throws SailoException jos tietokantayhteyden kanssa ongelmia
     */
    public void lisaa(Tulos tul) throws SailoException {
        tulokset.lisaa(tul);
    }
    
    /** 
     * Poistaa tämän tuloksen 
     * @param tulos poistettava harrastus 
     * @throws SailoException jos tietokantayhteyden kanssa ongelmia
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKerho();
     *   kerho.annaHarrastukset(aku1).size() === 2;
     *   kerho.poistaHarrastus(pitsi11);
     *   kerho.annaHarrastukset(aku1).size() === 1;
     */ 
    public void poistaTulos(Tulos tulos) throws SailoException { 
        tulokset.poista(tulos); 
    }

    
    /**
     * Palauttaa paivat listassa
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi 
     * @return paivat listassa
     * @throws SailoException jos tietokannan kanssa ongelmia
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Paiva aku1 = new Paiva(); aku1.vastaaAkuAnkka(); 
     * Paiva aku2 = new Paiva(); aku2.vastaaAkuAnkka(); 
     * treenipaivakirja.lisaa(aku1);
     * treenipaivakirja.lisaa(aku2);
     * treenipaivakirja.lisaa(aku2);  #THROWS SailoException // samaa ei saa laittaa kahdesti
     * Collection<Paiva> loytyneet = treenipaivakirja.etsi(aku1.getNimi(), 1);
     * loytyneet.size() === 1;
     * loytyneet.iterator().next() === aku1;
     * loytyneet = treenipaivakirja.etsi(aku2.getNimi(), 1);
     * loytyneet.size() === 1;
     * loytyneet.iterator().next() === aku2;
     * treenipaivakirja.etsi("", 15); #THROWS SailoException
     * </pre>
     * TODO: Tyo7 Collection pitää ehkä vaihtaa List tyypiksi, mutta ei välttämättä.
     */
    public Collection<Paiva> etsi(String hakuehto, int k) throws SailoException {   //Kesken, paiva pitää täydentää
        return paivat.etsi(hakuehto,k);
    }
    
    /**
     * TODO: Sama idea kuin paiva lisaa
     *  tulos Tulos
     * @param tulos x
     * @throws SailoException -
     */
    public void tulos(Tulos tulos) throws SailoException{
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
     * @param nimi tietokannan nimi
     * @throws SailoException jos tietokannan luominen epäonnistuu
     * SQL
     * Luo tietokannan. Jos annettu tiedosto on jo olemassa ja
     * sisältää tarvitut taulut, ei luoda mitään
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        //luoKopio();
        //String valinimi = "bat" + nimi;
        tiedostonNimi = nimi;
        luoKopio(nimi);
        paivat = new Paivat(nimi);
        tulokset = new Tulokset(nimi);
        
    }
    
    public void luoKopio(String nimi)  { //throws SailoException
        
        File fbak = new File("temp.db");   //bat + nimi
        File ftied = new File(tiedostonNimi + ".db");
        //fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        //ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");
        try {
            Files.copy(ftied.toPath(),fbak.toPath(),REPLACE_EXISTING);
            //fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        
       
//        try ( Connection con = kanta.annaKantayhteys();
//              PreparedStatement sql1 = con.prepareStatement("attach database ? as ?") ) {
//            sql1.setString(1, tiedostonNimi);
//            sql1.setString(2, nimi);
//            sql1.execute();
//              //sql.setString(1, "%" + ehto + "%");
//
//          } catch ( SQLException e ) {
//              e.printStackTrace();
//              throw new SailoException("Ongelmia tallentamisessa kanssa:" + e.getMessage());
//          }
//          
//          try ( Connection con = kanta.annaKantayhteys();
//                  PreparedStatement sql2 = con.prepareStatement("BEGIN;\nINSERT INTO " + nimi + ".Paivat SELECT * FROM " + tiedostonNimi + ".Paivat;\nEND;")  ) {
//                //sql2.setString(1, nimi);
//                //sql2.setString(2, tiedostonNimi);
//                  //sql.setString(1, "%" + ehto + "%");
//              sql2.execute();
//
//              } catch ( SQLException e ) {
//                  e.printStackTrace();
//                  throw new SailoException("Ongelmia tallentamisessa kanssa:" + e.getMessage());
//              }
    }
    
    /**
     * Tallettaa treenipaivakirjan tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {   //tallenna();
        //Poistettu käytöstä SQL yhteydessä
        //paivat.tallenna();
        File fbak = new File("temp.db");   //bat + nimi
        File ftied = new File(tiedostonNimi + ".db");
        //fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        //ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");
        try {
            Files.copy(fbak.toPath(),ftied.toPath(),REPLACE_EXISTING);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }        // TODO: -> yritä tallettaa toinen vaikka toinen epäonnistuisi
        return;
    }

    /**
     * TODO: Täydennä pääohjelma oikeaksi!
     * Testiohjelma treenipaivakirjasta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        try {
                    new File("kokeilu.db").delete();
                    Treenipaivakirja treenipaivakirja = new Treenipaivakirja();
                    treenipaivakirja.lueTiedostosta("kokeilu");
         
                    Paiva eka = new Paiva(), toka = new Paiva();
                    eka.vastaaEsimerkkiTreeni();
                    toka.vastaaEsimerkkiTreeni();
         
                    treenipaivakirja.lisaa(eka);
                    treenipaivakirja.lisaa(toka);
                    int id1 = eka.getTunnusNro();
                    int id2 = toka.getTunnusNro();
                    Tulos pitsi11 = new Tulos(id1); pitsi11.vastaaTulos(id1);  treenipaivakirja.lisaa(pitsi11);
                    Tulos pitsi12 = new Tulos(id1); pitsi12.vastaaTulos(id1);  treenipaivakirja.lisaa(pitsi12);
                    Tulos pitsi21 = new Tulos(id2); pitsi21.vastaaTulos(id2);  treenipaivakirja.lisaa(pitsi21);
                    Tulos pitsi22 = new Tulos(id2); pitsi22.vastaaTulos(id2);  treenipaivakirja.lisaa(pitsi22);
                    Tulos pitsi23 = new Tulos(id2); pitsi23.vastaaTulos(id2);  treenipaivakirja.lisaa(pitsi23);
         
                    System.out.println("============= treenipaivakirjan testi =================");
                    
                    Collection<Paiva> paivat = treenipaivakirja.etsi("", -1);
                    int i = 0;
                    for (Paiva paiva : paivat) {
                        System.out.println("Päivä paikassa: " + i);
                        paiva.tulosta(System.out);
                        List<Tulos> loytyneet = treenipaivakirja.annaTulokset(paiva);
                        for (Tulos tulos : loytyneet)
                            tulos.tulosta(System.out);
                        i++;
                    }
         
                } catch ( SailoException ex ) {
                    System.out.println(ex.getMessage());
                }
                
                new File("kokeilu.db").delete();
    }
}
