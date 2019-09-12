package com.rdecky.asmcalc.userEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.rdecky.asmcalc.CustomViewModelFactory;
import com.rdecky.asmcalc.databinding.FragmentUserEntryBinding;

public class UserEntryFragment extends Fragment {

    private FragmentUserEntryBinding dataBinding;
    private UserEntryViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomViewModelFactory viewModelFactory = CustomViewModelFactory.getInstance(getContext());
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserEntryViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = FragmentUserEntryBinding.inflate(inflater, container, false);
        dataBinding.setViewModel(viewModel);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataBinding.setLifecycleOwner(getViewLifecycleOwner());
        dataBinding.userEntryList.setAdapter(new UserEntriesAdapter());

        viewModel.observeUserEntries();
    }
}
