package com.rdecky.asmcalc.calculator.buttonValue;

public class DecButtonValue implements ButtonValue {

    private String value;

    public DecButtonValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isDecValue() {
        return true;
    }

    @Override
    public boolean isHexValue() {
        return false;
    }

    @Override
    public boolean isBinValue() {
        char firstDigit = value.charAt(0);
        return firstDigit == '0' || firstDigit == '1';
    }

    @Override
    public boolean isSpecialValue() {
        return false;
    }

    @Override
    public boolean isOperatorValue() {
        return false;
    }
}
