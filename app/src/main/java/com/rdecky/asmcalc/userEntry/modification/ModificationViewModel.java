package com.rdecky.asmcalc.userEntry.modification;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.UserEntryDao;

public class ModificationViewModel extends ViewModel {

    private UserEntryDao userEntryDao;
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
}
