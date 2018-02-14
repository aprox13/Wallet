package com.ifkbhit.wallet.wallet.tools;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ifkbhit.wallet.wallet.AddCardActivity;
import com.ifkbhit.wallet.wallet.R;
import com.ifkbhit.wallet.wallet.user.Card;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder> {

    private final Context context;
    private final List<Card> data;

    public CustomRecyclerViewAdapter(List<Card> data, Context context) {
        this.context = context;

        this.data = new ArrayList<>();
        this.data.addAll(data);
        this.data.add(new Card());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_item, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final Card current = data.get(position);

        holder.amount.setText(current.getAmount());
        holder.setIsAdd(current.getType() == Card.TYPE_ADD);
        Log.d("onBindHolder", "Position " + position);
        holder.icon.setImageResource(current.getIconId());
        holder.name.setText(current.getName());
        if (current.getType() == Card.TYPE_ADD) {
            holder.cardView.setVisibility(View.INVISIBLE);
            holder.addIcon.setVisibility(View.VISIBLE);
            holder.addIcon.setImageResource(current.getIconId());
        } else {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.addIcon.setVisibility(View.INVISIBLE);
        }

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isAdd) {
                    Intent i = new Intent(context, AddCardActivity.class);
                    context.startActivity(i);
                }
            }
        });
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        View parent;
        CardView cardView;
        ImageView icon, addIcon;
        TextView name, amount;

        boolean isAdd = false;

        void setIsAdd(boolean isAdd) {
            this.isAdd = isAdd;
        }

        CustomViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.balance_card_view);
            addIcon = (ImageView) itemView.findViewById(R.id.for_add_img_view);
            icon = (ImageView) cardView.findViewById(R.id.card_icon);
            name = (TextView) cardView.findViewById(R.id.card_name);
            amount = (TextView) cardView.findViewById(R.id.card_amount);
            parent = itemView;
        }

        void setOnClickListener(@Nullable View.OnClickListener l) {
            parent.setOnClickListener(l);
        }
    }
}