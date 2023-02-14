package treenit;

import fi.jyu.mit.fxgui.Dialogs;
/**
 * @author Eeli ja Antti
 * @version 25.1.2023
 *
 */
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController; //Ei vielä käytössä
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    

    @FXML private void avaaUusiLiike() {
        //eiToimi();
        ModalController.showModal(PaaIkkunaGUIController.class.getResource("treenit.UusiLiikeGUIView.fxml"), "UusiLiike", null, "");
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