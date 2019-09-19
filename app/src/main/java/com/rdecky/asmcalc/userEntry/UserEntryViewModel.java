package com.rdecky.asmcalc.userEntry;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.selection.Selection;

import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.UserEntryDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

    void showSelectionBoxes() {
        toggleSelectionBoxes(true);
    }

    void hideSelectionBoxes() {
        toggleSelectionBoxes(false);
    }

    void deleteSelectedUserEntries(Selection<Long> selection) {
        Iterator<Long> iterator = selection.iterator();
        List<UserEntryModel> userEntries = _items.getValue();
        final List<UserEntry> itemsToRemove = new ArrayList<>();

        while (iterator.hasNext()) {
            Long position = iterator.next();
            itemsToRemove.add(userEntries.get(position.intValue()).userEntry);
        }

        deleteEntriesFromDatabase(itemsToRemove);
        userEntries.removeAll(itemsToRemove);
    }

    private void deleteEntriesFromDatabase(final List<UserEntry> itemsToRemove) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                userEntryDao.deleteAll(itemsToRemove);
            }
        });
    }

    private void toggleSelectionBoxes(boolean value) {
        List<UserEntryModel> entryModels = _items.getValue();

        for (UserEntryModel userEntryModel : entryModels) {
            userEntryModel.showSelectionCheckbox = value;
        }

        _items.setValue(entryModels);
    }
}
