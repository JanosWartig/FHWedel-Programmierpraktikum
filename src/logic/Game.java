package logic;

import logic.Types.BotDirectionTypes;
import logic.Types.FieldTypes;
import logic.Types.InstructionTypes;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private final FieldTypes[][] gameField;
    private Bot bot;
    private List<InstructionTypes> program;
    private List<InstructionTypes> method;
    private List<InstructionTypes> method2;
    private List<Position> coinPositions;
    private List<InstructionTypes> allInstructions;
    private GuiConnector guiConnector;
    private List<AnimationHelper> animationHelper;

    public Game(FieldTypes[][] gameField) {
        this.gameField = gameField;
    }

    public Game(FieldTypes[][] gameField, Position botStartPosition, BotDirectionTypes botStartDirection) {
        this(gameField);
        this.bot = new Bot(botStartPosition, botStartDirection);
        this.coinPositions = this.findCoinPositions();
    }

    public Game(FieldTypes[][] gameField,
                Position botStartPosition,
                BotDirectionTypes botStartDirection,
                List<InstructionTypes> program,
                List<InstructionTypes> method,
                List<InstructionTypes> method2) {
        this(gameField, botStartPosition, botStartDirection);
        this.program = program;
        this.method = method;
        this.method2 = method2;
        this.coinPositions = this.findCoinPositions();
        this.allInstructions = this.makeListOfInstructions();
    }

    public Game(FieldTypes[][] gameField,
                Position botStartPosition,
                BotDirectionTypes botStartDirection,
                List<InstructionTypes> program,
                List<InstructionTypes> method,
                List<InstructionTypes> method2,
                GuiConnector guiConnector) {
        this(gameField, botStartPosition, botStartDirection);
        this.program = program;
        this.method = method;
        this.method2 = method2;
        this.coinPositions = this.findCoinPositions();
        this.allInstructions = this.makeListOfInstructions();
        this.guiConnector = guiConnector;
        this.animationHelper = new ArrayList<>();
    }

    private List<Position> findCoinPositions() {
        List<Position> coinPositions = new ArrayList<>();
        for (int a = 0; a < this.gameField.length; a++) {
            for (int b = 0; b < this.gameField[a].length; b++) {
               if (this.gameField[a][b] == FieldTypes.COIN) {
                   coinPositions.add(new Position(a,b));
               }
            }
        }
        return coinPositions;
    }

    private boolean checkForIllegalRecursion() {
        // Handle not allowed Recursion
        return !this.method.contains(InstructionTypes.METHOD) &&
                !this.method.contains(InstructionTypes.METHOD2) &&
                !this.method2.contains(InstructionTypes.METHOD) &&
                !this.method2.contains(InstructionTypes.METHOD2);
    }

    private List<InstructionTypes> makeListOfInstructions() {
        List<InstructionTypes> list = new ArrayList<>();
        for (InstructionTypes instructionTypes : this.program) {
            if (instructionTypes == InstructionTypes.METHOD) {
                list.addAll(this.method);
            } else if (instructionTypes == InstructionTypes.METHOD2) {
                list.addAll(this.method2);
            } else {
                list.add(instructionTypes);
            }
        }
        return list;
    }

    public void runInstructions() {
        if (this.allInstructions != null) {
            for (InstructionTypes allInstruction : this.allInstructions) {
                switch (allInstruction) {
                    case WALK:
                        if (this.walkPossible()) {
                            this.bot.walk();
                        }
                        break;
                    case LEFT:
                        this.bot.left();
                        break;
                    case RIGHT:
                        this.bot.right();
                        break;
                    case JUMP:
                        if (this.jumpPossible()) {
                            this.bot.jump();
                        }
                        break;
                    case EXIT:
                        if (this.exitPossible()) {
                            this.bot.exit();
                        }
                        break;
                }
                if (this.isCoin()) {
                    this.collectCoin();
                }
            }
        }
    }

    public void runInstructionsGUI() throws FileNotFoundException {
        if (this.allInstructions != null) {
            for (InstructionTypes allInstruction : this.allInstructions) {
                switch (allInstruction) {
                    case WALK:
                        if (this.walkPossible()) {
                            this.bot.walk();
                            this.animationHelper.add(
                                    new AnimationHelper(
                                            this.bot.getPosition(),
                                            this.bot.getVisited().get(this.bot.getVisited().size() -2),
                                            this.bot.getBotRotation(),
                                            InstructionTypes.WALK
                                    )
                            );
                        } else {
                            this.guiConnector.showError("ERROR: Walk not possible => Next field is not a Start, Normal or Coin field.");
                        }
                        break;
                    case LEFT:
                        this.bot.left();
                        this.animationHelper.add(
                                new AnimationHelper(
                                        this.bot.getPosition(),
                                        this.bot.getPosition(),
                                        this.bot.getBotRotation(),
                                        InstructionTypes.LEFT
                                )
                        );
                        break;
                    case RIGHT:
                        this.bot.right();
                        this.animationHelper.add(
                                new AnimationHelper(
                                        this.bot.getPosition(),
                                        this.bot.getPosition(),
                                        this.bot.getBotRotation(),
                                        InstructionTypes.RIGHT
                                )
                        );
                        break;
                    case JUMP:
                        if (this.jumpPossible()) {
                            this.bot.jump();
                        } else {
                            this.guiConnector.showError("ERROR: Jump not possible =>" +
                                    " Next Field is not a Chasm OR Landing Field is not a Coin," +
                                    " Normal or Start Field");
                        }
                        break;
                    case EXIT:
                        if (this.exitPossible()) {
                            this.bot.exit();
                            this.animationHelper.add(
                                    new AnimationHelper(
                                            this.bot.getPosition(),
                                            this.bot.getVisited().get(this.bot.getVisited().size() -2),
                                            this.bot.getBotRotation(),
                                            InstructionTypes.EXIT
                                    )
                            );
                            this.guiConnector.showSuccess();
                        } else {
                            this.guiConnector.showError("ERROR: Exit not possible =>" +
                                    " Next Field is not a Exit OR Not all coins are collected");
                        }
                        break;
                }
                if (this.isCoin()) {
                    this.collectCoin();
                }
            }
            this.guiConnector.animate(this.animationHelper);
        }
    }

    /**
     * Tests whether or not a walk is possible.
     * 1. The next field needs to be a Start, Normal or Coin field.
     * 2. The step needs to be in the Game Field otherwise
     *    this will trigger an IndexOutOfBoundsException.
     * @return true => walk is possible or false => walk is not possible
     */
    private boolean walkPossible() {
        try {
            Position position = this.bot.walkTester(1);
            FieldTypes field = this.gameField[position.getRowIndex()][position.getColumnIndex()];
            // Next field is not a Start, Normal or Coin field.
            return field == FieldTypes.START || field == FieldTypes.NORMAL || field == FieldTypes.COIN;
        } catch (IndexOutOfBoundsException e) {
            // rowIndex or columnIndex is larger or smaller than the gameField is big
        }
        return false;
    }

    /**
     * Tests whether or not a jump is possible.
     * 1. The next Field needs to be a one Field large Chasm
     * 2. The landing Field need to be a Coin, Normal or Start field
     * 3. Both steps needs to be in the Game Field otherwise
     *    this will trigger an IndexOutOfBoundsException.
     * @return true => jump is possible or false => jump is not possible
     */
    private boolean jumpPossible() {
        try {
            Position position = this.bot.walkTester(1);
            FieldTypes field = this.gameField[position.getRowIndex()][position.getColumnIndex()];
            if (field != FieldTypes.CHASM) {
                // Next Field is not a Chasm => Jump is not possible
                return false;
            }
            position = this.bot.walkTester(2);
            field = this.gameField[position.getRowIndex()][position.getColumnIndex()];
            // Landing Field is not a Coin, Normal or Start Field => Jump is not possible
            return field == FieldTypes.COIN || field == FieldTypes.NORMAL || field == FieldTypes.START;
        } catch (IndexOutOfBoundsException a) {
            // rowIndex or columnIndex is larger or smaller than the gameField is big

        }
        return false;
    }

    /**
     * Tests whether or not a exit is possible.
     * 1. The next Field needs to be an Exit field
     * 2. All Coins need to be collected
     * 3. The step needs to be in the Game Field otherwise
     *    this will trigger an IndexOutOfBoundsException.
     * @return true => jump is possible or false => jump is not possible
     */
    private boolean exitPossible() {
        try {
            Position position = this.bot.walkTester(1);
            FieldTypes field = this.gameField[position.getRowIndex()][position.getColumnIndex()];
            if (field != FieldTypes.DOOR) {
                // Next Field is not a Exit/Door => Exit not possible
                return false;
            }
            // There are Coins that are not collected => Exit not possible
            return this.getCoinPositions().size() == 0;
        } catch (IndexOutOfBoundsException e) {
            // rowIndex or columnIndex is larger or smaller than the gameField is big
        }
        return false;
    }

    /**
     * Checks whether or not the field is a Coin field
     * @return true => is a Coin field or false => is not a Coin Field
     */
    private boolean isCoin() {
        return this.gameField
                [this.bot.getPosition().getRowIndex()]
                [this.bot.getPosition().getColumnIndex()] == FieldTypes.COIN;
    }

    /**
     * Collects a Coin => Removes the Coin from the Game Field
     * and replaces the Coin field with a Normal field.
     */
    private void collectCoin() {
       this.getCoinPositions().remove(this.bot.getPosition());
    }

    public FieldTypes[][] getGameField() {
        return this.gameField;
    }

    public Bot getBot() {
        return this.bot;
    }

    public List<InstructionTypes> getProgram() {
        return this.program;
    }

    public List<InstructionTypes> getMethod() {
        return this.method;
    }

    public List<InstructionTypes> getMethod2() {
        return this.method2;
    }

    public List<InstructionTypes> getAllInstructions() {
        return this.allInstructions;
    }

    public List<Position> getCoinPositions() {
        return this.coinPositions;
    }

    @Override
    public String toString() {
        StringBuilder field = new StringBuilder();
        field.append("FIELD").append(System.getProperty("line.separator"));
        for (int a = 0; a < this.getGameField().length; a++) {
            field.append(a).append(". Row:");
            for (int b = 0; b < this.getGameField()[a].length; b++) {
                field.append(" ").append(this.getGameField()[a][b]);
            }
            field.append(System.getProperty("line.separator"));
        }
        field.append(System.getProperty("line.separator")).append("BOT ROTATION");
        field.append(System.getProperty("line.separator"));
        field.append(this.bot.getBotRotation());
        return field.toString();
    }
}
