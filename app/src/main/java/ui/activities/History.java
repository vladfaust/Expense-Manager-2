package ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.helpers.MyDrawer;
import ui.helpers.HistoryFirstLevelAdapter;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class History extends BaseActivity {

    ExpandableListView listView;
    Toolbar toolbar;
    MyDrawer drawer;
    int colorOfDrawer;

    // Flags
    public final static String DAY_CATEGORY = "DAY_CATEGORY";
    public final static String DAY_COMMENT = "DAY_COMMENT";
    public final static String DAY_MONEY = "DAY_MONEY";

    public final static String DAYS_NAME = "DAYS_NAME";
    public final static String DAYS_MONEY = "DAYS_MONEY";

    public final static String MONTHS_NAME = "MONTHS_NAME";
    public final static String MONTHS_MONEY = "MONTHS_MONEY";

    public final static String DAYS_TAG = "DAYS_TAG";
    public final static String MONTH_TAG = "MONTH_TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        startUI();
    }

    @Override
    protected void startUI() {
        getColors();
        initToolbar();
        initDrawer();
        initExpListView();
    }

    private void getColors() {
        colorOfDrawer = getResources().getColor(R.color.HomeColorPrimary);
    }

    private void initDrawer() {
        drawer = new MyDrawer(this, toolbar,colorOfDrawer);
        drawer.create();
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbarHistory);
    }

    private void initExpListView() {
        listView = (ExpandableListView)findViewById(R.id.history_list);

        // region content init
        List<String> listOfMoths = new ArrayList<>();

        List<List<String>> listOfAllDays = new ArrayList<>();
        List<List<String>> listOfAllDay = new ArrayList<>();

        List<String> listOfDays1= new ArrayList<>();
        List<String> listOfDays2= new ArrayList<>();
        List<String> listOfDays3= new ArrayList<>();

        List<String> listOfDay1= new ArrayList<>();
        List<String> listOfDay2= new ArrayList<>();
        List<String> listOfDay3= new ArrayList<>();
        List<String> listOfDay4= new ArrayList<>();
        List<String> listOfDay5= new ArrayList<>();
        List<String> listOfDay6= new ArrayList<>();
        List<String> listOfDay7= new ArrayList<>();
        List<String> listOfDay8= new ArrayList<>();
        List<String> listOfDay9= new ArrayList<>();

        listOfMoths.add("November");
        listOfMoths.add("December");
        listOfMoths.add("January");

        listOfDays1.add("09.11.2015");
        listOfDays1.add("10.11.2015");
        listOfDays1.add("11.11.2015");
        listOfDays1.add("12.11.2015");
        listOfAllDays.add(listOfDays1);

        listOfDays2.add("09.12.2015");
        listOfDays2.add("10.12.2015");
        listOfAllDays.add(listOfDays2);

        listOfDays3.add("09.01.2015");
        listOfAllDays.add(listOfDays3);


        listOfDay1.add("23");
        listOfDay2.add("23");
        listOfDay3.add("23");
        listOfDay4.add("23");
        listOfDay5.add("23");
        listOfDay6.add("23");
        listOfDay7.add("23");
        listOfDay8.add("23");
        listOfDay9.add("23");


        listOfAllDay.add(listOfDay1);
        listOfAllDay.add(listOfDay2);
        listOfAllDay.add(listOfDay3);
        listOfAllDay.add(listOfDay4);
        listOfAllDay.add(listOfDay5);
        listOfAllDay.add(listOfDay6);
        listOfAllDay.add(listOfDay7);
        listOfAllDay.add(listOfDay8);
        listOfAllDay.add(listOfDay9);

        // endregion


        ArrayList<Map<String,Object>> monthsData = new ArrayList<>();
        ArrayList<ArrayList<Map<String,Object>>> daysData = new ArrayList<>();
        ArrayList<ArrayList<ArrayList< Map<String,Object>>>> dayData = new ArrayList<>();

        Map<String,Object> monthsInnerData;
        ArrayList<Map<String,Object>> daysInnerData;
        ArrayList<ArrayList<Map<String,Object>>> dayInnerData;

        for(int i = 0; i < listOfMoths.size(); i++) {
            monthsInnerData = new HashMap<>();
            monthsInnerData.put(MONTHS_NAME, listOfMoths.get(i));
            monthsInnerData.put(MONTHS_MONEY, "$10");
            monthsData.add(monthsInnerData);

            for(int j = 0; j < listOfAllDays.size();j++) {
                daysInnerData = new ArrayList<>();
                Map<String,Object> innerContent = new HashMap<>();
                listOfAllDays.get(j).get(i);
                daysInnerData.add(innerContent);
                daysData.add(daysInnerData);

//                for(int k = 0; k < listOfAllDay.size();k++ ) {
//                    dayInnerData = new ArrayList<>();
//                    dayInnerData.get(i).get(j).put(DAY_CATEGORY, "asd");
//                    dayInnerData.get(i).get(j).put(DAY_MONEY, "asd");
//                    dayInnerData.get(i).get(j).put(DAY_COMMENT,"asd");
//                    dayData.add(dayInnerData);
//                }
            }
        }


        HistoryFirstLevelAdapter adapter = new HistoryFirstLevelAdapter(this,monthsData,daysData,dayData);
        listView.setAdapter(adapter);
    }
}
