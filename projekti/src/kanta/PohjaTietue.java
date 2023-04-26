package kanta;

/**
 * PohjaTietue joka osaa mm. itse huolehtia tunnus_nro:staan.
 * Jos t�t� perit��n, niin kaikilla perityill� on juokseva
 * id-numero kesken��n, jollei kentt�taulukon 1. kent�ksi ole annettu 
 *  IdKentta-tyyppist� kentt��.
 * 
 * Voidaan k�ytt�� myös suoraan luomalla
 * kentt�taulukko ennen olion luomista, ks, muodostajan esimerkki.
 * T�llöin taulukko pit�� muistaa luoda jokaista luotavaa tietuetta kohti
 * erikseen.
 *
 * @example
 * <pre name="testJAVA">
 * public static class Harrastus extends PohjaTietue {
 *     public Harrastus() { 
 *       super(new Kentta[]{
 *                  new IdKentta("id","harrastusID_testPohjatietue"),
 *                  new IntKentta("j�senId"),
 *                  new JonoKentta("ala"),
 *                  new IntKentta("aloitusvuosi"),
 *                  new IntKentta("h/vko")
 *            },2); 
 *     }
 *     public int getJasenNro() { return ((IntKentta)getKentta(1)).getValue(); }
 * }    
 * </pre>
 * 
 * @example
 * <pre name="test_getOtsikot">
 * #import java.util.Arrays;
 * // getOtsikot()
 *   Harrastus har = new Harrastus();
 *   Arrays.toString(har.getOtsikot()) =R= "\\[ala, aloitusvuosi, h/vko.*";
 * </pre>
 * 
 * @example
 * <pre name="test_anna">
 * // anna(int)
 *   Harrastus har = new Harrastus();
 *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
 *   har.anna(0) === "2";   
 *   har.anna(1) === "10";   
 *   har.anna(2) === "Kalastus";   
 *   har.anna(3) === "1949";   
 *   har.anna(4) === "22";   
 * </pre>
 * 
 * @example
 * <pre name="test_aseta">
 * // aseta(int,String)
 *   Harrastus har = new Harrastus();
 *   har.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
 *   har.aseta(3,"1940")  === null;
 *   har.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
 *   har.aseta(4,"20")    === null;
 * </pre>
 * 
 * @example
 * <pre name="test_rekisteroi">
 * // rekisteroi() 
 *   Harrastus pitsi1 = new Harrastus();
 *   pitsi1.getTunnusNro() === 0;
 *   pitsi1.rekisteroi();
 *   Harrastus pitsi2 = new Harrastus();
 *   pitsi2.rekisteroi();
 *   int n1 = pitsi1.getTunnusNro();
 *   int n2 = pitsi2.getTunnusNro();
 *   n1 === n2-1;
 * </pre>
 * 
 * @example
 * <pre name="test_parse">
 * // parse()
 *   Harrastus harrastus = new Harrastus();
 *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
 *   harrastus.getJasenNro() === 10;
 *   harrastus.toString()     =R= "2\\|10\\|Kalastus\\|1949\\|22.*";
 *   
 *   harrastus.rekisteroi(); // ei vaikuta
 *   int n = harrastus.getTunnusNro();
 *   n === 2;
 *   harrastus.parse(""+(n+20));
 *   harrastus.toString()     =R= "" + (n+20) + "\\|10\\|\\|1949\\|22.*";
 *   harrastus = new Harrastus();
 *   harrastus.rekisteroi();
 *   harrastus.getTunnusNro() === n+20+1;
 * </pre>
 */
public class PohjaTietue extends PerusTietue {
    /**
     */
    private Kentta     kentat[]    = null;
    private IdKentta idKentta = null;
    int eka;
       
    private static int seuraavaNro = 1;


    @Override
    public Kentta[] getKentat() {
        return kentat;
    }


    @Override
    protected final void setKentat(Kentta[] uudetKentat) {
        kentat = uudetKentat;
        idKentta = null;
        if ( kentat.length == 0 ) return;
        Kentta ekaKentta = getKentta(0);
        if ( !(ekaKentta instanceof IdKentta) ) return;
        idKentta = (IdKentta)ekaKentta;
    }


    /**
     * @return seuraava numero joka tullaan antamaan rekisteröinniss�
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.rekisteroi();
     *   harrastus.getSeuraavaNro() === harrastus.getTunnusNro()+1;
     * </pre>
     */
    @Override
    public int getSeuraavaNro() {
        if ( idKentta != null ) return idKentta.getSeuraavaNro(); 
        return seuraavaNro;
    }


    /**
     * Antaa tietueelle uuden id-numeron mik�li sit� jo ei ole
     * annettu.
     * @return seuraava numero joka annettiin rekisteröinniss�
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.rekisteroi();
     *   int n = harrastus.getTunnusNro();
     *   harrastus.rekisteroi();
     *   n === harrastus.getTunnusNro(); // ei muutu koska oli jo
     *   harrastus.getSeuraavaNro() === n+1;
     * </pre>
     */
    @Override
    public int rekisteroi() {
        if ( idKentta != null ) return idKentta.rekisteroi();
        return super.rekisteroi();
    }

    
    @Override
    protected void setSeuraavaNro(int i) {
        seuraavaNro = i;
    }
    

    /**
     * Alustetaan pohjatietue uusilla kentill�
     * @param kentat kentt�taulukko, jota jatkossa k�ytet��n
     * @param eka mik� on ekan kent�n indeksi
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Kentta kentat[] = { 
     *     new IdKentta("id","testPohjaTietueConstructor"), 
     *     new IntKentta("j�senId"),
     *     new JonoKentta("ala"),
     *     new IntKentta("aloitusvuosi"),
     *     new IntKentta("h/vko")
     *   };
     *   PohjaTietue har = new PohjaTietue(kentat,2);
     *   har.getKysymys(2) === "ala";  
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   har.getSeuraavaNro() === 3; 
     *   Tietue kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     *   kopio instanceof PohjaTietue === true;
     * </pre>
     */
    public PohjaTietue(Kentta[] kentat, int eka) {
        setKentat(kentat);
        this.eka = eka;
    }
    
    
    /**
     * @return ensimm�inen k�ytt�j�n syötett�v�n kent�n indeksi
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.ekaKentta() === 2;
     * </pre>
     */
    @Override
    public int ekaKentta() {
        return eka;
    }

    
    /**
     * Tehd��n identtinen klooni tietueesta
     * @return kloonattu tietue
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Tietue kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     *   kopio instanceof Harrastus === true;
     * </pre>
     */
    @Override
    public PohjaTietue clone() throws CloneNotSupportedException { 
        return (PohjaTietue)super.clone();
    }
    
}
