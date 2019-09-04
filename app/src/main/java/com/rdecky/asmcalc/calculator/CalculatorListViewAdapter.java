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
    private static final ButtonValue[] BUTTON_VALUES = {
            //Row 1
            new OperatorButtonValue("Lsh"),
            new OperatorButtonValue("Rsh"),
            new OperatorButtonValue("Or"),
            new OperatorButtonValue("Xor"),
            new OperatorButtonValue("Not"),
            new OperatorButtonValue("And"),
            //Row 2
            new SpecialButtonValue("MS"),
            new OperatorButtonValue("Mod"),
            new SpecialButtonValue("CE"),
            new SpecialButtonValue("Clear"),
            new SpecialButtonValue("Bksp"),
            new OperatorButtonValue("/"),
            //Row 3
            new HexButtonValue("A"),
            new HexButtonValue("B"),
            new DecButtonValue("7"),
            new DecButtonValue("8"),
            new DecButtonValue("9"),
            new OperatorButtonValue("*"),
            //Row 4
            new HexButtonValue("C"),
            new HexButtonValue("D"),
            new DecButtonValue("4"),
            new DecButtonValue("5"),
            new DecButtonValue("6"),
            new OperatorButtonValue("-"),
            //Row 5
            new HexButtonValue("E"),
            new HexButtonValue("F"),
            new DecButtonValue("1"),
            new DecButtonValue("2"),
            new DecButtonValue("3"),
            new OperatorButtonValue("+"),
            //Row 6
            new OperatorButtonValue("("),
            new OperatorButtonValue(")"),
            new SpecialButtonValue("+/-"),
            new DecButtonValue("0"),
            new SpecialButtonValue("."),
            new SpecialButtonValue("=")
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
            return createNewButton(BUTTON_VALUES[position]);
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

    List<CalculatorButton> getAllButtons() {
        return allButtons;
    }

    private View createNewButton(ButtonValue buttonValue) {
        final CalculatorButton button = new CalculatorButton(context, buttonValue);
        setButtonClickListener(button);
        allButtons.add(button);
        return button;
    }

    private void setButtonClickListener(final CalculatorButton button) {
        final ButtonValue buttonValue = button.getValue();

        if (buttonValue instanceof SpecialButtonValue) {
            setSpecialButtonClickListener(button, buttonValue);
        } else if (buttonValue instanceof OperatorButtonValue) {
            setOperatorButtonClickListener(button, buttonValue);
        } else {
            setRegularButtonClickListener(button, buttonValue);
        }
    }

    private void setRegularButtonClickListener(CalculatorButton button, final ButtonValue buttonValue) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.regularButtonPressed(buttonValue);
            }
        });
    }

    private void setSpecialButtonClickListener(CalculatorButton button, ButtonValue buttonValue) {
        final SpecialButtonValue specialButtonValue = (SpecialButtonValue) buttonValue;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.specialButtonPressed(specialButtonValue);
            }
        });
    }

    private void setOperatorButtonClickListener(CalculatorButton button, ButtonValue buttonValue) {
        final OperatorButtonValue operatorButtonValue = (OperatorButtonValue) buttonValue;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.operatorButtonPressed(operatorButtonValue);
            }
        });
    }
}
