package com.ifkbhit.wallet.wallet.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractFragment extends Fragment {

    protected Context appContext = null;
    protected Activity currentActivity = null;

    public void setAppContext(Context context){
        appContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = getActivity();
    }


    public abstract boolean onBackPressed();

    public boolean onMenuItemSelected(int id){
        return true;
    }

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
