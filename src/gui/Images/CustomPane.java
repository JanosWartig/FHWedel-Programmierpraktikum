package gui.Images;

import javafx.scene.layout.Pane;

public class CustomPane<T extends Enum<B>, B extends Enum<B>> extends Pane {

    private final T name;

    public CustomPane(T name) {
        this.name = name;
    }

    public T getName() {
        return this.name;
    }

}
