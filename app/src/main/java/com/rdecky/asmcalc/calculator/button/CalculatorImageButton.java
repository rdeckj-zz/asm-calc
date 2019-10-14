package com.rdecky.asmcalc.calculator.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageButton;

import com.rdecky.asmcalc.calculator.value.ButtonValue;

@SuppressLint("AppCompatCustomView")
public class CalculatorImageButton extends ImageButton implements CalculatorButton {

    private ButtonValue value;

    public CalculatorImageButton(Context context) {
        super(context);
    }

    public CalculatorImageButton(Context context, ButtonValue value) {
        super(context);
        this.value = value;
    }

    @Override
    public ButtonValue getValue() {
        return value;
    }
}
