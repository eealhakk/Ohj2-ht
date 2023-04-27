//package varasto;
//
//import fi.jyu.mit.ohj2.Mjonot;
//
//import java.io.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//
///**
// * @author Eeli ja Antti
// * @version 1 Mar 2023
// *
// */
//public class Liike {
//        private int                 tunnusNro;
//        private String              liikkeenNimi;
//        
//        private static int seuraavaNro    = 1;
//    
//    
//
//        
//        
//    /**
//     * Muodostaja
//     */
//    public Liike() {
//        //
//    }
//
//
//    
//    //TODO: Tämän voi luultavasti siirtää muualle
//    /**
//     * palauttaa random luvun min ja max välistä. (min ja max) ei ole välillä
//     * @param min minimi
//     * @param max maksimi
//     * @return luku
//     */
//    public int ranL(int min, int max) {
//        if (min == max) return min;
//        int luku;
//        luku = (int) (Math.random() * (max - min) + min);
//        return luku;
//    }
//    
//    /**
//     * Apumetodi, jolla saadaan täytettyä testiarvot Liikkeelle.
//     */
//    public void vastaaEsimerkkiLiiketyyppi() {
//        ArrayList<String> x = new ArrayList<String>(Arrays.asList("Mave","Penkki","Ylätalja"));
//        liikkeenNimi = x.get(ranL(1,3));
//    }
//    
//    
//    /**
//     * @param liike päivä
//     * @return päivä merkkijonona
//     */
//    public String toString(Liike liike){
//        return liike.toString();
//    }
//    
//    /**
//     * @return rekisteröi uuden alkion
//     */
//    public int rekisteroi() {
//        tunnusNro = seuraavaNro;
//        seuraavaNro++;
//        return tunnusNro;
//    }
//
//    
//
//    /**
//     * @param out output
//     */
//    public void tulosta(PrintStream out) {
//        out.println(String.format("%03d", tunnusNro, 3));
//        out.println("Liikkeen nimi: " + liikkeenNimi + "numero:  " + tunnusNro);
//    }
//
//    
//    /**
//     * Tulostetaan liikkeen tiedot
//     * @param os tietovirta johon tulostetaan
//     */
//    public void tulosta(OutputStream os) {
//        tulosta(new PrintStream(os));
//    }
//
//    /**
//     * @return Tunnus numero
//     */
//    public int getTunnusNro() {
//        return tunnusNro;
//    }
//
//    public String annaLuontilauseke() {
//        return  "CREATE TABLE Liikkeet (" +
//                "liikeID INTEGER PRIMARY KEY AUTOINCREMENT , " +
//                "liikkeenNimi VARCHAR(100) NOT NULL, " +
//                //"PRIMARY KEY (liikeID)" +
//                ")";
//    }
//
//    public PreparedStatement annaLisayslauseke(Connection con) throws SQLException {
//        PreparedStatement sql = con.prepareStatement("INSERT INTO Liikkeet" +
//                "(liikeID, liikkeenNimi)" +
//                "VALUES (?, ?)");
//        if ( tunnusNro != 0 ) sql.setInt(1, tunnusNro); else sql.setString(1, null);
//        sql.setString(2, liikkeenNimi);
////        sql.setString(4, luontipv);
////        sql.setString(5, muokattuViimeksi);
//        return sql;
//    }
//
//
//
//    public void tarkistaId(ResultSet rs) throws SQLException {
//        if ( !rs.next() ) return;
//        int id = rs.getInt(1);
//        if ( id == tunnusNro ) return;
//        setTunnusNro(id);
//    }
//
//    private void setTunnusNro(int id) {
//        tunnusNro = id;
//        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
//
//    }
//
//    /**
//     * Palauttaa k:tta paivan kenttää vastaavan kysymyksen
//     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
//     * @return k:netta kenttää vastaava kysymys
//     */
////    public String getKysymys(int k) {
////        return switch (k) {
////            case 0 -> "liikeID";
////            case 1 -> "liikkeenNimi";
////            default -> "Äääliö";
////        };
////    }
//
//    /**
//     * @param rivi jota parsitaan
//     */
//    public void parse(String rivi) {
//        var sb = new StringBuilder(rivi);
//        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
//        liikkeenNimi = Mjonot.erota(sb, '|', liikkeenNimi);
//    }
//
//    /**
//     * Ottaa paivan tiedot ResultSetistä
//     * @param tulokset mistä tiedot otetaan
//     * @throws SQLException jos jokin menee väärin
//     */
//    public void parse(ResultSet tulokset) throws SQLException {
//        setTunnusNro(tulokset.getInt("paivaID"));
//        liikkeenNimi = tulokset.getString("paivamaara");
//    }
//
//    /**
//     * Antaa paivan päivityslausekkeen
//     * @param con tietokantayhteys
//     * @return paivan päivityslauseke
//     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
//     */
//    @SuppressWarnings("unused")
//    public PreparedStatement annaPaivityslauseke(Connection con)
//            throws SQLException {
//        return null;
//    }
//
//    /**
//     * Antaa paivan poistolausekkeen
//     * @param con tietokantayhteys
//     * @return paivan poistolauseke
//     * @throws SQLException Jos lausekkeen luonnissa on ongelmia
//     */
//
//    public PreparedStatement annaPoistolauseke(Connection con)
//            throws SQLException {
//        return null;
//    }
//
//    /**
//     * @param args ei käytössä
//     */
//    public static void main(String args[]) {
//        Liike eka = new Liike();
//        eka.rekisteroi();
//
//        eka.tulosta(System.out);
//    }
//
//
//}
//
