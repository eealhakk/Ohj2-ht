package kanta;
import java.util.HashMap;

/**
 * Kenttä ykiskätteisen id-numeron hoitamiseksi
 */
public class IdKentta extends IntKentta  {
    
    /* rakenne, jossa tallessa kaikkien seuraavien numeroiden arvot */
    private static final HashMap<String,IntKentta> seuraavatNumerot = new HashMap<>();
    private IntKentta seuraavaNumero; // sama viite kaikilla saman tunnisteen kentillä
    
    /**
     * Alustetaan kenttä kysymyksellä ja seuraavan numeron tunnisteella
     * @param kysymys joka näytetään kenttää kysyttäessä.
     * @param tunniste saman tunnisteen alle luodut numerot pidetään samoina
     * @example
     * <pre name="test">
     *   IdKentta id1 = new IdKentta("id1","jäsenId__testIdKentta");
     *   IdKentta id2 = new IdKentta("id1","jäsenId__testIdKentta");
     *   IdKentta id3 = new IdKentta("id3","harrastusId__testIdKentta");
     *   id1.rekisteroi();
     *   id2.rekisteroi();
     *   id3.rekisteroi();
     *   id1.getValue() === 1;
     *   id2.getValue() === 2;
     *   id3.getValue() === 1;
     * </pre>
     */
    public IdKentta(String kysymys,String tunniste) { 
        super(kysymys);
        IntKentta nro  = seuraavatNumerot.get(tunniste);
        if ( nro == null ) {
            nro = new IntKentta(tunniste);
            nro.setValue(1);
        }
        seuraavaNumero = nro;
        seuraavatNumerot.put(tunniste, nro);
    }

    
    /**
     * @return tämän kentän seuraavan numeron tunniste
     * @example
     * <pre name="test">
     *   IdKentta id1 = new IdKentta("id1","jäsenId__testTunniste");
     *   id1.getTunniste() === "jäsenId__testTunniste"; 
     * </pre>
     */
    public String getTunniste() {
        return seuraavaNumero.getKysymys();
    }
    

    /**
     * Asetetaan kentän arvo kokonaislukuna.  Jos arvo on isompi kuin
     * seuraava numero, niin seuraava numero laitetaan tätä suuremmaksi.
     * @param value asetettava kokonaislukuarvo
     * @example
     * <pre name="test">
     *   IdKentta id1 = new IdKentta("id1","jäsenId__testSetValue");
     *   id1.setValue(20);
     *   id1.getSeuraavaNro() === 21;
     *   id1 = new IdKentta("id1","jäsenId__testSetValue");
     *   id1.aseta("33");
     *   id1.getSeuraavaNro() === 34;
     * </pre>
     */
    @Override
    public void setValue(int value) { 
        if ( value >= seuraavaNumero.getValue() ) seuraavaNumero.setValue(value+1);
        super.setValue(value);
    }

    
    /**
     * @return tähän id:hen liittyvä seuraava numero
     * <pre name="test">
     *   IdKentta id1 = new IdKentta("id1","jäsenId__testSeuraavaNro");
     *   id1.getSeuraavaNro() === 1;
     *   id1.setValue(20);
     *   id1.getSeuraavaNro() === 21;
     * </pre>
     */
    public int getSeuraavaNro() {
        return seuraavaNumero.getValue();
    }
    
    
    /**
     * Antaa kentälle seuraavan vapaan numeron
     * @return kentälle annettu arvo
     * <pre name="test">
     *   IdKentta id1 = new IdKentta("id1","jäsenId__testRekisteroi");
     *   id1.rekisteroi();
     *   id1.getValue() === 1;
     *   id1.rekisteroi(); // ei vaikuta kun numero on jo saatu
     *   id1.getValue() === 1;
     * </pre>
     */
    public int rekisteroi() {
        if ( getValue() != 0 ) return getValue();
        setValue(getSeuraavaNro()); // hoitaa seuraavan numeron kasvatuksen
        return getValue();
    }
    
}
