package varasto;

import treenipaivakirja.Kanta;
import treenipaivakirja.SailoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Liikkeet {

    private static final int MAX_LIIKKEITA   = 5;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Liike alkiot[]      = new Liike[MAX_LIIKKEITA];
    private boolean muutettu = false;
    private Kanta kanta;
    private static Liike apuLiike = new Liike();

    public Liikkeet() {
        // TODO Auto-generated constructor stub
    }

    public Liikkeet(String liike) throws SailoException {
        kanta = Kanta.alustaKanta(liike);
        try ( Connection con = kanta.annaKantayhteys() ) {
            DatabaseMetaData meta = con.getMetaData();

            try ( ResultSet taulu = meta.getTables(null, null, "Liikkeet", null) ) {
                if ( !taulu.next() ) {
                    try ( PreparedStatement sql = con.prepareStatement(apuLiike.annaLuontilauseke()) ) {
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
        public Liike anna(int i) throws IndexOutOfBoundsException {
            if (i < 0 || lkm <= i)
                throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
            return alkiot[i];
        }

        /**
         * Palauttaa treenipaivakirjan paivien lukumäärän
         * @return paivien lukumäärä
         */
        public int getlkm() {
            return lkm;
        }

    /**
     * @param liike liike luokka
     * @throws SailoException poikkeus
     */
    public void lisaa(Liike liike) throws SailoException{
        try (Connection con = kanta.annaKantayhteys(); PreparedStatement sql = liike.annaLisayslauseke(con) ){
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
                liike.tarkistaId(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }

    /**
     * Korvaa liikkeen tietorakenteessa.  Ottaa liikkeen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva jäsen.  Jos ei löydy,
     * niin lisätään uutena paivanä.
     * @param liike lisätäävän paivan viite.  Huom tietorakenne muuttuu omistajaksi
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
    public void korvaaTaiLisaa(Liike liike) throws SailoException {
        int id = liike.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = liike;
                muutettu = true;
                return;
            }
        }
        lisaa(liike);
    }

    /**
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return paivat listassa
     * @throws SailoException poikkeus
     */
    public Collection<Liike> etsi(String hakuehto, int k) throws SailoException {
        String ehto = hakuehto;
        String kysymys = apuLiike.getKysymys(k);
        if ( k < 0 ) { kysymys = apuLiike.getKysymys(0); ehto = ""; }
        // Avataan yhteys tietokantaan try .. with lohkossa.
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Liikkeet WHERE " + kysymys + " LIKE ?") ) {
            ArrayList<Liike> loytyneet = new ArrayList<Liike>();

            sql.setString(1, "%" + ehto + "%");
            try ( ResultSet tulokset = sql.executeQuery() ) {
                while ( tulokset.next() ) {
                    Liike j = new Liike();
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


    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}
