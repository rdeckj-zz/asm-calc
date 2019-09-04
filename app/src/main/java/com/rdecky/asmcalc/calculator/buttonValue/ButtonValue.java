package com.rdecky.asmcalc.calculator.buttonValue;

public interface ButtonValue {
    public String getValue();
    public boolean isDecValue();
    public boolean isHexValue();
    public boolean isBinValue();
    public boolean isSpecialValue();
    public boolean isOperatorValue();
}
