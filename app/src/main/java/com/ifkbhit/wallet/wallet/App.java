package com.ifkbhit.wallet.wallet;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;


public class App extends Application {

    public static final boolean DEBUG_MODE = true;


    public static final String BALANCES_COUNT = "balancesCount";
    public static final String BALANCE_SET = "balanceSet";
    public static final String BALANCE_TARGET = "balanceTarget";
    public static final String BALANCE_LOST = "balanceLost";
    //public static final String SUB_GROUPS_SIZE = "groupsSize";
    public static final String GROUPS_SET = "groupsSet";


    //PIE CONSTANT
    public static final float GLOBAL_PIE_Y_ICON_OFFSET = 60;
    private static final float pieOffsetX = 14;
    private static final float pieOffsetY = 11;

    public static final int MIN_DEGREE_PIE = 15;
    private static final float linePart1length = 0.4f;
    private static final float linePart2length = 0.6f;
    private static final float part1Offset = 80;
    public static final float DRAG_COEFFICIENT = 0f;


    @SuppressLint("StaticFieldLeak")
    private static Context context = null;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }

    public static int getRandomInt(int to) {
        return 10 + (int) (Math.random() * ((to - 10) + 1));
    }

    public static VectorDrawableCompat getVectorDrawableCompatById(int id) {

        return VectorDrawableCompat.create(context.getResources(), id, null);
    }


    public static void preparePieChart(PieChart chart, List<PieEntry> data, String descriptionName, int valueColor, int labelColor, PieDataSet.ValuePosition labelPosition, boolean enableAnimation, boolean disableLines) {

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawHoleEnabled(false);
        //chart.setDragDecelerationFrictionCoef(App.DRAG_COEFFICIENT);
        chart.setDragDecelerationEnabled(false);
        if (enableAnimation) chart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        chart.setExtraOffsets(pieOffsetX, (pieOffsetY), pieOffsetX, (pieOffsetY));

        Description description = new Description();
        description.setText(descriptionName);
        description.setTextColor(Color.BLACK);
        chart.setDescription(description);


        PieDataSet dataSet = new PieDataSet(data, "");
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setXValuePosition(labelPosition);
        dataSet.setValueLineColor(Color.BLACK);
        if (!disableLines) {
            dataSet.setValueLinePart1Length(linePart1length);
            dataSet.setValueLinePart2Length(linePart2length);
            dataSet.setValueLinePart1OffsetPercentage(part1Offset);
        } else {
            dataSet.setValueLineVariableLength(true);
            Log.d("APP", "DISABLED");
        }
        if (valueColor != 1) {
            dataSet.setValueTextColor(valueColor);
        } else {
            dataSet.setDrawValues(false);
        }
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);

        chart.clear();
        chart.setData(pieData);
        chart.setEntryLabelColor(labelColor);
        chart.invalidate();
    }


}
