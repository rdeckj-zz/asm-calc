package com.rdecky.asmcalc.calculator;

import java.util.ArrayList;
import java.util.List;

class HistoryBarController {

    private CalculatorViewModel calculatorViewModel;
    private List<HistoryValue> history = new ArrayList<>();

    HistoryBarController(CalculatorViewModel calculatorViewModel) {
        this.calculatorViewModel = calculatorViewModel;
    }

    void clear() {
        calculatorViewModel.setInputHistory("");
        history.clear();
    }

    void update(String operator) {
        updateHistoryList(operator);
        updateViewModel(operator);
    }

    List<HistoryValue> getHistory() {
        return history;
    }

    private void updateViewModel(String operator) {
        String currentHistory = calculatorViewModel.getInputHistory();
        String currentValue = calculatorViewModel.getInputText();

        String newHistory = appendToHistory(currentHistory, operator, currentValue);

        calculatorViewModel.setInputHistory(newHistory);
    }

    private void updateHistoryList(String operator) {
        history.add(new HistoryValue(calculatorViewModel.getCurrentValue()));
        history.add(new HistoryValue(operator));
    }

    private String appendToHistory(String currentHistory, String operator, String currentValue) {
        StringBuilder newHistory = new StringBuilder(currentHistory);

        if (!currentHistory.isEmpty()) {
            newHistory.append(" ");
        }

        newHistory.append(currentValue);
        newHistory.append(" ");
        newHistory.append(operator);

        return newHistory.toString();
    }
}
