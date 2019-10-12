package com.rdecky.asmcalc.calculator;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rdecky.asmcalc.calculator.value.ButtonValue;
import com.rdecky.asmcalc.calculator.value.SpecialButtonValue;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.UserEntryDao;
import com.rdecky.asmcalc.util.BaseConverter;
import com.rdecky.asmcalc.util.NumberFormatter;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat;
import static com.rdecky.asmcalc.util.BaseConverter.BIN_RADIX;
import static com.rdecky.asmcalc.util.BaseConverter.DEC_RADIX;
import static com.rdecky.asmcalc.util.BaseConverter.HEX_RADIX;

public class CalculatorViewModel extends ViewModel {

    private static final String INITIAL_DEC_TEXT = "0";
    private static final String INITIAL_HEX_TEXT = "0";
    private static final String INITIAL_BIN_TEXT = "0000 0000 0000 0000 0000 0000 0000 0000";

    private final UserEntryDao userEntryDao;

    private Observer<String> inputFormatObserver;
    private InputFormat currentInputFormat;

    private MutableLiveData<Long> _currentValue = new MutableLiveData<>();

    private MutableLiveData<String> _inputText = new MutableLiveData<>();
    public LiveData<String> inputText = _inputText;

    private MutableLiveData<String> _decText = new MutableLiveData<>();
    public LiveData<String> decText = _decText;

    private MutableLiveData<String> _hexText = new MutableLiveData<>();
    public LiveData<String> hexText = _hexText;

    private MutableLiveData<String> _binText = new MutableLiveData<>();

    private MutableLiveData<String> _binTextTop = new MutableLiveData<>();
    public LiveData<String> binTextTop = _binTextTop;

    private MutableLiveData<String> _binTextBottom = new MutableLiveData<>();
    public LiveData<String> binTextBottom = _binTextBottom;

    public CalculatorViewModel(UserEntryDao userEntryDao) {
        this.userEntryDao = userEntryDao;
        _currentValue.setValue(0L);
        _inputText.setValue(INITIAL_DEC_TEXT);
        _decText.setValue(INITIAL_DEC_TEXT);
        _hexText.setValue(INITIAL_HEX_TEXT);
        _binTextTop.setValue(INITIAL_BIN_TEXT);
        _binTextBottom.setValue(INITIAL_BIN_TEXT);
        setObservers();
        setInputFormat(InputFormat.DEC);
    }

    void regularButtonPressed(ButtonValue value) {
        String buttonText = value.getText();
        String newText = NumberFormatter.stripFormatting(inputText.getValue()) + buttonText;
        setCurrentValue(newText);
    }

    void specialButtonPressed(SpecialButtonValue value) {
        switch (value.getText().toLowerCase()) {
            case "bksp":
                backspace();
                break;
            case "clear":
            case "ce":
                clearEntry();
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

    void operatorButtonPressed() {
        clearEntry();
    }

    void setInputFormat(InputFormat newFormat) {
        currentInputFormat = newFormat;

        createInputFormatObserver();
        removeOldInputFormatObserver();
        setNewInputFormatObserver(newFormat);
    }

    void setCurrentValue(String newText) {
        if (currentInputFormat == InputFormat.DEC) {
            setCurrentValueBasedOnRadix(newText, DEC_RADIX);
        } else if (currentInputFormat == InputFormat.HEX) {
            setCurrentValueBasedOnRadix(newText, HEX_RADIX);
        } else if (currentInputFormat == InputFormat.BIN) {
            setCurrentValueBasedOnRadix(newText, BIN_RADIX);
        }
    }

    void setCurrentValueAsDec(long newValue) {
        _currentValue.setValue(newValue);
    }

    Long getCurrentValue() {
        return _currentValue.getValue();
    }

    private void clearEntry() {
        setCurrentValue("0");
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

    private void setCurrentValueBasedOnRadix(String newText, int radix) {
        try {
            long convertedValue;

            switch (radix) {
                case BIN_RADIX:
                    convertedValue = BaseConverter.convertBinString(newText);
                    break;
                case HEX_RADIX:
                    convertedValue = BaseConverter.convertHexString(newText);
                    break;
                default:
                    convertedValue = BaseConverter.convertDecString(newText);
                    break;
            }

            _currentValue.setValue(convertedValue);
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
        _decText.setValue(NumberFormatter.formatDec(currentValue));
    }

    private void setHexTextFormattedValue(Long currentValue) {
        _hexText.setValue(NumberFormatter.formatHexString(Long.toHexString(currentValue)));
    }

    private void setBinTextFormattedValue(Long currentValue) {
        String binary = Long.toBinaryString(currentValue);
        List<String> formattedBinary = NumberFormatter.formatBinString(binary);
        String topText = formattedBinary.get(0);
        String bottomText = formattedBinary.get(1);

        _binTextTop.setValue(topText);
        _binTextBottom.setValue(bottomText);
        _binText.setValue(topText + " " + bottomText);
    }

    private void backspace() {
        String noFormatting = NumberFormatter.stripFormatting(_inputText.getValue());
        if (noFormatting.length() == 1) {
            setCurrentValue("0");
        } else {
            String newString = noFormatting.substring(0, noFormatting.length() - 1);
            setCurrentValue(newString);
        }
    }

    private void invert() {
        setCurrentValueAsDec(_currentValue.getValue() * -1L);
    }

    private void saveEntry() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                UserEntry userEntry = new UserEntry.Builder()
                        .setShortName(DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()))
                        .setValue(_currentValue.getValue())
                        .setDecText(_decText.getValue())
                        .setHexText(_hexText.getValue())
                        .setDescription("Added from quick save")
                        .build();
                userEntryDao.insert(userEntry);
            }
        });
    }
}
