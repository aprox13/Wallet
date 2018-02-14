package com.ifkbhit.wallet.wallet.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ifkbhit.wallet.wallet.App;
import com.ifkbhit.wallet.wallet.R;

import java.util.ArrayList;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final ArrayList<ExpData> data;
    private final Context context;

    public ExpandableListAdapter(ArrayList<ExpData> data) {
        this.data = data;
        this.context = App.getContext();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public ExpData getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public ExpData getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.item_exp_label, parent);
            }
        }
        if (convertView == null) {
            return null;
        }
        ExpData<?> current = getGroup(groupPosition);
        TextView label = (TextView) convertView.findViewById(R.id.item_exp_label);
        label.setText(current.getName());
        if (!current.isEnable()) {
            label.setEnabled(false);
        } else {
            label.setEnabled(true);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.item_exp_label, parent);
            }
        }
        if (convertView == null) {
            return null;
        }
        ExpData<?> current = getChild(groupPosition, childPosition);
        if(current.isSubGroups()){


        } else {


        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
