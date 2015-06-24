package ui.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cheesehole.expencemanager.R;

import java.util.ArrayList;

import ui.activities.MainActivity;
import ui.helpers.FinancesExpListBundle;

/**
 * Created by Жамбыл on 15.06.2015.
 */
public class FinancesExpListViewAdapter extends BaseExpandableListAdapter {

    // Context
    Context mContext;

    // ExpandableListView itself
    ExpandableListView listView;

    // Adapter of calc
    MoneyExpListAdapter moneyAdapter;

    // Variables
    int lastExpandedGroupPosition;

    boolean isChosen = false;
    int[]chosenId = new int[2];;

    ArrayList<FinancesExpListBundle> bundles;



    /*
        Constructor
     */

    public FinancesExpListViewAdapter(Context context, ArrayList<FinancesExpListBundle> bundles, MoneyExpListAdapter moneyAdapter) {
        mContext = context;
        this.bundles = bundles;
        this.moneyAdapter = moneyAdapter;
    }

    @Override
    public int getGroupCount() {
        return bundles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bundles.get(groupPosition).childViews.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return bundles.get(groupPosition).groupView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return bundles.get(groupPosition).childViews.get(childPosition);
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

        convertView = (View) getGroup(groupPosition);
        // Parent's TextView
        TextView group = (TextView) convertView.findViewById(R.id.finances_textGroup);
        group.setTextSize(24);
        group.setPadding(58, 0, 0, 0);

        // Setting font
        group.setTypeface(MainActivity.robotoRegular);


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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, final ViewGroup parent) {
        convertView = (View) getChild(groupPosition,childPosition);

        // Child's TextView
        final TextView childName = (TextView) convertView.findViewById(R.id.finances_childName);


        // Setting fonts
        childName.setTypeface(MainActivity.robotoRegular);

        childName.setTextSize(16);


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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < getGroupCount();i++) {
                    for(int k = 0; k < getChildrenCount(i); k++) {
                        View child = (View)getChild(i,k);
                        // Change color it was chosen
                        if( ((ColorDrawable)child.getBackground()).getColor()
                                == mContext.getResources().getColor(R.color.FinancesColorPrimary)) {
                            child.setBackgroundColor(Color.WHITE);
                            ((TextView) child.findViewById(R.id.finances_childName)).setTextColor(Color.BLACK);
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }

                // Setting color to chosen
                v.setBackgroundColor(mContext.getResources().getColor(R.color.FinancesColorPrimary));
                ((TextView)v.findViewById(R.id.finances_childName)).setTextColor(Color.WHITE);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}