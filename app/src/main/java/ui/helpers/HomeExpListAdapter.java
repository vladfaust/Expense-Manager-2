package ui.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import ui.activities.MainActivity;

/**
 * Created by Жамбыл on 13.06.2015.
 */
public class HomeExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroups;
    private Context mContext;
    int lastExpandedGroupPosition;
    ExpandableListView listView;
    Toolbar mToolbar;
    RelativeLayout mSpaceBelowToolbar;
    FloatingActionMenu mFabMenu;

    public HomeExpListAdapter(Context context, ArrayList<ArrayList<String>> groups,
                              Toolbar toolbar, RelativeLayout spaceBelowToolbar, FloatingActionMenu fabMenu){
        mContext = context;
        mGroups = groups;
        mToolbar = toolbar;
        mSpaceBelowToolbar = spaceBelowToolbar;
        mFabMenu = fabMenu;
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
        if(mFabMenu!=null && mFabMenu.isOpened())
        {
            mFabMenu.close(false);
        }
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.home_group_view, null);
        }

        if (isExpanded){
            //
        }
        else{
            //
        }
        // Parent's TextView
        TextView group = (TextView) convertView.findViewById(R.id.home_textGroup);
        group.setTextSize(24);

        // Setting font
        group.setTypeface(MainActivity.robotoRegular);

        // Setting Parent' view height
        //group.getRootView().setMinimumHeight(280);


        // Parent's ProgressBar
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

        switch (groupPosition){
            case 0:
                group.setText("Cafes: $194.7");
                group.setTextColor(convertView.getResources().getColor(R.color.Cafes));
                progressBar.getProgressDrawable().setColorFilter(convertView.getResources().getColor(R.color.Cafes), PorterDuff.Mode.SRC_IN);
                progressBar.setProgress(100);
                break;
            case 1:
                group.setText("Grocery: $140.2");
                group.setTextColor(convertView.getResources().getColor(R.color.Grocery));
                progressBar.getProgressDrawable().setColorFilter(convertView.getResources().getColor(R.color.Grocery), PorterDuff.Mode.SRC_IN);
                progressBar.setProgress(85);
                break;
        }

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
        super.onGroupExpanded(groupPosition);
        lastExpandedGroupPosition = groupPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if(mFabMenu!=null && mFabMenu.isOpened())
        {
            mFabMenu.close(false);
        }

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.home_child_view, null);
        }

        // Child's TextView
        TextView childName = (TextView) convertView.findViewById(R.id.home_childName);
        TextView childDate = (TextView) convertView.findViewById(R.id.home_childDate);
        TextView  childPrice = (TextView) convertView.findViewById(R.id.home_childPrice);

        // Setting fonts
        childName.setTypeface(MainActivity.robotoRegular);
        childDate.setTypeface(MainActivity.robotoRegular);
        childPrice.setTypeface(MainActivity.robotoRegular);

        childName.setTextSize(16);
        childDate.setTextSize(16);
        childPrice.setTextSize(16);

        // Setting Child's view height
        //childName.getRootView().setMinimumHeight(250);

        childPrice.setTextColor(convertView.getResources().getColor(R.color.ChildMoneyColor));
        childPrice.setTextSize(22);

        switch (childPosition) {
            // dot is Alt + 0149
            case 0:
                childName.setText("Auchan");
                childDate.setText("Monday • Auchan");
                childPrice.setText("$81.32");
                break;
            case 1:
                childName.setText("Picnic");
                childDate.setText("June 10 • Auchan");
                childPrice.setText("$40.60");
                break;
            case 2:
                childName.setText("A lot of cheese");
                childDate.setText("June 8 • Other");
                childPrice.setText("$18.28");
                break;
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

