package com.ifkbhit.wallet.wallet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.ifkbhit.wallet.wallet.App;
import com.ifkbhit.wallet.wallet.ItemDetail;
import com.ifkbhit.wallet.wallet.R;
import com.ifkbhit.wallet.wallet.tools.CustomRecyclerViewAdapter;
import com.ifkbhit.wallet.wallet.tools.PieChartTools;
import com.ifkbhit.wallet.wallet.user.StatisticInfo;
import com.ifkbhit.wallet.wallet.user.User;

public class InfoFragment extends AbstractFragment {


    private PieChartTools targetPie, globalPie;
    private final User user = User.getInstance();


    public InfoFragment() {
        super();
    }


    @Override
    public boolean onBackPressed() {
        if (user.getStatistic().hideCombined()) {
            globalPie.setData(user.getGlobalStatistic());
            return true;
        }
        return false;
    }


    @Override
    public boolean onMenuItemSelected(int id) {
        if (id == R.id.action_refresh) {
            globalPie.setData(user.getRefreshedGlobalStatistic());
        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_balance);

        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(user.getCardList(), appContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        targetPie = new PieChartTools((PieChart) view.findViewById(R.id.pie_target));

        targetPie.setDrawIcons(false)
                .setDrawValue(true)
                .disableTouch()
                .setNamePosition(PieDataSet.ValuePosition.INSIDE_SLICE)
                //.setValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE)
                .setData(user.getStatisticTarget());


        globalPie = new PieChartTools((PieChart) view.findViewById(R.id.pie_global));

        globalPie.setNamePosition(PieDataSet.ValuePosition.INSIDE_SLICE)
                .setDrawValue(false)
                .setDrawIcons(true)
                .setListener(new OnChartValueSelectedListener() {

                    @Override
                    public void onValueSelected(Entry e, Highlight h) {

                        StatisticInfo.StatisticInfoElement element = user.getStatistic().getStatisticInfoElement((PieEntry) e);
                        if (element.isCombined()) {
                            if (user.getStatistic().openCombined()) {
                                globalPie.setData(user.getGlobalStatistic());
                            }
                        } else {
                            startActivity(new Intent(App.getContext(), ItemDetail.class));
                        }
                    }

                    @Override
                    public void onNothingSelected() {
                        //Toast.makeText(App.getContext(), "Nothing", Toast.LENGTH_SHORT).show();
                    }
                })
                .setData(user.getGlobalStatistic());

        return view;
    }


    @Override
    public void onResume() {
        globalPie.setData(user.getGlobalStatistic(), false);
        super.onResume();
    }


}
