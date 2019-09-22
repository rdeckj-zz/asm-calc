package com.rdecky.asmcalc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rdecky.asmcalc.calculator.CalculatorViewModel;
import com.rdecky.asmcalc.calculator.HistoryBarViewModel;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.AsmCalcDatabase;
import com.rdecky.asmcalc.userEntry.UserEntryModel;
import com.rdecky.asmcalc.userEntry.UserEntryViewModel;
import com.rdecky.asmcalc.userEntry.modification.ModificationViewModel;

public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private AsmCalcDatabase database;
    private CalculatorViewModel calculatorViewModel;

    public CustomViewModelFactory(AsmCalcDatabase database) {
        this.database = database;
    }

    public CustomViewModelFactory(CalculatorViewModel calculatorViewModel) {
        this.calculatorViewModel = calculatorViewModel;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CalculatorViewModel.class)) {
            return (T) new CalculatorViewModel(database.userEntryDao());
        } else if (modelClass.isAssignableFrom(UserEntryViewModel.class)) {
            return (T) new UserEntryViewModel(database.userEntryDao());
        } else if (modelClass.isAssignableFrom(HistoryBarViewModel.class)) {
            return (T) new HistoryBarViewModel(calculatorViewModel);
        } else if (modelClass.isAssignableFrom(ModificationViewModel.class)) {
            return (T) new ModificationViewModel(database.userEntryDao(), new UserEntry());
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
