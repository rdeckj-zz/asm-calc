package com.rdecky.asmcalc.userEntry;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserEntryBinding {
    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<UserEntryModel> items) {
        RecyclerView.Adapter<?> adapter = view.getAdapter();
        if (adapter instanceof UserEntryAdapter) {
            ((UserEntryAdapter) adapter).setUserEntryModels(items);
        } else {
            throw new IllegalArgumentException("RecyclerView.Adapter is not a UserEntryAdapter");
        }
    }
}
