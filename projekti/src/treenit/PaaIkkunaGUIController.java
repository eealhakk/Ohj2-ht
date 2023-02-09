package treenit;

/**
 * @author Eeli ja Antti
 * @version 25.1.2023
 *
 */
import fi.jyu.mit.fxgui.ListChooser;
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

}