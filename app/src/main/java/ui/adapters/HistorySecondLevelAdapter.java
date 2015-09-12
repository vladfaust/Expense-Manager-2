package ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;

import bl.models.Category;
import ui.activities.ExpenseViewActivity;
import ui.fragments.HistoryFragment;
import ui.helpers.HistorySecondLevel;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class HistorySecondLevelAdapter extends BaseExpandableListAdapter {

    // Context
    Context context;

    // ExpandableListView itself
    private ExpandableListView listView;

    // Variables
    ArrayList<HistorySecondLevel> secondLevelList;
    private int lastExpandedGroupPosition;

    /*
        Constructor
     */

    public HistorySecondLevelAdapter(Context context, ArrayList<HistorySecondLevel> secondLevelList) {
        this.context = context;
        this.secondLevelList = secondLevelList;
    }

    /*
        GroupView
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View groupView, ViewGroup parent) {
        listView = (ExpandableListView)parent;
        if(groupView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            groupView = inflater.inflate(R.layout.history_second_layer_group,null);
        }

        TextView group_text = (TextView) groupView.findViewById(R.id.history_second_layer_group_text);
        group_text.setText((String) secondLevelList.get(groupPosition).secondLevelHeader.get(HistoryFragment.DAYS_NAME));

        return groupView;
    }

    /*
        ChildView
     */
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View childView, ViewGroup parent) {

        final int childIndex = childPosition;
        final int groupIndex = groupPosition;
        if(childView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childView = inflater.inflate(R.layout.history_second_layer_child,null);
        }

        TextView name = (TextView) childView.findViewById(R.id.history_childName);
        TextView category = (TextView) childView.findViewById(R.id.history_childDate);
        TextView price = (TextView) childView.findViewById(R.id.history_childPrice);

        name.setText(String.valueOf(secondLevelList.get(groupPosition).thirdLevelList.get(childPosition).thirdLevel.get(HistoryFragment.DAY_COMMENT)));
        category.setText(((Category)secondLevelList.get(groupPosition).thirdLevelList.get(childPosition).thirdLevel.get(HistoryFragment.DAY_CATEGORY)).getName());
        price.setText(String.valueOf(secondLevelList.get(groupPosition).thirdLevelList.get(childPosition).thirdLevel.get(HistoryFragment.DAY_MONEY)));



        // setting color to hole view
        int color = Color.TRANSPARENT;
        Drawable background = childView.getBackground();
        if(background instanceof ColorDrawable) {
            color = ((ColorDrawable) background).getColor();
        }
        childView.getRootView().setBackgroundColor(color);

        // Showing expense data
        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExpenseViewActivity.class);
                Bundle bundle = new Bundle();
                String date = (String)(secondLevelList.get(groupIndex).thirdLevelList.get(childIndex).thirdLevel.get(HistoryFragment.DAY_DATE));
                bundle.putString(HistoryFragment.DATE, date);
                String category = String.valueOf(((Category)secondLevelList.get(groupIndex).thirdLevelList.get(childIndex).thirdLevel.get(HistoryFragment.DAY_CATEGORY)).getName());
                bundle.putString(HistoryFragment.CATEGORY, category);
                String subcat = (String.valueOf(secondLevelList.get(groupIndex).thirdLevelList.get(childIndex).thirdLevel.get(HistoryFragment.DAY_SUBCATEGORY)));
                bundle.putString(HistoryFragment.SUBCATEGORY, subcat);
                String comment = (String.valueOf(secondLevelList.get(groupIndex).thirdLevelList.get(childIndex).thirdLevel.get(HistoryFragment.DAY_COMMENT)));
                bundle.putString(HistoryFragment.COMMENT, comment);
                String money = (String.valueOf(secondLevelList.get(groupIndex).thirdLevelList.get(childIndex).thirdLevel.get(HistoryFragment.DAY_MONEY)));
                bundle.putString(HistoryFragment.MONEY, money);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return childView;
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
        return secondLevelList.get(groupPosition).thirdLevelList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return secondLevelList.get(groupPosition).thirdLevelList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return secondLevelList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return secondLevelList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

}
