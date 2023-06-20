package gui;

import gui.Images.ImageTranslator;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Game;
import logic.Level;
import logic.Position;
import logic.Types.BotDirectionTypes;
import logic.Types.FieldTypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class SceneController implements Initializable {

    public BorderPane main;
    public GridPane gameField;
    public HBox hbox;
    public VBox vbox;
    public Button btnDeleteLevel;
    public Button btnStartGame;
    public Button btnOpenLevel;
    public GridPane program;
    public GridPane method;
    public GridPane method2;

    public VBox instructionContainer;
    public VBox instructionButtons;
    public Button btnSwitchToEditor;
    public Button btnSettings;

    /**
     * Game Instanz
     */
    private Game game;

    private FieldTypes[][] field;
    private Position botPosition;
    private BotDirectionTypes botDirection;

    private Instructions instructions;
    private Editor editor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.openSettings();
        this.instructions = new Instructions(this.program, this.method, this.method2, this.instructionButtons);
        try {
            this.initGame("level1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initGame(String json) throws FileNotFoundException {
        this.gameField.prefHeightProperty().bind(this.vbox.heightProperty());
        this.gameField.prefWidthProperty().bind(this.vbox.widthProperty());
        this.vbox.prefWidthProperty().bind(this.hbox.widthProperty());
        this.vbox.prefHeightProperty().bind(this.hbox.heightProperty());
        this.gameField.setHgap(2);
        this.gameField.setVgap(2);
        this.gameField.getChildren().clear();
        String entry = "src/logic/JSON/";
        File file = new File(entry + json + ".json");
        Level parser = new Level(file);
        this.field = parser.getField();
        this.botPosition = parser.getBotStartPosition();
        this.botDirection = parser.getBotStartDirection();
        ImageTranslator.translate(this.field, this.botDirection, this.gameField);
        this.startGame(); // Init Start Game Event Handler
        this.loadLevel(); // Init load Level Event Handler
        this.editor = new Editor(this.instructionButtons, this.gameField, this.field);
        this.switchModus();
        this.btnDeleteLevel.setDisable(true);
    }

    private void startGame() {
        EventHandler<MouseEvent> handler = event -> {
            this.gameField.getChildren().clear();
            ImageTranslator.translate(this.field, this.botDirection, this.gameField);
            this.game = new Game(
                    this.field,
                    new Position(this.botPosition.getRowIndex(), this.botPosition.getColumnIndex()),
                    this.botDirection,
                    this.instructions.getProgramInstructions(),
                    this.instructions.getMethodInstructions(),
                    this.instructions.getMethod2Instructions(),
                    new JavaFXGUI(this.gameField, this.field, this.botPosition, this.botDirection)
            );
            try {
                this.game.runInstructionsGUI();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Children SIZE: " + this.gameField.getChildren().size());
        };
        this.btnStartGame.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }

    private void loadLevel() {
        EventHandler<MouseEvent> handler = mouseEvent -> {
            File currDir = null;
            try {
                currDir = new File(SceneController.class.getProtectionDomain()
                        .getCodeSource().getLocation().toURI());
            } catch (URISyntaxException ignored) {
                System.out.println("JANOS MADE A MISTAKE");
            }
            FileChooser fileChooser = new FileChooser();
            if (currDir != null) {
                fileChooser.setInitialDirectory(currDir.getParentFile());
            }
            fileChooser.setTitle("Open JSON Graph-File");
            File selectedFile = fileChooser.showOpenDialog(this.main.getScene().getWindow());
            try {
                this.gameField.getChildren().clear();
                Level parser = new Level(selectedFile);
                this.field = parser.getField();
                this.botPosition = parser.getBotStartPosition();
                this.botDirection = parser.getBotStartDirection();
                ImageTranslator.translate(this.field, this.botDirection, this.gameField);
            } catch (Exception e) {
                System.out.println("Auswahl eines Levels" +
                        " abgebrochen oder keine gültige Level Datei");
            }
        };
        btnOpenLevel.addEventHandler(MouseEvent.MOUSE_CLICKED,handler);
    }

    private void switchModus() {
        EventHandler<MouseEvent> eventHandler = event -> {
            String game = "Zum Spiel wechseln";
            String editor = "Zum Editor wechseln";
            this.instructionButtons.getChildren().clear();
            if (Objects.equals(btnSwitchToEditor.getText(), editor)) {
                btnSwitchToEditor.setText(game);
                instructionContainer.setDisable(true);
                btnStartGame.setDisable(true);
                this.editor.initEditorButtons();
                this.editor.addEventHandler();
            } else {
                btnSwitchToEditor.setText(editor);
                instructionContainer.setDisable(false);
                btnStartGame.setDisable(false);
                this.instructions.initInstructionButtons();
                this.editor.removeEventHandler();
            }
        };
        this.btnSwitchToEditor.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    /**
     * Opens the Settins Menu
     */
    private void openSettings() {
        EventHandler<MouseEvent> settings = event -> {
                Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(SceneController.class.getResource("Settings.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Settings");
                stage.setMinWidth(1280);
                stage.setMinHeight(450);
                stage.setScene(new Scene(root, 1280, 450));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        this.btnSettings.addEventHandler(MouseEvent.MOUSE_CLICKED, settings);
    }

}
