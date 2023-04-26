package kanta;


/**
 * Rajapinta tietueelle johon voidaan taulukon avulla rakentaa 
 * "attribuutit".
 * @author Eeli ja Antti
 * @example
 */
public interface Tietue {

    
    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *   #import treenipaivakirja.Tulos;
     *   Tulos tul = new Tulos();
     *   tul.getKenttia() === 5;
     * </pre>
     */
    public abstract int getKenttia();


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Tulos tul = new Tulos();
     *   tul.ekaKentta() === 2;
     * </pre>
     */
    public abstract int ekaKentta();


    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Tulos tul = new Tulos();
     *   tul.getKysymys(2) === "ala";
     * </pre>
     */
    public abstract String getKysymys(int k);


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Tulos tul = new Tulos();
     *   tul.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   tul.anna(0) === "2";   
     *   tul.anna(1) === "10";   
     *   tul.anna(2) === "Kalastus";   
     *   tul.anna(3) === "1949";   
     *   tul.anna(4) === "22";   
     * </pre>
     */
    public abstract String anna(int k);

    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Tulos tul = new Tulos();
     *   tul.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
     *   tul.aseta(3,"1940")  === null;
     *   tul.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
     *   tul.aseta(4,"20")    === null;
     * </pre>
     */
    public abstract String aseta(int k, String s);


    /**
     * Tehdään identtinen klooni tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Tulos tul = new Tulos();
     *   tul.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Object kopio = tul.clone();
     *   kopio.toString() === tul.toString();
     *   tul.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(tul.toString()) === false;
     *   kopio instanceof Tulos === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;


    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Tulos Tulos = new Tulos();
     *   Tulos.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Tulos.toString()    =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
     * </pre>
     */
    @Override
    public abstract String toString();

}
