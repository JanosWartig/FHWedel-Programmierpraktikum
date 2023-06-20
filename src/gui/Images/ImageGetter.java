package gui.Images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Types.BotDirectionTypes;
import logic.Types.FieldTypes;
import logic.Types.InstructionTypes;

import java.util.Objects;

public class ImageGetter {

    // Used for Images that are not displayed in the Grid
    // and therefore do not have to Scale up or down
    public static CustomPane<InstructionTypes, InstructionTypes> getInstructionImage(InstructionTypes picture) {
        CustomPane<InstructionTypes, InstructionTypes> pane = new CustomPane<>(picture);
        pane.setMaxSize(60,60);
        ImageView imageView = new ImageView();
        Image image = new Image(Objects.requireNonNull(
                ImageGetter.class.getResourceAsStream("assets/" + picture.toString() + ".png")));
        imageView.setImage(image);
        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(imageView);
        return pane;
    }

    // Used for Images that are not displayed in the Grid
    // and therefore do not have to Scale up or down
    public static CustomPane<BotDirectionTypes, BotDirectionTypes> getBotDirectionImage(BotDirectionTypes picture) {
        CustomPane<BotDirectionTypes, BotDirectionTypes> pane = new CustomPane<>(picture);
        pane.setMaxSize(60,60);
        ImageView imageView = new ImageView();
        Image image = new Image(Objects.requireNonNull(
                ImageGetter.class.getResourceAsStream("assets/" + picture.toString() + ".png")));
        imageView.setImage(image);
        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(imageView);
        return pane;
    }

    // Used for Images that are displayed in the Grid
    // these images need to scale either up or down in size
    public static CustomPane<BotDirectionTypes, BotDirectionTypes> getBotDirectionImageResizable(BotDirectionTypes picture) {
        CustomPane<BotDirectionTypes, BotDirectionTypes> pane = new CustomPane<>(picture);
        ImageView imageView = new ImageView();
        Image image = new Image(Objects.requireNonNull(
                ImageGetter.class.getResourceAsStream("assets/" + picture.toString() + ".png")));
        imageView.setImage(image);
        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(imageView);
        return pane;
    }

    public static CustomPane<FieldTypes, FieldTypes> getFieldImage(FieldTypes picture) {
        CustomPane<FieldTypes, FieldTypes> pane = new CustomPane<>(picture);
        pane.setMaxSize(60,60);
        ImageView imageView = new ImageView();
        Image image = new Image(Objects.requireNonNull(
                ImageGetter.class.getResourceAsStream("assets/" + picture.toString() + ".png")));
        imageView.setImage(image);
        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(imageView);
        return pane;
    }


    // Used for Images that are displayed in the Grid
    // these images need to scale either up or down in size
    public static CustomPane<FieldTypes, FieldTypes> getFieldImageResizable(FieldTypes picture) {
        CustomPane<FieldTypes, FieldTypes> pane = new CustomPane<>(picture);
        ImageView imageView = new ImageView();
        Image image = new Image(Objects.requireNonNull(
                ImageGetter.class.getResourceAsStream("assets/" + picture.toString() + ".png")));
        imageView.setImage(image);
        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(imageView);
        return pane;
    }

}
