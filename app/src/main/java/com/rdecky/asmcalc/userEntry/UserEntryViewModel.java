package com.rdecky.asmcalc.userEntry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.AsmCalcDatabase;
import com.rdecky.asmcalc.data.source.UserEntryDao;

import java.util.List;

public class UserEntryViewModel extends ViewModel {

    private MutableLiveData<List<UserEntry>> _items = new MutableLiveData<>();
    public LiveData<List<UserEntry>> items = _items;

    private UserEntryDao userEntryDao;

    public UserEntryViewModel(UserEntryDao userEntryDao) {
        this.userEntryDao = userEntryDao;
    }

    public void loadItems() {
        _items.setValue(userEntryDao.getUserEntries());
    }
}
