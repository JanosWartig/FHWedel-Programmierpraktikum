package gui;

import gui.Images.CustomPane;
import gui.Images.ImageGetter;
import gui.Images.ImageTranslator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.AnimationHelper;
import logic.GuiConnector;
import logic.Position;
import logic.Types.BotDirectionTypes;
import logic.Types.FieldTypes;
import logic.Types.InstructionTypes;

import java.io.FileNotFoundException;
import java.util.List;

public class JavaFXGUI implements GuiConnector {

    private final GridPane gameField;
    private Node[][] store;
    private final FieldTypes[][] field;
    private Position botPosition;
    private final BotDirectionTypes botDirection;
    private double cellHeight;
    private double cellWidth;

    public JavaFXGUI(
            GridPane gameField,
            FieldTypes[][] field,
            Position botPosition,
            BotDirectionTypes botDirection
    ) {
       this.gameField = gameField;
       this.store = this.storeGameField();
       this.field = field;
       this.botPosition = botPosition;
       this.botDirection = botDirection;
       this.cellHeight = this.gameField.getHeight() / this.gameField.getRowCount();
       this.cellWidth = this.gameField.getWidth() / this.gameField.getColumnCount();
    }

    private Node[][] storeGameField() {
        Node[][] store = new Node[8][8];
        store[0] = this.gameField.getChildren().subList(0, 8).toArray(new Node[0]);
        store[1] = this.gameField.getChildren().subList(8, 16).toArray(new Node[0]);
        store[2] = this.gameField.getChildren().subList(16, 24).toArray(new Node[0]);
        store[3] = this.gameField.getChildren().subList(24, 32).toArray(new Node[0]);
        store[4] = this.gameField.getChildren().subList(32, 40).toArray(new Node[0]);
        store[5] = this.gameField.getChildren().subList(40, 48).toArray(new Node[0]);
        store[6] = this.gameField.getChildren().subList(48, 56).toArray(new Node[0]);
        store[7] = this.gameField.getChildren().subList(56, 64).toArray(new Node[0]);
        return store;
    }

    private TranslateTransition walk(
            CustomPane<BotDirectionTypes, BotDirectionTypes> customPane,
            AnimationHelper element) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), customPane);
        switch (element.getBotDirection()) {
            case NORTH:
                transition.setToY(this.cellHeight);
                this.cellHeight = this.cellHeight - this.gameField.getHeight() / this.gameField.getRowCount();
                break;
            case EAST:
                transition.setToX(this.cellWidth);
                this.cellWidth = this.cellWidth + this.gameField.getWidth() / this.gameField.getColumnCount();
                break;
            case SOUTH:
                transition.setToY(this.cellHeight);
                this.cellHeight = this.cellHeight + this.gameField.getHeight() / this.gameField.getRowCount();
                break;
            case WEST:
                transition.setToX(this.cellWidth);
                this.cellWidth = this.cellWidth - this.gameField.getWidth() / this.gameField.getColumnCount();
                break;
        }
        return transition;
    }

    private TranslateTransition left(
            CustomPane<BotDirectionTypes, BotDirectionTypes> customPane,
            AnimationHelper element) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), customPane);
        return transition;
    }

    private TranslateTransition right(
            CustomPane<BotDirectionTypes, BotDirectionTypes> customPane,
            AnimationHelper element) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), customPane);
        return transition;
    }

    private TranslateTransition jump(
            CustomPane<BotDirectionTypes, BotDirectionTypes> customPane,
            AnimationHelper element) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(1000), customPane);
        return transition;
    }

    @Override
    public void animate(List<AnimationHelper> list) {
        int listSize = list.size();
        // Replace the Pane on the old Position of the Bot with a Normal Field
        this.gameField.getChildren().
                remove(this.store[list.get(0).getOldPos().getRowIndex()][list.get(0).getOldPos().getColumnIndex()]);
        CustomPane<FieldTypes, FieldTypes> pane = ImageGetter.getFieldImageResizable(FieldTypes.NORMAL);
        this.gameField.
                add(pane, list.get(0).getOldPos().getColumnIndex(), list.get(0).getOldPos().getRowIndex());
        this.store[list.get(0).getOldPos().getRowIndex()][list.get(0).getOldPos().getColumnIndex()] = pane;
        // Create Sequential Transition
        SequentialTransition sequentialTransition = new SequentialTransition();
        CustomPane<BotDirectionTypes, BotDirectionTypes> customPane =
                ImageGetter.getBotDirectionImageResizable(list.get(0).getBotDirection());
        this.gameField.add(customPane,list.get(0).getOldPos().getColumnIndex(),list.get(0).getOldPos().getRowIndex());
        // Collect all Animations
        for (int i = 0; i < listSize; i++) {
            switch (list.get(i).getInstruction()) {
                case WALK:
                    sequentialTransition.getChildren().add(this.walk(customPane,list.get(i)));
                    break;
                case LEFT:
                    sequentialTransition.getChildren().add(this.left(customPane,list.get(i)));
                    break;
                case RIGHT:
                    sequentialTransition.getChildren().add(this.right(customPane,list.get(i)));
                    break;
                case JUMP:
                    sequentialTransition.getChildren().add(this.jump(customPane,list.get(i)));
                    break;
            }
        }
        sequentialTransition.setOnFinished(event -> {
            // Fires when all Animations are done
            this.gameField.getChildren().remove(customPane);
            // Replace the Pane on the new Position of the Bot with a Bot Direction Field
            this.gameField.getChildren().
                    remove(this.store[list.get(listSize - 1).getNewPos().
                            getRowIndex()][list.get(listSize - 1).getNewPos().getColumnIndex()]);
            CustomPane<BotDirectionTypes, BotDirectionTypes> pane2 =
                    ImageGetter.getBotDirectionImageResizable(list.get(listSize - 1).getBotDirection());
            this.gameField.
                    add(pane2, list.get(listSize - 1).getNewPos().
                            getColumnIndex(), list.get(listSize - 1).getNewPos().getRowIndex());
            this.store[list.get(listSize - 1).getNewPos().getRowIndex()][list.get(listSize - 1).
                    getNewPos().getColumnIndex()] = pane2;
        });
        sequentialTransition.play();
    }

    @Override
    public void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Somewhere");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(error);
        alert.showAndWait();
        this.gameField.getChildren().clear();
        ImageTranslator.translate(this.field, this.botDirection, this.gameField);
        this.store = this.storeGameField();
    }

    @Override
    public void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Level geschafft!");
        alert.setHeaderText("Sehr sehr gut :)");
        alert.setContentText("Sehr nice => Level geschafft !!!!");
        alert.showAndWait();
        this.gameField.getChildren().clear();
        ImageTranslator.translate(this.field, this.botDirection, this.gameField);
        this.store = this.storeGameField();
    }

}
