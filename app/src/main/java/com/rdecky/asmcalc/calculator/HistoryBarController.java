package com.rdecky.asmcalc.calculator;

class HistoryBarController {

    private CalculatorViewModel calculatorViewModel;

    HistoryBarController(CalculatorViewModel calculatorViewModel) {
        this.calculatorViewModel = calculatorViewModel;
    }

    void clear() {
        calculatorViewModel.setInputHistory("");
    }

    void update(String operator) {
        String currentHistory = calculatorViewModel.getInputHistory();
        String currentValue = calculatorViewModel.getInputText();

        String newHistory = appendToHistory(currentHistory, operator, currentValue);

        calculatorViewModel.setInputHistory(newHistory);
    }

    private String appendToHistory(String currentHistory, String operator, String currentValue) {
        StringBuilder newHistory = new StringBuilder();

        if (currentHistory != null) {
            newHistory.append(currentHistory);
            if (!currentHistory.isEmpty()) {
                newHistory.append(" ");
            }
        }

        newHistory.append(newHistory);
        newHistory.append(currentValue);
        newHistory.append(" ");
        newHistory.append(operator);

        return newHistory.toString();
    }
}
