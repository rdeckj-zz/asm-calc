package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.value.HistoryValue;
import com.rdecky.asmcalc.calculator.value.NumberValue;
import com.rdecky.asmcalc.calculator.value.OperatorValue;

import java.util.ArrayList;
import java.util.List;

class HistoryBarController {

    private CalculatorViewModel calculatorViewModel;
    private OperatorValue previousValue;
    private static final OperatorValue EQUALS = new OperatorValue("=");

    HistoryBarController(CalculatorViewModel calculatorViewModel) {
        this.calculatorViewModel = calculatorViewModel;
    }

    void clear() {
        calculatorViewModel.clearHistory();
    }

    void equals() {
        if(!previousValue.isRightParenthesis()) {
            update(EQUALS);
        }
    }

    void update(OperatorValue value) {
        updateViewModel(value);
        previousValue = value;
    }

    private void updateViewModel(OperatorValue value) {
        if(!value.isLeftParenthesis()) {
            calculatorViewModel.addHistoryValue(new NumberValue(calculatorViewModel.getCurrentValue()));
        }
        calculatorViewModel.addHistoryValue(value);
    }

    private String appendToHistory(String currentHistory, OperatorValue value, String currentValue) {
        StringBuilder newHistory = new StringBuilder(currentHistory);

        if (!currentHistory.isEmpty()) {
            newHistory.append(" ");
        }

        if (!value.isLeftParenthesis()) {
            newHistory.append(currentValue);
            newHistory.append(" ");
        }
        newHistory.append(value.getText());

        return newHistory.toString();
    }
}
