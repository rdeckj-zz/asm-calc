package com.rdecky.asmcalc.calculator;

import android.widget.Button;

class SpecialInputHandler {

    private CalculatorViewModel calculatorViewModel;
    private InputFormatter inputFormatter;
    private HistoryBarController historyBarController;

    SpecialInputHandler(CalculatorViewModel calculatorViewModel, InputFormatter inputFormatter, HistoryBarController historyBarController) {
        this.calculatorViewModel = calculatorViewModel;
        this.inputFormatter = inputFormatter;
        this.historyBarController = historyBarController;
    }

    void handleInput(Button button) {
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
            case "+":
                historyBarController.update("+");
                break;
            default:
                break;
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
