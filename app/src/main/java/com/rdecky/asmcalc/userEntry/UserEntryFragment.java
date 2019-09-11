package com.rdecky.asmcalc.userEntry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rdecky.asmcalc.CustomViewModelFactory;
import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.databinding.FragmentCalculatorBinding;
import com.rdecky.asmcalc.databinding.FragmentUserEntryBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class UserEntryFragment extends Fragment {

    FragmentUserEntryBinding dataBinding;
    private UserEntryViewModel viewModel;
    private View root;

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

        root = dataBinding.getRoot();

        dataBinding.setLifecycleOwner(getViewLifecycleOwner());
        dataBinding.userEntryList.setAdapter(new UserEntriesAdapter(viewModel));

        viewModel.observeUserEntries();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
