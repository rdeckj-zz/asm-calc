package com.rdecky.asmcalc.userEntry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.UserEntryDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserEntryViewModel extends ViewModel {

    private MutableLiveData<List<UserEntryModel>> _items = new MutableLiveData<>();
    public LiveData<List<UserEntryModel>> items = _items;

    private UserEntryDao userEntryDao;

    public UserEntryViewModel(UserEntryDao userEntryDao) {
        this.userEntryDao = userEntryDao;
        _items.setValue(Collections.<UserEntryModel>emptyList());
    }

    void observeUserEntries() {
        userEntryDao.getUserEntries().observeForever(new Observer<List<UserEntry>>() {
            @Override
            public void onChanged(List<UserEntry> userEntries) {
                _items.setValue(UserEntryModel.createList(userEntries));
            }
        });
    }
}
