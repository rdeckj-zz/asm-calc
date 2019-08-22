package com.rdecky.asmcalc.calculator;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class CalculatorButton extends Button {

    public CalculatorButton(Context context) {
        super(context);
    }

    boolean isNumericDigit() {
        String text = getText().toString();
        char firstDigit = text.charAt(0);

        return text.length() == 1 && firstDigit >= '0' && firstDigit <= '9';
    }

    boolean isHexDigit() {
        String text = getText().toString();
        char firstDigit = text.charAt(0);

        return text.length() == 1 && firstDigit >= 'A' && firstDigit <= 'F';
    }

    boolean isBinDigit() {
        String text = getText().toString();
        char firstDigit = text.charAt(0);

        return text.length() == 1 && (firstDigit == '0' || firstDigit == '1');
    }

    boolean isSpecial() {
        return !isNumericDigit() && !isHexDigit() && !isBinDigit();
    }

    boolean isOperator() {
        String text = getText().toString().toLowerCase();
        return isSpecial() && !(text.equals(".") ||
                text.equals("ms") || text.equals("clear") ||
                text.equals("ce") || text.equals("bksp") ||
                text.equals("+/-") || text.equals("(") || text.equals(")") || text.equals("="));
    }
}
