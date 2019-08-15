package com.rdecky.asmcalc.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.databinding.FragmentCalculatorBinding;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel viewModel;
    private FragmentCalculatorBinding dataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = FragmentCalculatorBinding.inflate(inflater, container, false);
        dataBinding.setViewmodel(viewModel);
        dataBinding.setLifecycleOwner(this.getViewLifecycleOwner());

        GridView calculatorButtons = dataBinding.getRoot().findViewById(R.id.calculator_buttons);
        calculatorButtons.setAdapter(new CalculatorListViewAdapter(getContext()));

        viewModel.initializeValues();

        return dataBinding.getRoot();
    }
}
