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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
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
    //@FXML private Label PaaIkTiedosto;
    //@FXML private ListChooser<?> PaaIkToistotTaul;
    @FXML public StringGrid<Tulos> PaaIKTuloksetTaul;
    @FXML private ListChooser<Paiva> PaaIkTreeniJaPaivaTaul;
    @FXML private Button PaaIkUusiLiike;
    @FXML private Button PaaIkUusiTreeni;
    
    //uusia
    @FXML private Menu PaaIkApua;
    @FXML private MenuBar PaaIkMenubar;
    @FXML private Menu PaaIkMuokkaa;
    @FXML private Menu PaaIkTiedosto;
    //Turhia?
    @FXML private MenuItem MenuBarMuokkaaMuokka;
    @FXML private MenuItem MenuBarMuokkaaPoista;

    @FXML void MuokkaaTapahtuma() {
        muokkaaTulosta();
    }
    
    @FXML void PoistaTapahtuma() {
        //
        poistaTulos();
    }

    /*
     * Poistetaan listalta valittu jäsen

    private void poistaTreeni() {
        Paiva paiva = paivaKohdalla;
        if ( paiva == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko päivä: " + paiva, "Kyllä", "Ei") )
            return;
        treenipaivakirja.poista(paiva);
        int index = PaaIkTreeniJaPaivaTaul.getSelectedIndex();
        hae(0);
        PaaIkTreeniJaPaivaTaul.setSelectedIndex(index);
    }
     */
    

    @FXML private ScrollPane PaaIkScrollPane;
    private static int treeneja = 0;

    @FXML void avaaUusiLiike() {
        uusiTulos();
        ModalController.showModal(PaaIkkunaGUIController.class.getResource("UusiLiikeGUIView.fxml"), "UusiLiike", null, "");
    }
    
    @FXML private void handleLopeta() {
        ModalController.closeStage(PaaIkSulje);
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
    public static boolean tarkistaTreeneja() {
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

    @FXML private void avaaTallenna() {
        tallenna();
    }
    
    @FXML void handleHakuehto() {
        hae(0);
    }



    //@FXML private void avaaApua() {eiToimi();}

    //@FXML private void avaaAlasveto() {
        
    

//======================================================
    
    private Treenipaivakirja treenipaivakirja;
    //toString(System.currentTimeMillis());//
    private String treeninTunnusVuosi = "2023";
    private TextArea areaPaiva = new TextArea();
    private Paiva paivaKohdalla;
    //Vaihe 7
    private Tulos tulosKohdalla;

    //Hakua varten vaan?
    private static Paiva apupaiva = new Paiva(); 
    //Vaihe 7
    private static Tulos aputulos = new Tulos(); 
    private TextField edits[];
    
    
    /**
     * Näyttää vikaviestin
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
    
//    public static void vie(String[] rivi) {
//        Tulos tulos = new Tulos();
//
//    }
    
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
        //PaaIKTuloksetTaul.addImageListener(e -> naytaPaiva());

        PaaIkDropp.clear(); 
        for (int k = apupaiva.ekaKentta(); k < apupaiva.getKenttia(); k++) 
            PaaIkDropp.add(apupaiva.getKysymys(k), null); 
        PaaIkDropp.getSelectionModel().select(0); 

        
       /*   //kerhon vastaava jäsenen tiedot pääikkuna boxin alustus
       edits = UusiLiikeGUIController.luoKentat(gridPaiva, new Paiva());  
       for (TextField edit: edits)  
           if ( edit != null ) {  
               edit.setEditable(false);  
               edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); });  
               edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
               edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaa(kentta);});
           }
        */
        // alustetaan tulostaulukon otsikot:
        int eka = aputulos.ekaKentta(); //aputulos.ekaKentta() return 2;
        int lkm = aputulos.getKenttia(); //aputulos.getKenttia() return 5;
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = aputulos.getKysymys(k); 
        PaaIKTuloksetTaul.initTable(headings); 
        PaaIKTuloksetTaul.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        PaaIKTuloksetTaul.setEditable(false); 
        PaaIKTuloksetTaul.setPlaceholder(new Label("Ei vielä tuloksia")); 
         
        // kentän tekstit, lajitteluehdot ja muokkaus sekä double click kuuntelu 
        //PaaIKTuloksetTaul.setPlaceholder(new Label("Ei vielä tuloksia")); ??? tyo7 kohdalla poistettu
       
        //StringGrid sisällön määrittelijät
        PaaIKTuloksetTaul.setOnCellString( (g, tulos, defValue, r, c) -> tulos.anna(c+tulos.ekaKentta()) );
        //PaaIKTuloksetTaul.setOnCellValue( (g, tulos, defValue, r, c) -> tulos.getAvain(c+tulos.ekaKentta()) );
        
        PaaIKTuloksetTaul.setOnGridLiveEdit((g, tulos, defValue, r, c, edit) -> {
            String virhe = tulos.aseta(c+tulos.ekaKentta(), defValue);
            if ( virhe == null ) { 
                edit.setStyle(null);
                //Onko muutettu
                //treenipaivakirja.setTulosMuutos();
                Dialogs.setToolTipText(edit,""); 
            } else { 
                edit.setStyle("-fx-background-color: red"); 
                Dialogs.setToolTipText(edit,virhe); 
            }
            return defValue; 
        });
        //Kuuntelee halutaanko ikkunaa(PaaIKTuloksetTaul) muokata
        PaaIKTuloksetTaul.setOnMouseClicked( e -> { if ( e.getClickCount() == 2 )  muokkaaTulosta();  } ); 
        
        Platform.runLater(()-> HakuPalkki.requestFocus()); 


    }
    //Muokkaa tulosta ylipäänsä vai vain dialog boxia

    //vaihe7
    private void naytaTulokset(Paiva paiva) {
        //Vanha
        PaaIKTuloksetTaul.clear();
        if ( paiva == null ) return;
        
        try {
            List<Tulos> tulokset = treenipaivakirja.annaTulokset(paiva);
            if ( tulokset.size() == 0 ) return;
            //PaaIKTuloksetTaul.add(tulokset); 
            for (Tulos tul: tulokset)
                naytaTulos(tul);

        } catch (SailoException e) {
            //naytaVirhe(e.getMessage());
        }   
    }
    
    private void naytaTulos(Tulos tul) {
        int kenttia = tul.getKenttia(); 
        String[] rivi = new String[kenttia-tul.ekaKentta()]; 
        for (int i=0, k=tul.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = tul.anna(k); 
        PaaIKTuloksetTaul.add(tul, rivi);
    }

    
    /**
     * Tekee uuden tyhjän tuloksen editointia varten
     *
     */
    public void uusiTulos() {
        paivaKohdalla = PaaIkTreeniJaPaivaTaul.getSelectedObject(); //Piilotettu, muutetaan myöhemmin kaikki lokaaliksi
        //Tyo 7
        //tulosKohdalla = PaaIKTuloksetTaul.getObject();  //Oikee kohta?
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
    
    /**
     * Tekee uuden tyhjän tuloksen editointia varten
     * @param liike liike
     * @param sarja sarja
     * @param paino paino
     * @param muut muut tiedot
     */
    public void uusiTulos(String liike, String sarja, String paino, String muut) {
        paivaKohdalla = PaaIkTreeniJaPaivaTaul.getSelectedObject(); //Piilotettu, muutetaan myöhemmin kaikki lokaaliksi
        //Tyo 7
        tulosKohdalla = PaaIKTuloksetTaul.getObject();  //Oikee kohta?
        
        if ( paivaKohdalla == null ) return;  
        Tulos tul = new Tulos();  
        
        
        tul.rekisteroi();
        tul.asetaArvot(liike, sarja, paino, muut, paivaKohdalla.getTunnusNro());  
        try {
            treenipaivakirja.lisaa(tul);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        }  
        hae(paivaKohdalla.getTunnusNro());          
    } 
    
    public void kysyTulos() {
//    //TODO: Tähän kohtaan kysytään vuosi ja luetaan se
//        String[] uusiVuosi = UusiLiikeGUIController.vie(null, treeninTunnusVuosi);
//        if ((uusiVuosi == null)) return;
    }


        /*
        if ( paivaKohdalla == null ) return;
        try {
            Tulos uusi = new Tulos(paivaKohdalla.getTunnusNro());
            uusi = TietueDialogController.kysyTietue(null, uusi, 0, "Uusi tulos");
            if ( uusi == null ) return;
            uusi.rekisteroi();
            treenipaivakirja.lisaa(uusi);
            naytaTulokset(paivaKohdalla); 
            PaaIKTuloksetTaul.selectRow(1000);  // järjestetään viimeinen rivi valituksi 
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Lisääminen epäonnistui: " + e.getMessage());
        }
        */
        
    
     
    private void muokkaaTulosta() {
        //Tulos uusiTul;
        //tulosKohdalla = PaaIKTuloksetTaul.getObject();  //Oikee kohta?
        //if ( tulosKohdalla == null ) return;
        //ModalController.showModal(PaaIkkunaGUIController.class.getResource("UusiLiikeGUIView.fxml"), "UusiLiike", null, "");
        
        int r = PaaIKTuloksetTaul.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Tulos tul = PaaIKTuloksetTaul.getObject();
        if ( tul == null ) return;
        //int k = PaaIKTuloksetTaul.getColumnNr()+tul.ekaKentta();
        try {
            //tul = TietueDialogController.kysyTietue(null, tul.clone(), k, "Tulos");
            //TODO: Toimii ehkä. Vasta kokeilu versio
             tul = treenipaivakirja.annaTulos(paivaKohdalla.getTunnusNro(),tulosKohdalla.getTunnusNro());
            
            if ( tul == null ) return;
            treenipaivakirja.korvaaTaiLisaa(tul); 
            naytaTulokset(paivaKohdalla); 
            PaaIKTuloksetTaul.selectRow(r);  // järjestetään sama rivi takaisin valituksi
            
        //} catch (CloneNotSupportedException  e) { /* clone on tehty */ 
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }

    /**
     * Poistetaan harrastustaulukosta valitulla kohdalla oleva harrastus. 
     */
    private void poistaTulos() {
        //Tulos kuuntelija
        tulosKohdalla = PaaIKTuloksetTaul.getObject();  //Oikee kohta?

        int rivi = PaaIKTuloksetTaul.getRowNr();
        if ( rivi < 0 ) return;
        Tulos tulos = tulosKohdalla;
        if ( tulos == null ) return;
        try {
            treenipaivakirja.poistaTulos(tulos);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        }  
        naytaTulokset(paivaKohdalla);
        int harrastuksia = PaaIKTuloksetTaul.getItems().size(); 
        if ( rivi >= harrastuksia ) rivi = harrastuksia -1;
        PaaIKTuloksetTaul.getFocusModel().focus(rivi);
        PaaIKTuloksetTaul.getSelectionModel().select(rivi);
    }

    


    /**
     * Näyttää listasta valitun paiva (jäsenen) tiedot, tilapäisesti yhteen isoon edit-kentgit tään
     * Kun klikkaa hiirellä niin kutsuu aikanaan tätä.
     */
    protected void naytaPaiva(){
            paivaKohdalla = PaaIkTreeniJaPaivaTaul.getSelectedObject();

            if (paivaKohdalla == null) return;

            
            //Vaihe 7 tieltä muokattu
            areaPaiva.setText("");
            try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaPaiva)) {
                tulosta(os, paivaKohdalla);//Oli alunperin paivakohdalla.tulsota(os);
            }
            
            naytaTulokset(paivaKohdalla);
            //PaaIKTuloksetTaul.setTeksti();
            //PaivaDialogController.naytaPaiva(edits, paivaKohdalla); 
            //naytaHarrastukset(paivaKohdalla);
    }


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

    /**
     * @param treenipaivakirja Asettaa treenipaivakirjan
     */
    public void setTreenipaivakirja(Treenipaivakirja treenipaivakirja) {
        this.treenipaivakirja = treenipaivakirja;
        //Vesalla vastaavassa kohdassa naytaPaiva();
    }
    

    /**
     * Hakee Paivan tiedot listaan
     * TODO: Tähän kohtaan sijoitetaan liike, toistot, kg arvot
     * @param jnr Paivan numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnr) {
        int jnro = jnr; // jnro paivan numero, joka aktivoidaan haun jälkeen 
        if ( jnro <= 0 ) { 
            Paiva kohdalla = paivaKohdalla; 
            if ( kohdalla != null ) jnro = kohdalla.getTunnusNro(); 
        }

        
        int k = PaaIkDropp.getSelectionModel().getSelectedIndex() + apupaiva.ekaKentta(); //Lisätty viimesin + -> tyo7
        String ehto = HakuPalkki.getText(); 
        //if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
        //if (k > 0 || ehto.length() > 0)
            //naytaVirhe(String.format("Ei osata hakea (kenttä: %d, ehto: %s)", k, ehto));

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

    public void kysyVuosi() {
    //TODO: Tähän kohtaan kysytään vuosi ja luetaan se
        String uusiVuosi = AlkuNakymaGUIController.kysyVuosi(null, treeninTunnusVuosi);
        if ((uusiVuosi == null)) return;
        lueTiedosto(uusiVuosi);
    }
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        //tallenna();
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



    /**
     * Luo uuden jäsenen jota aletaan editoimaan
     */
    protected void uusiPaiva() {
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
        //MERGE selvitys 
//        try {
//            Paiva uusi = new Paiva();
//            uusi = (Paiva) AlkuNakymaGUIController.kysyTiedot(null, uusi, 0);
//            if ( uusi == null ) return;
//            uusi.rekisteroi();
//            treenipaivakirja.lisaa(uusi);
//            hae(uusi.getTunnusNro());
//        } catch (SailoException e) {
//            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
//            return;
//        }
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
}