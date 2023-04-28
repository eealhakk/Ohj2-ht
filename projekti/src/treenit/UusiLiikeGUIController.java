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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kanta.Tietue;
import org.w3c.dom.DOMStringList;
import treenipaivakirja.Paiva;
import treenipaivakirja.SailoException;
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



    @FXML private void avaaTallenna() {eiToimi();}
    @FXML private void avaaSulje() {handleLopeta();} //TODO: kesken
    
    @FXML private void handleLopeta() {
        ModalController.closeStage(uusiLiikeSulje);
    }
    
    /**
     * Näyttää vikaviestin
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
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
