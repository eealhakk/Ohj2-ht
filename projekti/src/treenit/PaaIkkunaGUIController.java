package treenit;

//import java.awt.Menu; <- ei toimi allaolevan "import javafx.scene.control.Menu;" kanssa
import java.awt.event.ActionEvent;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


//uusia
import fi.jyu.mit.fxgui.ListChooser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import treenipaivakirja.Paiva;
import treenipaivakirja.Treenipaivakirja;
import treenipaivakirja.Tulos;
import treenipaivakirja.SailoException;


/**
 * Treenipäiväkirjan pääikkunan muodostaja.
 * @author Eeli ja Antti
 * @version 18.2.2023
 *
 */
public class PaaIkkunaGUIController implements ModalControllerInterface<String>, Initializable  {

    @FXML private TextField HakuPalkki;
    //@FXML private Label PaaIkApua;
    @FXML
    private ComboBoxChooser<?> PaaIkDropp;
    @FXML private ListChooser<?> PaaIkKgTaul;
    @FXML private ListChooser<?> PaaIkLiikeTaul;
    //@FXML private Label PaaIkMuokkaa;
    @FXML private Button PaaIkSulje;
    @FXML private Button PaaIkTallenna;
    //@FXML private Label PaaIkTiedosto;
    @FXML private ListChooser<?> PaaIkToistotTaul;
    @FXML private ListChooser<Paiva> PaaIkTreeniJaPaivaTaul;
    @FXML private Button PaaIkUusiLiike;
    @FXML private Button PaaIkUusiTreeni;
    
    //uusia
    @FXML private Menu PaaIkApua;
    @FXML private MenuBar PaaIkMenubar;
    @FXML private Menu PaaIkMuokkaa;
    @FXML private Menu PaaIkTiedosto;
    

    @FXML private ScrollPane PaaIkScrollPane;
    

    @FXML void avaaUusiLiike() {
        //Toimii: eiToimi();
        //Ei Toimi: 
        uusiTulos();
        ModalController.showModal(PaaIkkunaGUIController.class.getResource("UusiLiikeGUIView.fxml"), "UusiLiike", null, "");
    }
    
    @FXML void avaaUusiTreeni() {
        uusiPaiva();
        //ModalController.showModal(PaaIkkunaGUIController.class.getResource("ValitseTreeniGUIView.fxml"), "UusiTreeni", null, "");
        }

    @FXML void tallentamattomatMuutokset() {
        ModalController.showModal(PaaIkkunaGUIController.class.getResource("TallentamatonMuutosGUIView.fxml"), "TallentamatonMuutos", null, "");
    }
    
    /*
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }
     */
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        alusta();
        
    }
    
    //@FXML private void avaaTiedosto() {eiToimi();}
    
    //@FXML private void avaaMuokkaa() {eiToimi();}

    @FXML private void avaaSulje() { tallentamattomatMuutokset();}

    @FXML private void avaaTallenna() {eiToimi();}



    //@FXML private void avaaApua() {eiToimi();}

    //@FXML private void avaaAlasveto() {
        
    

//======================================================
    
    private Treenipaivakirja treenipaivakirja;
    //toString(System.currentTimeMillis());//
    //TODO: vaihda tyyppi int ja metodit vastaanottamaan int mitkä käyttää tätät arvoa
    private String treeninTunnusVuosi = "2023";  
    private TextArea areaPaiva = new TextArea();
    //TODO: Tämä jossainkohtaa lokaaliksi muuttujiski aliohjelmiin
    private Paiva paivaKohdalla;
    
    /**
     * Näyttää vikaviestin
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
    
    
    
    
    /**
     * Alustaa myöhemmin Liike, Toistot ja Kg ikkunat??
     * Otettu vasta pohjaksi
     */
    protected void alusta() {
        PaaIkScrollPane.setContent(areaPaiva);
        areaPaiva.setFont(new Font("Courier New", 12));
        PaaIkScrollPane.setFitToHeight(true);
        
        PaaIkTreeniJaPaivaTaul.clear();
        PaaIkTreeniJaPaivaTaul.addSelectionListener(e -> naytaPaiva());
    }


    /**
     * Näyttää listasta valitun paiva (jäsenen) tiedot, tilapäisesti yhteen isoon edit-kenttään
     * Kun klikkaa hiirellä niin kutsuu aikanaan tätä.
     */
    protected void naytaPaiva() {
            paivaKohdalla = PaaIkTreeniJaPaivaTaul.getSelectedObject();

            if (paivaKohdalla == null) return;

            areaPaiva.setText("");
            try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaPaiva)) {
                tulosta(os, paivaKohdalla);//Oli alunperin paivakohdalla.tulsota(os);
            }
    }
    
    /**
     * Tulostaa paivan tiedot
     * @param os tietovirta johon tulostetaan
     * @param paiva tulostettava jäsen
     */
    public void tulosta(PrintStream os, final Paiva paiva) {
        os.println("----------------------------------------------");
        paiva.tulosta(os);
        os.println("----------------------------------------------");
        List<Tulos> tulokset = treenipaivakirja.annaTulokset(paiva);   
        for (Tulos har:tulokset)
            har.tulosta(os);  
    }


    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }


    /**
     * @param treenipaivakirja Asettaa treenipaivakirjan
     */
    public void setTreenipaivakirja(Treenipaivakirja treenipaivakirja) {
        this.treenipaivakirja = treenipaivakirja;
        //Vesalla vastaavassa kohdassa naytaJasen();
    }
    
    /**
     * Hakee paivien tiedot listaan
     * @param jnro paivan numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        PaaIkTreeniJaPaivaTaul.clear();

        int index = 0;
        for (int i = 0; i < treenipaivakirja.getPaivia(); i++) {
            Paiva paiva = treenipaivakirja.annaPaiva(i);
            if (paiva.getTunnusNro() == jnro) index = i;
            PaaIkTreeniJaPaivaTaul.add("" + paiva.getPvm(), paiva);
        }
        PaaIkTreeniJaPaivaTaul.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää paivan -> (jäsenen)
    }
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        /* try { */
        String uusiVuosi = AlkuNakymaGUIController.kysyVuosi(null, treeninTunnusVuosi);
        if ((uusiVuosi == null)) return false;
        lueTiedosto(uusiVuosi);
        return true;
        /*
        }catch (SailoException e) {
            Dialogs.showMessageDialog("Virheellinen vuosi: " + e.getMessage());
            return false;
        }
        */
    }
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
    }


    
    /**
     * Alustaa treenipaivakirjan lukemalla sen valitun nimisestä tiedostosta
     * @param vuosi tiedosto josta treenipaivakirjan tiedot luetaan
     */
    protected void lueTiedosto(String vuosi) {
        treeninTunnusVuosi = vuosi;
        setTitle("treenipaivakirja - " + " vuosi: " + treeninTunnusVuosi);
        String virhe = "Ei osata lukea vielä";  // TODO: tähän oikea tiedoston lukeminen
        // if (virhe != null) 
            Dialogs.showMessageDialog(virhe);
    }
    
    private void setTitle(String string) {
        ModalController.getStage(HakuPalkki).setTitle("" + string);
    }



    //: Heittää nullpointer -> korjattu
    /** 
     * Tekee uuden Paivan editointia varten 
     */ 
    private void uusiPaiva() {
        Paiva uusi = new Paiva();
        uusi.rekisteroi();
        uusi.vastaaEsimerkkiTreeni();
        try {
            treenipaivakirja.lisaa(uusi); //<-- Cannot invoke "treenipaivakirja.Treenipaivakirja.lisaa(treenipaivakirja.Paiva)" because "this.treenipaivakirja" is null
            //Ei pääse Treenipaivakirjan lisaa metodiin.
        }catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa" + e.getMessage());    
        }
        
        hae(uusi.getTunnusNro());
    }
    
    /** 
     * Tekee uuden tyhjän harrastuksen editointia varten 
     */ 
    public void uusiTulos() { 
        paivaKohdalla = PaaIkTreeniJaPaivaTaul.getSelectedObject(); //Piilotettu, muutetaan myöhemmin kaikki lokaaliksi
        if ( paivaKohdalla == null ) return;  
        Tulos tul = new Tulos();  
        tul.rekisteroi();  
        tul.vastaaTulos(paivaKohdalla.getTunnusNro());  
        treenipaivakirja.lisaa(tul);  
        hae(paivaKohdalla.getTunnusNro());          
    } 

    

}