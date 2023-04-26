package kanta;

import javax.swing.SwingConstants;

/**
 * Peruskenttä joka implementoi kysymyksen käsittelyn
 * ja tarkistajan käsittelyn.
 */
public abstract class PerusKentta implements Kentta { 
    private String kysymys;

    /**
     * Yleisen tarkistajan viite
     */
    protected Tarkistutin tarkistaja = null;

    /**
     * Alustetaan kenttä kysymyksen tiedoilla.
     * @param kysymys joka esitetään kenttää kysyttäessä.
     */
    public PerusKentta(String kysymys)  { this.kysymys = kysymys; }

    /**
     * Alustetaan kysymyksellä ja tarkistajalla.
     * @param kysymys joka esitetään kenttää kysyttäessä.
     * @param tarkistaja tarkistajaluokka joka tarkistaa syötän oikeellisuuden
     */
    public PerusKentta(String kysymys,Tarkistutin tarkistaja) {
        this.kysymys = kysymys;
        this.tarkistaja = tarkistaja;
    }

    /**
     * @return kentän arvo merkkijonona
     * @see kanta.Kentta#toString()
     */
    @Override
    public abstract String toString();

    /**
     * @return Kenttää vastaava kysymys
     * @see kanta.Kentta#getKysymys()
     */
    @Override
    public String getKysymys() {
        return kysymys;
    }

    
    /**
     * @param kysymys asetettava kysymyksen teksti
     */
    @Override
    public void setKysymys(String kysymys) {
        this.kysymys = kysymys;
    }

    
    /**
     * @param jono josta otetaan kentän arvo
     * @see kanta.Kentta#aseta(java.lang.String)
     */
    @Override
    public abstract String aseta(String jono);

    /**
     * Palauttaa kentän tiedot veratiltavana merkkijonona
     * @return vertailtava merkkijono kentästä
     */
    @Override
    public String getAvain() { // NOPMD (jostain syystä luulee abstraktiksi metodiksi)
        return toString().toUpperCase();
    }


    /**
     * @return syväkopio kentästä, tehtävä jokaiseen luokkaa toimivaksi
     * @throws CloneNotSupportedException mikäli ei tue kloonausta
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
    @Override
    public Kentta clone() throws CloneNotSupportedException {
        return (Kentta)super.clone();
    }


    /**
     * @return vertailee kahta oliota keskenään
     */
    @Override
    public int compareTo(Kentta k) {
            return getAvain().compareTo(k.getAvain());
    }

    
    /**
     * @return vaakasuuntainen sijainti kentälle
     */
    @Override
    public int getSijainti() {
        return SwingConstants.LEFT;        
    }
}






