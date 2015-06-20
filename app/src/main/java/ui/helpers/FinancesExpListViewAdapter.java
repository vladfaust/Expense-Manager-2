package ui.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;

import ui.activities.MainActivity;

/**
 * Created by Жамбыл on 15.06.2015.
 */
public class FinancesExpListViewAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroups;
    private Context mContext;
    int lastExpandedGroupPosition;
    ExpandableListView listView;
    MoneyExpListAdapter moneyAdapter;

    public FinancesExpListViewAdapter(Context context, ArrayList<ArrayList<String>> groups, MoneyExpListAdapter moneyAdapter) {
        mContext = context;
        mGroups = groups;
        this.moneyAdapter = moneyAdapter;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        listView = (ExpandableListView) parent;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.finances_group_view, null);
        }

        if (isExpanded){
            //
        }
        else{
            //
        }

        // Parent's TextView
        TextView group = (TextView) convertView.findViewById(R.id.finances_textGroup);
        group.setTextSize(24);
        group.setPadding(58,0,0,0);

        // Setting font
        group.setTypeface(MainActivity.robotoRegular);

        // Setting Parent' view height
        group.getRootView().setMinimumHeight(280);



        switch (groupPosition){
            case 0:
                group.setText("Cafes");
                group.setTextColor(convertView.getResources().getColor(R.color.Cafes));
                break;
            case 1:
                group.setText("Grocery");
                group.setTextColor(convertView.getResources().getColor(R.color.Grocery));
                break;
        }

        return convertView;
    }

    @Override
    public void onGroupExpanded(int groupPosition){
        // collapse the old expanded group, if not the same
        // as new group to expand
        if(groupPosition != lastExpandedGroupPosition) {
            listView.collapseGroup(lastExpandedGroupPosition);
        }
        moneyAdapter.close();

        super.onGroupExpanded(groupPosition);
        lastExpandedGroupPosition = groupPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.finances_child_view, null);
        }

        // Child's TextView
        TextView childName = (TextView) convertView.findViewById(R.id.finances_childName);

        // Setting fonts
        childName.setTypeface(MainActivity.robotoRegular);

        childName.setTextSize(16);

        // Setting Child's view height
        childName.getRootView().setMinimumHeight(250);
        childName.setPadding(58,0,0,0);


        switch (childPosition) {
            case 0:
                childName.setText("Auchan");
                break;
            case 1:
                childName.setText("Picnic");
                break;
            case 2:
                childName.setText("A lot of cheese");
                break;
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}