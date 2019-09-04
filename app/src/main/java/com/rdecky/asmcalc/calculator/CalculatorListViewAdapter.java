package com.rdecky.asmcalc.calculator;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.rdecky.asmcalc.calculator.buttonValue.ButtonValue;
import com.rdecky.asmcalc.calculator.buttonValue.DecButtonValue;
import com.rdecky.asmcalc.calculator.buttonValue.HexButtonValue;
import com.rdecky.asmcalc.calculator.buttonValue.OperatorButtonValue;
import com.rdecky.asmcalc.calculator.buttonValue.SpecialButtonValue;

import java.util.ArrayList;
import java.util.List;

public class CalculatorListViewAdapter implements ListAdapter {
    private static final String[] BUTTON_SYMBOLS = {
            "Lsh", "Rsh", "Or", "Xor", "Not", "And",
            "MS", "Mod", "CE", "Clear", "Bksp", "/",
            "A", "B", "7", "8", "9", "*",
            "C", "D", "4", "5", "6", "-",
            "E", "F", "1", "2", "3", "+",
            "(", ")", "+/-", "0", ".", "="
    };

    private static final ButtonValue[] ROW_1 = {
            new OperatorButtonValue("Lsh"),
            new OperatorButtonValue("Rsh"),
            new OperatorButtonValue("Or"),
            new OperatorButtonValue("Xor"),
            new OperatorButtonValue("Not"),
            new OperatorButtonValue("And")
    };

    private static final ButtonValue[] ROW_2 = {
            new SpecialButtonValue("MS"),
            new OperatorButtonValue("Mod"),
            new SpecialButtonValue("CE"),
            new SpecialButtonValue("Clear"),
            new SpecialButtonValue("Bksp"),
            new OperatorButtonValue("/")
    };

    private static final ButtonValue[] ROW_3 = {
            new HexButtonValue("A"),
            new HexButtonValue("B"),
            new DecButtonValue("7"),
            new DecButtonValue("8"),
            new DecButtonValue("9"),
            new OperatorButtonValue("*")
    };

    private static final ButtonValue[] ROW_4 = {
            new HexButtonValue("C"),
            new HexButtonValue("D"),
            new DecButtonValue("4"),
            new DecButtonValue("5"),
            new DecButtonValue("6"),
            new OperatorButtonValue("-")
    };

    private static final ButtonValue[] ROW_5 = {
            new HexButtonValue("E"),
            new HexButtonValue("F"),
            new DecButtonValue("1"),
            new DecButtonValue("2"),
            new DecButtonValue("3"),
            new OperatorButtonValue("+")
    };

    private static final ButtonValue[] ROW_6 = {
            new OperatorButtonValue("("),
            new OperatorButtonValue(")"),
            new SpecialButtonValue("+/-"),
            new DecButtonValue("0"),
            new SpecialButtonValue("."),
            new OperatorButtonValue("=")
    };

    private Context context;
    private CalculatorViewModel viewModel;
    private List<CalculatorButton> allButtons;

    CalculatorListViewAdapter(Context context, CalculatorViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        allButtons = new ArrayList<>();
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
        if (convertView == null) {
            return createNewButton(BUTTON_SYMBOLS[position]);
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

    private View createNewButton(String buttonSymbol) {
        final CalculatorButton button = new CalculatorButton(context);
        button.setText(buttonSymbol);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.buttonPressed(button);
            }
        });
        allButtons.add(button);
        return button;
    }

    List<CalculatorButton> getAllButtons() {
        return allButtons;
    }
}
