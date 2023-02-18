package treenit;

//import java.awt.Button;
import javafx.scene.control.Button;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;



/**
 * Tallentamaton muutos-näkymän muodostaja.
 * @author antti ja eeli
 * @version 18.2.2023
 *
 */
public class TallentamatonMuutosGUIController implements ModalControllerInterface<String>  {
    @FXML private Button TallentamatonAlaTallenna;
    @FXML private Button TallentamatonTallenna;
    @FXML private Button tallentamatonPeruuta;

    @FXML private void avaaAlaTallenna() {eiToimi();}
    @FXML private void avaaPeruuta() {eiToimi();}
    @FXML private void avaaTallenna() {eiToimi();}
    
    /**
     * Näyttää vikaviestin.
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