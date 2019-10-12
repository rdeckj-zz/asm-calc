package com.rdecky.asmcalc.userEntry.modification;

import android.os.AsyncTask;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

import com.rdecky.asmcalc.BR;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.UserEntryDao;
import com.rdecky.asmcalc.util.NumberFormatter;

public class ModificationViewModel extends ViewModel implements Observable {

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private UserEntryDao userEntryDao;
    @Bindable
    private UserEntry userEntry;

    public ModificationViewModel(UserEntryDao userEntryDao, UserEntry userEntry) {
        this.userEntryDao = userEntryDao;
        this.userEntry = userEntry;
    }

    public UserEntry getUserEntry() {
        return userEntry;
    }

    public void setUserEntry(UserEntry userEntry) {
        this.userEntry = userEntry;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    void saveUserEntry() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (userEntryDao.getUserEntryById(userEntry.getUid()) != null) {
                    userEntryDao.update(userEntry);
                } else {
                    userEntryDao.insert(userEntry);
                }
            }
        });
    }

    void hexTextChanged(CharSequence newValue) {
        String noFormatting = NumberFormatter.stripFormatting(newValue.toString());
        try {
            Long value = Long.valueOf(noFormatting, 16);
            setNewValues(value);
        } catch (NumberFormatException e) {
            // Invalid, don't update
        }
    }

    void decTextChanged(CharSequence newValue) {
        String noFormatting = NumberFormatter.stripFormatting(newValue.toString());
        try {
            Long value = Long.valueOf(noFormatting, 10);
            setNewValues(value);
        } catch (NumberFormatException e) {
            // Invalid, don't update
        }
    }

    private void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }

    private void setNewValues(Long value) {
        String newHex = NumberFormatter.formatHex(value);
        String newDec = NumberFormatter.formatDec(value);
        userEntry.setHexText(newHex);
        userEntry.setDecText(newDec);

        notifyPropertyChanged(BR.userEntry);
    }
}
