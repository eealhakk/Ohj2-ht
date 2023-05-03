package treenit;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kanta.Tietue;
import org.w3c.dom.DOMStringList;
import treenipaivakirja.Paiva;
import treenipaivakirja.SailoException;
import treenipaivakirja.Tulos;
import fi.jyu.mit.ohj2.Mjonot;
/**
 * Uusi liike-näkymän moudostaja.
 * @author antti ja eeli
 * @version 18.2.2023
 *
 */
public class UusiLiikeGUIController implements ModalControllerInterface<Tulos>,Initializable {



    //@FXML private TextField uusiLiikeKg;
    //@FXML private TextField uusiLiikeLiike;
    //@FXML private TextField uusiLiikeMuut;
    //@FXML private Button uusiLiikeSulje;
    //@FXML private Button uusiLiikeTallenna;
    //@FXML private TextField uusiLiikeToistot;
    
    @FXML private GridPane gridTulosIkkuna;
    @FXML private Label labelVirhe;


//    @FXML private void avaaTallenna() {
////        if (tulosKohdalla != null && tulosKohdalla.getTunnusNro().trim().equals("")) {
////            naytaVirhe("Nimi ei saa olla tyhja");
////            return;
////        }
////        if (lisatietoja.getText() != null) {
////            tulosKohdalla.setCustom(lisatietoja.getText());
////        }
////        else tulosKohdalla.setCustom("Ei lisätietoja");
//        
//        ModalController.closeStage(uusiLiikeSulje);
//               
//        //ModalController.closeStage(labelVirhe);
//
//        /*eiToimi();*/
//        }
//    @FXML private void avaaSulje() {handleLopeta();} //TODO: kesken
//    
//    @FXML private void handleLopeta() {
//        tulosKohdalla = null;
//        ModalController.closeStage(uusiLiikeSulje);
//    }
    
    @FXML private void handleOK() {
        if ( tulosKohdalla != null && tulosKohdalla.getLiike().toString().equals("")) {
            naytaVirhe("Liike ei saa olla tyhjä");
            return;
        }
        if ( tulosKohdalla != null && tulosKohdalla.getPaino().toString().equals("")) {
            naytaVirhe("Paino ei saa olla tyhjä");
            return;
        }
        if ( tulosKohdalla != null && tulosKohdalla.getSarja().toString().equals("")) {
            naytaVirhe("Sarja ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    
    @FXML private void handleCancel() {
        tulosKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }


    
//==================================================================================
    private Tulos tulosKohdalla;
    private static Tulos aputulos = new Tulos();
    private TextField[] muokkaa;
    private TextArea lisatietoja;
    
    private int kentta = 0;
    
    
    /**
     * Luodaan GridPaneen jäsenen tiedot
     * @param gridTulos mihin tiedot luodaan
     * @return luodut tekstikentät
     */
    public static TextField[] luoKentat(GridPane gridTulos) {
        gridTulos.getChildren().clear();
        TextField[] edits = new TextField[aputulos.getKenttia()];
        
        for (int i=0, k = aputulos.ekaKentta(); k < aputulos.getKenttia(); k++, i++) {
            Label label = new Label(aputulos.getKysymys(k));
            gridTulos.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridTulos.add(edit, 1, i);
        }
        return edits;
    }
    
    /**
     * Tyhjentään tekstikentät 
     * @param edits tyhjennettävät kentät
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit: edits) 
            if ( edit != null ) edit.setText(""); 
    }
    
    /**
     * Palautetaan komponentin id:stä saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mikä arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna 
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa jäsenten tiedot.
     */
    protected void alusta() {
        muokkaa = luoKentat(gridTulosIkkuna);
        for (TextField edit : muokkaa)
            if ( edit != null )
                edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen((TextField)(e.getSource())));
        //panelJasen.setFitToHeight(true);
    }
    
    
    @Override
    public void setDefault(Tulos oletus) {
        tulosKohdalla = oletus;
        naytaTulos(muokkaa, tulosKohdalla);
    }

    
    @Override
    public Tulos getResult() {
        return tulosKohdalla;
    }
    
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
    
    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        kentta = Math.max(aputulos.ekaKentta(), Math.min(kentta, aputulos.getKenttia()-1));
        muokkaa[kentta].requestFocus();
    }
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            //labelVirhe.setText("");
            //labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        //labelVirhe.setText(virhe);
        //labelVirhe.getStyleClass().add("virhe");
    }

    
    /**
     * Käsitellään jäseneen tullut muutos
     * @param edit muuttunut kenttä
     */
    protected void kasitteleMuutosJaseneen(TextField edit) {
        if (tulosKohdalla == null) return;
        int k = getFieldId(edit,aputulos.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = tulosKohdalla.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
        
        /**
         * Näytetään jäsenen tiedot TextField komponentteihin
         * @param edits taulukko TextFieldeistä johon näytetään
         * @param tulos näytettävä jäsen
         */
        public static void naytaTulos(TextField[] edits, Tulos tulos) {
            if (tulos == null) return;
            for (int k = tulos.ekaKentta(); k < tulos.getKenttia(); k++) {
                edits[k].setText(tulos.anna(k));
            }
        }
        
        
        /**
         * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
         * TODO: korjattava toimimaan
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @param oletus mitä dataan näytetään oletuksena
         * @param kentta mikä kenttä saa fokuksen kun näytetään
         * @return null jos painetaan Cancel, muuten täytetty tietue
         */
        public static Tulos kysyTulos(Stage modalityStage, Tulos oletus, int kentta) {
            return ModalController.<Tulos, UusiLiikeGUIController>showModal(
                    UusiLiikeGUIController.class.getResource("UusiLiikeGUIView.fxml"),
                        "Uusi liike",
                        modalityStage, oletus,
                        ctrl -> ctrl.setKentta(kentta) 
                    );
        }

    

    
    //========================
    
//    /**
//     * @param modalityStage mille ollaan modaalisia
//     * @param oletus mitä näytetään oletuksena
//     * @return null, jos painetaan peruuta, muuten täytetty tieto.
//     */
//    public static Tulos kysyTulos(Stage modalityStage, Tulos oletus) {
//        return ModalController.<Tulos, UusiLiikeGUIController>showModal(UusiLiikeGUIController.class.getResource("UusiLiikeGUIView.fxml"), "Uusi Tulos", modalityStage, oletus, null);
//    }
    
    /**
     * Näyttää vikaviestin
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
    
//    protected void alusta() {
//        this.muokkaa = new TextField[] {uusiLiikeKg, uusiLiikeLiike, uusiLiikeToistot, uusiLiikeMuut};
//        
//        int i = 0;
//        for (TextField edit : muokkaa) {
//            final int k = ++i;
//            edit.setOnKeyReleased(e -> kasitteleMuutosTulokseen(k, (TextField)(e.getSource())));
//        }
//        
//    }
//    
//    
//    
//    private void kasitteleMuutosTulokseen(int k, TextField edit) {
//        if (tulosKohdalla == null) return;
//        String s = edit.getText();
//        
//        String virhe = null;
//        switch (k) {
//            case 1 : virhe = tulosKohdalla.asetaLiike(s); break;
//            case 2 : virhe = tulosKohdalla.asetaSarja(s); break;
//            case 3 : virhe = tulosKohdalla.asetaPaino(s); break;
//            case 4 : virhe = tulosKohdalla.asetaMuut(s); break;
//            default:
//        }
//        if (virhe == null) {
//            Dialogs.setToolTipText(edit, "");
//            edit.getStyleClass().removeAll("virhe");
//            //naytaVirhe(virhe);
//        } else {
//            Dialogs.setToolTipText(edit, virhe);
//            edit.getStyleClass().add("virhe");
//            //naytaVirhe(virhe);
//        }
//    }
//    
//    private void naytaVirhe(String virhe) {
////        if (virhe == null || virhe.isEmpty()) {
////            labelVirhe.setText("");
////            labelVirhe.getStyleClass().removeAll("virhe");
////            return;
////        }
////        labelVirhe.setText(virhe);
////        labelVirhe.getStyleClass().add("virhe");
//    }
//
//
//
//    
//    /**
//     * @param muokkaa taulukko tekstikentistä
//     * @param lisatietoja tekstialue lisätiedoista
//     * @param tulos n
//     */
//    public static void naytaPaiva(TextField[] muokkaa, TextArea lisatietoja, Tulos tulos) {
//        if (tulos == null) return;
//        muokkaa[0].setText(tulos.getLiike());
//        muokkaa[1].setText(tulos.getSarja());
//        muokkaa[2].setText(tulos.getPaino());
//        muokkaa[3].setText(tulos.getMuut());
//    }
//    




    

//    @Override
//    public void handleShown() {
//        // TODO Auto-generated method stub
//        
//    }
//    @Override
//    public Tulos getResult() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//    @Override
//    public void setDefault(Tulos oletus) {
//        // TODO Auto-generated method stub
//        
//    }
}
