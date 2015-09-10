package ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheesehole.expencemanager.R;

import ui.adapters.TabViewPagerAdapter;
import ui.helpers.SlidingTabLayout;

/**
 * Created by Жамбыл on 09.09.2015.
 */
public class StatisticsFragment extends BaseFragment {

    int NumbOfTabs = 2;
    ViewPager pager;
    TabViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Pie Chart","Line Chart"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_statistics,null);

        startUI(v);

        return v;
    }

    @Override
    protected void startUI(View v) {
        initTabs(v);
    }



    private void initTabs(View v) {
        adapter =  new TabViewPagerAdapter(getFragmentManager(),Titles, NumbOfTabs);
        pager = (ViewPager) v.findViewById(R.id.statistics_pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) v.findViewById(R.id.statistics_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.GRAY;
            }
        });
    }
}
