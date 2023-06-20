package logic;

import logic.Types.BotDirectionTypes;
import logic.Types.InstructionTypes;

public class AnimationHelper {

    private final Position oldPos;
    private final Position newPos;
    private final BotDirectionTypes botDirection;
    private final InstructionTypes instruction;

    public AnimationHelper(Position newPos, Position oldPos, BotDirectionTypes botDirection, InstructionTypes instruction) {
        this.newPos = newPos;
        this.oldPos = oldPos;
        this.botDirection = botDirection;
        this.instruction = instruction;
    }

    public Position getOldPos() {
        return this.oldPos;
    }

    public Position getNewPos() {
        return this.newPos;
    }

    public BotDirectionTypes getBotDirection() {
        return this.botDirection;
    }

    public InstructionTypes getInstruction() {
        return this.instruction;
    }

}
