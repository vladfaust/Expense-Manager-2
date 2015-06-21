package ui.helpers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ui.activities.History;

/**
 * Created by Жамбыл on 21.06.2015.
 */

public class HistoryFirstLevelAdapter extends BaseExpandableListAdapter {
        Context context;
        ArrayList<Map<String, Object>> monthsData;
        ArrayList<ArrayList<Map<String, Object>>> daysData;
        ArrayList<ArrayList<ArrayList<Map<String, Object>>>> dayData;

        public HistoryFirstLevelAdapter(Context context, ArrayList<Map<String, Object>> monthsData, ArrayList<ArrayList<Map<String, Object>>> daysData, ArrayList<ArrayList<ArrayList<Map<String, Object>>>> dayData) {
            this.context = context;
            this.monthsData = monthsData;
            this.daysData = daysData;
            this.dayData = dayData;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.history_first_layer,null);
            }

            TextView monthName = (TextView)convertView.findViewById(R.id.history_first_layer_month);
            TextView monthMoney = (TextView)convertView.findViewById(R.id.history_first_layer_money);

            monthName.setText((String) monthsData.get(groupPosition).get(History.MONTHS_NAME));
            monthMoney.setText((String) monthsData.get(groupPosition).get(History.MONTHS_MONEY));

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            CustExpListview SecondLevelexplv = new CustExpListview(context);
            SecondLevelexplv.setAdapter(new HistorySecondLevelAdapter(context,daysData,dayData,groupPosition));
            SecondLevelexplv.setGroupIndicator(null);
            return SecondLevelexplv;
        }

        @Override
        public Object getChild(int groupPosiotion, int childPosition) {
            return daysData.get(groupPosiotion).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }


        @Override
        public int getChildrenCount(int groupPosition) {
            return daysData.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount() {
            return monthsData.size();
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

