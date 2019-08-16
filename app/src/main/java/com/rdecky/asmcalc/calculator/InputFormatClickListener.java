package com.rdecky.asmcalc.calculator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

public class InputFormatClickListener implements View.OnClickListener {

    enum InputFormat {
        BIN, HEX, DEC
    }

    private CalculatorViewModel viewModel;
    private InputFormat newFormat;

    InputFormatClickListener(CalculatorViewModel viewModel, InputFormat newFormat) {
        this.viewModel = viewModel;
        this.newFormat = newFormat;
    }

    @Override
    public void onClick(View view) {
        viewModel.setInputFormat(newFormat);
        highlightView((TextView) view);
    }

    private void highlightView(TextView view) {
        view.setTypeface(null, Typeface.BOLD);
        view.setTextColor(Color.BLUE);
    }
}
