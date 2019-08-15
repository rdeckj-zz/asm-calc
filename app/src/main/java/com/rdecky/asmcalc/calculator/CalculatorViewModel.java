package com.rdecky.asmcalc.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CalculatorViewModel extends ViewModel {

    private static final String INITIAL_DEC_TEXT = "0";
    private static final String INITIAL_HEX_TEXT = "0";
    private static final String INITIAL_BIN_TEXT = "0000 0000 0000 0000 0000 0000 0000 0000";

    private NumberFormatter numberFormatter;

    private MutableLiveData<Long> _currentValue = new MutableLiveData<>();

    private MutableLiveData<String> _inputHistory = new MutableLiveData<>();
    public LiveData<String> inputHistory = _inputHistory;

    private MutableLiveData<String> _inputText = new MutableLiveData<>();
    public LiveData<String> inputText = _inputText;

    private MutableLiveData<String> _decText = new MutableLiveData<>();
    public LiveData<String> decText = _decText;

    private MutableLiveData<String> _hexText = new MutableLiveData<>();
    public LiveData<String> hexText = _hexText;

    private MutableLiveData<String> _binTextTop = new MutableLiveData<>();
    public LiveData<String> binTextTop = _binTextTop;

    private MutableLiveData<String> _binTextBottom = new MutableLiveData<>();
    public LiveData<String> binTextBottom = _binTextBottom;

    void initialize() {
        numberFormatter = new NumberFormatter();
        _currentValue.setValue(0L);
        _inputText.setValue(INITIAL_DEC_TEXT);
        _decText.setValue(INITIAL_DEC_TEXT);
        _hexText.setValue(INITIAL_HEX_TEXT);
        _binTextTop.setValue(INITIAL_BIN_TEXT);
        _binTextBottom.setValue(INITIAL_BIN_TEXT);
        setObservers();
    }

    void buttonPressed(String buttonText) {
        if (isNumber(buttonText)) {
            handleNumericInput(buttonText);
        }
    }

    private void handleNumericInput(String buttonText) {
        Long value = _currentValue.getValue();
        if(value == null || value == 0) {
            _currentValue.setValue(Long.valueOf(buttonText));
        } else {
            String newDecText = _currentValue.getValue().toString() + buttonText;
            _currentValue.setValue(Long.parseLong(newDecText));
        }
    }

    private void setObservers() {
        _currentValue.observeForever(new Observer<Long>() {
            @Override
            public void onChanged(Long newValue) {
                setDecTextFormattedValue(newValue);
                setHexTextFormattedValue(newValue);
                setBinTextFormattedValue(newValue);
            }
        });
    }

    private void setDecTextFormattedValue(Long currentValue) {
        _decText.setValue(numberFormatter.formatDec(currentValue));
    }

    private void setHexTextFormattedValue(Long currentValue) {
        _hexText.setValue(numberFormatter.formatHexString(Long.toHexString(currentValue)));
    }

    private void setBinTextFormattedValue(Long currentValue) {
        String binary = Long.toBinaryString(currentValue);
        List<String> formattedBinary = numberFormatter.formatBinString(binary);
        _binTextTop.setValue(formattedBinary.get(0));
        _binTextBottom.setValue(formattedBinary.get(1));
    }

    private boolean isNumber(String buttonText) {
        return buttonText.charAt(0) >= '0' && buttonText.charAt(0) <= '9';
    }

}
