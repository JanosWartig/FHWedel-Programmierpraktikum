import logic.AnimationHelper;
import logic.GuiConnector;

import java.io.FileNotFoundException;
import java.util.List;

public class FakeGUI implements GuiConnector {

    @Override
    public void animate(List<AnimationHelper> list) {

    }

    @Override
    public void showError(String error) throws FileNotFoundException {

    }

    @Override
    public void showSuccess() throws FileNotFoundException {

    }

}
