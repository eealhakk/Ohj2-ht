package treenipaivakirja;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Eeli
 * @version 1 Mar 2023
 *
 */
public class Paiva {
        private int                 tunnusNro;
        private final Paivamaara    paivamaara       = new Paivamaara();
        private String              treeninTyyppi    = "";
        private String              luontipv         = "";
        private String              muokattuViimeksi = "";
        
        private static int seuraavaNro    = 1;
    
    
        /**
         * Muodostaja
         
        public Paiva() {//==
            // TODO Auto-generated constructor stub
        }
        */
        
        
    private static class Paivamaara{
        private int pp;
        private int kk;
        private int vv;
        
        public Paivamaara() {
            this.pp = 0;
            this.kk = 0;
            this.vv = 2000;
        }
        
        
        
        
        
        //Setit
        public void setpp(int pp) {
            this.pp = pp;
        }
        
        public void setkk(int ikk) {
            this.kk = ikk;
        }
        
        public void setvv(int ivv) {
            this.vv = ivv;
        }
        
        public void setPv(int pvm, int kk, int vuosi) {
            this.pp = pvm;
            this.kk = kk;
            this.vv = vuosi;
        }
        //Setit loppuu
        
        @Override
        public String toString(){
            String jono = String.format("%02d",pp) + 
                    "." + String.format("%02d",kk) + 
                    "." + String.format("%04d",vv);
            return jono;
        }
        


    }//==
    
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
    public String toString(Paivamaara pv){
        return pv.toString();
    }
    
    /**
     * @return rekisteröi uuden alkion
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    //Setit
    /**
     * @param ipp asettaa päivän
     */
    public void setpp(int ipp) {
        paivamaara.setpp(ipp);
    }
    
    /**
     * @param ikk asettaa kuukauden
     */
    public void setkk(int ikk) {
        paivamaara.setkk(ikk);
    }
    
    /**
     * @param ivv asettaa vuoden
     */
    public void setvv(int ivv) {
        paivamaara.setvv(ivv);
    }
        
    /**
     * Asettaa päivämäärän
     * @param pvm päivä
     * @param kk kuukausi
     * @param vv vuosi
     */
    public void setPv(int pvm, int kk, int vv) {
        paivamaara.setPv(pvm,kk,vv);
    }
    //Setit loppuu
    

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot paivalle.
     */
    public void vastaaEsimerkkiTreeni() {
        int ipp = ranL(1,31);
        int ikk = ranL(1,12);
        int ivv = ranL(2022,2023);
        
        paivamaara.setpp(ipp);
        paivamaara.setkk(ikk);
        paivamaara.setvv(ivv);
        vastaaEsimerkkiTreenityyppi();
        luontipv = "1.1.2000";
        muokattuViimeksi = "2.2.2000";
    }
    

    /**
     * @param out output
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3));
        out.println("pvm:  " + paivamaara.toString() + "\n" +
                    "Treenin tyyppi: "+ treeninTyyppi + "\n" + 
                    "luontipv  " + "( " + luontipv + " )" + "\n" +
                    "muokattuViimeksi " + "( " +  muokattuViimeksi + " )");
    }

    
    /**
     * @param os -
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    @Override
    public String toString() {
        return ("=/="+String.format("%03d", tunnusNro, 3) +
                "Treenin tyyppi: "+ treeninTyyppi + "\n" + 
                "luontipv  " + "( " + luontipv + " )" + "\n" +
                "muokattuViimeksi " + "( " +  muokattuViimeksi +
                " )" + "=/=");
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
        // TODO Auto-generated method stub
        return super.hashCode();
    }
    
    
    // Tietokantaan liittyvää koodia
    //------------------------------------------------------------------------------------
    
    
    /**
     * Antaa tietokannan luontilausekkeen päivätaululle
     * @return päivätaulun luontilauseke
     */
    public String annaLuontilauseke() {
        return "CREATE TABLE Paivat (" +
                "tunnusNro INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "paivamaara VARCHAR(100) NOT NULL, " +
                "treeninTyyppi VARCHAR(100) NOT NULL, " +
                "luontiPV VARCHAR(100) NOT NULL, " +
                "muokattuViimeksi VARCHAR(100) NOT NULL, " +
                // "PRIMARY KEY (paivaID)" + 
                ")";
    }
    
    
    /**
     * Antaa jäsenen lisäyslausekkeen
     * @param con tietokantayhteys
     * @return jäsenen lisäyslauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    public PreparedStatement annaLisayslauseke(Connection con)
            throws SQLException {
        PreparedStatement sql = con.prepareStatement("INSERT INTO Treenit" +
                "(tunnusNro, paivamaara, treeninTyyppi, luontipv, muokattuViimeksi" +
                "VALUES (?, ?, ?, ?, ?, ?)");
        
        // Syötetään kentät näin välttääksemme SQL injektiot.
        // Käyttäjän syötteitä ei ikinä vain kirjoiteta kysely
        // merkkijonoon tarkistamatta niitä SQL injektioiden varalta!
        if ( tunnusNro != 0 ) sql.setInt(1, tunnusNro); else sql.setString(1, null);
        sql.setLong(2, tunnusNro);
        sql.setObject(3, paivamaara);
        sql.setString(4, treeninTyyppi);
        sql.setString(5, luontipv);
        sql.setString(6, muokattuViimeksi);
        
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
    
    
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
        
    }
    
    //TODO: laita toimimaan
//    /**
//     * @return paivamäärä
//     * LISÄTTY SQL OHESSA
//     */
//    public Object getPaivamaara(String jono) {
//        return paivamaara;
//    }
//    
    
    /** 
     * Ottaa jäsenen tiedot ResultSetistä
     * @param tulokset mistä tiedot otetaan
     * @throws SQLException jos jokin menee väärin
     */
    public void parse(ResultSet tulokset) throws SQLException {
        setTunnusNro(tulokset.getInt("tunnusNro"));
        //paivamaara = tulokset.getPaivamaara("päivämäärä");
        treeninTyyppi = tulokset.getString("treeninTyyppi");
        luontipv = tulokset.getString("luontipv");
        muokattuViimeksi = tulokset.getString("muokattuViimeksi");
    }
    
    
    
    /**
     * Antaa jäsenen päivityslausekkeen
     * @param con tietokantayhteys
     * @return jäsenen päivityslauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    @SuppressWarnings("unused")
    public PreparedStatement annaPaivityslauseke(Connection con)
            throws SQLException {
        return null;
    }
    
    
    /**
     * Antaa jäsenen poistolausekkeen
     * @param con tietokantayhteys
     * @return jäsenen poistolauseke
     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
     */
    @SuppressWarnings("unused")
    public PreparedStatement annaPoistolauseke(Connection con)
            throws SQLException {
        return null;
    }
    
    

    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Paiva eka = new Paiva();
        eka.rekisteroi();
        eka.setPv(1,3,2023);
        
        eka.tulosta(System.out);


    }

   




}
