package com.rdecky.asmcalc.calculator;

public class HistoryValue {

    private boolean isOperator;
    private String specialValue;
    private long value;

    HistoryValue(String specialValue) {
        this.specialValue = specialValue;
        this.isOperator = true;
    }

    HistoryValue(long value) {
        this.value = value;
        this.isOperator = false;
    }

    boolean isOperator() {
        return isOperator && !isParenthesis();
    }

    boolean isParenthesis() {
        return (specialValue != null && (specialValue.equals(")") || specialValue.equals("(")));
    }

    boolean isNumber() {
        return !isOperator;
    }

    String getOperator() {
        return specialValue;
    }

    long getValue() {
        return value;
    }
}
