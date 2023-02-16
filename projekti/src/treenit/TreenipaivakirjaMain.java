package treenit;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Eeli
 * @version 25.1.2023
 *
 */
public class TreenipaivakirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr2 = new FXMLLoader(getClass().getResource("AlkuNakymaGUIView.fxml"));
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaIkkunaGUIView.fxml"));
            final Pane root2 = ldr2.load();
            final Pane root = ldr.load();
            //final PaaIkkunaGUIController treenipaivakirjaCtrl = (PaaIkkunaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            Scene scene2 = new Scene(root2);
            scene.getStylesheets().add(getClass().getResource("treenipaivakirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setScene(scene2);
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