package ui.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;

import ui.activities.MainActivity;

/**
 * Created by Жамбыл on 17.06.2015.
 */
public class MoneyExpListAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<String>> mGroups;
    private Context mContext;
    int lastExpandedGroupPosition;
    ExpandableListView listView;

    public MoneyExpListAdapter(Context context, ArrayList<ArrayList<String>> groups){
        mContext = context;
        mGroups = groups;
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
            convertView = inflater.inflate(R.layout.money_group_view, null);
        }

        if (isExpanded){
            //
        }
        else{
            //
        }
        // Parent's TextView
        TextView group = (TextView) convertView.findViewById(R.id.moneyText);
        group.setTextSize(24);

        // Setting font
        group.setTypeface(MainActivity.robotoRegular);

        // Setting Parent' view height
        group.getRootView().setBackgroundColor(Color.WHITE);
        group.getRootView().setMinimumHeight(280);


        // Parent's ProgressBar

        group.setText("$2.35");
        group.setTextColor(convertView.getResources().getColor(R.color.Cafes));

        return convertView;
    }

    @Override
    public void onGroupExpanded(int groupPosition){
        //collapse the old expanded group, if not the same
        //as new group to expand
        if(groupPosition != lastExpandedGroupPosition) {
            listView.collapseGroup(lastExpandedGroupPosition);
        }
        listView.setSelection(groupPosition);

//        listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 800));

        super.onGroupExpanded(groupPosition);
        lastExpandedGroupPosition = groupPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.money_child_view, null);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

