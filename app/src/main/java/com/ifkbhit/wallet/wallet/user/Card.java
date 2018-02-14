package com.ifkbhit.wallet.wallet.user;

import com.ifkbhit.wallet.wallet.R;

import java.util.Locale;


public class Card {
    static final int TYPE_CARD = 0;
    static final int TYPE_WALLET = 1;
    static final int TYPE_BANK = 2;
    public static final int TYPE_ADD = 3;
    private final int[] icons = {R.drawable.ic_card_drawable, R.drawable.ic_wallet, R.drawable.ic_bank, R.drawable.ic_add_button};
    private int type;
    private String currency;
    private String name;
    private double amount;

    Card(int type, String name, String currency, double amount) {
        this.type = type;
        this.name = name;
        this.currency = currency;
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public Card() {
        type = TYPE_ADD;
        name = "Add";
        amount = -1;
    }

    public int getIconId() {
        return icons[type];
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        if (type == TYPE_ADD) {
            return "";
        }
        return String.format(Locale.UK, "%.2f %s", amount, currency);
    }

    Card(String cardString) {
        String[] parse = cardString.split("!#!");
        type = Integer.parseInt(parse[0]);
        name = parse[1];
        currency = parse[2];
        amount = Double.parseDouble(parse[3]);
    }

    @Override
    public String toString() {
        return String.format(Locale.UK, "%d!#!%s!#!%s!#!%.2f", type, name, currency, amount);
    }
}