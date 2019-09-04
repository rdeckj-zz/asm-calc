package com.rdecky.asmcalc.calculator.buttonValue;

import com.rdecky.asmcalc.calculator.buttonValue.ButtonValue;

public class SpecialButtonValue implements ButtonValue {

    private String value;

    public SpecialButtonValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isDecValue() {
        return false;
    }

    @Override
    public boolean isHexValue() {
        return false;
    }

    @Override
    public boolean isBinValue() {
        return false;
    }

    @Override
    public boolean isSpecialValue() {
        return true;
    }

    @Override
    public boolean isOperatorValue() {
        return false;
    }
}
