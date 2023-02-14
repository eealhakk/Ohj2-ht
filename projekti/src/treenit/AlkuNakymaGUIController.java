package treenit;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author antti
 * @version 9.2.2023
 *
 */
public class AlkuNakymaGUIController {
    @FXML private TextField alkuNakAnnaVuosi;
    
    @FXML private void avaaAsetaVuosi() {eiToimi();}
    
    /**
     * Palauttaa viestin, että kyseinen toiminto ei vielä tee mitään
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
}