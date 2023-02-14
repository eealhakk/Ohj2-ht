package treenit;

import java.awt.event.ActionEvent;

import fi.jyu.mit.fxgui.Dialogs;
/**
 * @author Eeli ja Antti
 * @version 25.1.2023
 *
 */
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController; //Ei vielä käytössä
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PaaIkkunaGUIController {

    @FXML private TextField HakuPalkki;
    @FXML private Label PaaIkApua;
    @FXML private ChoiceBox<?> PaaIkDropp;
    @FXML private ListChooser<?> PaaIkKgTaul;
    @FXML private ListChooser<?> PaaIkLiikeTaul;
    @FXML private Label PaaIkMuokkaa;
    @FXML private Button PaaIkSulje;
    @FXML private Button PaaIkTallenna;
    @FXML private Label PaaIkTiedosto;
    @FXML private ListChooser<?> PaaIkToistotTaul;
    @FXML private ListChooser<?> PaaIkTreeniJaPaivaTaul;
    @FXML private Button PaaIkUusiLiike;
    @FXML private Button PaaIkUusiTreeni;
    

    @FXML void avaaUusiLiike() {
        //Toimii: eiToimi();
        //Ei Toimi: ModalController.showModal(PaaIkkunaGUIController.class.getResource("UusiLiikeGUIView.fxml"), "UusiLiike", null, "");
        
        //Ei toimi. ongelmia kohdan -- Parent root1 = (Parent) fxmlLoader.load(); -- kanssa.
        //Caused by: java.lang.ClassCastException: class treenit.UusiLiikeGUIController cannot be cast to class fi.jyu.mit.fxgui.ModalControllerInterface (treenit.UusiLiikeGUIController and fi.jyu.mit.fxgui.ModalControllerInterface are in unnamed module of loader 'app')
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PaaIkkunaGUIController.class.getResource("treenit.UusiLiikeGUIView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
            } catch (Exception e) {
                Dialogs.showMessageDialog("Ei toimi vielä!");
        }
        
    }
    

    @FXML private void avaaTiedosto() {eiToimi();}
    
    @FXML private void avaaMuokkaa() {eiToimi();}

    @FXML private void avaaSulje() {eiToimi();}

    @FXML private void avaaTallenna() {eiToimi();}

    @FXML private void avaaUusiTreeni() {eiToimi();}

    @FXML private void avaaApua() {eiToimi();}

    @FXML private void avaaAlasveto() {eiToimi();}

//======================================================
    
    /**
     * Näyttää vikaviestin
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
    

}