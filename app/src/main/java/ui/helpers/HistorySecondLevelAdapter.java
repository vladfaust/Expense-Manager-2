package ui.helpers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Жамбыл on 21.06.2015.
 */
public class HistorySecondLevelAdapter extends BaseExpandableListAdapter {

    Context context;
    ArrayList<ArrayList<Map<String, Object>>> daysData;
    ArrayList<ArrayList<ArrayList<Map<String, Object>>>> dayData;
    int baseGroupPosition;

    public HistorySecondLevelAdapter(Context context, ArrayList<ArrayList<Map<String, Object>>> daysData, ArrayList<ArrayList<ArrayList<Map<String, Object>>>> dayData, int groupPosition) {
        this.context = context;
        this.daysData = daysData;
        this.dayData = dayData;
        this.baseGroupPosition = groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.history_second_layer_group,null);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.history_second_layer_child,null);
        }

        return convertView;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dayData.get(baseGroupPosition).get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return dayData.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return daysData.size();
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
