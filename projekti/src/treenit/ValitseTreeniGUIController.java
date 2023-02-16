package treenit;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

    public class ValitseTreeniGUIController implements ModalControllerInterface<String> {

        @FXML
        private ChoiceBox<?> lisaaTreeniIkValitsin;

        @FXML
        void lisaaTreeniIkValitsinAvaa() {
            eiToimi();
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
        
        //=================================
        
        public void eiToimi() {
            Dialogs.showMessageDialog("Ei toimi vielä!");
        }
}