package com.ifkbhit.wallet.wallet.user;

import android.content.SharedPreferences;
import android.util.Log;

import com.github.mikephil.charting.data.PieEntry;
import com.ifkbhit.wallet.wallet.App;
import com.ifkbhit.wallet.wallet.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class StatisticInfo {


    enum Type {FOOD, BILLS, ENTERTAINMENT, SHOPPING, TRANSPORT, OTHER, HOUSE_HOLD, COMBINED}

    private static final String LOG_TAG = "StatisticInfo";

    private boolean isCombinedHidden = true;
    private double target, spendMoney;

    private List<PieEntry> globalStat = new ArrayList<>();
    private List<PieEntry> combinedStat = new ArrayList<>();
    private List<PieEntry> currentEntries = new ArrayList<>();

    private List<StatisticInfoElement> combinedStatisticInfoElements = new ArrayList<>();
    private List<StatisticInfoElement> statisticInfoElements = new ArrayList<>();

    private Map<Type, Integer> icons = new HashMap<>();
    private SharedPreferences sp;

    public class StatisticInfoElement {
        public Type getType() {
            return type;
        }

        double getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        void incValue(double incValue) {
            value += incValue;
        }

        private Type type;
        private double value;
        private String label;

        StatisticInfoElement(Type type, double value, String label) {
            this.type = type;
            this.value = value;
            this.label = label;
        }


        PieEntry toPieEntry() {
            return new PieEntry((float) value, App.getVectorDrawableCompatById(icons.get(type)));
        }

        @Override
        public String toString() {
            return "(" + label + " " + value + ")";
        }

        public boolean isCombined() {
            return type == Type.COMBINED;
        }
    }

    StatisticInfo(SharedPreferences sp) {
        icons.put(Type.FOOD, R.drawable.ic_food);
        icons.put(Type.BILLS, R.drawable.ic_bills);
        icons.put(Type.ENTERTAINMENT, R.drawable.ic_entertainment);
        icons.put(Type.SHOPPING, R.drawable.ic_shopping);
        icons.put(Type.TRANSPORT, R.drawable.ic_transport);
        icons.put(Type.OTHER, R.drawable.ic_family);   // TODO change icon
        icons.put(Type.HOUSE_HOLD, R.drawable.ic_household);
        icons.put(Type.COMBINED, R.drawable.ic_combined);
        loadTarget(sp);
        this.sp = sp;
        loadStatistic(sp);
        buildGlobalStatistic();

    }

    public boolean openCombined() {
        if(!isCombinedHidden) {
            return false;
        }
        isCombinedHidden = false;
        currentEntries = combinedStat;
        return true;
    }

    public boolean hideCombined() {
        if (isCombinedHidden) {
            return false;
        }
        currentEntries = globalStat;
        isCombinedHidden = true;
        return true;
    }


    private void loadStatistic(SharedPreferences sp) {
        loadSubStatistic(sp);
    }

    private void loadSubStatistic(SharedPreferences sp) {  //Todo split random and storage
        Set<String> set = sp.getStringSet(App.GROUPS_SET, null);

        String[] names = App.getContext().getResources().getStringArray(R.array.groups_list);
        if (set != null) {
            List<String> tmp = new ArrayList<>(set);
            Type[] base = Type.values();
            for (int i = 0; i < base.length; i++) {
                statisticInfoElements.add(new StatisticInfoElement(base[i], Float.parseFloat(tmp.get(i).split("<>")[0]), base[i].name()));
            }
        } else if (App.DEBUG_MODE) {
            Type[] base = Type.values();
            double tmpLost = spendMoney;
            for (int i = 0; i < base.length - 1; i++) {
                double next = App.getRandomInt((int) tmpLost);
                if (tmpLost < next) {
                    next = tmpLost;
                }
                if (next != 0) {
                    statisticInfoElements.add(new StatisticInfoElement(base[i], next, names[i]));
                    tmpLost -= next;
                }
            }
        }
    }

    private void loadTarget(SharedPreferences sp) {
        target = sp.getFloat(App.BALANCE_TARGET, -1);
        if (target != -1) {
            spendMoney = sp.getFloat(App.BALANCE_LOST, 0);
        } else {
            if (App.DEBUG_MODE) {
                target = 310000;
                spendMoney = 16543.1;
            } else {
                spendMoney = target = 0;
            }
        }

    }

    public StatisticInfoElement getStatisticInfoElement(PieEntry e) {
        int pos = -1;
        for (int i = 0; i < currentEntries.size(); i++) {
            if (currentEntries.get(i).getValue() == e.getValue()) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            return null;
        }
        if (!isCombinedHidden) {
            return combinedStatisticInfoElements.get(pos);
        }
        return statisticInfoElements.get(pos);
    }


    List<PieEntry> getTargetEntry() {
        List<PieEntry> entryList = new ArrayList<>();
        entryList.add(new PieEntry((float) (target - spendMoney), "Free"));
        entryList.add(new PieEntry((float) (spendMoney), "Lost"));
        return entryList;
    }


    private StatisticInfoElement buildCombined() {
        if (statisticInfoElements.size() == 0) {
            return null;
        }
        Collections.sort(statisticInfoElements, new Comparator<StatisticInfoElement>() {
            @Override
            public int compare(StatisticInfoElement s1, StatisticInfoElement s2) {
                double v1 = s1.getValue();
                double v2 = s2.getValue();
                return Double.compare(v1, v2);
            }
        });
        Log.d(LOG_TAG, statisticInfoElements.toString());
        double minValue = spendMoney / 360.0 * (double) App.MIN_DEGREE_PIE;
        if (statisticInfoElements.get(0).value > minValue) {
            return null;
        }
        StatisticInfoElement combined = new StatisticInfoElement(Type.COMBINED, 0, "Combined");
        int i = 0;
        while (statisticInfoElements.get(i).value < minValue) {
            Log.d(LOG_TAG, statisticInfoElements.get(i).toString() + " combined");
            combined.incValue(statisticInfoElements.get(i).value);
            combinedStatisticInfoElements.add(statisticInfoElements.remove(i));
        }
        if (combined.value < minValue) {
            combined.value = minValue;
        }
        for (StatisticInfoElement statisticInfoElement : combinedStatisticInfoElements) {
            combinedStat.add(statisticInfoElement.toPieEntry());
        }
        return combined;
    }


    private void buildGlobalStatistic() {
        Log.d(LOG_TAG, "Start. Have " + statisticInfoElements.size() + "elements");

        int n = statisticInfoElements.size();
        StatisticInfoElement combined = buildCombined();

        if (combined != null) {
            globalStat.add(combined.toPieEntry());
        }

        for (int i = 0; i < statisticInfoElements.size(); i++) {
            StatisticInfoElement s = statisticInfoElements.get(i);
            Log.d(LOG_TAG, s.toString() + " added");
            globalStat.add(s.toPieEntry());
        }
        Log.d(LOG_TAG, String.format("Result: combined size is %d(%s), global stat is %d(expected %d)", combinedStatisticInfoElements.size(), combinedStatisticInfoElements.toString(), globalStat.size(), n - combinedStatisticInfoElements.size() + 1));
        statisticInfoElements.add(0, combined);
        currentEntries = globalStat;
    }

    List<PieEntry> getRefreshedGlobalStatistic() {
        statisticInfoElements.clear();
        isCombinedHidden = true;
        combinedStat.clear();
        globalStat.clear();
        combinedStatisticInfoElements.clear();
        loadStatistic(sp);
        buildGlobalStatistic();
        return getGlobalStatistic();
    }

    List<PieEntry> getGlobalStatistic() {
        return currentEntries;
    }
}
