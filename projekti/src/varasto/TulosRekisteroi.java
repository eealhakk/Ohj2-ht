package varasto;

public class TulosRekisteroi {
    /**
     * EI sittekkään, oli tarve!!
     * 
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
     
    public int rekisteroi() {
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    */
}
