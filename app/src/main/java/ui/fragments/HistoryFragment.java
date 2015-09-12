package ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;
import java.util.List;

import bl.models.DatabaseInstrument;
import bl.models.Transaction;
import ui.adapters.HistoryFirstLevelAdapter;
import ui.helpers.HistoryFirstLevel;
import ui.helpers.HistorySecondLevel;
import ui.helpers.HistoryThirdLevel;

/**
 * Created by Жамбыл on 07.09.2015.
 */
public class HistoryFragment extends BaseFragment {

    ExpandableListView listView;
    int colorOfDrawer;

    // Flags
    public final static String DAY_CATEGORY = "DAY_CATEGORY";
    public final static String DAY_COMMENT = "DAY_COMMENT";
    public final static String DAY_MONEY = "DAY_MONEY";
    public final static String DAY_DATE = "DAY_MONEY";
    public final static String DAY_SUBCATEGORY = "DAY_MONEY";

    public final static String DAYS_NAME = "DAYS_NAME";
    public final static String DAYS_MONEY = "DAYS_MONEY";

    public final static String MONTHS_NAME = "MONTHS_NAME";
    public final static String MONTHS_MONEY = "MONTHS_MONEY";

    public final static String DATE = "DATE";
    public final static String CATEGORY = "CATEGORY";
    public final static String SUBCATEGORY = "SUBCATEGORY";
    public final static String COMMENT = "COMMENT";
    public final static String MONEY = "MONEY";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history,null);

        startUI(v);
        return v;
    }

    @Override
    protected void startUI(View v) {
        // UI blocks
        getColors();
        initExpListView(v);
    }

    private void getColors() {
        colorOfDrawer = getResources().getColor(R.color.HomeColorPrimary);
    }


    private void initExpListView(View v) {
        listView = (ExpandableListView) v.findViewById(R.id.history_list);

        // region content init
        List<String> listOfMonths = new ArrayList<>();

        List<List<String>> monthDaysList = new ArrayList<>();
        List<List<String>> listOfAllDay = new ArrayList<>();
//
//        List<String> listOfDays1= new ArrayList<>();
//        List<String> listOfDays2= new ArrayList<>();
//        List<String> listOfDays3= new ArrayList<>();
//
//        List<String> listOfDay1= new ArrayList<>();
//        List<String> listOfDay2= new ArrayList<>();
//        List<String> listOfDay3= new ArrayList<>();
//        List<String> listOfDay4= new ArrayList<>();
//        List<String> listOfDay5= new ArrayList<>();
//        List<String> listOfDay6= new ArrayList<>();
//        List<String> listOfDay7= new ArrayList<>();
//        List<String> listOfDay8= new ArrayList<>();
//        List<String> listOfDay9= new ArrayList<>();
//
//        listOfMonths.add("November");
//        listOfMonths.add("December");
//        listOfMonths.add("January");

//        for(int i = 1; i<=30; i++) {
//            listOfDays1.add(i<= 9? "0" + i + ".11.2014" : i + ".11.2014");
//        }
        DatabaseInstrument.instance.getAllMonths(listOfMonths, monthDaysList, listOfAllDay);
//        monthDaysList.add(listOfDays1);
//
//        listOfDays2.add("09.12.2014");
//        listOfDays2.add("10.12.2014");
//        monthDaysList.add(listOfDays2);
//
//        listOfDays3.add("09.01.2015");
//        listOfDays3.add("10.01.2015");
//        listOfDays3.add("11.01.2015");
//        listOfDays3.add("12.01.2015");
//        listOfDays3.add("13.01.2015");
//        listOfDays3.add("14.01.2015");
//        monthDaysList.add(listOfDays3);
//
//        // to make triple layer of  day
//
//        listOfDay1.add("23");
//        listOfDay2.add("23");
//        listOfDay3.add("23");
//        listOfDay4.add("23");
//        listOfDay5.add("23");
//        listOfDay6.add("23");
//        listOfDay7.add("23");
//        listOfDay8.add("23");
//        listOfDay9.add("23");
//
//
//        listOfAllDay.add(listOfDay1);
//        listOfAllDay.add(listOfDay2);
//        listOfAllDay.add(listOfDay3);

        // endregion

        // Container of data
        ArrayList<HistoryFirstLevel> historyData = new ArrayList<>();

        for(int i = 0; i < listOfMonths.size(); i++) {
            // First store month data (first level)
            HistoryFirstLevel firstLevel = new HistoryFirstLevel();
            firstLevel.firstLevelHeader.put(MONTHS_NAME, listOfMonths.get(i));

            int year = Integer.parseInt(listOfMonths.get(i).split(" ")[1]);
            int month = DatabaseInstrument.instance.getNumByMonth(listOfMonths.get(i).split(" ")[0]);

            ArrayList<Transaction> selectedMonthTransacts = DatabaseInstrument.instance.getTransactions(
                    year, month, 1, year, month, 31
            );
            firstLevel.firstLevelHeader.put(MONTHS_MONEY, "$"+DatabaseInstrument.instance.getAllSpendsFrom(selectedMonthTransacts));

            for(int j =0; j < monthDaysList.get(i).size(); j++) {
                // Then store days data (second level)
                HistorySecondLevel secondLevel= new HistorySecondLevel();
                secondLevel.secondLevelHeader.put(DAYS_NAME, monthDaysList.get(i).get(j) );
                secondLevel.secondLevelHeader.put(DAYS_MONEY, "$45");

                for(int k = 0; k < listOfAllDay.get(0).size(); k++) {
                    // Then store day data (third level)
                    ArrayList<Transaction> trs = DatabaseInstrument.instance.getTransactionsByOneDay(listOfAllDay.get(0).get(k));
                    for (Transaction tr : trs){
                        HistoryThirdLevel transaction = new HistoryThirdLevel();
                        transaction.thirdLevel.put(DAY_DATE, tr.getDate());
                        transaction.thirdLevel.put(DAY_CATEGORY, tr.getCategory());
                        transaction.thirdLevel.put(DAY_SUBCATEGORY, tr.getSubCategory());
                        transaction.thirdLevel.put(DAY_COMMENT, tr.getComment());
                        transaction.thirdLevel.put(DAY_MONEY, tr.getAmount());

                        // Add third level to second level
                        secondLevel.thirdLevelList.add(transaction);
                    }

                }
                // Add second level to first level
                firstLevel.secondLevelList.add(secondLevel);
            }
            // Add first level to container
            historyData.add(firstLevel);
        }
        // Puting container to adapter
        HistoryFirstLevelAdapter adapter = new HistoryFirstLevelAdapter(v.getContext(),historyData);
        listView.setAdapter(adapter);
    }


}
