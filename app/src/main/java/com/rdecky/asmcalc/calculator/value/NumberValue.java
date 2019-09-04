package com.rdecky.asmcalc.calculator.value;

public class NumberValue implements HValue {

    private long value;

    public NumberValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
