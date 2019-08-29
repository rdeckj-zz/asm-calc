package com.rdecky.asmcalc.calculator;

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

    void handle(CalculatorButton button) {
        handleOperator(button);
        handleNonOperator(button);
    }

    private void handleNonOperator(CalculatorButton button) {
        String buttonText = button.getText().toString();
        switch (buttonText.toLowerCase()) {
            case "bksp":
                backspace();
                break;
            case "clear":
                clear();
                break;
            case "ce":
                clearEntry();
                break;
            case "=":
                equals();
                break;
            default:
                break;
        }
    }

    private void equals() {
        historyBarController.update("=");
        long result = calculator.evaluate(historyBarController.getHistory());
        historyBarController.clear();
        calculatorViewModel.setCurrentValueAsDec(result);
    }

    private void handleOperator(CalculatorButton button) {
        if (button.isOperator()) {
            historyBarController.update(button.getText().toString());
            clearEntry();
        }
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
        clearEntry();
        historyBarController.clear();
    }

    private void clearEntry() {
        calculatorViewModel.setCurrentValue("0");
    }
}
