package com.rdecky.asmcalc.calculator;

import android.widget.TextView;

import java.util.List;

import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat;

public class GroupedInputView {
    private List<TextView> groupedViews;
    private List<TextView> allViews;
    private InputFormat inputFormat;

    public GroupedInputView(List<TextView> groupedViews, List<TextView> allViews, InputFormat inputFormat) {
        this.groupedViews = groupedViews;
        this.allViews = allViews;
        this.inputFormat = inputFormat;
    }

    public List<TextView> getGroupedViews() {
        return groupedViews;
    }

    InputFormat getInputFormat() {
        return inputFormat;
    }

    List<TextView> getAllViews() {
        return allViews;
    }
}
