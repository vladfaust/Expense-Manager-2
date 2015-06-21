package ui.helpers;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Æאלבûכ on 21.06.2015.
 */
public class HistoryFirstLevel {

    public Map<String,Object> firstLevelHeader;

    public ArrayList<HistorySecondLevel> firstLevel;

    public HistoryFirstLevel(Map<String, Object> firstLevelHeader, ArrayList<HistorySecondLevel> firstLevel) {
        this.firstLevelHeader = firstLevelHeader;
        this.firstLevel = firstLevel;
    }
}
