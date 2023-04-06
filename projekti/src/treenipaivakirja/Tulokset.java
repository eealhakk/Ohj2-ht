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
     * Palauttaa tulokset listassa
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi 
     * @return tulokset listassa
     * @throws SailoException jos tietokannan kanssa ongelmia
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Tulos aku1 = new Tulos(); aku1.vastaaTulos(); 
     * Tulos aku2 = new Tulos(); aku2.vastaaTulos(); 
     * tulokset.lisaa(aku1);
     * tulokset.lisaa(aku2);
     * tulokset.lisaa(aku2);  #THROWS SailoException  // ei saa lisätä sama id:tä uudelleen
     * Collection<Tulos> loytyneet = tulokset.etsi(aku1.getNimi(), 1);
     * loytyneet.size() === 1;
     * loytyneet.iterator().next() === aku1;
     * loytyneet = tulokset.etsi(aku2.getNimi(), 1);
     * loytyneet.size() === 1;
     * loytyneet.iterator().next() === aku2;
     * loytyneet = tulokset.etsi("", 15); #THROWS SailoException
     *
     * ftied.delete();
     * </pre>
     */
    public List<Tulos> annaTulokset(String hakuehto, int k) throws SailoException {
        String ehto = hakuehto;
        String kysymys = aputulos.getKysymys(k);
        if ( k < 0 ) { kysymys = aputulos.getKysymys(0); ehto = ""; }
        // Avataan yhteys tietokantaan try .. with lohkossa.
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Tulokset WHERE " + kysymys + " LIKE ?") ) {
            ArrayList<Tulos> loytyneet = new ArrayList<Tulos>();
            
            sql.setString(1, "%" + ehto + "%");
            try ( ResultSet tulokset = sql.executeQuery() ) {
                while ( tulokset.next() ) {
                    Tulos j = new Tulos();
                    j.parse(tulokset);
                    loytyneet.add(j);
                }
            }
            return loytyneet;
        } catch ( SQLException e ) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }


    /**
     * Testiohjelma jäsenistölle
     * @param args ei käytössä
     */
    public static void main(String args[])  {
        try {
            new File("kokeilu.db").delete();
            Tulokset tulokset = new Tulokset("kokeilu");
    
            Tulos aku = new Tulos(), aku2 = new Tulos();
            aku.vastaaTulos();
            //aku2.rekisteroi();
            aku2.vastaaTulos();
            
            tulokset.lisaa(aku);
            tulokset.lisaa(aku2);
            aku2.tulosta(System.out);
            
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

}






