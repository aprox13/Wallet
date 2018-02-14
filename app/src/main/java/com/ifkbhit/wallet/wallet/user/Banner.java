package com.ifkbhit.wallet.wallet.user;

import android.view.View;
import android.widget.TextView;

import com.ifkbhit.wallet.wallet.R;

import java.util.ArrayList;

/**
 * Created by Roman on 14.02.2018.
 */

public class Banner {

    private int[] ids = {R.id.tv_banner_target, R.id.tv_banner_spent, R.id.tv_banner_available, R.id.tv_banner_spent_today, R.id.tv_banner_rec};
    private ArrayList<TextView> bannerItems = new ArrayList<>();
    Banner(View parentView){
        for (int id : ids) {
            bannerItems.add((TextView)parentView.findViewById(id));
        }
    }

    public void update(StatisticInfo info) {
        String[] base = getFakeItem();
        for (int i = 0; i < base.length; i++) {
            bannerItems.get(i).setText(base[i]);
        }
    }

    public String[] getFakeItem() {
        return new String[]{

        };
    }
}
