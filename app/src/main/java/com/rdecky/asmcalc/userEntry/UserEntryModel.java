package com.rdecky.asmcalc.userEntry;

import com.rdecky.asmcalc.data.UserEntry;

import java.util.ArrayList;
import java.util.List;

public class UserEntryModel {

    private UserEntry userEntry;
    private boolean showSelectionCheckbox;

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

    public UserEntry getUserEntry() {
        return userEntry;
    }

    public boolean getShowSelectionCheckbox() {
        return showSelectionCheckbox;
    }

    void setShowSelectionCheckbox(boolean showSelectionCheckbox) {
        this.showSelectionCheckbox = showSelectionCheckbox;
    }
}
