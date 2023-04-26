package treenipaivakirja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Eeli ja Antti
 * @version 1 Mar 2023
 *
 */
public class Paivat {
    private static final int MAX_PAIVIA   = 5;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Paiva            alkiot[]      = new Paiva[MAX_PAIVIA];
    private boolean muutettu = false;
    
    private Kanta kanta;
    private static Paiva apupaiva = new Paiva();


    
     
    public Paivat() {
        // Atribuuttien oma alustus riittänee
    }
    
    
    /**
     * @param paiva pv
     * @throws SailoException poikkeus
     */
    public Paivat(String paiva) throws SailoException {
        kanta = Kanta.alustaKanta(paiva);
        try ( Connection con = kanta.annaKantayhteys() ) {
            // Hankitaan tietokannan metadata ja tarkistetaan siitä onko
            // paivaet nimistä taulua olemassa.
            // Jos ei ole, luodaan se. Ei puututa tässä siihen, onko
            // mahdollisesti olemassa olevalla taululla oikea rakenne,
            // käyttäjä saa kuulla siitä virheilmoituksen kautta
            DatabaseMetaData meta = con.getMetaData();
            
            try ( ResultSet taulu = meta.getTables(null, null, "Paivat", null) ) {
                if ( !taulu.next() ) {
                    // Luodaan paivaet taulu
                    try ( PreparedStatement sql = con.prepareStatement(apupaiva.annaLuontilauseke()) ) {
                    sql.execute();
                    }
                }
            }
                
                }
            catch (SQLException e) {
                e.printStackTrace();
                throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
                }
    
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
     
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    */
    
  
    
    
    /**
     * Palauttaa treenipaivakirjan paivien lukumäärän
     * @return paivien lukumäärä
     */
    public int getlkm() {
        return lkm;
    }
    
    
    
    
    /**
     * @param paiva päivä luokka
     * @throws SailoException poikkeus
     */
    public void lisaa(Paiva paiva) throws SailoException{
        try (Connection con = kanta.annaKantayhteys(); PreparedStatement sql = paiva.annaLisayslauseke(con) ){
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
            paiva.tarkistaId(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
            }
    }
    


    /**
     * Korvaa paivan tietorakenteessa.  Ottaa paivan omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva jäsen.  Jos ei löydy,
     * niin lisätään uutena paivanä.
     * @param paiva lisätäävän paivan viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * paivaet paivaet = new paivaet();
     * paiva aku1 = new paiva(), aku2 = new paiva();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * paivaet.getLkm() === 0;
     * paivaet.korvaaTaiLisaa(aku1); paivaet.getLkm() === 1;
     * paivaet.korvaaTaiLisaa(aku2); paivaet.getLkm() === 2;
     * paiva aku3 = aku1.clone();
     * aku3.aseta(3,"kkk");
     * Iterator<paiva> it = paivaet.iterator();
     * it.next() == aku1 === true;
     * paivaet.korvaaTaiLisaa(aku3); paivaet.getLkm() === 2;
     * it = paivaet.iterator();
     * paiva j0 = it.next();
     * j0 === aku3;
     * j0 == aku3 === true;
     * j0 == aku1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Paiva paiva) throws SailoException {
        int id = paiva.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = paiva;
                muutettu = true;
                return;
            }
        }
        lisaa(paiva);
    }
}
    
    
    /**
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return paivat listassa
     * @throws SailoException poikkeus
     */
    public Collection<Paiva> etsi(String hakuehto, int k) throws SailoException {
        String ehto = hakuehto;
        String kysymys = apupaiva.getKysymys(k);
        if ( k < 0 ) { kysymys = apupaiva.getKysymys(0); ehto = ""; }
        // Avataan yhteys tietokantaan try .. with lohkossa.
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Paivat WHERE " + kysymys + " LIKE ?") ) {
            ArrayList<Paiva> loytyneet = new ArrayList<Paiva>();
            
            sql.setString(1, "%" + ehto + "%");
            try ( ResultSet tulokset = sql.executeQuery() ) {
                while ( tulokset.next() ) {
                    Paiva j = new Paiva();
                    j.parse(tulokset);
                    loytyneet.add(j);
                }
            }
            return loytyneet;
        } catch ( SQLException e ) {
            e.printStackTrace();
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }
    
    
    
    /**
    * Testiohjelma jäsenistölle
    * @param args ei käytössä
    */
    public static void main(String args[]) {
            
       try {
           new File("kokeilu.db").delete();
           Paivat paivat = new Paivat();
            Paiva eka = new Paiva(), toka = new Paiva();
            //eka.rekisteroi();
            eka.vastaaEsimerkkiTreeni();
            //toka.rekisteroi();
            toka.vastaaEsimerkkiTreeni();
            paivat.lisaa(eka);
            paivat.lisaa(toka);
            toka.tulosta(System.out);
            
            System.out.println("============= paivat testi =================");
    
            int i = 0;
            for (Paiva paiva:paivat.etsi("", -1)) {
                System.out.println("Päivä nro: " + i++);
                paiva.tulosta(System.out);
            }
            new File("kokeilu.db").delete();   
        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
        
    }
    
}





