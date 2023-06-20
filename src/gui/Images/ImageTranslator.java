package gui.Images;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import logic.Types.BotDirectionTypes;
import logic.Types.FieldTypes;

public class ImageTranslator {

    public static void translate(FieldTypes[][] level, BotDirectionTypes botDirection, GridPane gameField) {
        for (int a = 0; a < level.length; a++) {
            for(int b = 0; b < level[a].length; b++) {
                switch (level[a][b]) {
                    case CHASM:
                        gameField.add(ImageGetter.getFieldImageResizable(FieldTypes.CHASM),b,a);
                        break;
                    case COIN:
                        gameField.add(ImageGetter.getFieldImageResizable(FieldTypes.COIN),b,a);
                        break;
                    case DOOR:
                        gameField.add(ImageGetter.getFieldImageResizable(FieldTypes.DOOR),b,a);
                        break;
                    case NORMAL:
                        gameField.add(ImageGetter.getFieldImageResizable(FieldTypes.NORMAL),b,a);
                        break;
                    case START:
                        gameField.add(translateBotDirection(botDirection),b,a);
                        break;
                    case WALL:
                        gameField.add(ImageGetter.getFieldImageResizable(FieldTypes.WALL),b,a);
                        break;
                }
            }
        }
    }

    public static Pane translateBotDirection(BotDirectionTypes botDirection) {
        Pane direction = new Pane();
        switch (botDirection) {
            case NORTH:
                return ImageGetter.getBotDirectionImageResizable(BotDirectionTypes.NORTH);
            case EAST:
                return ImageGetter.getBotDirectionImageResizable(BotDirectionTypes.EAST);
            case SOUTH:
                return ImageGetter.getBotDirectionImageResizable(BotDirectionTypes.SOUTH);
            case WEST:
                return ImageGetter.getBotDirectionImageResizable(BotDirectionTypes.WEST);
        }
        return direction;
    }

}
