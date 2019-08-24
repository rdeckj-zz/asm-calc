package com.rdecky.asmcalc.calculator;

public class HistoryValue {

    private boolean isOperator;
    private String operator;
    private long value;

    HistoryValue(String operator) {
        this.operator = operator;
        this.isOperator = true;
    }

    HistoryValue(long value) {
        this.value = value;
        this.isOperator = false;
    }

    boolean isOperator() {
        return isOperator;
    }

    String getOperator() {
        return operator;
    }

    long getValue() {
        return value;
    }
}
