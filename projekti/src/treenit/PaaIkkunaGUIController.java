package treenit;

//import java.awt.Menu; <- ei toimi allaolevan "import javafx.scene.control.Menu;" kanssa
import java.awt.event.ActionEvent;
import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
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
import javafx.scene.control.TableView;
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
import javafx.scene.input.KeyCode;
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
    //@FXML private ListChooser<?> PaaIkKgTaul;
    //@FXML private ListChooser<?> PaaIkLiikeTaul;
    //@FXML private Label PaaIkMuokkaa;
    @FXML private Button PaaIkSulje;
    @FXML private Button PaaIkTallenna;
    //@FXML private Label PaaIkTiedosto;
    //@FXML private ListChooser<?> PaaIkToistotTaul;
    @FXML private StringGrid<Tulos> PaaIKTuloksetTaul;
    @FXML private ListChooser<Paiva> PaaIkTreeniJaPaivaTaul;
    @FXML private Button PaaIkUusiLiike;
    @FXML private Button PaaIkUusiTreeni;
    
    //uusia
    @FXML private Menu PaaIkApua;
    @FXML private MenuBar PaaIkMenubar;
    @FXML private Menu PaaIkMuokkaa;
    @FXML private Menu PaaIkTiedosto;
    

    @FXML private ScrollPane PaaIkScrollPane;
    private int treeneja = 0;

    @FXML void avaaUusiLiike() {
        //Toimii: eiToimi();
        //Ei Toimi: 
        if(tarkistaTreeneja()) uusiTulos();
        ModalController.showModal(PaaIkkunaGUIController.class.getResource("UusiLiikeGUIView.fxml"), "UusiLiike", null, "");
    }
    
    @FXML void avaaUusiTreeni() {
        treeneja++;
        uusiPaiva();
        //ModalController.showModal(PaaIkkunaGUIController.class.getResource("ValitseTreeniGUIView.fxml"), "UusiTreeni", null, "");
        }

    @FXML void tallentamattomatMuutokset() {
        ModalController.showModal(PaaIkkunaGUIController.class.getResource("TallentamatonMuutosGUIView.fxml"), "TallentamatonMuutos", null, "");
    }
    
    /**
     * Tarkistaaa onko olemassa jo treeniä
     * @return true tai false 
     */
    public boolean tarkistaTreeneja() {
        if (treeneja <= 0) { Dialogs.showMessageDialog("Lisää treeni!"); return false;}
        return true;
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
    
    //Vaihe 7
    private static Tulos aputulos = new Tulos(); 
    private TextField edits[];
    
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
        
       /*   //kerhon vastaava jäsenen tiedot pääikkuna boxin alustus
       edits = UusiLiikeGUIController.luoKentat(gridJasen, new Jasen());  
       for (TextField edit: edits)  
           if ( edit != null ) {  
               edit.setEditable(false);  
               edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); });  
               edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
               edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaa(kentta);});
           }
        */
        // alustetaan harrastustaulukon otsikot
        int eka = aputulos.ekaKentta(); //aputulos.ekaKentta() return 2;
        int lkm = aputulos.getKenttia(); //aputulos.getKenttia() return 5;
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = aputulos.getKysymys(k); 
        PaaIKTuloksetTaul.initTable(headings); 
        PaaIKTuloksetTaul.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        PaaIKTuloksetTaul.setEditable(false); 
        PaaIKTuloksetTaul.setPlaceholder(new Label("Ei vielä harrastuksia")); 
         
        // Tämä on vielä huono, ei automaattisesti muutu jos kenttiä muutetaan. 
        PaaIKTuloksetTaul.setColumnSortOrderNumber(1); 
        PaaIKTuloksetTaul.setColumnSortOrderNumber(2); 
        PaaIKTuloksetTaul.setColumnWidth(1, 60); 
        PaaIKTuloksetTaul.setColumnWidth(2, 60); 
        
        PaaIKTuloksetTaul.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaTulosta(); } );
        PaaIKTuloksetTaul.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaTulosta();}); 

    }
    
    private void muokkaaTulosta() {
        int r = PaaIKTuloksetTaul.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Tulos tul = PaaIKTuloksetTaul.getObject();
        if ( tul == null ) return;
        int k = PaaIKTuloksetTaul.getColumnNr()+tul.ekaKentta();
        try {
            tul = UusiLiikeGUIController.kysyTietue(null, tul.clone(), k);
            if ( tul == null ) return;
            treenipaivakirja.korvaaTaiLisaa(tul); 
            naytaTulokset(paivaKohdalla); 
            PaaIKTuloksetTaul.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }


    /**
     * Näyttää listasta valitun paiva (jäsenen) tiedot, tilapäisesti yhteen isoon edit-kentgit tään
     * Kun klikkaa hiirellä niin kutsuu aikanaan tätä.
     */
    protected void naytaPaiva(){
            paivaKohdalla = PaaIkTreeniJaPaivaTaul.getSelectedObject();

            if (paivaKohdalla == null) {
                //areaPaiva.clear();
                return;
            }
            
            //Vaihe 7 tieltä muokattu
            areaPaiva.setText("");
            try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaPaiva)) {
                tulosta(os, paivaKohdalla);//Oli alunperin paivakohdalla.tulsota(os);
            }
            
            //PaaIKTuloksetTaul.setTeksti()
            //JasenDialogController.naytaJasen(edits, jasenKohdalla); 
            //naytaHarrastukset(jasenKohdalla);
    }
    
    private void naytaTulokset(Paiva tulos) {
        PaaIKTuloksetTaul.clear();
        if ( tulos == null ) return;
        
        try {
            List<Tulos> tulokset = treenipaivakirja.annaTulokset(tulos);
            if ( tulokset.size() == 0 ) return;
            for (Tulos tul: tulokset)
                naytaTulos(tul);
        } catch (SailoException e) {
            // naytaVirhe(e.getMessage());
        } 
    }
    
    private void naytaTulos(Tulos tul) {
        int kenttia = tul.getKenttia(); 
        String[] rivi = new String[kenttia-tul.ekaKentta()]; 
        for (int i=0, k=tul.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = tul.anna(k); 
        PaaIKTuloksetTaul.add(tul,rivi);
    }


    /*
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    */
    
    /**
     * Tulostaa paivan tiedot
     * @param os tietovirta johon tulostetaan
     * @param paiva tulostettava jäsen
     */
    public void tulosta(PrintStream os, final Paiva paiva){
        os.println("----------------------------------------------");
        paiva.tulosta(os);
        os.println("----------------------------------------------");

        try {
            List<Tulos> tulokset;
            tulokset = treenipaivakirja.annaTulokset(paiva);
            
            for (Tulos har:tulokset)
                har.tulosta(os);  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Paivien hakemisessa ongelmia! " + e.getMessage());
        }   

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
    */
    

    /**
     * Hakee Paivan tiedot listaan
     * TODO: Tähän kohtaan sijoitetaan liike, toistot, kg arvot
     * @param jnro Paivan numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        int k = PaaIkDropp.getSelectionModel().getSelectedIndex();
        String ehto = HakuPalkki.getText(); 
        if (k > 0 || ehto.length() > 0);
            //naytaVirhe(String.format("Ei osata hakea (kenttä: %d, ehto: %s)", k, ehto));
        //else
            
            //naytaVirhe(null);
        
        PaaIkTreeniJaPaivaTaul.clear();

        int index = 0;
        Collection<Paiva> paivat;
        try {
            paivat = treenipaivakirja.etsi(ehto, k);
            int i = 0;
            for (Paiva paiva:paivat) {
                if (paiva.getTunnusNro() == jnro) index = i;
                PaaIkTreeniJaPaivaTaul.add(paiva.getPvm(), paiva);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Paivan hakemisessa ongelmia! " + ex.getMessage());
            ex.printStackTrace();
        }
        PaaIkTreeniJaPaivaTaul.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
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
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            treenipaivakirja.talleta();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }


    
//    /**
//     * Alustaa treenipaivakirjan lukemalla sen valitun nimisestä tiedostosta
//     * @param vuosi tiedosto josta treenipaivakirjan tiedot luetaan
//     */
//    protected void lueTiedosto(String vuosi) {
//        treeninTunnusVuosi = vuosi;
//        setTitle("treenipaivakirja - " + " vuosi: " + treeninTunnusVuosi);
//        String virhe = "Ei osata lukea vielä";  // TODO: tähän oikea tiedoston lukeminen
//        // if (virhe != null) 
//            Dialogs.showMessageDialog(virhe);
//    }
    /**
     * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
        treeninTunnusVuosi = nimi;
        setTitle("Treenipaivakirja - " + treeninTunnusVuosi);
        try {
            treenipaivakirja.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
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
        //uusi.rekisteroi();
        uusi.vastaaEsimerkkiTreeni();
        try {
            treenipaivakirja.lisaa(uusi); //<-- Cannot invoke "treenipaivakirja.Treenipaivakirja.lisaa(treenipaivakirja.Paiva)" because "this.treenipaivakirja" is null
            //Ei pääse Treenipaivakirjan lisaa metodiin.
        }catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa" + e.getMessage());
            return;
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
        try {
            treenipaivakirja.lisaa(tul);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        }  
        hae(paivaKohdalla.getTunnusNro());          
    } 

    

}