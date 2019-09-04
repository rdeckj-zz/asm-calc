package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.value.SpecialButtonValue;

class SpecialButtonHandler {

    private CalculatorViewModel calculatorViewModel;
    private InputFormatter inputFormatter;
    private HistoryBarController historyBarController;
    private Calculator calculator;

    SpecialButtonHandler(CalculatorViewModel calculatorViewModel, InputFormatter inputFormatter, HistoryBarController historyBarController, Calculator calculator) {
        this.calculatorViewModel = calculatorViewModel;
        this.inputFormatter = inputFormatter;
        this.historyBarController = historyBarController;
        this.calculator = calculator;
    }

    void handle(SpecialButtonValue value) {
        switch (value.getText().toLowerCase()) {
            case "bksp":
                backspace();
                break;
            case "clear":
                clear();
                break;
            case "ce":
                calculatorViewModel.clearEntry();
                break;
            case "=":
                equals();
                break;
            default:
                break;
        }
    }

    private void equals() {
        historyBarController.equals();
        long result = calculator.evaluate(historyBarController.getHistory());
        historyBarController.clear();
        calculatorViewModel.setCurrentValueAsDec(result);
    }

    private void backspace() {
        String noFormatting = inputFormatter.stripFormatting(calculatorViewModel.getInputText());
        if (noFormatting.length() == 1) {
            calculatorViewModel.setCurrentValue("0");
        } else {
            String newString = noFormatting.substring(0, noFormatting.length() - 1);
            calculatorViewModel.setCurrentValue(newString);
        }
    }

    private void clear() {
        calculatorViewModel.clearEntry();
        historyBarController.clear();
    }
}
