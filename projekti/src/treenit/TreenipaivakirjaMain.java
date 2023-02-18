package treenit;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Treenipäiväkirjan pääohjelma.
 * @author Eeli ja Antti
 * @version 18.2.2023
 *
 */
public class TreenipaivakirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("AlkuNakymaGUIView.fxml"));
            final Pane root = ldr.load();
            //final PaaIkkunaGUIController treenipaivakirjaCtrl = (PaaIkkunaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("treenipaivakirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Treenipaivakirja");
            primaryStage.show();
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