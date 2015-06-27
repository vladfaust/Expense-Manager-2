package ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;
import java.util.List;

import ui.helpers.HistoryFirstLevel;
import ui.helpers.HistorySecondLevel;
import ui.helpers.HistoryThirdLevel;
import ui.helpers.MyDrawer;
import ui.adapters.HistoryFirstLevelAdapter;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class HistoryActivity extends BaseActivity {

    ExpandableListView listView;
    Toolbar toolbar;
    MyDrawer drawerBuilder;
    int colorOfDrawer;

    // Flags
    public final static String DAY_CATEGORY = "DAY_CATEGORY";
    public final static String DAY_COMMENT = "DAY_COMMENT";
    public final static String DAY_MONEY = "DAY_MONEY";

    public final static String DAYS_NAME = "DAYS_NAME";
    public final static String DAYS_MONEY = "DAYS_MONEY";

    public final static String MONTHS_NAME = "MONTHS_NAME";
    public final static String MONTHS_MONEY = "MONTHS_MONEY";

    public final static String DATE = "DATE";
    public final static String CATEGORY = "CATEGORY";
    public final static String SUBCATEGORY = "SUBCATEGORY";
    public final static String COMMENT = "COMMENT";
    public final static String MONEY = "MONEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        startUI();
    }

    @Override
    protected void startUI() {
        // UI blocks
        getColors();
        initToolbar();
        initDrawer();
        initExpListView();
    }

    private void getColors() {
        colorOfDrawer = getResources().getColor(R.color.HomeColorPrimary);
    }

    private void initDrawer() {
        drawerBuilder = new MyDrawer(this, toolbar,colorOfDrawer);
        drawerBuilder.create();
        // for proper display
        drawerBuilder.getDrawer().setSelection(1);
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

        for(int i = 0; i<30; i++) {
            listOfDays1.add(i<= 9? "0":"" + String.valueOf(i) + ".11.2015");
        }
        listOfAllDays.add(listOfDays1);

        listOfDays2.add("09.12.2015");
        listOfDays2.add("10.12.2015");
        listOfAllDays.add(listOfDays2);

        listOfDays3.add("09.01.2015");
        listOfDays3.add("10.01.2015");
        listOfDays3.add("11.01.2015");
        listOfDays3.add("12.01.2015");
        listOfDays3.add("13.01.2015");
        listOfDays3.add("14.01.2015");
        listOfAllDays.add(listOfDays3);

        // to make triple layer of  day

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

        // endregion

        // (Oh, dat feeling when the code below is so perfect you want to jerk to it)
        // Container
        ArrayList<HistoryFirstLevel> firstLevelList = new ArrayList<>();

        for(int i = 0; i < listOfMoths.size(); i++) {
            // First store month data (first level)
            HistoryFirstLevel firstLevel = new HistoryFirstLevel();
            firstLevel.firstLevelHeader. put(MONTHS_NAME, listOfMoths.get(i));
            firstLevel.firstLevelHeader.put(MONTHS_MONEY, "$8000");

            for(int j =0; j < listOfAllDays.get(i).size(); j++) {
                // Then store days data (second level)
                HistorySecondLevel secondLevel= new HistorySecondLevel();
                secondLevel.secondLevelHeader.put(DAYS_NAME, listOfAllDays.get(i).get(j) );
                secondLevel.secondLevelHeader.put(DAYS_MONEY, "$45");

                for(int k = 0; k < listOfAllDay.size(); k++) {
                    // Then store day data (third level)
                    HistoryThirdLevel day = new HistoryThirdLevel();
                    day.thirdLevel.put(DAY_CATEGORY, "Auchan");
                    day.thirdLevel.put(DAY_COMMENT, "Auchan");
                    day.thirdLevel.put(DAY_MONEY, listOfAllDay.get(k).get(0));

                    // Add third level to second level
                    secondLevel.thirdLevelList.add(day);
                }
                // Add second level to first level
                firstLevel.secondLevelList.add(secondLevel);
            }
            // Add first level to container
            firstLevelList.add(firstLevel);
        }
        // Put container to adapter
        HistoryFirstLevelAdapter adapter = new HistoryFirstLevelAdapter(this,firstLevelList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        // Close drawer if it's open
        if(drawerBuilder.getDrawer()!=null && drawerBuilder.getDrawer().isDrawerOpen()) {
            drawerBuilder.getDrawer().closeDrawer();
        }
        else {
            super.onBackPressed();
        }
    }
}
