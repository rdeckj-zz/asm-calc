package com.rdecky.asmcalc.calculator;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.calculator.button.CalculatorButton;
import com.rdecky.asmcalc.calculator.button.CalculatorImageButton;
import com.rdecky.asmcalc.calculator.button.CalculatorTextButton;
import com.rdecky.asmcalc.calculator.value.ButtonValue;
import com.rdecky.asmcalc.calculator.value.DecButtonValue;
import com.rdecky.asmcalc.calculator.value.HexButtonValue;
import com.rdecky.asmcalc.calculator.value.OperatorValue;
import com.rdecky.asmcalc.calculator.value.SpecialButtonValue;

import java.util.ArrayList;
import java.util.List;

public class CalculatorListViewAdapter implements ListAdapter {
    private static final ButtonValue[] BUTTON_VALUES = {
            //Row 1
            new OperatorValue("Lsh"),
            new OperatorValue("Rsh"),
            new OperatorValue("Or"),
            new OperatorValue("Xor"),
            new OperatorValue("Not"),
            new OperatorValue("And"),
            //Row 2
            new SpecialButtonValue("MS"),
            new OperatorValue("Mod"),
            new SpecialButtonValue("CE"),
            new SpecialButtonValue("Clear"),
            new SpecialButtonValue("Bksp"),
            new OperatorValue("/"),
            //Row 3
            new HexButtonValue("A"),
            new HexButtonValue("B"),
            new DecButtonValue("7"),
            new DecButtonValue("8"),
            new DecButtonValue("9"),
            new OperatorValue("*"),
            //Row 4
            new HexButtonValue("C"),
            new HexButtonValue("D"),
            new DecButtonValue("4"),
            new DecButtonValue("5"),
            new DecButtonValue("6"),
            new OperatorValue("-"),
            //Row 5
            new HexButtonValue("E"),
            new HexButtonValue("F"),
            new DecButtonValue("1"),
            new DecButtonValue("2"),
            new DecButtonValue("3"),
            new OperatorValue("+"),
            //Row 6
            new OperatorValue("("),
            new OperatorValue(")"),
            new SpecialButtonValue("+/-"),
            new DecButtonValue("0"),
            new SpecialButtonValue("."),
            new SpecialButtonValue("=")
    };

    private Context context;
    private CalculatorViewModel calculatorViewModel;
    private HistoryBarViewModel historyBarViewModel;
    private List<CalculatorButton> allButtons = new ArrayList<>();

    CalculatorListViewAdapter(Context context, CalculatorViewModel calculatorViewModel,
                              HistoryBarViewModel historyBarViewModel) {
        this.context = context;
        this.calculatorViewModel = calculatorViewModel;
        this.historyBarViewModel = historyBarViewModel;
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
        if (buttonValue.getText().equals("Bksp")) {
            CalculatorImageButton button = new CalculatorImageButton(context, buttonValue);
            button.setBackground(context.getDrawable(R.drawable.bg_calculator_button));
            button.setImageResource(R.drawable.ic_backspace_24dp);
            button.setMinimumHeight(((View) allButtons.get(0)).getHeight());
            button.setZ(((View) allButtons.get(0)).getZ());
            setButtonClickListener(button);
            button.setEnabled(true);
            button.setClickable(true);
            allButtons.add(button);
            return button;
        } else {
            CalculatorTextButton button = new CalculatorTextButton(context, buttonValue);
            button.setBackground(context.getDrawable(R.drawable.bg_calculator_button));
            setButtonClickListener(button);
            allButtons.add(button);
            return button;
        }
    }

    private void setButtonClickListener(final CalculatorButton button) {
        final ButtonValue buttonValue = button.getValue();

        if (buttonValue instanceof SpecialButtonValue) {
            setSpecialButtonClickListener(button, buttonValue);
        } else if (buttonValue instanceof OperatorValue) {
            setOperatorButtonClickListener(button, buttonValue);
        } else {
            setRegularButtonClickListener(button, buttonValue);
        }
    }

    private void setRegularButtonClickListener(CalculatorButton button, final ButtonValue buttonValue) {
        ((View) button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculatorViewModel.regularButtonPressed(buttonValue);
            }
        });
    }

    private void setSpecialButtonClickListener(CalculatorButton button, ButtonValue buttonValue) {
        final SpecialButtonValue specialButtonValue = (SpecialButtonValue) buttonValue;
        ((View) button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyBarViewModel.specialButtonPressed(specialButtonValue);
                calculatorViewModel.specialButtonPressed(specialButtonValue);
            }
        });
    }

    private void setOperatorButtonClickListener(CalculatorButton button, ButtonValue buttonValue) {
        final OperatorValue operatorButtonValue = (OperatorValue) buttonValue;
        ((View) button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyBarViewModel.operatorButtonPressed(operatorButtonValue);
                calculatorViewModel.operatorButtonPressed();
            }
        });
    }
}
