package com.rdecky.asmcalc.calculator;

import android.widget.TextView;

import java.util.List;

import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat;

class GroupedInputView {
    private List<TextView> groupedViews;
    private List<TextView> allViews;
    private InputFormat inputFormat;

    GroupedInputView(List<TextView> groupedViews, List<TextView> allViews, InputFormat inputFormat) {
        this.groupedViews = groupedViews;
        this.allViews = allViews;
        this.inputFormat = inputFormat;
    }

    List<TextView> getGroupedViews() {
        return groupedViews;
    }

    InputFormat getInputFormat() {
        return inputFormat;
    }

    List<TextView> getAllViews() {
        return allViews;
    }
}
