package kanta;

/**
 * Rajapinta tietueen yhdelle kentälle.
 *
 */
public interface Kentta extends Cloneable, Comparable<Kentta>{

    /**
     * kentän arvo merkkijonona.
     * @return kenttä merkkkijonona
     * @example
     * <pre name="test">
     *  IntKentta kentta = new IntKentta("määrä");
     *  kentta.aseta("12") === null;
     *  kentta.toString() === "12";
     * </pre>
     */
    @Override
    String toString();

    /**
     * Palauttaa kentään liittyvän kysymyksen.
     * @return kenttän liittyvä kysymys.
     * @example
     * <pre name="test">
     *  IntKentta kentta = new IntKentta("määrä");
     *  kentta.getKysymys() === "määrä";
     * </pre>
     */
    String getKysymys();

    /**
     * @param kysymys asetettava kysymyksen teksti
     */
    void setKysymys(String kysymys);
    
    
    /**
     * Asettaa kentän sisällön ottamalla tiedot
     * merkkijonosta.
     * @param jono jono josta tiedot otetaan.
     * @return null jos sisältö on hyvä, muuten merkkijonona virhetieto
     * @example
     * <pre name="test">
     *  IntKentta kentta = new IntKentta("määrä");
     *  kentta.aseta("12") === null; kentta.getValue() === 12;
     *  kentta.aseta("k") === "Ei kokonaisluku (k)"; kentta.getValue() === 12;
     * </pre>
     */
    String aseta(String jono);


    /**
     * Palauttaa kentän tiedot veratiltavana merkkijonona
     * @return vertailtava merkkijono kentästä
     * @example
     * <pre name="test">
     *  IntKentta k1 = new IntKentta("määrä");
     *  IntKentta k2 = new IntKentta("määrä");
     *  k1.aseta("12"); k2.aseta("5");
     *  k1.getAvain().compareTo(k2.getAvain()) > 0 === true;
     * </pre>
     */
    String getAvain();


    /**
     * @return syväkopio kentästä, tehtävä jokaiseen luokkaa toimivaksi
     * @throws CloneNotSupportedException mikäli ei tue cloonausta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *  IntKentta k1 = new IntKentta("määrä");
     *  k1.aseta("12");
     *  IntKentta k2 = k1.clone();
     *  k1.toString() === k2.toString();
     *  k1.aseta("5");
     *  k1.toString() == k2.toString() === false;
     * </pre>
     */
    Kentta clone() throws CloneNotSupportedException ;

    
    /**
     * @return vaakasuuntainen sijainti kentälle
     * @example
     * <pre name="test">
     * #import javax.swing.SwingConstants;
     *  IntKentta k1 = new IntKentta("määrä");
     *  k1.getSijainti() === SwingConstants.RIGHT; 
     * </pre>
     */
    int getSijainti();
    
}
