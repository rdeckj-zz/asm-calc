package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.value.OperatorValue;

class OperatorButtonHandler {

    private CalculatorViewModel calculatorViewModel;

    OperatorButtonHandler(CalculatorViewModel calculatorViewModel) {
        this.calculatorViewModel = calculatorViewModel;
    }

    void handle(OperatorValue value) {
        calculatorViewModel.clearEntry();
    }
}
