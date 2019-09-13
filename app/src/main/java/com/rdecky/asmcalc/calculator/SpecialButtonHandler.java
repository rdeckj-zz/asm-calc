package com.rdecky.asmcalc.calculator;

import android.os.AsyncTask;

import com.rdecky.asmcalc.calculator.value.SpecialButtonValue;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.UserEntryDao;

import java.text.DateFormat;
import java.util.Date;

class SpecialButtonHandler {

    private CalculatorViewModel calculatorViewModel;
    private UserEntryDao userEntryDao;

    SpecialButtonHandler(CalculatorViewModel calculatorViewModel, UserEntryDao userEntryDao) {
        this.calculatorViewModel = calculatorViewModel;
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
//            case "=":
//                equals();
//                break;
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
//        long result = Calculator.evaluate(calculatorViewModel.getHistory());
//        calculatorViewModel.setCurrentValueAsDec(result);
    }

    private void backspace() {
        String noFormatting = InputFormatter.stripFormatting(calculatorViewModel.getInputText());
        if (noFormatting.length() == 1) {
            calculatorViewModel.setCurrentValue("0");
        } else {
            String newString = noFormatting.substring(0, noFormatting.length() - 1);
            calculatorViewModel.setCurrentValue(newString);
        }
    }

    private void clear() {
        calculatorViewModel.clearEntry();
    }

    private void invert() {
        long currentValue = calculatorViewModel.getCurrentValue();
        long invertedValue = currentValue * -1L;
        calculatorViewModel.setCurrentValueAsDec(invertedValue);
    }

    private void saveEntry() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                UserEntry userEntry = new UserEntry();
                userEntry.shortName = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
                userEntry.value = calculatorViewModel.getCurrentValue();
                userEntry.decText = calculatorViewModel.getDecText();
                userEntry.hexText = calculatorViewModel.getHexText();
                userEntry.description = "Added from quick save";
                userEntryDao.insert(userEntry);
            }
        });
    }
}
