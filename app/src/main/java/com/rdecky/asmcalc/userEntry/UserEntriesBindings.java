package com.rdecky.asmcalc.userEntry;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rdecky.asmcalc.data.UserEntry;

import java.util.List;

public class UserEntriesBindings {
    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<UserEntry> items) {
        RecyclerView.Adapter<?> adapter = view.getAdapter();
        if (adapter instanceof UserEntriesAdapter) {
            ((UserEntriesAdapter) adapter).setUserEntries(items);
        } else {
            throw new IllegalArgumentException("RecyclerView.Adapter is not a UserEntriesAdapter");
        }
    }
}
