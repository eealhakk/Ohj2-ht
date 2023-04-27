package treenipaivakirja;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.Tietue;

/**
 * @author antti ja eeli
 * @version Mar 9, 2023
 *
 */
public class Tulos {
    private int            tunnusNro;
    private int            paivaNro;
    private String paiva = "2.2.2020";
    private String liike = "penkki";
    private int sarja;
    
    private static int     seuraavaNro      = 1;



    /*
    @Override
    public boolean equals(Object tulos) {
        return this.toString().equals(tulos.toString());
    }
    */
    /**
     * Toistaiseksi ei tarvetta 
     */
    public Tulos() {
        //
    }
    
    
    /**
     * Kun tehdään tulos niin määritetään samalla mille päivälle tulos on
     * TODO: Tähän lisätään ehkä myös sarja ja toisto/toistot?
     * @param paiva mikä paiva kyseessä
     */
    public Tulos(int paiva) {
        this.paivaNro = paiva;
        //this.toistoNro = toisto;
        //this.sarjaNro = sarja;
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
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", this.tunnusNro)+ " " + this.liike + " x " + this.sarja);
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
        paiva = "20.10.2015";
        sarja = ranL(2,6);
        paivaNro = nro;
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
                "sarja INTEGER " +
                //"FOREIGN KEY (paivaID) REFERENCES Paivat(paivaID)" +
//                "sarja2 INTEGER, " +
//                "sarja3 INTEGER, " +
//                "sarja4 INTEGER, " +
//                "sarja5 INTEGER " +
                // "PRIMARY KEY (tulosID)" + 
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
               "(paivaID, liike, sarja) " +
               "VALUES (?, ?, ?)");
       
       // Syötetään kentät näin välttääksemme SQL injektiot.
       // Käyttäjän syötteitä ei ikinä vain kirjoiteta kysely
       // merkkijonoon tarkistamatta niitä SQL injektioiden varalta!
//       if ( tunnusNro != 0 ) sql.setInt(1, tunnusNro); else sql.setString(1, null);
//       sql.setInt(2, paivaNro);
//       sql.setInt(3, seuraavaNro);
       sql.setInt(1, paivaNro); //PaivaID
       sql.setString(2, liike);
       sql.setInt(3, sarja);
//       sql.setInt(7, sarja2);
//       sql.setInt(8, sarja3);
//       sql.setInt(9, sarja4);   //<-------TODO:--------Liikaa suhteessa VALUES määrään - Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 8 out of bounds for length 8
//       sql.setInt(10, sarja5);


       
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
//       seuraavaNro = tulokset.getInt("seuraavaNro");
       //paiva = tulokset.getString("paiva");
       liike = tulokset.getString("liike");
       sarja = tulokset.getInt("sarja");
//       sarja2 =tulokset.getInt("sarja2");
//       sarja3 = tulokset.getInt("sarja3");
//       sarja4 = tulokset.getInt("sarja4"); 
//       sarja5 = tulokset.getInt("sarja5");
   }
   
   /**
    * Selvitää harrastuksen tiedot | erotellusta merkkijonosta.
    * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
    * @param rivi josta harrastuksen tiedot otetaan
    * @example
    * <pre name="test">
    *   Tulos tulos = new Tulos();
    *  tulos.parse("   1  |  4  |  1  |  1  |  1  ");
    *  tulos.getTunnusNro() === 1;
    *  tulos.getPaivaNro() === 4;
    *  tulos.tulosta(System.out) === "1|4|1|1|1"*
    *  tulos.rekisteroi();
    *  int n = tulos.getTunnusNro();
    *  tulos.parse(""+(n+20));
    *  tulos.rekisteroi();
    *  tulos.getTunnusNro() === n+20+1;
    *  tulos.tulosta(System.out) === ""+(n+20+1)+"|4|1|1|1";
    * </pre>
    */
   public void parse(String rivi) {
       StringBuffer sb = new StringBuffer(rivi);
       setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
       paivaNro = Mjonot.erota(sb, '|', paivaNro);
       paiva = Mjonot.erota(sb, '|', paiva);
       liike = Mjonot.erota(sb, '|', liike);
       sarja = Mjonot.erota(sb, '|', sarja);
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
//           case 1: return "paiva";
//           case 2: return "seuraavaNro";
           case 1: return "paivaID";
           case 2: return "liike";
           case 3: return "sarja";
//           case 6: return "sarja2";
//           case 7: return "sarja3";
//           case 8: return "sarja4";
//           case 9: return "sarja5";
           default: return "Väärin meno urpo";
       }
   }
   
    //====================================================
   
   //VAIHE 7 alla ennen main
   /**
    * Tehdään identtinen klooni jäsenestä
    * @return Object kloonattu jäsen
    * @example
    * <pre name="test">
    * #THROWS CloneNotSupportedException 
    *   Jasen jasen = new Jasen();
    *   jasen.parse("   3  |  Ankka Aku   | 123");
    *   Jasen kopio = jasen.clone();
    *   kopio.toString() === jasen.toString();
    *   jasen.parse("   4  |  Ankka Tupu   | 123");
    *   kopio.toString().equals(jasen.toString()) === false;
    * </pre>
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
       return 1;
   }
   
   /**
    * Palauttaa jäsenen kenttien lukumäärän
    * @return kenttien lukumäärä
    */
   //@Override
   public int getKenttia() {
       return 4;
   }
   
   /**
    * @param k Minkä kentän sisältö halutaan
    * @return valitun kentän sisältö
    * @example
    * <pre name="test">
    *   Harrastus har = new Harrastus();
    *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
    *   har.anna(0) === "2";   
    *   har.anna(1) === "10";   
    *   har.anna(2) === "Kalastus";   
    *   har.anna(3) === "1949";   
    *   har.anna(4) === "22";   
    *   
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
               //väkäset extrana varuiks
               return "" + paiva;
           case 3:
               return "" + liike;
           case 4:
               return "" + sarja;
           default:
               return "???";
       }
   }
   
   //TODO: Vaihe 7 mukaan??
   /**
    *     /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
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


    //@Override
    public String aseta(int k, String s) {
        // TODO Auto-generated method stub
        return null;
    }

}

