package com.rdecky.asmcalc.userEntry.modification;

import android.os.AsyncTask;

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
}
