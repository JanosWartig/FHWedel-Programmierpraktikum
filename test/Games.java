import logic.Types.FieldTypes;

public class Games {

    public static FieldTypes[][] normal() {
        return new FieldTypes[][]{
            {FieldTypes.START, FieldTypes.NORMAL, FieldTypes.NORMAL}
        };
    }

    public static FieldTypes[][] wall() {
        return new FieldTypes[][]{
                {FieldTypes.START, FieldTypes.WALL, FieldTypes.NORMAL}
        };
    }

    public static FieldTypes[][] chasm() {
        return new FieldTypes[][]{
                {FieldTypes.START, FieldTypes.CHASM, FieldTypes.NORMAL}
        };
    }

    public static FieldTypes[][] coin() {
        return new FieldTypes[][]{
                {FieldTypes.START, FieldTypes.COIN, FieldTypes.NORMAL}
        };
    }

    public static FieldTypes[][] exit() {
        return new FieldTypes[][]{
                {FieldTypes.START, FieldTypes.NORMAL, FieldTypes.DOOR}
        };
    }

    public static FieldTypes[][] coinExit() {
        return new FieldTypes[][]{
                {FieldTypes.START, FieldTypes.COIN, FieldTypes.DOOR}
        };
    }

    public static FieldTypes[][] coinsExit() {
        return new FieldTypes[][]{
                {FieldTypes.START, FieldTypes.COIN, FieldTypes.DOOR},
                {FieldTypes.WALL, FieldTypes.COIN, FieldTypes.WALL}
        };
    }

}
