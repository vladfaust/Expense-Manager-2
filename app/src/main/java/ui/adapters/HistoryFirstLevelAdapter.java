package ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;

import ui.fragments.HistoryFragment;
import ui.views.CustExpListview;
import ui.helpers.HistoryFirstLevel;

/**
 * Created by Жамбыл on 21.06.2015.
 */

public class HistoryFirstLevelAdapter extends BaseExpandableListAdapter {

    // Context
    Context context;

    // ExpandableListView itself
    ExpandableListView listView;

    // Variables
    private int lastExpandedGroupPosition;
    ArrayList<HistoryFirstLevel> firstLevelList;

    /*
        Constructor
     */
    public HistoryFirstLevelAdapter(Context context, ArrayList<HistoryFirstLevel> firstLevel) {
            this.context = context;
            this.firstLevelList = firstLevel;
        }

    /*
        GroupView
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View groupView, ViewGroup parent) {
        listView = (ExpandableListView)parent;

        if(groupView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            groupView = inflater.inflate(R.layout.history_first_layer,null);
        }
        // Name of Month
        TextView monthName = (TextView)groupView.findViewById(R.id.history_first_layer_month);

        // Money spent per month
        TextView monthMoney = (TextView)groupView.findViewById(R.id.history_first_layer_money);

        // Getting stored data
        monthName.setText((String) firstLevelList.get(groupPosition).firstLevelHeader.get(HistoryFragment.MONTHS_NAME));
        monthMoney.setText((String) firstLevelList.get(groupPosition).firstLevelHeader.get(HistoryFragment.MONTHS_MONEY));

        return groupView;
    }

    /*
        ChildView
    */
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View childView, ViewGroup parent) {
        // Inner expandableListView
        CustExpListview SecondLevelexplv = new CustExpListview(context);

        // Passing second level to inner adapter
        SecondLevelexplv.setAdapter(new HistorySecondLevelAdapter(context,
                firstLevelList.get(groupPosition).secondLevelList));
        SecondLevelexplv.setGroupIndicator(null);

        return SecondLevelexplv;
    }
    @Override
    public void onGroupExpanded(int groupPosition){
        // collapse the old expanded group, if not the same
        // as new group to expand
        if(groupPosition != lastExpandedGroupPosition) {
            listView.collapseGroup(lastExpandedGroupPosition);
        }

        super.onGroupExpanded(groupPosition);
        lastExpandedGroupPosition = groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return firstLevelList.get(groupPosition).secondLevelList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        // must always return 1
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return firstLevelList.get(groupPosition).firstLevelHeader;
    }

    @Override
    public int getGroupCount() {
        return firstLevelList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

