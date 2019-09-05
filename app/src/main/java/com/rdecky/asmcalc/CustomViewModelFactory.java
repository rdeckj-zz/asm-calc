package com.rdecky.asmcalc;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rdecky.asmcalc.calculator.CalculatorViewModel;
import com.rdecky.asmcalc.data.source.AsmCalcDatabase;

public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private static CustomViewModelFactory INSTANCE = null;
    private AsmCalcDatabase database;

    private CustomViewModelFactory(AsmCalcDatabase database) {
        this.database = database;
    }

    public static CustomViewModelFactory getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new CustomViewModelFactory(AsmCalcDatabase.getInstance(context));
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CalculatorViewModel.class)) {
            return (T) new CalculatorViewModel(database);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
