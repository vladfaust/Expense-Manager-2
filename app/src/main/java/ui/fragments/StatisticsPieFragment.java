package ui.fragments;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cheesehole.expencemanager.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;

/**
 * Created by Жамбыл on 09.09.2015.
 */
public class StatisticsPieFragment extends BaseFragment {

    // Views
    PieChart pieChart;
    LinearLayout mainLayout;

    float[] yData = {10,46,57,7.2f};
    String[] xData = {"Jan", "Dec","Mar", "Arp", "May"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_statistics_pie_chart,null);

        startUI(v);

        return v;
    }

    @Override
    protected void startUI(View v) {
        initPieChart(v);
        addData();
    }

    private void initPieChart(final View v) {
        mainLayout = (LinearLayout)v.findViewById(R.id.statistics_main_layout);
        pieChart = ((PieChart)v.findViewById(R.id.pie_chart));

        mainLayout.setBackgroundColor(Color.WHITE);

        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(50);
        pieChart.setTransparentCircleRadius(100);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null) {
                    return;
                }

                Toast.makeText(v.getContext(), xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addData() {
        ArrayList<Entry> yVals = new ArrayList<>();

        for(int i = 0; i < yData.length; i++) {
            yVals.add(new Entry(yData[i], i));
        }

        ArrayList<String> xVals = new ArrayList<>();
        for(int i = 0; i < xData.length; i++) {
            xVals.add(xData[i]);
        }

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<>();

        for(int c : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }
        for(int c : ColorTemplate.JOYFUL_COLORS) {
            colors.add(c);
        }
        for(int c : ColorTemplate.COLORFUL_COLORS) {
            colors.add(c);
        }
        for(int c : ColorTemplate.PASTEL_COLORS) {
            colors.add(c);
        }

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(xVals,dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.GRAY);

        pieChart.setData(data);

        pieChart.highlightValues(null);

        pieChart.invalidate();

    }
}
