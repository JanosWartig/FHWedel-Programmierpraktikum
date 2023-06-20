package logic;

import java.util.Arrays;
import java.util.List;

public class Constants {

    /**
     * For Accessibility all of the current used Constants are located here
     * when the program is finished, I will delete this class and make these fields only locally accessible
     * in the needed classes
     */

    public static final int ROW_COUNT = 8;
    public static final int COLUMN_COUNT = 8;
    public static final String FIELD_KEY = "field";
    public static final String BOT_ROTATION_KEY = "botRotation";
    public static final List<Integer> ALLOWED_FIELD_NUMBERS = Arrays.asList(0,1,2,3,4,5);
    public static final List<Integer> ALLOWED_BOT_ROTATION_NUMBERS = Arrays.asList(0,1,2,3);
    public static final int PROGRAM_MAX_SIZE = 12;
    public static final int METHOD_MAX_SIZE = 8;


}
