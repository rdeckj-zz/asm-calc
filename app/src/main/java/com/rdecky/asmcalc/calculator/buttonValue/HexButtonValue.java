package com.rdecky.asmcalc.calculator.buttonValue;

import com.rdecky.asmcalc.calculator.buttonValue.ButtonValue;

public class HexButtonValue implements ButtonValue {

    private String value;

    public HexButtonValue(String value) {
        this.value = value;
    }

    @Override
    public String getText() {
        return value;
    }

    @Override
    public boolean isDecValue() {
        return false;
    }

    @Override
    public boolean isHexValue() {
        return true;
    }

    @Override
    public boolean isBinValue() {
        return false;
    }
}
