package gui;

import gui.Images.CustomPane;
import gui.Images.ImageGetter;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.Types.FieldTypes;

import java.util.ArrayList;
import java.util.List;

public class Editor {

    private CustomPane<FieldTypes, FieldTypes> selectedButton;
    private final VBox instructionButtons;
    private final GridPane gameField;
    private FieldTypes[][] field;

    public Editor(VBox instructionButtons, GridPane gameField, FieldTypes[][] field) {
        this.instructionButtons = instructionButtons;
        this.gameField = gameField;
        this.field = field;
        this.addEventHandler();
    }

    public void initEditorButtons() {
        List<CustomPane<FieldTypes, FieldTypes>> buttons = new ArrayList<>();
        buttons.add(ImageGetter.getFieldImage(FieldTypes.CHASM));
        buttons.add(ImageGetter.getFieldImage(FieldTypes.COIN));
        buttons.add(ImageGetter.getFieldImage(FieldTypes.DOOR));
        buttons.add(ImageGetter.getFieldImage(FieldTypes.NORMAL));
        buttons.add(ImageGetter.getFieldImage(FieldTypes.START));
        buttons.add(ImageGetter.getFieldImage(FieldTypes.WALL));
        this.instructionButtons.getChildren().addAll(buttons);
        this.instructionButtons.getChildren()
                .forEach(element -> element.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandlerEditorButtons()));
    }

    private EventHandler<MouseEvent> eventHandlerEditorButtons() {
        return mouseEvent -> {
            CustomPane<FieldTypes, FieldTypes> pane = (CustomPane) mouseEvent.getSource();
            pane.setEffect(new InnerShadow(10.0, Color.RED));
            if (this.selectedButton != null) {
                this.selectedButton.setEffect(null);
            }
            this.selectedButton = pane;
        };
    }

    private EventHandler<MouseEvent> eventHandlerGameFieldButtons() {
        return event -> {
            if (this.selectedButton != null) {
                Object source = event.getSource();
                int rowIndex = GridPane.getRowIndex((Node) source);
                int columnIndex = GridPane.getColumnIndex((Node) source);
                this.gameField.getChildren().remove(source);
                CustomPane<FieldTypes, FieldTypes> pane = ImageGetter.getFieldImageResizable(this.selectedButton.getName());
                this.gameField.add(pane, columnIndex, rowIndex);
                this.setField(pane.getName(), rowIndex, columnIndex);
            }
        };
    }

    public void addEventHandler() {
        this.gameField.getChildren().
                forEach(element -> element.addEventHandler(MouseEvent.MOUSE_CLICKED, this.eventHandlerGameFieldButtons()));
    }

    public void removeEventHandler() {
        this.gameField.getChildren().forEach(element -> element.removeEventHandler(MouseEvent.MOUSE_CLICKED, this.eventHandlerGameFieldButtons()));
    }

    private void setField(FieldTypes name, int rowIndex, int columnIndex) {
        this.field[rowIndex][columnIndex] = name;
    }
}
