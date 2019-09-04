package com.rdecky.asmcalc.calculator.value;

public class NumberValue implements HistoryValue {

    private long value;

    public NumberValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
