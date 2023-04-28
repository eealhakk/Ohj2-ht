package treenit;

import fi.jyu.mit.fxgui.Dialogs;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import treenipaivakirja.Paiva;
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

    public static Object kysyTiedot(Object o, Paiva uusi, int i) {
        return ModalController.showModal(
                AlkuNakymaGUIController.class.getResource("AlkuNakymaGUIView.fxml"),
                "Treenipaivakirja",
                null, uusi);

    }

    @FXML private void avaaAsetaVuosi() {
        //ModalController.showModal(AlkuNakymaGUIController.class.getResource("PaaIkkunaGUIView.fxml"), "PaaIkkuna", null, "");
        vastaus = alkuNakAnnaVuosi.getText();
        if (tarkistaVuosi(vastaus)) ModalController.closeStage(alkuNakAnnaVuosi);
    }
    
    
    private boolean tarkistaVuosi(String vastaus1) {
        if (vastaus1 == null || vastaus1.length() == 0) {
            Dialogs.showMessageDialog("Anna vuosi!");
            return false;
        }
        try {
            int vuosi = Integer.parseInt(vastaus1);
            if (vuosi < 2000 || vuosi > 2100) {
                Dialogs.showMessageDialog("Vuosi ei ole sallitulla välillä!");
                return false;
            }
        } catch (NumberFormatException e) {
            Dialogs.showMessageDialog("Anna vuosi kokonaislukuna!");
            return false;
        }
        return true;
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