package com.rdecky.asmcalc.calculator;

import com.rdecky.asmcalc.calculator.value.SpecialButtonValue;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.UserEntryDao;

class SpecialButtonHandler {

    private CalculatorViewModel calculatorViewModel;
    private InputFormatter inputFormatter;
    private HistoryBarController historyBarController;
    private Calculator calculator;
    private UserEntryDao userEntryDao;

    SpecialButtonHandler(CalculatorViewModel calculatorViewModel, InputFormatter inputFormatter, HistoryBarController historyBarController,
                         Calculator calculator, UserEntryDao userEntryDao) {
        this.calculatorViewModel = calculatorViewModel;
        this.inputFormatter = inputFormatter;
        this.historyBarController = historyBarController;
        this.calculator = calculator;
        this.userEntryDao = userEntryDao;
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
            case "+/-":
                invert();
                break;
            case "ms":
                saveEntry();
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

    private void invert() {
        long currentValue = calculatorViewModel.getCurrentValue();
        long invertedValue = currentValue * -1L;
        calculatorViewModel.setCurrentValueAsDec(invertedValue);
    }

    private void saveEntry() {
        //TODO
        UserEntry userEntry = new UserEntry();
        userEntry.shortName = "potato";
        userEntryDao.insert(userEntry);
    }
}
