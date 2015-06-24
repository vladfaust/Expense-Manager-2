package ui.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class HistoryFirstLevel {

    public Map<String,Object> firstLevelHeader;

    public ArrayList<HistorySecondLevel> secondLevelList;

    /*
        Constructor
     */
    public HistoryFirstLevel() {
        this.firstLevelHeader = new HashMap<>();
        this.secondLevelList = new ArrayList<>();
    }
}
