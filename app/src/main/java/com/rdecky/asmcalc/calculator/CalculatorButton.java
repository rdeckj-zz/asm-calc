package com.rdecky.asmcalc.calculator;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;

import com.rdecky.asmcalc.calculator.buttonValue.ButtonValue;

@SuppressLint("AppCompatCustomView")
public class CalculatorButton extends Button {

    private ButtonValue value;

    public CalculatorButton(Context context) {
        super(context);
    }

    public CalculatorButton(Context context, ButtonValue value) {
        super(context);
        this.value = value;
        setText(value.getText());
    }

    ButtonValue getValue() {
        return value;
    }
}
