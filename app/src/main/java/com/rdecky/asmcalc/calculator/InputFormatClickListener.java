package com.rdecky.asmcalc.calculator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.rdecky.asmcalc.calculator.button.CalculatorButton;
import com.rdecky.asmcalc.calculator.value.ButtonValue;

import java.util.List;

public class InputFormatClickListener implements View.OnClickListener {

    public enum InputFormat {
        BIN, HEX, DEC
    }

    private CalculatorViewModel calculatorViewModel;
    private HistoryBarViewModel historyBarViewModel;
    private GroupedInputView groupedInputView;
    private List<CalculatorButton> calculatorTextButtons;

    InputFormatClickListener(CalculatorViewModel calculatorViewModel, HistoryBarViewModel historyBarViewModel, GroupedInputView groupedInputView, List<CalculatorButton> calculatorTextButtons) {
        this.calculatorViewModel = calculatorViewModel;
        this.historyBarViewModel = historyBarViewModel;
        this.groupedInputView = groupedInputView;
        this.calculatorTextButtons = calculatorTextButtons;
    }

    @Override
    public void onClick(View view) {
        historyBarViewModel.setInputFormat(groupedInputView.getInputFormat());
        calculatorViewModel.setInputFormat(groupedInputView.getInputFormat());
        resetAllViews();
        highlightLinkedViews();
        enableAllButtons();
        disableNecessaryButtons();
    }

    private void resetAllViews() {
        for (TextView view : groupedInputView.getAllViews()) {
            view.setTypeface(null, Typeface.NORMAL);
            view.setTextColor(Color.BLACK);
        }
    }

    private void highlightLinkedViews() {
        for (TextView view : groupedInputView.getGroupedViews()) {
            view.setTypeface(null, Typeface.BOLD);
            view.setTextColor(Color.BLUE);
        }
    }

    private void enableAllButtons() {
        for (CalculatorButton button : calculatorTextButtons) {
            setEnabled(button, true);
        }
    }

    private void disableNecessaryButtons() {
        if (groupedInputView.getInputFormat() == InputFormat.DEC) {
            disableHexButtons();
        }

        if (groupedInputView.getInputFormat() == InputFormat.BIN) {
            disableNonBinaryButtons();
        }
    }

    private void disableNonBinaryButtons() {
        for (CalculatorButton button : calculatorTextButtons) {
            ButtonValue value = button.getValue();
            if (!value.isBinValue() && (value.isDecValue() || value.isHexValue())) {
                setEnabled(button, false);
            }
        }
    }

    private void disableHexButtons() {
        for (CalculatorButton button : calculatorTextButtons) {
            ButtonValue value = button.getValue();
            if (value.isHexValue()) {
                setEnabled(button, false);
            }
        }
    }

    private void setEnabled(CalculatorButton button, boolean enabled) {
        ((View) button).setEnabled(enabled);
    }
}
