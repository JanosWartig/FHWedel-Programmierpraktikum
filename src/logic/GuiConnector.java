package logic;

import java.io.FileNotFoundException;
import java.util.List;

public interface GuiConnector {

    void animate(List<AnimationHelper> list);

    void showError(String error) throws FileNotFoundException;

    void showSuccess() throws FileNotFoundException;

}
