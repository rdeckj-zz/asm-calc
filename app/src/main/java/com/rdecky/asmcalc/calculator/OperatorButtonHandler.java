package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.buttonValue.OperatorButtonValue;

class OperatorButtonHandler {

    private CalculatorViewModel calculatorViewModel;
    private HistoryBarController historyBarController;

    OperatorButtonHandler(CalculatorViewModel calculatorViewModel, HistoryBarController historyBarController) {
        this.calculatorViewModel = calculatorViewModel;
        this.historyBarController = historyBarController;
    }

    void handle(OperatorButtonValue value) {
        historyBarController.update(value.getText());
        calculatorViewModel.clearEntry();
    }
}
