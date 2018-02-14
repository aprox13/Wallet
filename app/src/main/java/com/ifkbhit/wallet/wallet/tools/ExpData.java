package com.ifkbhit.wallet.wallet.tools;

import com.ifkbhit.wallet.wallet.R;

import java.util.ArrayList;

public class ExpData<T>{

    int getViewId() {
        return (type == Type.SUB_GROUPS)? R.layout.item_exp_sub: R.layout.item_exp_history;
    }

    enum Type{SUB_GROUPS, HISTORY};


    private String name;
    private boolean enable;
    private ArrayList<T> data;
    private Type type;

    public ExpData(Type type, String name, ArrayList<T> data) {
        this.name = name;
        this.enable = data.size() != 0;
        this.data = data;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isEnable() {
        return enable;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public boolean isSubGroups(){
        return type == Type.SUB_GROUPS;
    }

}
