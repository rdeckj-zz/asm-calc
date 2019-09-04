package com.rdecky.asmcalc.calculator.value;

public class OperatorValue implements ButtonValue, HistoryValue {

    private String value;

    public OperatorValue(String value) {
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

    public int getPrecedence() {
        switch (value.toLowerCase()) {
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

    public boolean isLeftAssociative() {
        return !value.toLowerCase().equals("pow");
    }

    public boolean isLeftParenthesis() {
        return value.equals("(");
    }

    public boolean isRightParenthesis() {
        return value.equals(")");
    }

    public boolean isParenthesis() {
        return isLeftParenthesis() || isRightParenthesis();
    }

}
