package com.rdecky.asmcalc.calculator.buttonValue;

public class OperatorButtonValue implements ButtonValue {

    private String value;

    public OperatorButtonValue(String value) {
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
        return false;
    }

    @Override
    public boolean isBinValue() {
        return false;
    }
}
