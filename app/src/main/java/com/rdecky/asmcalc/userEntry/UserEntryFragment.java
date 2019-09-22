package com.rdecky.asmcalc.userEntry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.data.source.AsmCalcDatabase;
import com.rdecky.asmcalc.databinding.FragmentUserEntryBinding;
import com.rdecky.asmcalc.userEntry.modification.ModificationActivity;

import java.util.Objects;

public class UserEntryFragment extends Fragment {

    private FragmentUserEntryBinding dataBinding;
    private UserEntryViewModel userEntryViewModel;
    private SelectionTracker<Long> selectionTracker;
    private UserEntryAdapter userEntryAdapter;
    private ImageView delete;
    private Activity parentActivity;

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
        parentActivity = Objects.requireNonNull(getActivity());

        setDeleteClickListener();

        dataBinding.setLifecycleOwner(getViewLifecycleOwner());
        createUserEntryAdapter();
        RecyclerView recyclerView = dataBinding.userEntryList;
        recyclerView.setAdapter(userEntryAdapter);

        createSelectionTracker(recyclerView);

        userEntryViewModel.observeUserEntries();
        userEntryAdapter.setSelectionTracker(selectionTracker);

        setAddClickListener();
        setSelectionObserver();
    }

    private void setAddClickListener() {
        parentActivity.findViewById(R.id.add_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentActivity, ModificationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setDeleteClickListener() {
        delete = parentActivity.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEntryViewModel.deleteSelectedUserEntries(selectionTracker.getSelection());
                selectionTracker.clearSelection();
            }
        });
    }

    private void createUserEntryAdapter() {
        userEntryAdapter = new UserEntryAdapter();
        userEntryAdapter.setHasStableIds(true);
    }

    private void createSelectionTracker(RecyclerView recyclerView) {
        selectionTracker = new SelectionTracker.Builder<>(
                "selection-tracker",
                recyclerView,
                new StableIdKeyProvider(recyclerView),
                new UserEntryLookup(recyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.<Long>createSelectAnything())
                .build();
    }

    private void setSelectionObserver() {
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();

                if (selectionTracker.getSelection().size() > 0) {
                    delete.setVisibility(View.VISIBLE);
                    userEntryViewModel.showSelectionBoxes();
                } else {
                    delete.setVisibility(View.GONE);
                    userEntryViewModel.hideSelectionBoxes();
                }
            }
        });
    }
}
