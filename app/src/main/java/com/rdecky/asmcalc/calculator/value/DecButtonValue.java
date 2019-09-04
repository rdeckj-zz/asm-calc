package com.rdecky.asmcalc.calculator.value;

public class DecButtonValue implements ButtonValue {

    private String value;

    public DecButtonValue(String value) {
        this.value = value;
    }

    @Override
    public String getText() {
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
}
