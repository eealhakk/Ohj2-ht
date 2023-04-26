package kanta;

/**
 * Rajapinta yleiselle tarkistajalle.
 * Tarkistajan tehtävä on tutkia onko annettu
 * merkkijono kelvollinen kentän sisällöksi ja jos on.
 * palautetaan null.
 * Virhetapauksessa palautetaan virhettä mahdollisimman
 * hyvin kuvaava merkkijono.
 */
public interface Tarkistutin {
    /**
     * Tutkitaan käykö annettu merkkijono kentän sisällöksi.
     * @param jono tutkittava merkkijono
     * @return null jos jono oikein, muuten virheilmoitusta vastaava merkkijono
     * @example
     * <pre name="test">
     *   RegExpTarkistaja tar = new RegExpTarkistaja("[1-4]*");
     *   tar.tarkista("12") === null;
     *   tar.tarkista("15") === "Ei vastaa maskia: [1-4]*";
     *   tar.tarkista("")   === null;
     *   tar = new RegExpTarkistaja("[1-4]+","Vain numeroita 1-4");
     *   tar.tarkista("15") === "Vain numeroita 1-4";
     *   tar.tarkista("") === "Vain numeroita 1-4";
     *   tar.tarkista("1") === null;
     * </pre>
     */
    String tarkista(String jono);
}
