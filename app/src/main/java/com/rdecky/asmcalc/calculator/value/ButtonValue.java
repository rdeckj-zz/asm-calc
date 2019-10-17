package com.rdecky.asmcalc.calculator.value;

public interface ButtonValue {
    String getText();

    boolean isDecValue();

    boolean isHexValue();

    boolean isBinValue();

    int getDrawableResourceId();
}
