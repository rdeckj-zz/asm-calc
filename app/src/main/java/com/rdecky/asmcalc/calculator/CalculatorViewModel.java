package com.rdecky.asmcalc.calculator;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat;

public class CalculatorViewModel extends ViewModel {

    private static final String INITIAL_DEC_TEXT = "0";
    private static final String INITIAL_HEX_TEXT = "0";
    private static final String INITIAL_BIN_TEXT = "0000 0000 0000 0000 0000 0000 0000 0000";

    private NumberFormatter numberFormatter;
    private Observer<String> inputFormatObserver;
    private InputFormat currentInputFormat;

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

    private MutableLiveData<String> _binText = new MutableLiveData<>();

    void initialize() {
        numberFormatter = new NumberFormatter();
        _currentValue.setValue(0L);
        _inputText.setValue(INITIAL_DEC_TEXT);
        _decText.setValue(INITIAL_DEC_TEXT);
        _hexText.setValue(INITIAL_HEX_TEXT);
        _binTextTop.setValue(INITIAL_BIN_TEXT);
        _binTextBottom.setValue(INITIAL_BIN_TEXT);
        setObservers();
        setInputFormat(InputFormat.DEC);
    }

    void buttonPressed(CalculatorButton button) {
        if (!button.isSpecialOperator()) {
            handleRegularInput(button);
        } else {
            handleSpecialInput(button);
        }
    }

    void setInputFormat(InputFormat newFormat) {
        currentInputFormat = newFormat;
        createInputFormatObserver();
        removeOldInputFormatObserver();
        setNewInputFormatObserver(newFormat);
    }

    private void setNewInputFormatObserver(InputFormat newFormat) {
        if (newFormat == InputFormat.DEC) {
            _decText.observeForever(inputFormatObserver);
        } else if (newFormat == InputFormat.HEX) {
            _hexText.observeForever(inputFormatObserver);
        } else {
            _binText.observeForever(inputFormatObserver);
        }
    }

    private void createInputFormatObserver() {
        if (inputFormatObserver == null) {
            inputFormatObserver = new Observer<String>() {
                @Override
                public void onChanged(String newString) {
                    _inputText.setValue(newString);
                }
            };
        }
    }

    private void removeOldInputFormatObserver() {
        _decText.removeObserver(inputFormatObserver);
        _hexText.removeObserver(inputFormatObserver);
        _binText.removeObserver(inputFormatObserver);
    }

    private void handleRegularInput(CalculatorButton button) {
        String buttonText = button.getText().toString();

        if (currentInputFormat == InputFormat.DEC) {
            setNewValueBasedOnInput(buttonText, 10, _decText);
        } else if (currentInputFormat == InputFormat.HEX) {
            setNewValueBasedOnInput(buttonText, 16, _hexText);
        } else if (currentInputFormat == InputFormat.BIN) {
            setNewValueBasedOnInput(buttonText, 2, _binText);
        }
    }

    private void handleSpecialInput(Button button) {
        String buttonText = button.getText().toString();

        switch (buttonText.toLowerCase()) {
            case "ce":
                break;
            case "clear":
                clear();
                break;
            default:
                break;
        }
    }

    private void clear() {
        _currentValue.setValue(0L);
    }

    private void setNewValueBasedOnInput(String buttonText, int radix, MutableLiveData<String> inputText) {
        Long value = _currentValue.getValue();
        if (value == null || value == 0) {
            _currentValue.setValue(Long.parseLong(buttonText, radix));
        } else {
            String newText = stripFormatting(inputText.getValue()) + buttonText;
            setNewValue(radix, newText);
        }
    }

    private void setNewValue(int radix, String newText) {
        try {
            Long newValue = Long.parseLong(newText, radix);
            _currentValue.setValue(newValue);
        } catch (NumberFormatException e) {
            // Overflow, don't do anything
        }
    }

    private String stripFormatting(String input) {
        return input.replace(" ", "").replace(",", "");
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
        String topText = formattedBinary.get(0);
        String bottomText = formattedBinary.get(1);

        _binTextTop.setValue(topText);
        _binTextBottom.setValue(bottomText);
        _binText.setValue(topText + " " + bottomText);
    }
}
