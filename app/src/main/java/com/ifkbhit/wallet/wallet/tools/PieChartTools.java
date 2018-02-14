package com.ifkbhit.wallet.wallet.tools;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.ifkbhit.wallet.wallet.App;

import java.util.List;


public class PieChartTools {
    private final PieChart chart;
    private boolean drawValue = true;
    private boolean drawIcons = false;
    private PieDataSet.ValuePosition position = PieDataSet.ValuePosition.INSIDE_SLICE;
    private String name;
    private boolean disableLines = false;

    public PieChartTools(PieChart chart){
        this.chart = chart;
        this.name = "";

    }

    public void setData(List<PieEntry> data){
        setData(data, true);
    }

    public void setData(List<PieEntry> data, boolean withAnimation){
        App.preparePieChart(chart, data, name, Color.BLACK, Color.GRAY, position, withAnimation, disableLines);
        if(!drawValue){
            for(IDataSet<?> set: chart.getData().getDataSets()){
                set.setDrawValues(false);
            }
        }
        if(drawIcons){
            chart.getData().getDataSet().setIconsOffset(new MPPointF(0, App.GLOBAL_PIE_Y_ICON_OFFSET));
        }
    }


    public PieChartTools setDrawValue(boolean drawValue) {
        this.drawValue = drawValue;
        return this;
    }

    public PieChartTools setNamePosition(PieDataSet.ValuePosition position) {
        this.position = position;
        return this;
    }

    public PieChartTools setValuePosition(PieDataSet.ValuePosition position){
        ((PieDataSet)chart.getData().getDataSet()).setYValuePosition(position);
        return this;
    }

    public PieChartTools setDrawIcons(boolean drawIcons) {
        this.drawIcons = drawIcons;
        return this;
    }

    public PieChartTools setListener(OnChartValueSelectedListener listener) {
        chart.setOnChartValueSelectedListener(listener);
        return this;
    }

    public PieChartTools disableLines(){
        disableLines = true;
        return this;
    }

    public PieChartTools disableTouch(){
        chart.setTouchEnabled(false);
        return this;
    }
}
