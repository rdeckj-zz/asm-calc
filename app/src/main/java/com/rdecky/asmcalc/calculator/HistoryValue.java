package com.rdecky.asmcalc.calculator;

class HistoryValue {

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
        return isOperator && !isLeftParenthesis() && !isRightParenthesis();
    }

    boolean isLeftParenthesis() {
        return specialValue != null && specialValue.equals("(");
    }

    boolean isRightParenthesis() {
        return specialValue != null && specialValue.equals(")");
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

    int getPrecedence() {
        if (specialValue == null) {
            return 0;
        }
        switch (specialValue.toLowerCase()) {
            case "pow":
                return 4;
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 2;
            default:
                return 0;
        }
    }

    boolean isLeftAssociative() {
        if (specialValue == null) {
            return false;
        }

        return !specialValue.equals("pow");
    }
}
