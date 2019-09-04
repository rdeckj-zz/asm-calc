package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.value.HValue;
import com.rdecky.asmcalc.calculator.value.NumberValue;
import com.rdecky.asmcalc.calculator.value.OperatorValue;

import java.util.ArrayList;
import java.util.List;

class HistoryBarController {

    private CalculatorViewModel calculatorViewModel;
    private List<HValue> history = new ArrayList<>();
    private OperatorValue previousValue;
    private static final OperatorValue EQUALS = new OperatorValue("=");

    HistoryBarController(CalculatorViewModel calculatorViewModel) {
        this.calculatorViewModel = calculatorViewModel;
    }

    void clear() {
        calculatorViewModel.setInputHistory("");
        history.clear();
    }

    void equals() {
        if(!previousValue.isRightParenthesis()) {
            update(EQUALS);
        }
    }

    void update(OperatorValue value) {
        updateHistoryList(value);
        updateViewModel(value);
        previousValue = value;
    }

    List<HValue> getHistory() {
        return history;
    }

    private void updateViewModel(OperatorValue value) {
        String currentHistory = calculatorViewModel.getInputHistory();
        String currentValue = calculatorViewModel.getInputText();

        String newHistory = appendToHistory(currentHistory, value, currentValue);

        calculatorViewModel.setInputHistory(newHistory);
    }

    private void updateHistoryList(OperatorValue value) {
        if (!value.isLeftParenthesis()) {
            history.add(new NumberValue(calculatorViewModel.getCurrentValue()));
        }
        history.add(value);
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
