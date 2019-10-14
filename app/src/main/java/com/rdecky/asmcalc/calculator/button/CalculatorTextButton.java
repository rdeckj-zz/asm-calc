package com.rdecky.asmcalc.calculator.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;

import com.rdecky.asmcalc.calculator.value.ButtonValue;

@SuppressLint("AppCompatCustomView")
public class CalculatorTextButton extends Button implements CalculatorButton {

    private ButtonValue value;

    public CalculatorTextButton(Context context) {
        super(context);
    }

    public CalculatorTextButton(Context context, ButtonValue value) {
        super(context);
        this.value = value;
        setText(value.getText());
    }

    @Override
    public ButtonValue getValue() {
        return value;
    }
}
