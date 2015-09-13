package ui.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheesehole.expencemanager.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bl.models.DatabaseInstrument;

/**
 * Created by Жамбыл on 09.09.2015.
 */

public class StatisticsLineFragment extends BaseFragment implements OnChartValueSelectedListener {

    LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_statistics_line_chart,null);

        startUI(v);

        return v;
    }

    @Override
    protected void startUI(View v) {
        initLineChart(v);
        setData(10, 60);
    }

    private void initLineChart(View v) {

        lineChart = (LineChart) v.findViewById(R.id.line_chart);
        lineChart.setOnChartValueSelectedListener(this);

        // no description text
        lineChart.setDescription("");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable value highlighting
        lineChart.setHighlightEnabled(true);

        // enable touch gestures
        lineChart.setTouchEnabled(false);

        lineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        // set an alternative background color
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setGridBackgroundColor(ColorTemplate.getHoloBlue());

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        // Animation speed
        //lineChart.animateX(5000);

        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setTypeface(tf);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setGridLineWidth(2);
        xAxis.setGridColor(Color.GRAY);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawAxisLine(true);
//        leftAxis.setTypeface(tf);
        //leftAxis.setAxisMaxValue(200f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridLineWidth(2);
        leftAxis.setGridColor(Color.GRAY);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setTextColor(Color.TRANSPARENT);
        rightAxis.setDrawGridLines(false);
    }

    private void setData(int count, float range) {
        // HorizontalValues
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Jan");
        xVals.add("Feb");
        xVals.add("Mar");
        xVals.add("Apr");
        xVals.add("May");
        xVals.add("Jun");
        xVals.add("Jul");
        xVals.add("Aug");
        xVals.add("Sep");
        xVals.add("Oct");

        // Vertical Values
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = DatabaseInstrument.instance.getSpendsForMonth(DatabaseInstrument.instance.getNumByMonth(xVals.get(i)),
                    Calendar.getInstance().get(Calendar.YEAR));
            yVals1.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(2f);
        set1.setCircleSize(6f);
        set1.setCircleColorHole(Color.WHITE);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(true);
//        set1.setDrawHorizontalHighlightIndicator(false);
//        set1.setVisible(false);
//        set1.setCircleHoleColor(Color.WHITE);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.TRANSPARENT);
        data.setValueTextSize(9f);

        // set data
        lineChart.setData(data);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

}
