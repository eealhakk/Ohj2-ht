package treenit;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import treenipaivakirja.Treenipaivakirja;

/**
 * Treenipäiväkirjan alkunäkymän muodostaja.
 * @author antti ja eeli
 * @version 18.2.2023
 *
 */
public class AlkuNakymaGUIController implements ModalControllerInterface<String>  {
    @FXML private TextField alkuNakAnnaVuosi;
    private String vastaus = null;
    
    @FXML private void avaaAsetaVuosi() {
        //ModalController.showModal(AlkuNakymaGUIController.class.getResource("PaaIkkunaGUIView.fxml"), "PaaIkkuna", null, "");
        vastaus = alkuNakAnnaVuosi.getText();
        ModalController.closeStage(alkuNakAnnaVuosi);
    }
    
    /**
     * Palauttaa viestin, että kyseinen toiminto ei vielä tee mitään
     */
    public void eiToimi() {
        Dialogs.showMessageDialog("Ei toimi vielä!");
    }
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyVuosi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                AlkuNakymaGUIController.class.getResource("AlkuNakymaGUIView.fxml"),
                "Treenipaivakirja",
                modalityStage, oletus);
    }

    @Override
    public String getResult() {
        return vastaus;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
    
//==========

    
//==========

}