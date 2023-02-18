package treenit;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Treenipäiväkirjan alkunäkymän muodostaja.
 * @author antti ja eeli
 * @version 18.2.2023
 *
 */
public class AlkuNakymaGUIController  {
    @FXML private TextField alkuNakAnnaVuosi;
    
    @FXML private void avaaAsetaVuosi() {
        ModalController.showModal(AlkuNakymaGUIController.class.getResource("PaaIkkunaGUIView.fxml"), "PaaIkkuna", null, "");
      
    }
    
    /**
     * Palauttaa viestin, että kyseinen toiminto ei vielä tee mitään
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
}