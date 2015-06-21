package ui.helpers;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Æאלבûכ on 21.06.2015.
 */
public class HistorySecondLevel {

    public Map<String,Object> secondLevelHeader;

    public ArrayList<HistoryThirdLevel> secondLevel;

    public HistorySecondLevel(Map<String, Object> secondLevelHeader, ArrayList<HistoryThirdLevel> secondLevel) {
        this.secondLevelHeader = secondLevelHeader;
        this.secondLevel = secondLevel;
    }
}
