package logic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import logic.Types.BotDirectionTypes;
import logic.Types.FieldTypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Level {

    private final File file;
    private final JsonObject level;
    private final FieldTypes[][] field;
    private final Position botStartPosition;
    private final BotDirectionTypes botStartDirection;

    public Level(File file) throws FileNotFoundException {
        this.file = file;
        this.level = this.read();
        this.field = this.translateIntToField();
        this.botStartPosition = this.translateBotStartPosition();
        this.botStartDirection = this.translateBotDirection();
    }

    private JsonObject read() throws FileNotFoundException {
        FileReader reader = new FileReader(this.file);
        Gson gson = new Gson();
        return gson.fromJson(reader, JsonObject.class);
    }

    private int[][] translateFieldToInt() {
        int[][] newField = new int[Constants.ROW_COUNT][Constants.COLUMN_COUNT];
        for (int a = 0; a < this.level.get(Constants.FIELD_KEY).getAsJsonArray().size(); a++) {
            for (int b = 0; b < this.level.get(Constants.FIELD_KEY).getAsJsonArray().get(a).getAsJsonArray().size(); b++) {
                newField[a][b] = this.level.get(Constants.FIELD_KEY).getAsJsonArray().get(a).getAsJsonArray().get(b).getAsInt();
            }
        }
        return newField;
    }

    private FieldTypes[][] translateIntToField() {
        int[][] field = this.translateFieldToInt();
        FieldTypes[][] newField = new FieldTypes[Constants.ROW_COUNT][Constants.COLUMN_COUNT];
        int digit;
        for (int a = 0; a < field.length; a++) {
            for (int b = 0; b <  field[a].length; b++) {
                digit = field[a][b];
                switch (digit) {
                    case 0:
                        newField[a][b] = FieldTypes.CHASM;
                        break;
                    case 1:
                        newField[a][b] = FieldTypes.COIN;
                        break;
                    case 2:
                        newField[a][b] = FieldTypes.DOOR;
                        break;
                    case 3:
                        newField[a][b] = FieldTypes.NORMAL;
                        break;
                    case 4:
                        newField[a][b] = FieldTypes.START;
                        break;
                    case 5:
                        newField[a][b] = FieldTypes.WALL;
                        break;
                }
            }
        }
        return newField;
    }

    private Position translateBotStartPosition() {
        for (int a = 0; a < this.field.length; a++) {
            for (int b = 0; b < this.field[a].length; b++) {
                if (this.field[a][b] == FieldTypes.START) {
                    return new Position(a,b);
                }
            }
        }
        return null;
    }

    private BotDirectionTypes translateBotDirection() {
        int botDirection = this.level.get(Constants.BOT_ROTATION_KEY).getAsInt();
        BotDirectionTypes directionType;
        switch (botDirection) {
            case 0:
                directionType = BotDirectionTypes.NORTH;
                break;
            case 1:
                directionType = BotDirectionTypes.EAST;
                break;
            case 2:
                directionType = BotDirectionTypes.SOUTH;
                break;
            case 3:
                directionType = BotDirectionTypes.WEST;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + botDirection);
        }
        return directionType;
    }

    public FieldTypes[][] getField() {
        return this.field;
    }

    public Position getBotStartPosition() {
        return this.botStartPosition;
    }

    public BotDirectionTypes getBotStartDirection() {
        return this.botStartDirection;
    }

}
