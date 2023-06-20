package gui;

import gui.Images.CustomPane;
import gui.Images.ImageGetter;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.Position;
import logic.Types.FieldTypes;
import logic.Types.InstructionTypes;

import java.util.ArrayList;
import java.util.List;

public class Instructions {

    private final VBox instructionButtons;

    private final List<Instruction> programInstructions;
    private final List<Instruction> methodInstructions;
    private final List<Instruction> method2Instructions;

    private CustomPane<InstructionTypes, InstructionTypes> selectedButton;

    public Instructions(GridPane program, GridPane method, GridPane method2, VBox instructionButtons) {
        this.instructionButtons = instructionButtons;
        this.initInstructionGrids(program,12);
        this.initInstructionGrids(method,8);
        this.initInstructionGrids(method2,8);
        this.initInstructionButtons();
        program.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, addInstruction(program));
        method.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, addInstruction(method));
        method2.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, addInstruction(method2));
        this.programInstructions = new ArrayList<>();
        this.methodInstructions = new ArrayList<>();
        this.method2Instructions = new ArrayList<>();
    }

    public void initInstructionButtons() {
        List<CustomPane<InstructionTypes, InstructionTypes>> buttons = new ArrayList<>();
        buttons.add(ImageGetter.getInstructionImage(InstructionTypes.WALK));
        buttons.add(ImageGetter.getInstructionImage(InstructionTypes.LEFT));
        buttons.add(ImageGetter.getInstructionImage(InstructionTypes.RIGHT));
        buttons.add(ImageGetter.getInstructionImage(InstructionTypes.JUMP));
        buttons.add(ImageGetter.getInstructionImage(InstructionTypes.METHOD));
        buttons.add(ImageGetter.getInstructionImage(InstructionTypes.METHOD2));
        buttons.add(ImageGetter.getInstructionImage(InstructionTypes.EXIT));
        this.instructionButtons.getChildren().addAll(buttons);
        this.instructionButtons.getChildren().
                forEach(element -> element.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerInstructionButtons()));
    }

    private EventHandler<MouseEvent> handlerInstructionButtons() {
        return event -> {
            CustomPane<InstructionTypes, InstructionTypes> pane = (CustomPane) event.getSource();
            pane.setEffect(new InnerShadow(10.0, Color.RED));
            if (this.selectedButton != null) {
                this.selectedButton.setEffect(null);
            }
            this.selectedButton = pane;
        };
    }

    private Pane[] generatePanes(int count) {
        Pane[] panes = new Pane[count];
        for (int i = 0; i < count; i++) {
            Pane pane = new Pane();
            pane.setPrefSize(60,60);
            pane.setMaxSize(60,60);
            pane.setStyle("-fx-border-style: solid;");
            panes[i] = pane;
        }
        return panes;
    }

    private void initInstructionGrids(GridPane grid, int count) {
        Pane[] panes = this.generatePanes(count);
        int rowIndex = 0;
        int columnIndex = 0;
        for (int i = 0; i < panes.length; i++) {
            grid.add(panes[i], columnIndex, rowIndex);
            columnIndex++;
            if(columnIndex == 4) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }

    private Instruction helperGUI(Event event, GridPane instructionGrid, int rowIndex, int columnIndex) {
        instructionGrid.getChildren().remove((Node) event.getTarget());
        CustomPane<InstructionTypes, InstructionTypes> pane = ImageGetter.getInstructionImage(
                this.selectedButton.getName()
        );
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, updateInstruction(instructionGrid));
        instructionGrid.add(pane, columnIndex, rowIndex);
        instructionGrid.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, addInstruction(instructionGrid));
        return new Instruction(pane.getName(), new Position(rowIndex,columnIndex));
    }

    private EventHandler<MouseEvent> addInstruction(GridPane instructionGrid) {
        return event -> {
            if (this.selectedButton != null && (MouseButton.PRIMARY == event.getButton())) {
                Integer rowIndex = GridPane.getRowIndex((Node) event.getSource());
                Integer columnIndex = GridPane.getColumnIndex((Node) event.getSource());
                switch (instructionGrid.getId()) {
                    case "program":
                        this.programInstructions.add(this.helperGUI(event, instructionGrid, rowIndex, columnIndex));
                        break;
                    case "method":
                        this.methodInstructions.add(this.helperGUI(event, instructionGrid, rowIndex, columnIndex));
                        break;
                    case "method2":
                        this.method2Instructions.add(this.helperGUI(event, instructionGrid, rowIndex, columnIndex));
                        break;
                }
            }
        };
    }

    private EventHandler<MouseEvent> updateInstruction(GridPane instructionGrid) {
        return event -> {
            if (this.selectedButton != null && (MouseButton.PRIMARY == event.getButton())) {
                Integer rowIndex = GridPane.getRowIndex((Node) event.getSource());
                Integer columnIndex = GridPane.getColumnIndex((Node) event.getSource());
                switch (instructionGrid.getId()) {
                    case "program":
                        this.removeInstruction(this.programInstructions,rowIndex,columnIndex);
                        this.programInstructions.add(this.helperGUI(event, instructionGrid, rowIndex, columnIndex));
                        break;
                    case "method":
                        this.removeInstruction(this.methodInstructions,rowIndex,columnIndex);
                        this.methodInstructions.add(this.helperGUI(event, instructionGrid, rowIndex, columnIndex));
                        break;
                    case "method2":
                        this.removeInstruction(this.method2Instructions,rowIndex,columnIndex);
                        this.method2Instructions.add(this.helperGUI(event, instructionGrid, rowIndex, columnIndex));
                        break;
                }
            }
        };
    }

    private void removeInstruction(List<Instruction> instructions, int rowIndex, int columnIndex) {
        for (Instruction instruction : instructions) {
            if (instruction.getPosition().getRowIndex() == rowIndex && instruction.getPosition().getColumnIndex() == columnIndex) {
                instructions.remove(instruction);
                break;
            }
        }
    }

    private List<InstructionTypes> parseInstructionList(List<Instruction> list) {
        List<InstructionTypes> newList = new ArrayList<>();
        list.forEach(element -> newList.add(element.getName()));
        return newList;
    }

    public List<InstructionTypes> getProgramInstructions() {
        return this.parseInstructionList(this.programInstructions);
    }

    public List<InstructionTypes> getMethodInstructions() {
        return this.parseInstructionList(this.methodInstructions);
    }

    public List<InstructionTypes> getMethod2Instructions() {
        return this.parseInstructionList(this.method2Instructions);
    }

}
