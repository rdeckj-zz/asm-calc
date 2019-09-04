package com.rdecky.asmcalc.calculator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rdecky.asmcalc.calculator.buttonValue.ButtonValue;

import java.util.List;

public class InputFormatClickListener implements View.OnClickListener {

    enum InputFormat {
        BIN, HEX, DEC
    }

    private CalculatorViewModel viewModel;
    private GroupedInputView groupedInputView;
    private List<CalculatorButton> calculatorButtons;

    InputFormatClickListener(CalculatorViewModel viewModel, GroupedInputView groupedInputView, List<CalculatorButton> calculatorButtons) {
        this.viewModel = viewModel;
        this.groupedInputView = groupedInputView;
        this.calculatorButtons = calculatorButtons;
    }

    @Override
    public void onClick(View view) {
        viewModel.setInputFormat(groupedInputView.getInputFormat());
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
        for (Button button : calculatorButtons) {
            button.setEnabled(true);
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
        for (CalculatorButton button : calculatorButtons) {
            ButtonValue value = button.getValue();
            if (!value.isBinValue() && (value.isDecValue() || value.isHexValue())) {
                button.setEnabled(false);
            }
        }
    }

    private void disableHexButtons() {
        for (CalculatorButton button : calculatorButtons) {
            ButtonValue value = button.getValue();
            if (value.isHexValue()) {
                button.setEnabled(false);
            }
        }
    }
}
