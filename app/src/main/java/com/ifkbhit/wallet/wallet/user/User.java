package com.ifkbhit.wallet.wallet.user;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.github.mikephil.charting.data.PieEntry;
import com.ifkbhit.wallet.wallet.App;
import com.ifkbhit.wallet.wallet.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class User {

    @SuppressLint("StaticFieldLeak")
    private static User instance = null;

    private List<Card> cards = new ArrayList<>();
    private SharedPreferences sp = null;
    private List<PieEntry> statisticTarget;

    private StatisticInfo statisticInfo;

    public static void init(){
        instance = new User();
    }

    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }


    private User(){
        sp = App.getContext().getSharedPreferences(App.getContext().getString(R.string.shared_pref), Context.MODE_PRIVATE);
        statisticInfo = new StatisticInfo(sp);

        loadCards();
        loadStatisticTarget();
    }

    private void loadStatisticTarget() {
        statisticTarget = new ArrayList<>();
        statisticTarget.addAll(statisticInfo.getTargetEntry());
    }


    public List<Card> getCardList(){
        return cards;
    }

    private void loadCards(){
        int count = sp.getInt(App.BALANCES_COUNT, 0);
        if (count > 0) {
            Set<String> set = sp.getStringSet(App.BALANCE_SET, null);
            if (null != set) {
                for (String s : set) {
                    cards.add(new Card(s));
                }
            }
        } else {
            // TESTING
            if(!App.DEBUG_MODE){
                return;
            }
            cards.add(new Card(Card.TYPE_WALLET, "My wallet", App.getContext().getString(R.string.rub_sym), 7653.43));
            cards.add(new Card(Card.TYPE_CARD, "VISA", App.getContext().getString(R.string.eu_sym), 56.3));
            cards.add(new Card(Card.TYPE_BANK, "Sberbank", App.getContext().getString(R.string.rub_sym), 117653.43));
            cards.add(new Card(Card.TYPE_WALLET, "My wallet", App.getContext().getString(R.string.rub_sym), 7653.43));
            cards.add(new Card(Card.TYPE_CARD, "VISA", App.getContext().getString(R.string.eu_sym), 56.3));
            cards.add(new Card(Card.TYPE_BANK, "Sberbank", App.getContext().getString(R.string.rub_sym), 117653.43));
            cards.add(new Card(Card.TYPE_WALLET, "My wallet", App.getContext().getString(R.string.rub_sym), 7653.43));
            cards.add(new Card(Card.TYPE_CARD, "VISA", App.getContext().getString(R.string.eu_sym), 56.3));
            cards.add(new Card(Card.TYPE_BANK, "SberbankVeryLongname", App.getContext().getString(R.string.rub_sym), 117653.43));
        }
    }

    public List<PieEntry> getStatisticTarget() {
        return statisticTarget;
    }

    public StatisticInfo getStatistic() {
        return statisticInfo;

    }

    public List<PieEntry> getGlobalStatistic() {
        return statisticInfo.getGlobalStatistic();
    }

    public List<PieEntry> getRefreshedGlobalStatistic(){
        return statisticInfo.getRefreshedGlobalStatistic();
    }


}
