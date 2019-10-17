package com.rdecky.asmcalc.calculator.value;

public class SpecialButtonValue implements ButtonValue {

    private String value;
    private int drawableResourceId;

    public SpecialButtonValue(String value, int drawableResourceId) {
        this.value = value;
        this.drawableResourceId = drawableResourceId;
    }

    public SpecialButtonValue(String value) {
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

    @Override
    public int getDrawableResourceId() {
        return drawableResourceId;
    }
}
