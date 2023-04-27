package treenit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import treenipaivakirja.Treenipaivakirja;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * 
 * Treenipäiväkirjan pääohjelma.
 * @author Eeli ja Antti
 * @version 18.2.2023
 *
 */
public class TreenipaivakirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //Oli: FXMLLoader ldr = new FXMLLoader(getClass().getResource("AlkuNakymaGUIView.fxml"));
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaIkkunaGUIView.fxml"));
            final Pane root = ldr.load();          
            //Lisätty
            final PaaIkkunaGUIController treenipaivakirjaCtrl = (PaaIkkunaGUIController) ldr.getController();
            
            //lisätty final Scene...
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("treenipaivakirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Treenipaivakirja");
            
            //Lisätty x2
            primaryStage.setOnCloseRequest((event) -> {
                if ( !treenipaivakirjaCtrl.voikoSulkea() ) event.consume();
            });
                        
            //lisätty
            Treenipaivakirja treenipaivakirja = new Treenipaivakirja();
            treenipaivakirjaCtrl.setTreenipaivakirja(treenipaivakirja);

            
            primaryStage.show();
            //Lisätty
            if ( !treenipaivakirjaCtrl.avaa() ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}