package treenipaivakirja;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Eeli ja Antti
 * @version 1 Mar 2023
 *
 */
public class Paiva {
        private int                 tunnusNro;
        private String              paivamaara       = "";
        private String              treeninTyyppi    = "";
        private String              luontipv         = "";
        private String              muokattuViimeksi = "";
        
        private static int seuraavaNro    = 1;
    
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
     * Apumetodi, jolla saadaan täytettyä testiarvot paivalle.
     */
    public void vastaaEsimerkkiTreenityyppi() {
        ArrayList<String> x = new ArrayList<String>(Arrays.asList("Kuntosali","Lenkki","Uinti"));
        treeninTyyppi = x.get(ranL(1,3));
    }
    
    
    /**
     * @param pv päivä
     * @return päivä merkkijonona
     */
    public String toString(String pv){
        return pv.toString();
    }
    
    /**
     * Antaa paivalle seuraavan rekisterinumeron.
     * @return rekisteröi uuden alkion
     * @example
     * <pre name="test">
     *   Paiva aku1 = new Paiva();
     *   aku1.getTunnusNro() === 0;
     *   aku1.rekisteroi();
     *   Paiva aku2 = new Paiva();
     *   aku2.rekisteroi();
     *   int n1 = aku1.getTunnusNro();
     *   int n2 = aku2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot paivalle.
     */
    public void vastaaEsimerkkiTreeni() {
        int ipp = ranL(1,31);
        int ikk = ranL(1,12);
        int ivv = ranL(2022,2023);
        paivamaara = ipp + "." + ikk + "." + ivv;
        vastaaEsimerkkiTreenityyppi();
        luontipv = "1.1.2000";
        muokattuViimeksi = "2.2.2000";
        
    }
    


    /**
     * @param out output
     */
    public void tulosta(PrintStream out) {
        vastaaEsimerkkiTreeni();
        out.println(String.format("%03d", tunnusNro, 3));
        out.println("pvm:  " + paivamaara.toString() + "\n" +
                    "Treenin tyyppi: "+ treeninTyyppi + "\n" + 
                    "luontipv  " + "( " + luontipv + " )" + "\n" +
                    "muokattuViimeksi " + "( " +  muokattuViimeksi + " )");
    }

    
    /**
     * Tulostetaan paivan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    @Override
    public String toString() {
        return ("=/="+String.format("%03d", tunnusNro, 3) +
                "Treenin tyyppi: "+
                treeninTyyppi + "\n" + 
                "luontipv  " +
                "( " + luontipv + " )" + "\n" +
                "muokattuViimeksi " + 
                "( " +  muokattuViimeksi +" )" +
                "=/=");
    }
    
    @Override
    public boolean equals(Object paiva) {
        return this.toString().equals(paiva.toString());
    }
    
    
    /**
     * @return Tunnus numero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    

    /**
     * @return päivämäärä muotoa 12.1.2022
     */
    public String getPvm() {
        return this.paivamaara.toString();
    }
    

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    /**
     * Palauttaa k:tta paivan kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
            case 0: return "paivaID";
            case 1: return "paivamaara";
            case 2: return "treeninTyyppi";
//            case 3: return "luontiPv";
//            case 4: return "muokattuViimeksi";
            default: return "Äääliö";
        }
    }

    // Tietokantaan liittyvää koodia
    //------------------------------------------------------------------------------------
    
    /**
     * Antaa tietokannan luontilausekkeen päivätaululle
     * @return päivätaulun luontilauseke
     */
    public String annaLuontilauseke() {
        return  "CREATE TABLE Paivat (" +
                "paivaID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "paivamaara VARCHAR(100) NOT NULL, " +
                "treeninTyyppi VARCHAR(100)" +
                // "PRIMARY KEY (jasenID)" + 
                ")";
    }


    
    /**
     * Antaa paivan lisäyslausekkeen
     * @param con tietokantayhteys
     * @return paivan lisäyslauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    public PreparedStatement annaLisayslauseke(Connection con)
            throws SQLException {
        PreparedStatement sql = con.prepareStatement("INSERT INTO Paivat" +
                "(paivaID, paivamaara, treeninTyyppi)" + //, luontipv, muokattuViimeksi
                "VALUES (?, ?, ?)"); //Vähennetty yksi
        
        // Syötetään kentät näin välttääksemme SQL injektiot.
        // Käyttäjän syötteitä ei ikinä vain kirjoiteta kysely
        // merkkijonoon tarkistamatta niitä SQL injektioiden varalta!
        if ( tunnusNro != 0 ) sql.setInt(1, tunnusNro); else sql.setString(1, null);
        sql.setString(2, paivamaara);
        sql.setString(3, treeninTyyppi);
//        sql.setString(4, luontipv);
//        sql.setString(5, muokattuViimeksi);
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
     * Asettaa tunnusnumeron
     * @param nr nykyinen arvo?
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
        
    }
    
    /**
     * @param rivi jota parsitaan
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        paivamaara = Mjonot.erota(sb, '|', paivamaara);
        treeninTyyppi = Mjonot.erota(sb, '|', treeninTyyppi);
        luontipv = Mjonot.erota(sb, '|', luontipv);
        muokattuViimeksi = Mjonot.erota(sb, '|', muokattuViimeksi);
        
    }
    
    /** 
     * Ottaa paivan tiedot ResultSetistä
     * @param tulokset mistä tiedot otetaan
     * @throws SQLException jos jokin menee väärin
     */
    public void parse(ResultSet tulokset) throws SQLException {
        setTunnusNro(tulokset.getInt("paivaID"));
        paivamaara = tulokset.getString("paivamaara");
        treeninTyyppi = tulokset.getString("treeninTyyppi");
//        luontipv = tulokset.getString("luontipv");
//        muokattuViimeksi = tulokset.getString("muokattuViimeksi");
    }
    
    /**
     * Antaa paivan päivityslausekkeen
     * @param con tietokantayhteys
     * @return paivan päivityslauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    @SuppressWarnings("unused")
    public PreparedStatement annaPaivityslauseke(Connection con)
            throws SQLException {
        return null;
    }
    
    /**
     * Antaa paivan poistolausekkeen
     * @param con tietokantayhteys
     * @return paivan poistolauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    @SuppressWarnings("unused")
    public PreparedStatement annaPoistolauseke(Connection con)
            throws SQLException {
        return null;
    }
      
    /**
     * Asettaa p
     * @param pvm pvm 
     * @param kk kk
     * @param vuosi vuosi
     */
    public void setPv(int pvm, int kk, int vuosi) {
        paivamaara = pvm + "." + kk + "." + vuosi;
    }
    
    /**
     * Testiohjelma päiville
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Paiva eka = new Paiva();
        eka.rekisteroi();
        eka.setPv(1,3,2023);
   
        eka.tulosta(System.out);
    }

    
}
