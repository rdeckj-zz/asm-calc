package com.rdecky.asmcalc.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.databinding.FragmentCalculatorBinding;

import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat.BIN;
import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat.DEC;
import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat.HEX;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel viewModel;
    private FragmentCalculatorBinding dataBinding;
    private View root;

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

        root = dataBinding.getRoot();
        createCalculatorButtons();
        setInputFormatListeners();

        viewModel.initialize();

        return dataBinding.getRoot();
    }

    private void setInputFormatListeners() {
        setDecFormatListeners();
        setHexFormatListeners();
        setBinFormatListeners();
    }

    private void setDecFormatListeners() {
        InputFormatClickListener decClickListener = new InputFormatClickListener(viewModel, DEC);
        root.findViewById(R.id.dec_title).setOnClickListener(decClickListener);
        root.findViewById(R.id.dec_display).setOnClickListener(decClickListener);
    }

    private void setHexFormatListeners() {
        InputFormatClickListener hexClickListener = new InputFormatClickListener(viewModel, HEX);
        root.findViewById(R.id.hex_title).setOnClickListener(hexClickListener);
        root.findViewById(R.id.hex_display).setOnClickListener(hexClickListener);
    }

    private void setBinFormatListeners() {
        InputFormatClickListener binClickListener = new InputFormatClickListener(viewModel, BIN);
        root.findViewById(R.id.bin_title).setOnClickListener(binClickListener);
        root.findViewById(R.id.bin_display_top).setOnClickListener(binClickListener);
        root.findViewById(R.id.bin_display_bottom).setOnClickListener(binClickListener);
    }

    private void createCalculatorButtons() {
        GridView calculatorButtons = root.findViewById(R.id.calculator_buttons);
        calculatorButtons.setAdapter(new CalculatorListViewAdapter(getContext(), viewModel));
    }
}
