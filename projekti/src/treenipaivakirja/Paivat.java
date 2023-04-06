package treenipaivakirja;

import java.io.File;
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
    
    private Kanta kanta;
    private static Paiva apupaiva = new Paiva();


    /**
     * Muodostaja
     */
    public Paivat() {
        // Atribuuttien oma alustus riittänee
    }
    
    /**
     * @param paiva pv
     * @throws SailoException poikkeus
     * @throws SQLException poikkeus
     */
    public Paivat(String paiva) throws SailoException, SQLException {
        kanta = Kanta.alustaKanta(paiva);
        try ( Connection con = kanta.annaKantayhteys() ) {
            DatabaseMetaData meta = con.getMetaData();
            try ( ResultSet taulu = meta.getTables(null, null, "Jasenet", null) ) {
                if ( !taulu.next() ) {
                // Luodaan Jasenet taulu
                try ( PreparedStatement sql = con.prepareStatement(apupaiva.annaLuontilauseke()) ) {
                sql.execute();
                }
                }
            }
                
                }
            catch (SQLException e) {
                throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
                }
    
    }
    
    
    /**
     * @param paiva päivä luokka
     * @throws SailoException poikkeus
     * @throws SQLException poikkeus
     */
    public void lisaa(Paiva paiva) throws SailoException, SQLException {
        try (Connection con = kanta.annaKantayhteys(); PreparedStatement sql = paiva.annaLisayslauseke(con) ){
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
            paiva.tarkistaId(rs);
        }
        } catch (SQLException e) {
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
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return jäsenet listassa
     * @throws SailoException poikkeus
     * @throws SQLException poikkeus
     */
    public Collection<Paiva> etsi(String hakuehto, int k) throws SailoException, SQLException {
        String ehto = hakuehto;
        String kysymys = apupaiva.getKysymys(k);
        if ( k < 0 ) { kysymys = apupaiva.getKysymys(0); ehto = ""; }
     // Avataan yhteys tietokantaan try .. with lohkossa.
        try ( Connection con = kanta.annaKantayhteys();
                PreparedStatement sql = con.prepareStatement("SELECT * FROM Jasenet WHERE " + kysymys + " LIKE ?") ) {
            ArrayList<Paiva> loytyneet = new ArrayList<Paiva>();
            
            sql.setString(1, "%" + ehto + "%");
            try ( ResultSet tulokset = sql.executeQuery() ) {
            while ( tulokset.next() ) {
            Paiva p = new Paiva();
            p.parse(tulokset);
            loytyneet.add(p);
            }
            return loytyneet;
            } catch ( SQLException e ) {
                throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
                }
        }
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
    * Testiohjelma jäsenistölle
    * @param args ei käytössä
     * @throws SQLException poikkeus
    */
    public static void main(String args[]) throws SQLException  {
    try {
    new File("kokeilu.db").delete();
    Paivat paivat = new Paivat("kokeilu");
    
    Paiva eka = new Paiva(), toka = new Paiva();
                eka.vastaaEsimerkkiTreeni();
                //toka.rekisteroi();
                toka.vastaaEsimerkkiTreeni();
                
                paivat.lisaa(eka);
                paivat.lisaa(toka);
                toka.tulosta(System.out);
                
                System.out.println("============= Jäsenet testi =================");
    
                int i = 0;
                for (Paiva paiva:paivat.etsi("", -1)) {
                    System.out.println("Treeni nro: " + i++);
                    paiva.tulosta(System.out);
                }
                
                new File("kokeilu.db").delete();
            } catch ( SailoException ex ) {
                System.out.println(ex.getMessage());
            }
        }
    
    }





