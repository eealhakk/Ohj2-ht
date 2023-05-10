package treenipaivakirja;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static treenipaivakirja.Kanta.alustaKanta;
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
    /*
     * Alustuksia ja puhdistuksia testiä varten
     * @example
     * <pre name="testJAVA">
     * #import java.io.*;
     * #import java.util.*;
     * 
     * private Tulokset tulokset;
     * private String tiedNimi;
     * private File ftied;
     * 
     * @Before
     * public void alusta() throws SailoException { 
     *    tiedNimi = "testailutulokset";
     *    ftied = new File(tiedNimi+".db");
     *    ftied.delete();
     *    tulokset = new Tulokset(tiedNimi);
     * }   
     *
     * @After
     * public void siivoa() {
     *    ftied.delete();
     * }   
     * </pre>
     */
    private String tiedostonNimi = "";
    //SQLite
    private Kanta kanta;
    private boolean muutettu = false;
    
    /** Taulukko harrastuksista */
    private final List<Tulos> alkiot        = new ArrayList<Tulos>();
    //SQL
    private static Tulos aputulos = new Tulos();
    //Vaihe 7
    private static Tulos aputulos2;

    public Tulokset() {
        // TODO Auto-generated constructor stub
    }
    
    public boolean getMuutettu() {
        return muutettu;
    }
    
    /**
     * SQL
     * Tarkistetaan että kannassa tulosten tarvitsema taulu
     * @param nimi tietokannan nimi
     * @throws SailoException jos jokin menee pieleen
     */
    public Tulokset(String nimi) throws SailoException {
//        String valipaiva = "bat" + nimi;
        String temporoaryF = "temp";


        kanta = alustaKanta(temporoaryF);
        
        
        try ( Connection con = kanta.annaKantayhteys() ) {
            //if (nimi.length() > 3) tiedostonNimi = nimi.substring(2);
            
            
            // Hankitaan tietokannan metadata ja tarkistetaan siitä onko
            // Jasenet nimistä taulua olemassa.
            // Jos ei ole, luodaan se. Ei puututa tässä siihen, onko
            // mahdollisesti olemassa olevalla taululla oikea rakenne,
            // käyttäjä saa kuulla siitä virheilmoituksen kautta
            DatabaseMetaData meta = con.getMetaData();
            
            try ( ResultSet taulu = meta.getTables(null, null, "Tulokset", null) ) {
                if ( !taulu.next() ) {
                    // Luodaan Jasenet taulu
                    try ( PreparedStatement sql = con.prepareStatement(aputulos.annaLuontilauseke()) ) {
                        sql.execute();
                    }
                }
            }
            
        } catch ( SQLException e ) {
            System.out.println("Ongelmia tietokannan kanssa:" + e.getMessage());    //TODO: Poista myöhemmin!
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }
    
    /**
     * Lisää uuden tuloksen tietorakenteeseen.  Ottaa tuloksen omistukseensa.
     * @param tul lisättävä tulos.  Huom tietorakenne muuttuu omistajaksi
     
    public void lisaa(Tulos tul) {
        alkiot.add(tul);
    }
    */
    
    /**
     * Lisää uuden tuloksen tietorakenteeseen.  Ottaa tuloksen omistukseensa.
     * @param tulos lisätäävän tuloksen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    public void lisaa(Tulos tulos) throws SailoException {
        try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = tulos.annaLisayslauseke(con) ) {
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
                tulos.tarkistaId(rs);
            }
            //Lisätty tyo7
            muutettu = true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }
    
    public void poista(Tulos tulos) throws SailoException {
        try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = tulos.annaPoistolauseke(con) ) {
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
                tulos.tarkistaId(rs);
            }
            //Lisätty tyo7
            muutettu = true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }
    
    
    /**
     * Poistaa kaikki tietyn tietyn paivan tulokset
     * @param tunnusNro viite siihen, mihin liittyvät "tietueet" poistetaan
     * @return montako poistettiin 
     * @throws SailoException jos tietokannan kanssa ongelmia
     */
    public int poistaPaivanTulokset(int tunnusNro) throws SailoException {
        int n = 0;
        for (Iterator<Tulos> it = alkiot.iterator(); it.hasNext();) {
            Tulos tul = it.next();
            if ( tul.getPaivaNro() == tunnusNro ) {
                it.remove();    //Tähän poista(it);

            try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = tul.annaPoistolauseke(con) ) {
                    sql.executeUpdate();
                    try ( ResultSet rs = sql.getGeneratedKeys() ) {
                        tul.tarkistaId(rs);
                    }
                    //Lisätty tyo7
                    muutettu = true;
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
                }
                 n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    
    /**
     * Korvaa tuloksen tietorakenteessa.  Ottaa tuloksen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva tulos.  Jos ei löydy,
     * niin lisätään uutena tuloksena.
     * @param tulos lisättävän tuloksen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     *     #THROWS CloneNotSupportedException, SailoException
     *     #PACKAGEIMPORT
     *
     *     Tulokset tulokset = new Tulokset("testi");
     *     Tulos tulos1 = new Tulos(), tulos2 = new Tulos();
     *     tulos1.vastaaTulos();
     *     tulos2.vastaaTulos();
     *     tulos1.rekisteroi(); tulos2.rekisteroi();
     *     tulokset.getLkm() === 0;
     *     tulokset.korvaaTaiLisaa(tulos1);
     *     tulokset.getLkm() === 1;
     *     tulokset.korvaaTaiLisaa(tulos2);
     *     tulokset.getLkm() === 2;
     *     Tulos tulos3 = tulos1.clone();
     *     tulos3.aseta(2, "xxx");
     *     Iterator<Tulos> it = tulokset.iterator();
     *     it.next() === tulos1;
     *     tulokset.korvaaTaiLisaa(tulos3); tulokset.getLkm() === 2;
     *     it = tulokset.iterator();
     *     Tulos t1 = it.next();
     *     Tulos t2 = it.next();
     *     t1 === tulos3;
     *     t2 === tulos2;
     *     tulos1.getTunnusNro() === t1.getTunnusNro();
     *     tulos2.getTunnusNro() === t2.getTunnusNro();
     * </pre>
     */
    public void korvaaTaiLisaa(Tulos tulos) throws SailoException {
        int id = tulos.getTunnusNro();
        for (int i = 0; i < getLkm(); i++) {
            if (alkiot.get(i).getTunnusNro() == id) {
                alkiot.set(i, tulos);
                muutettu = true;
                return;
            }
        }
        lisaa(tulos);
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


   // /**
   //  * Tallentaa tulokset tiedostoon.
   //  * @throws SailoException jos talletus epäonnistuu
   //  */
   // public void talleta() throws SailoException {
   //     throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
   // }
    
    //Lisätty tyo7
    /**
     * Palauttaa kerhon harrastusten lukumäärän
     * @return harrastusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }

    
    /**
     * Iteraattori kaikkien tulosten läpikäymiseen
     * @return tulositeraattori
     */
    @Override
    public Iterator<Tulos> iterator() {
        return alkiot.iterator();
    }
    
    
    
    /**
     * Haetaan kaikki tulos Tulokset
     * @param tunnusnro jäsenen tunnusnumero jolle harrastuksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin harrastuksiin
     * @throws SailoException jos Tulosten hakeminen tietokannasta epäonnistuu
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *
     *  Tulos tulos21 = new Tulos(2); tulos21.vastaaTulos(2); tulokset.lisaa(tulos21);
     *  Tulos tulos11 = new Tulos(1); tulos11.vastaaTulos(1); tulokset.lisaa(tulos11);
     *  Tulos tulos22 = new Tulos(2); tulos22.vastaaTulos(2); tulokset.lisaa(tulos22);
     *  Tulos tulos12 = new Tulos(1); tulos12.vastaaTulos(1); tulokset.lisaa(tulos12);
     *  Tulos tulos23 = new Tulos(2); tulos23.vastaaTulos(2); tulokset.lisaa(tulos23);
     *  Tulos tulos51 = new Tulos(5); tulos51.vastaaTulos(5); tulokset.lisaa(tulos51);
     *
     *
     *  List<Tulos> loytyneet;
     *  loytyneet = tulokset.annaTulokset(3);
     *  loytyneet.size() === 0;
     *  loytyneet = tulokset.annaTulokset(1);
     *  loytyneet.size() === 2;
     *
     *  loytyneet.get(0) === tulos11;
     *  loytyneet.get(1) === tulos12;
     *
     *  loytyneet = tulokset.annaTulokset(5);
     *  loytyneet.size() === 1;
     *  loytyneet.get(0) === tulos51;
     * </pre>
     */
    public List<Tulos> annaTulokset(int tunnusnro) throws SailoException {
        List<Tulos> loydetyt = new ArrayList<>();
        
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Tulokset WHERE paivaID = ?")
                ) {
            sql.setInt(1, tunnusnro);
            try ( ResultSet tulokset = sql.executeQuery() )  {
                while ( tulokset.next() ) {
                    Tulos tul = new Tulos();
                    tul.parse(tulokset);
                    loydetyt.add(tul);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
        return loydetyt;
    }

    /**
     * Antaa tuloksen
     * @param pvTunnusNro tunnusnumero jolle päivälle tulos haetaan
     * @param tulTunnusNro tunnusnumero jolle tulokselle haetaan
     * @return tul tulos
     * @throws SailoException jos tietokannasta ei löydy tulosta
     */
    
    public Tulos annaTulos(int pvTunnusNro, int tulTunnusNro) throws SailoException {
        
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Tulokset WHERE paivaID = ?")
                ) {
            sql.setInt(1, pvTunnusNro);
            try ( ResultSet tulokset = sql.executeQuery() )  {
                while ( tulokset.next() ) {
                    Tulos tul = new Tulos();
                    tul.parse(tulokset);
                    if (tul.getTunnusNro() == tulTunnusNro)return tul;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
        return null;
    }



    /**
     * Testiohjelma jäsenistölle
     * @param args ei käytössä
     */
    public static void main(String args[])  {
        try {
            Tulokset harrasteet = new Tulokset("treenikokeiluTulokset");
            Tulos tulos1 = new Tulos();
            tulos1.vastaaTulos(2);
            Tulos tulos2 = new Tulos();
            tulos2.vastaaTulos(1);
            Tulos tulos3 = new Tulos();
            tulos3.vastaaTulos(2);
            Tulos tulos4 = new Tulos();
            tulos4.vastaaTulos(2);

            harrasteet.lisaa(tulos1);
            harrasteet.lisaa(tulos2);
            harrasteet.lisaa(tulos3);
            harrasteet.lisaa(tulos2);
            harrasteet.lisaa(tulos4);
            
            System.out.println("============= Tulokset testi =================");
    
            List<Tulos> Tulokset2;
            
            Tulokset2 = harrasteet.annaTulokset(2);
            
            for (Tulos har : Tulokset2) {
                System.out.print(har.getPaivaNro() + " ");
                har.tulosta(System.out);
            }
            
            new File("kokeiluTulokset.db").delete();
        } catch (SailoException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
     
}






