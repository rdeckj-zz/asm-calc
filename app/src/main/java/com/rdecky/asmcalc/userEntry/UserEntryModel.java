package com.rdecky.asmcalc.userEntry;

import com.rdecky.asmcalc.data.UserEntry;

import java.util.ArrayList;
import java.util.List;

public class UserEntryModel {

    public UserEntry userEntry;
    public boolean showSelectionCheckbox;

    public UserEntryModel(UserEntry userEntry) {
        this.userEntry = userEntry;
    }

    static List<UserEntryModel> createList(List<UserEntry> userEntries) {
        List<UserEntryModel> userEntryModels = new ArrayList<>();

        for (UserEntry userEntry : userEntries) {
            userEntryModels.add(new UserEntryModel(userEntry));
        }

        return userEntryModels;
    }
}
