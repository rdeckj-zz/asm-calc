package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.value.OperatorValue;

class OperatorButtonHandler {

    private CalculatorViewModel calculatorViewModel;
    private HistoryBarController historyBarController;

    OperatorButtonHandler(CalculatorViewModel calculatorViewModel, HistoryBarController historyBarController) {
        this.calculatorViewModel = calculatorViewModel;
        this.historyBarController = historyBarController;
    }

    void handle(OperatorValue value) {
        historyBarController.update(value);
        calculatorViewModel.clearEntry();
    }
}
