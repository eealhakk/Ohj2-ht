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
    private static int     seuraavaNro      = 1;
    private String paiva = "1.1.2000";
    private int liike;
    private int sarja1;


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
        out.println(String.format("%03d", this.tunnusNro)+ " " + this.paiva + " " + this.liike + "x" + this.sarja1);
    }
    
    
    /**
     * @param os -
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    


    /**
     * Antaa tuloslle seuraavan rekisterinumeron.
     * @return tulosn uusi tunnusNro
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
    
    
  //TODO: Tämän voi luultavasti siirtää muualle
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
        sarja1 = ranL(1,6);
        liike = ranL(5,15);     
        paivaNro = nro;
    }
    
    public void vastaaTulos() {
        vastaaTulos(12);
    }
    
    /*//The type Tulos should also implement hashCode() since it overrides Object.equals()
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }
    */
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
                "sarja1 INTEGER " +
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
               "(paivaID, liike, sarja1) " + //, sarja2, sarja3, " + "sarja4, sarja5
               "VALUES (?, ?, ?)"); //Kokeiltu lisätä 2kpl extra kysymysmerkkiä
       
       // Syötetään kentät näin välttääksemme SQL injektiot.
       // Käyttäjän syötteitä ei ikinä vain kirjoiteta kysely
       // merkkijonoon tarkistamatta niitä SQL injektioiden varalta!
//       if ( tunnusNro != 0 ) sql.setInt(1, tunnusNro); else sql.setString(1, null);
//       sql.setInt(2, paivaNro);
//       sql.setInt(3, seuraavaNro);
       sql.setInt(1, paivaNro); //PaivaID
       sql.setInt(2, liike);
       sql.setInt(3, sarja1);
//       sql.setInt(7, sarja2);
//       sql.setInt(8, sarja3);
//       sql.setInt(9, sarja4);   //<-------TODO:--------Liikaa suhteessa VALUES määrään - Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 8 out of bounds for length 8
//       sql.setInt(10, sarja5);


       
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
       liike = tulokset.getInt("liike");
       sarja1 = tulokset.getInt("sarja1");
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
    *   tulos.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
    *   tulos.getPaivaNro() === 10;
    *   tulos.toString()    === "2|10|Kalastus|1949|22";
    *   
    *   tulos.rekisteroi();
    *   int n = tulos.getTunnusNro();
    *   tulos.parse(""+(n+20));
    *   tulos.rekisteroi();
    *   tulos.getTunnusNro() === n+20+1;
    *   tulos.toString()     === "" + (n+20+1) + "|10|Kalastus|1949|22";
    * </pre>
    */
   public void parse(String rivi) {
       StringBuffer sb = new StringBuffer(rivi);
       setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
       paivaNro = Mjonot.erota(sb, '|', paivaNro);
       paiva = Mjonot.erota(sb, '|', paiva);
       liike = Mjonot.erota(sb, '|', liike);
       sarja1 = Mjonot.erota(sb, '|', sarja1);
   }
   
   
   
   /**
    * SQL   //TODO: Muuta kaikki sarjat myöhemmin yhdeksi sarjaksi
    * Palauttaa k:tta jäsenen kenttää vastaavan kysymyksen
    * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
    * @return k:netta kenttää vastaava kysymys
    */
   public String getKysymys(int k) {
       switch ( k ) {
           case 0: return "tulosID";
//           case 1: return "paiva";
//           case 2: return "seuraavaNro";
           case 1: return "paivaID";
           case 2: return "liike";
           case 3: return "sarja1";
//           case 6: return "sarja2";
//           case 7: return "sarja3";
//           case 8: return "sarja4";
//           case 9: return "sarja5";
           default: return "Väärin meno urpo";
       }
   }
   
    //====================================================

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

