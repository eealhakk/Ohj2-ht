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
public class UusiLiikeGUIController implements ModalControllerInterface<String> {



    @FXML private TextField uusiLiikeKg;
    @FXML private TextField uusiLiikeLiike;
    @FXML private TextField uusiLiikeMuut;
    @FXML private Button uusiLiikeSulje;
    @FXML private Button uusiLiikeTallenna;
    @FXML private TextField uusiLiikeToistot;



    @FXML private void avaaTallenna() {
//        if (tulosKohdalla != null && tulosKohdalla.getTunnusNro().trim().equals("")) {
//            naytaVirhe("Nimi ei saa olla tyhja");
//            return;
//        }
//        if (lisatietoja.getText() != null) {
//            tulosKohdalla.setCustom(lisatietoja.getText());
//        }
//        else tulosKohdalla.setCustom("Ei lisätietoja");
        
        ModalController.closeStage(uusiLiikeSulje);
               
        //ModalController.closeStage(labelVirhe);

        /*eiToimi();*/
        }
    @FXML private void avaaSulje() {handleLopeta();} //TODO: kesken
    
    @FXML private void handleLopeta() {
        tulosKohdalla = null;
        ModalController.closeStage(uusiLiikeSulje);
    }
    
    public void initialize() {
        alusta();
    }

    
//==================================================================================
    private Tulos tulosKohdalla;
    private TextField[] muokkaa;
    private TextArea lisatietoja;
    
    protected void alusta() {
        this.muokkaa = new TextField[] {uusiLiikeKg, uusiLiikeLiike, uusiLiikeToistot, uusiLiikeMuut};
        
        int i = 0;
        for (TextField edit : muokkaa) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosTulokseen(k, (TextField)(e.getSource())));
        }
        
    }
    
    
    
    private void kasitteleMuutosTulokseen(int k, TextField edit) {
        if (tulosKohdalla == null) return;
        String s = edit.getText();
        
        String virhe = null;
        switch (k) {
            case 1 : virhe = tulosKohdalla.asetaLiike(s); break;
            case 2 : virhe = tulosKohdalla.asetaSarja(s); break;
            case 3 : virhe = tulosKohdalla.asetaPaino(s); break;
            case 4 : virhe = tulosKohdalla.asetaMuut(s); break;
            default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            //naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            //naytaVirhe(virhe);
        }
    }
    
    private void naytaVirhe(String virhe) {
//        if (virhe == null || virhe.isEmpty()) {
//            labelVirhe.setText("");
//            labelVirhe.getStyleClass().removeAll("virhe");
//            return;
//        }
//        labelVirhe.setText(virhe);
//        labelVirhe.getStyleClass().add("virhe");
    }



    
    /**
     * @param muokkaa taulukko tekstikentistä
     * @param lisatietoja tekstialue lisätiedoista
     * @param tulos n
     */
    public static void naytaJasen(TextField[] muokkaa, TextArea lisatietoja, Tulos tulos) {
        if (tulos == null) return;
        muokkaa[0].setText(tulos.getLiike());
        muokkaa[1].setText(tulos.getSarja());
        muokkaa[2].setText(tulos.getPaino());
        muokkaa[3].setText(tulos.getMuut());
    }

    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä näytetään oletuksena
     * @return null, jos painetaan peruuta, muuten täytetty tieto.
     */
//    public static Tulos kysyTulos(Stage modalityStage, Tulos oletus) {
//        return ModalController.<Tulos, UusiLiikeGUIController>showModal(UusiLiikeGUIController.class.getResource("UusiLiikeGUIView.fxml"), "Uusi Tulos", modalityStage, oletus, null);
//    }

    /**
     * Näyttää vikaviestin
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
    

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    public void setDefault(Tulos arg0) {
        // TODO Auto-generated method stub
        
    }
    
    //TÄMÄ AVATTAVA JOS RETURN TYPE TULOS
    
//    @Override
//    public Tulos getResult() {
//        // TODO Auto-generated method stub
//        return null;
//    }
    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

}
