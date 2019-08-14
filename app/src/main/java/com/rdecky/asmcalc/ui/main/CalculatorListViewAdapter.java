package com.rdecky.asmcalc.ui.main;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;

public class CalculatorListViewAdapter implements ListAdapter {
    private static final String[] BUTTON_SYMBOLS = {
            "Lsh", "Rsh", "Or", "Xor", "Not", "And",
            "MS", "Mod", "CE", "Clear", "Bksp", "/",
            "A", "B", "7", "8", "9", "*",
            "C", "D", "4", "5", "6", "-",
            "E", "F", "1", "2", "3", "+",
            "(", ")", "+/-", "0", ".", "="
    };

    private Context context;

    public CalculatorListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 36;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            Button button = new Button(context);
            button.setText(BUTTON_SYMBOLS[position]);
            return button;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
