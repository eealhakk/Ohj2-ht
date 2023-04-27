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
import java.util.Iterator;

/**
 * @author Eeli ja Antti
 * @version 1 Mar 2023
 *
 */
public class Paivat implements Iterable<Paiva> {
    private static final int MAX_PAIVIA    = 5;
    private int              lkm           = 0;
    private String kokoNimi                = "";
    private String           tiedostonNimi = "nimet";
    private Paiva            alkiot[]      = new Paiva[MAX_PAIVIA];
    private boolean muutettu = false;
    
    private Kanta kanta;//eli tallennuksen jälkeen sijoitettava db/kanta
    //tyo7
    //private Kanta backupKanta;//eli alkuperäinen tiedosto
    
    private static Paiva apupaiva = new Paiva();


    
     
    public Paivat() {
        // Atribuuttien oma alustus riittänee
    }
    
    
    /**
     * @param paiva pv
     * @throws SailoException poikkeus
     */
    public Paivat(String paiva) throws SailoException {
        tiedostonNimi = paiva;
        //Pidetään alkuperäinen backup filenä
        //ja tallentamisessa luodaan uusi kanta olio samalla nimellä
        //ja kopioidaan vanha kanta/db tiedot siihen
        //, minkäjälkeen positetaan vanha kanta/db
        
        String valipaiva = "bat" + paiva;
        
        kanta = Kanta.alustaKanta(valipaiva);
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
    
//    /**
//     * Tallentaa jäsenistön tiedostoon.  
//     * Tiedoston muoto:
//     * <pre>
//     * Kelmien kerho
//     * 20
//     * ; kommenttirivi
//     * 2|Ankka Aku|121103-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
//     * 3|Ankka Tupu|121153-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
//     * </pre>
//     * @throws SailoException jos talletus epäonnistuu
//     */
//    public void tallenna() throws SailoException {
//        if ( !muutettu ) return;
//
//        //Kopioi backup varsinaiseen
//        try ( Connection con = kanta.annaKantayhteys();
//              PreparedStatement sql1 = con.prepareStatement("attach database database/path as ?\r\n");
//                
//            
//              PreparedStatement sql2 = con.prepareStatement("INSERT OR REPLACE INTO table SELECT * FROM ?") ) {//"retrieved_database.table"
//            sql1.setString(1, tiedostonNimi);
//              //sql.setString(1, "%" + ehto + "%");
//
//          } catch ( SQLException e ) {
//              e.printStackTrace();
//              throw new SailoException("Ongelmia tallentamisessa kanssa:" + e.getMessage());
//          }
//
//        muutettu = false;
//    }
    
    public void tallenna() {    //throws SailoException
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");
//        try ( Connection con = kanta.annaKantayhteys();
//                PreparedStatement sql = con.prepareStatement("SELECT * FROM Tulokset WHERE paivaID = ?")
//                  ) {
//              sql.setInt(1, tunnusnro);
//              try ( ResultSet tulokset = sql.executeQuery() )  {
//                  while ( tulokset.next() ) {
//                      Tulos tul = new Tulos();
//                      tul.parse(tulokset);
//                      loydetyt.add(tul);
//                  }
//              }
//              
//          } catch (SQLException e) {
//              e.printStackTrace();
//              throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
//          }
//        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
//            fo.println(getKokoNimi());
//            fo.println(alkiot.length);
//            for (Paiva paiva : this) {
//                fo.println(paiva.toString());
//            }
//            //} catch ( IOException e ) { // ei heitä poikkeusta
//            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
//        } catch ( FileNotFoundException ex ) {
//            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
//        } catch ( IOException ex ) {
//            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
//        }

        muutettu = false;
    }
    

    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonNimi + ".bak";
    }
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonNimi;
    }
    

    /**
     * Palauttaa Kerhon koko nimen
     * @return Kerhon koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }





    
//    public void copydb(String kopioitava, String kohde) throws SailoException {
//        try ( Connection con = kanta.annaKantayhteys();
//                PreparedStatement sql1 = con.prepareStatement("attach database database/path as ?\r\n");
//                  
//              
//                PreparedStatement sql2 = con.prepareStatement("INSERT OR REPLACE INTO table SELECT * FROM ?") ) {//"retrieved_database.table"
//              sql1.setString(1, tiedostonNimi);
//                //sql.setString(1, "%" + ehto + "%");
//
//            } catch ( SQLException e ) {
//                e.printStackTrace();
//                throw new SailoException("Ongelmia tallentamisessa kanssa:" + e.getMessage());
//            }
//    }

    
    
    
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


    @Override
    public Iterator<Paiva> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
}





