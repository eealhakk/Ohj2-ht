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
    
    /** Taulukko harrastuksista */
    private final Collection<Tulos> alkiot        = new ArrayList<Tulos>();
    //SQL
    private static Tulos aputulos = new Tulos();

    
    
    /**
     * SQL
     * Tarkistetaan että kannassa tulosten tarvitsema taulu
     * @param nimi tietokannan nimi
     * @throws SailoException jos jokin menee pieleen
     */
    public Tulokset(String nimi) throws SailoException {
        kanta = alustaKanta(nimi);
        try ( Connection con = kanta.annaKantayhteys() ) {
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
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * 
     * Collection<Tulos> loytyneet = tulokset.etsi("", 1);
     * loytyneet.size() === 0;
     * 
     * Tulos alkio1 = new Tulos(), alkio2 = new Tulos();
     * tulokset.lisaa(alkio1); 
     * tulokset.lisaa(alkio2);
     *  
     * loytyneet = tulokset.etsi("", 1);
     * loytyneet.size() === 2;
     * 
     * // Unique constraint ei hyväksy
     * tulokset.lisaa(alkio1); #THROWS SailoException
     * Tulos alkio3 = new Tulos(); Tulos alkio4 = new Tulos(); Tulos alkio5 = new Tulos();
     * tulokset.lisaa(alkio3); 
     * tulokset.lisaa(alkio4); 
     * tulokset.lisaa(alkio5); 
    
     * loytyneet = tulokset.etsi("", 1);
     * loytyneet.size() === 5;
     * Iterator<Tulos> i = loytyneet.iterator();
     * i.next() === alkio1;
     * i.next() === alkio2;
     * i.next() === alkio3;
     * </pre>
     */
    public void lisaa(Tulos tulos) throws SailoException {
        try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = tulos.annaLisayslauseke(con) ) {
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
                tulos.tarkistaId(rs);
            }   
            
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
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
    
    /**
     * Iteraattori kaikkien tulosten läpikäymiseen
     * @return tulositeraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Tulokset tulokset = new Tulokset();
     *  Tulos pitsi21 = new Tulos(2); tulokset.lisaa(pitsi21);
     *  Tulos pitsi11 = new Tulos(1); tulokset.lisaa(pitsi11);
     *  Tulos pitsi22 = new Tulos(2); tulokset.lisaa(pitsi22);
     *  Tulos pitsi12 = new Tulos(1); tulokset.lisaa(pitsi12);
     *  Tulos pitsi23 = new Tulos(2); tulokset.lisaa(pitsi23);
     * 
     *  Iterator<Tulos> i2=tulokset.iterator();
     *  i2.next() === pitsi21;
     *  i2.next() === pitsi11;
     *  i2.next() === pitsi22;
     *  i2.next() === pitsi12;
     *  i2.next() === pitsi23;
     *  i2.next() === pitsi12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Tulos har:tulokset ) { 
     *    har.getPaivaNro() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
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
     * #THROWS SailoException
     *  
     *  Tulos pitsi21 = new Tulos(2); pitsi21.vastaaPitsinNyplays(2); harrasteet.lisaa(pitsi21);
     *  Tulos pitsi11 = new Tulos(1); pitsi11.vastaaPitsinNyplays(1); harrasteet.lisaa(pitsi11);
     *  Tulos pitsi22 = new Tulos(2); pitsi22.vastaaPitsinNyplays(2); harrasteet.lisaa(pitsi22);
     *  Tulos pitsi12 = new Tulos(1); pitsi12.vastaaPitsinNyplays(1); harrasteet.lisaa(pitsi12);
     *  Tulos pitsi23 = new Tulos(2); pitsi23.vastaaPitsinNyplays(2); harrasteet.lisaa(pitsi23);
     *  Tulos pitsi51 = new Tulos(5); pitsi51.vastaaPitsinNyplays(5); harrasteet.lisaa(pitsi51);
     *  
     *  
     *  List<Tulos> loytyneet;
     *  loytyneet = harrasteet.annaTulokset(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = harrasteet.annaTulokset(1);
     *  loytyneet.size() === 2; 
     *  
     *  loytyneet.get(0) === pitsi11;
     *  loytyneet.get(1) === pitsi12;
     *  
     *  loytyneet = harrasteet.annaTulokset(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) === pitsi51;
     * </pre> 
     */
    public List<Tulos> annaTulokset(int tunnusnro) throws SailoException {
        List<Tulos> loydetyt = new ArrayList<Tulos>();
        
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Tulokset WHERE tulosID = ?")
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
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
        return loydetyt;
    }


    /**
     * Testiohjelma jäsenistölle
     * @param args ei käytössä
     */
    public static void main(String args[])  {
        try {
            Tulokset harrasteet = new Tulokset("treenikokeiluTulokset");
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
    
            List<Tulos> Tulokset2;
            
            Tulokset2 = harrasteet.annaTulokset(2);
            
            for (Tulos har : Tulokset2) {
                System.out.print(har.getPaivaNro() + " ");
                har.tulosta(System.out);
            }
            
            new File("kokeiluTulokset.db").delete();
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    
        /*
        try {
            new File("kokeilu.db").delete();
            Tulokset tulokset = new Tulokset("kokeilu");
    
            Tulos alkio = new Tulos(), alkio2 = new Tulos();
            alkio.vastaaTulos();
            //alkio2.rekisteroi();
            alkio2.vastaaTulos();
            
            tulokset.lisaa(alkio);
            tulokset.lisaa(alkio2);
            alkio2.tulosta(System.out);
            
            System.out.println("============= Jäsenet testi =================");

            int i = 0;
            for (Tulos tulos:tulokset.annaTulokset("", -1)) {
                System.out.println("Jäsen nro: " + i++);
                tulos.tulosta(System.out);
            }
            
            new File("kokeilu.db").delete();
        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
    }
    */
    }
     
}






