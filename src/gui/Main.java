package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class loads the gui to play the game
 *
 * @author Jonas, Lena
 */
public class Main extends Application {

    /**
     * Methode um Spiel zu starten
     *
     * @param primaryStage Anfangszustanf
     * @throws Exception wirft Exception bei einem Fehler
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("Scene.fxml")));
        primaryStage.setTitle("Escape Bot");
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1280);
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();
    }

    /**
     * Hauptprogramm
     */
    public static void main(String[] args) {
        launch(args);
    }
}