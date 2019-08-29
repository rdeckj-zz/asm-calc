package com.rdecky.asmcalc.calculator;

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

    private InputFormatter inputFormatter;
    private Observer<String> inputFormatObserver;
    private InputFormat currentInputFormat;
    private SpecialButtonHandler specialButtonHandler;

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
        inputFormatter = new InputFormatter();
        specialButtonHandler = new SpecialButtonHandler(
                this,
                inputFormatter,
                new HistoryBarController(this),
                new Calculator()
        );
        _currentValue.setValue(0L);
        _inputHistory.setValue("");
        _inputText.setValue(INITIAL_DEC_TEXT);
        _decText.setValue(INITIAL_DEC_TEXT);
        _hexText.setValue(INITIAL_HEX_TEXT);
        _binTextTop.setValue(INITIAL_BIN_TEXT);
        _binTextBottom.setValue(INITIAL_BIN_TEXT);
        setObservers();
        setInputFormat(InputFormat.DEC);
    }

    void buttonPressed(CalculatorButton button) {
        if (!button.isSpecial()) {
            handleRegularInput(button);
        } else {
            specialButtonHandler.handle(button);
        }
    }

    void setInputFormat(InputFormat newFormat) {
        currentInputFormat = newFormat;
        createInputFormatObserver();
        removeOldInputFormatObserver();
        setNewInputFormatObserver(newFormat);

    }

    void setCurrentValue(String newText) {
        if (currentInputFormat == InputFormat.DEC) {
            setCurrentValueBasedOnRadix(newText, 10);
        } else if (currentInputFormat == InputFormat.HEX) {
            setCurrentValueBasedOnRadix(newText, 16);
        } else if (currentInputFormat == InputFormat.BIN) {
            setCurrentValueBasedOnRadix(newText, 2);
        }
    }

    void setCurrentValueAsDec(long newValue) {
        _currentValue.setValue(newValue);
    }

    String getInputText() {
        return _inputText.getValue();
    }

    String getInputHistory() {
        return _inputHistory.getValue();
    }

    void setInputHistory(String newValue) {
        _inputHistory.setValue(newValue);
    }

    Long getCurrentValue() {
        return _currentValue.getValue();
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
        String newText = inputFormatter.stripFormatting(inputText.getValue()) + buttonText;
        setCurrentValue(newText);
    }

    private void setCurrentValueBasedOnRadix(String newText, int radix) {
        try {
            Long newValue = Long.parseLong(newText, radix);
            _currentValue.setValue(newValue);
        } catch (NumberFormatException e) {
            // Overflow, don't do anything
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
        _decText.setValue(inputFormatter.formatDec(currentValue));
    }

    private void setHexTextFormattedValue(Long currentValue) {
        _hexText.setValue(inputFormatter.formatHexString(Long.toHexString(currentValue)));
    }

    private void setBinTextFormattedValue(Long currentValue) {
        String binary = Long.toBinaryString(currentValue);
        List<String> formattedBinary = inputFormatter.formatBinString(binary);
        String topText = formattedBinary.get(0);
        String bottomText = formattedBinary.get(1);

        _binTextTop.setValue(topText);
        _binTextBottom.setValue(bottomText);
        _binText.setValue(topText + " " + bottomText);
    }
}
