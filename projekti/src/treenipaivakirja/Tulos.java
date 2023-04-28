package treenipaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author antti ja eeli
 * @version Mar 9, 2023
 *
 */
public class Tulos {
    private int            tunnusNro;
    private int            paivaNro;
    private String liike = "";
    private String sarja = ""; // sisältää sarjat ja toistot esim. 3x10. 3 sarjaa ja 10 toistoa
    private String paino = "";

    private String muut = "";

    
    private static int     seuraavaNro      = 1;



    /**
     * Toistaiseksi ei tarvetta 
     */
    public Tulos() {
        //
    }
    
//    public Tulos(String liike, String sarja, String paino, String muut, int paiva) {
//        this.liike = liike;
//        this.sarja = sarja;
//        this.paino = paino;
//        this.muut = muut;
//        this.paivaNro = paiva;
//    }
    
    public void asetaArvot(String liike, String sarja, String paino, String muut, int paiva) {
        this.liike = liike;
        this.sarja = sarja;
        this.paino = paino;
        this.muut = muut;
        this.paivaNro = paiva;
    }
    
    
    /**
     * Kun tehdään tulos niin määritetään samalla mille päivälle tulos on
     * TODO: Tähän lisätään ehkä myös sarja ja toisto/toistot?
     * @param paiva mikä paiva kyseessä
     */
    public Tulos(int paiva) {
        this.paivaNro = paiva;
    }
    
    
    /**
     * @return Tunnus numero
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     * 
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * @return Paiva numero
     */
    public int getPaivaNro() {
        return this.paivaNro;
    }

    
    /**
     * Tulostaa tiedot
     *
     * @param out tietovirta johon tulostetaan
     * @return tulosteen
     */
    public String tulosta(PrintStream out) {
        return this.tunnusNro+ " " + this.paivaNro + " " + this.liike + " " + this.sarja + " " + this.paino + " " + this.muut;
    }
    
    
    /**
     * @param os -
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    


    /**
     * Antaa tulokselle seuraavan rekisterinumeron.
     * @return tuloksen uusi tunnusNro
     * @example
     * <pre name="test">
     *   Tulos tulos1 = new Tulos();
     *   tulos1.getTunnusNro() === 0;
     *   tulos1.rekisteroi();
     *   Tulos tulos2 = new Tulos();
     *   tulos2.rekisteroi();
     *   int n1 = tulos1.getTunnusNro();
     *   int n2 = tulos2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    
    
    /**
     * palauttaa random luvun min ja max välistä. (min ja max) ei ole välillä
     * @param min minimi
     * @param max maksimi
     * @return luku
     */
    public int ranL(int min, int max) {
        if (min == max) return min;
        int luku;
        luku = (int) (Math.random() * (max - min) + min);
        return luku;
    }

    /**
     * Vastaa esimerkkitulostuksen
     * @param nro päivän numero
     */
    public void vastaaTulos(int nro) {
        liike = "Penkki";
        paivaNro = nro;
        sarja = ranL(1, 5) + "x" + ranL(1, 15);
        paino = ranL(1, 100) + "kg";
        muut = "väsytti";

    }


    /**
     * Vastaa tuloksen.
     */
    public void vastaaTulos() {
        vastaaTulos(12);
    }

    //Tietokantaan liittyväkoodi
    //====================================================
    
    /**
     * Antaa tietokannan luontilausekkeen tulostaululle
     * @return tulsotaulun luontilauseke
     */
    
    public String annaLuontilauseke() {
        return "CREATE TABLE Tulokset (" +
                "tulosID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "paivaID INTEGER, " +
                "liike VARCHAR(100), " +
                "sarja VARCHAR(100), " +
                "paino VARCHAR(100), " +
                "muut VARCHAR(100) " +
                ")";
    }
    
    
    /**
     * Antaa tuloksen lisäyslausekkeen
     * @param con tietokantayhteys
     * @return tuloksen lisäyslauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    
   public PreparedStatement annaLisayslauseke(Connection con)
           throws SQLException {
       PreparedStatement sql = con.prepareStatement("INSERT INTO Tulokset" +
               "(paivaID, liike, sarja, paino, muut) " +
               "VALUES (?, ?, ?, ?, ?)");
       
       // Syötetään kentät näin välttääksemme SQL injektiot.
       // Käyttäjän syötteitä ei ikinä vain kirjoiteta kysely
       // merkkijonoon tarkistamatta niitä SQL injektioiden varalta!
//       if ( tunnusNro != 0 ) sql.setInt(1, tunnusNro); else sql.setString(1, null);
//       sql.setInt(2, paivaNro);
//       sql.setInt(3, seuraavaNro);
       sql.setInt(1, paivaNro); //PaivaID
       sql.setString(2, liike);
       sql.setString(3, sarja);
       sql.setString(4, paino);
       sql.setString(5, muut);
       return sql;
   }
   
   /**
    * Antaa tuloksen poistolausekkeen
    * @param con tietokantayhteys
    * @return tuloksen lisäyslauseke
    * @throws SQLException Jos lausekkeen poistossa on ongelmia
    */
   public PreparedStatement annaPoistolauseke(Connection con)
           throws SQLException {
       PreparedStatement sql = con.prepareStatement("DELETE FROM Tulokset " +
               "WHERE tulosID = ?");  //Kokeiltu lisätä 2kpl extra kysymysmerkkiä
       
       sql.setInt(1, tunnusNro); //PaivaID

       return sql;
   }
   

   /**
    * Tarkistetaan onko id muuttunut lisäyksessä
    * @param rs lisäyslauseen ResultSet
    * @throws SQLException jos tulee jotakin vikaa
    */
   public void tarkistaId(ResultSet rs) throws SQLException {
       if ( !rs.next() ) return;
       int id = rs.getInt(1);
       if ( id == tunnusNro ) return;
       setTunnusNro(id);
   }
   
   /** 
    * Ottaa jäsenen tiedot ResultSetistä
    * @param tulokset mistä tiedot otetaan
    * @throws SQLException jos jokin menee väärin
    */
   public void parse(ResultSet tulokset) throws SQLException {
       setTunnusNro(tulokset.getInt("tulosID"));
       paivaNro = tulokset.getInt("paivaID"); //<--------------------PaivaID
       liike = tulokset.getString("liike");
       sarja = tulokset.getString("sarja");
       paino = tulokset.getString("paino");
       muut = tulokset.getString("muut");
   }

   /**
    * Selvitää harrastuksen tiedot | erotellusta merkkijonosta.
    * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
    * @param rivi josta harrastuksen tiedot otetaan
    * @example
    * <pre name="test">
    *     Tulos tulos = new Tulos();
    *     tulos.parse("   2  |  1  |  Penkki  |  5x5  |  100kg  |  vitutti");
    *     tulos.getTunnusNro() === 2;
    *     tulos.getPaivaNro() === 1;
    *     tulos.tulosta(System.out) === "2 1 Penkki 5x5 100kg vitutti";
    * </pre>
    */
   public void parse(String rivi) {
       StringBuffer sb = new StringBuffer(rivi);
       setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
       paivaNro = Mjonot.erota(sb, '|', paivaNro);
       liike = Mjonot.erota(sb, '|', liike);
       sarja = Mjonot.erota(sb, '|', sarja);
       paino = Mjonot.erota(sb, '|', paino);
       muut = Mjonot.erota(sb, '|', muut);
   }
   
   
   
   /**
    * SQL
    * Palauttaa k:tta jäsenen kenttää vastaavan kysymyksen
    * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
    * @return k:netta kenttää vastaava kysymys
    */
   //@Override    //Tyo7 Lisätty Override----------------------------------<----------
public String getKysymys(int k) {
       switch ( k ) {
           case 0: return "tulosID";
           case 1: return "paivaID";
           case 2: return "liike";
           case 3: return "sarja";
           case 4: return "paino";
           case 5: return "muut";
           default: return "Väärin meno urpo";
       }
   }
   
    //====================================================
   
   //VAIHE 7 alla ennen main
   /**
    * Tehdään identtinen klooni jäsenestä
    * @return Object kloonattu jäsen
    */
   @Override
   public Tulos clone() throws CloneNotSupportedException {
       Tulos uusi;
       uusi = (Tulos) super.clone();
       return uusi;
   }

   /**
    * Eka kenttä joka on mielekäs kysyttäväksi
    * @return eknn kentän indeksi
    */
   //@Override
   public int ekaKentta() {
       return 2;
   }
   
   /**
    * Palauttaa tuloksen kenttien lukumäärän
    * @return kenttien lukumäärä
    */
   //@Override
   public int getKenttia() {
       return 6;
   }
   
   /**
    * @param k Minkä kentän sisältö halutaan
    * @return valitun kentän sisältö
    * @example
    * <pre name="test">
    *     Tulos tulos = new Tulos();
    *     tulos.parse("   2  |  1  |  Penkki  |  5x5  |  100kg  |  vitutti");
    *     tulos.anna(0) === "2";
    *     tulos.anna(1) === "1";
    *     tulos.anna(2) === "Penkki";
    *     tulos.anna(3) === "5x5";
    *     tulos.anna(4) === "100kg";
    *     tulos.anna(5) === "vitutti";
    * </pre>
    */
   //@Override
   public String anna(int k) {
       switch (k) {
           case 0:
               return "" + tunnusNro;
           case 1:
               return "" + paivaNro;
           case 2:
               return "" + liike;
           case 3:
               return "" + sarja;
           case 4:
               return "" + paino;
           case 5:
               return "" + muut;
           default:
               return "???";
       }
   }

    //@Override
    public String aseta(int k, String s) {
        // TODO Auto-generated method stub
        return null;
    }
   
   //TODO: Vaihe 7 mukaan??
   /*
    *     /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="teest">
     *   Harrastus har = new Harrastus();
     *   har.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
     *   har.aseta(3,"1940")  === null;
     *   har.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
     *   har.aseta(4,"20")    === null;
     * </pre>
     *
    @Override
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro()));
                return null;
            case 1:
                jasenNro = Mjonot.erota(sb, '$', jasenNro);
                return null;
            case 2:
                ala = st;
                return null;
            case 3:
                try {
                    aloitusvuosi = Mjonot.erotaEx(sb, '§', aloitusvuosi);
                } catch (NumberFormatException ex) {
                    return "aloitusvuosi: Ei kokonaisluku ("+st+")";
                }
                return null;

            case 4:
                try {
                    tuntiaViikossa = Mjonot.erotaEx(sb, '§', tuntiaViikossa);
                } catch (NumberFormatException ex) {
                    return "h/vko: Ei kokonaisluku ("+st+")";
                }
                return null;

            default:
                return "Väärä kentän indeksi";
        }
    }

    */



    /**
     * testipääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tulos tulos = new Tulos();
        Tulos tulos2 = new Tulos();
        tulos.rekisteroi();
        tulos2.rekisteroi();
        
        tulos.tulosta(System.out);
        tulos2.tulosta(System.out);
        
        System.out.println("=============");
        
        tulos.vastaaTulos(1); //tulos.taytatulosTiedoilla();
        tulos.tulosta(System.out);
        
        tulos2.vastaaTulos(0); //tulos.taytatulosTiedoilla();
        tulos2.tulosta(System.out);
        }




}

