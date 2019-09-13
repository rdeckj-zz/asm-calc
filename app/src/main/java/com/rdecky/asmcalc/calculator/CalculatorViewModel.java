package com.rdecky.asmcalc.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rdecky.asmcalc.calculator.value.ButtonValue;
import com.rdecky.asmcalc.calculator.value.HistoryValue;
import com.rdecky.asmcalc.calculator.value.NumberValue;
import com.rdecky.asmcalc.calculator.value.OperatorValue;
import com.rdecky.asmcalc.calculator.value.SpecialButtonValue;
import com.rdecky.asmcalc.data.source.UserEntryDao;

import java.util.ArrayList;
import java.util.List;

import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat;

public class CalculatorViewModel extends ViewModel {

    private static final String INITIAL_DEC_TEXT = "0";
    private static final String INITIAL_HEX_TEXT = "0";
    private static final String INITIAL_BIN_TEXT = "0000 0000 0000 0000 0000 0000 0000 0000";

    private Observer<String> inputFormatObserver;
    private InputFormat currentInputFormat;
    private Observer<String> historyFormatObserver;

    private SpecialButtonHandler specialButtonHandler;
    private OperatorButtonHandler operatorButtonHandler;

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

//    private MutableLiveData<List<HistoryValue>> _history = new MutableLiveData<>();
//
//    private MutableLiveData<String> _decHistory = new MutableLiveData<>();
//    private MutableLiveData<String> _hexHistory = new MutableLiveData<>();
//
//    private MutableLiveData<String> _inputHistory = new MutableLiveData<>();
//    public LiveData<String> inputHistory = _inputHistory;

    public CalculatorViewModel(UserEntryDao userEntryDao) {
        specialButtonHandler = new SpecialButtonHandler(
                this,
                userEntryDao
        );
        operatorButtonHandler = new OperatorButtonHandler(this);
        _currentValue.setValue(0L);
        _inputText.setValue(INITIAL_DEC_TEXT);
        _decText.setValue(INITIAL_DEC_TEXT);
        _hexText.setValue(INITIAL_HEX_TEXT);
        _binTextTop.setValue(INITIAL_BIN_TEXT);
        _binTextBottom.setValue(INITIAL_BIN_TEXT);
        setObservers();
        setInputFormat(InputFormat.DEC);

//        _history.observeForever(createHistoryObserver());
    }

    void regularButtonPressed(ButtonValue value) {
        String buttonText = value.getText();
        String newText = InputFormatter.stripFormatting(inputText.getValue()) + buttonText;
        setCurrentValue(newText);
    }

    void specialButtonPressed(SpecialButtonValue value) {
        specialButtonHandler.handle(value);
    }

    void operatorButtonPressed(OperatorValue value) {
        operatorButtonHandler.handle(value);
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

//    void addHistoryValue(HistoryValue historyValue) {
//        List<HistoryValue> currentHistory = _history.getValue();
//        if (currentHistory == null) {
//            currentHistory = new ArrayList<>();
//        }
//        currentHistory.add(historyValue);
//        _history.setValue(currentHistory);
//    }

//    void clearHistory() {
//        _history.setValue(new ArrayList<HistoryValue>());
//    }

//    List<HistoryValue> getHistory() {
//        return _history.getValue();
//    }

    Long getCurrentValue() {
        return _currentValue.getValue();
    }

    String getDecText() {
        return _decText.getValue();
    }

    String getHexText() {
        return _hexText.getValue();
    }

    void clearEntry() {
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

//    private void createInputHistoryObserver() {
//        if(historyFormatObserver == null) {
//            historyFormatObserver = new Observer<String>() {
//                @Override
//                public void onChanged(String newString) {
//                    _inputHistory.setValue(newString);
//                }
//            };
//        }
//    }

//    private void setNewHistoryFormatObserver(InputFormat inputFormat) {
//        if (inputFormat.equals(InputFormat.DEC)) {
//            _decHistory.observeForever(historyFormatObserver);
//        } else {
//            _hexHistory.observeForever(historyFormatObserver);
//        }
//    }
//
//    private void removeOldHistoryFormatObservers() {
//        _decHistory.removeObserver(historyFormatObserver);
//        _hexHistory.removeObserver(historyFormatObserver);
//    }

//    private Observer<List<HistoryValue>> createHistoryObserver() {
//        return new Observer<List<HistoryValue>>() {
//            @Override
//            public void onChanged(List<HistoryValue> changedHistoryValues) {
//                StringBuilder decHistory = new StringBuilder();
//                StringBuilder hexHistory = new StringBuilder();
//
//                for (HistoryValue historyValue : changedHistoryValues) {
//                    if (historyValue instanceof NumberValue) {
//                        long value = ((NumberValue) historyValue).getValue();
//                        decHistory.append(inputFormatter.formatDec(value));
//                        hexHistory.append(inputFormatter.formatHex(value));
//                    }
//                    if (historyValue instanceof OperatorValue) {
//                        String operator = ((OperatorValue) historyValue).getText();
//                        decHistory.append(" ");
//                        decHistory.append(operator);
//                        hexHistory.append(" ");
//                        hexHistory.append(operator);
//                    }
//                }
//
//                _decHistory.setValue(decHistory.toString());
//                _hexHistory.setValue(hexHistory.toString());
//            }
//        };
//    }

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
        _decText.setValue(InputFormatter.formatDec(currentValue));
    }

    private void setHexTextFormattedValue(Long currentValue) {
        _hexText.setValue(InputFormatter.formatHexString(Long.toHexString(currentValue)));
    }

    private void setBinTextFormattedValue(Long currentValue) {
        String binary = Long.toBinaryString(currentValue);
        List<String> formattedBinary = InputFormatter.formatBinString(binary);
        String topText = formattedBinary.get(0);
        String bottomText = formattedBinary.get(1);

        _binTextTop.setValue(topText);
        _binTextBottom.setValue(bottomText);
        _binText.setValue(topText + " " + bottomText);
    }
}
