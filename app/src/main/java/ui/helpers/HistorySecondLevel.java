package ui.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class HistorySecondLevel {

    public Map<String,Object> secondLevelHeader;

    public ArrayList<HistoryThirdLevel> thirdLevelList;

    /*
        Constructor
     */
    public HistorySecondLevel() {
        this.secondLevelHeader = new HashMap<>();
        this.thirdLevelList = new ArrayList<>();
    }
}
