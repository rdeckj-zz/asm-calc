package com.rdecky.asmcalc.calculator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class InputFormatClickListener implements View.OnClickListener {

    enum InputFormat {
        BIN, HEX, DEC
    }

    private CalculatorViewModel viewModel;
    private InputFormat newFormat;
    private List<TextView> linkedViews;
    private List<TextView> inputFormatViews;

    InputFormatClickListener(CalculatorViewModel viewModel, InputFormat newFormat, List<TextView> linkedViews, List<TextView> inputFormatViews) {
        this.viewModel = viewModel;
        this.newFormat = newFormat;
        this.linkedViews = linkedViews;
        this.inputFormatViews = inputFormatViews;
    }

    @Override
    public void onClick(View view) {
        viewModel.setInputFormat(newFormat);
        resetAllViews();
        highlightLinkedViews();
    }

    private void resetAllViews() {
        for (TextView view : inputFormatViews) {
            view.setTypeface(null, Typeface.NORMAL);
            view.setTextColor(Color.BLACK);
        }
    }

    private void highlightLinkedViews() {
        for (TextView view : linkedViews) {
            view.setTypeface(null, Typeface.BOLD);
            view.setTextColor(Color.BLUE);
        }
    }
}
