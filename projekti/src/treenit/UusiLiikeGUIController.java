package treenit;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 * @author antti
 * @version 9.2.2023
 *
 */


public class UusiLiikeGUIController implements ModalControllerInterface<String> {

    @FXML private TextField uusiLiikeKg;
    @FXML private TextField uusiLiikeLiike;
    @FXML private TextField uusiLiikeMuut;
    @FXML private Button uusiLiikeSulje;
    @FXML private Button uusiLiikeTallenna;
    @FXML private TextField uusiLiikeToistot;
    
    @FXML private void avaaLiike() {eiToimi();}
    @FXML private void avaaToistot() {eiToimi();}
    @FXML private void avaaKg() {eiToimi();}
    @FXML private void avaaMuut() {eiToimi();}
    @FXML private void avaaTallenna() {eiToimi();}
    @FXML private void avaaSulje() {eiToimi();}
    
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
