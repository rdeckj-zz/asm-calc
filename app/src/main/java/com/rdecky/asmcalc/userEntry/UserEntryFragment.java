package com.rdecky.asmcalc.userEntry;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.RecyclerView;

import com.rdecky.asmcalc.CustomViewModelFactory;
import com.rdecky.asmcalc.data.source.AsmCalcDatabase;
import com.rdecky.asmcalc.databinding.FragmentUserEntryBinding;

public class UserEntryFragment extends Fragment {

    private FragmentUserEntryBinding dataBinding;
    private UserEntryViewModel userEntryViewModel;
    private SelectionTracker<Long> selectionTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomViewModelFactory viewModelFactory = new CustomViewModelFactory(AsmCalcDatabase.getInstance(getContext()));
        userEntryViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserEntryViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = FragmentUserEntryBinding.inflate(inflater, container, false);
        dataBinding.setUserEntryViewModel(userEntryViewModel);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataBinding.setLifecycleOwner(getViewLifecycleOwner());
        UserEntryAdapter userEntriesAdapter = new UserEntryAdapter();
        RecyclerView recyclerView = dataBinding.userEntryList;
        userEntriesAdapter.setHasStableIds(true);
        recyclerView.setAdapter(userEntriesAdapter);

        selectionTracker = new SelectionTracker.Builder<>(
                "selection-tracker",
                recyclerView,
                new StableIdKeyProvider(recyclerView),
                new UserEntryLookup(recyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.<Long>createSelectAnything())
                .build();

        userEntryViewModel.observeUserEntries();
        userEntriesAdapter.setSelectionTracker(selectionTracker);

        setSelectionObserver();
    }

    private void setSelectionObserver() {
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onSelectionChanged() {
                Log.v("Selection Changed", "Selection: " + selectionTracker.getSelection());
                super.onSelectionChanged();
            }
        });
    }
}
