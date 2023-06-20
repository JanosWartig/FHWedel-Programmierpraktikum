package gui;

import logic.Position;
import logic.Types.InstructionTypes;

public class Instruction {

    private final InstructionTypes name;
    private final Position position;

    public Instruction(InstructionTypes name, Position position) {
        this.name = name;
        this.position = position;
    }

    public InstructionTypes getName() {
        return this.name;
    }

    public Position getPosition() {
        return this.position;
    }

}
