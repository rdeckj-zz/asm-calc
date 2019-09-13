package com.rdecky.asmcalc.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.rdecky.asmcalc.CustomViewModelFactory;
import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.data.source.AsmCalcDatabase;
import com.rdecky.asmcalc.databinding.FragmentCalculatorBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat.BIN;
import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat.DEC;
import static com.rdecky.asmcalc.calculator.InputFormatClickListener.InputFormat.HEX;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel calculatorViewModel;
    private HistoryBarViewModel historyBarViewModel;
    private View root;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomViewModelFactory calculatorViewModelFactory = new CustomViewModelFactory(AsmCalcDatabase.getInstance(getContext()));
        calculatorViewModel = ViewModelProviders.of(this, calculatorViewModelFactory).get(CalculatorViewModel.class);
        CustomViewModelFactory historyBarViewModelFactory = new CustomViewModelFactory(calculatorViewModel);
        historyBarViewModel = ViewModelProviders.of(this, historyBarViewModelFactory).get(HistoryBarViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCalculatorBinding dataBinding = FragmentCalculatorBinding.inflate(inflater, container, false);
        dataBinding.setCalculatorViewModel(calculatorViewModel);
        dataBinding.setHistoryBarViewModel(historyBarViewModel);
        dataBinding.setLifecycleOwner(this.getViewLifecycleOwner());

        root = dataBinding.getRoot();
        List<CalculatorButton> calculatorButtons = createCalculatorButtons();
        setInputFormatListeners(calculatorButtons);

        return root;
    }

    private void setInputFormatListeners(List<CalculatorButton> calculatorButtons) {
        setDecFormatListeners(calculatorButtons);
        setHexFormatListeners(calculatorButtons);
        setBinFormatListeners(calculatorButtons);
    }

    private void setDecFormatListeners(List<CalculatorButton> calculatorButtons) {
        GroupedInputView decGroup = new GroupedInputView(getDecInputViews(), getAllInputViews(), DEC);
        InputFormatClickListener decClickListener = new InputFormatClickListener(calculatorViewModel, historyBarViewModel, decGroup, calculatorButtons);

        setFormatListenerOnViews(decGroup, decClickListener);
    }

    private void setHexFormatListeners(List<CalculatorButton> calculatorButtons) {
        GroupedInputView hexGroup = new GroupedInputView(getHexInputViews(), getAllInputViews(), HEX);
        InputFormatClickListener hexClickListener = new InputFormatClickListener(calculatorViewModel, historyBarViewModel, hexGroup, calculatorButtons);

        setFormatListenerOnViews(hexGroup, hexClickListener);
    }

    private void setBinFormatListeners(List<CalculatorButton> calculatorButtons) {
        GroupedInputView binGroup = new GroupedInputView(getBinInputViews(), getAllInputViews(), BIN);
        InputFormatClickListener binClickListener = new InputFormatClickListener(calculatorViewModel, historyBarViewModel, binGroup, calculatorButtons);

        setFormatListenerOnViews(binGroup, binClickListener);
    }

    private void setFormatListenerOnViews(GroupedInputView groupedInputView, InputFormatClickListener clickListener) {
        for (TextView view : groupedInputView.getGroupedViews()) {
            view.setOnClickListener(clickListener);
        }
    }

    private List<TextView> getDecInputViews() {
        TextView decTitle = root.findViewById(R.id.dec_title);
        TextView decDisplay = root.findViewById(R.id.dec_display);

        return Arrays.asList(decTitle, decDisplay);
    }

    private List<TextView> getHexInputViews() {
        TextView hexTitle = root.findViewById(R.id.hex_title);
        TextView hexDisplay = root.findViewById(R.id.hex_display);

        return Arrays.asList(hexTitle, hexDisplay);
    }

    private List<TextView> getBinInputViews() {
        TextView binTitle = root.findViewById(R.id.bin_title);
        TextView binDisplayTop = root.findViewById(R.id.bin_display_top);
        TextView binDisplayBottom = root.findViewById(R.id.bin_display_bottom);

        return Arrays.asList(binTitle, binDisplayTop, binDisplayBottom);
    }

    private List<TextView> getAllInputViews() {
        List<TextView> allInputViews = new ArrayList<>();
        allInputViews.addAll(getDecInputViews());
        allInputViews.addAll(getHexInputViews());
        allInputViews.addAll(getBinInputViews());

        return allInputViews;
    }

    private List<CalculatorButton> createCalculatorButtons() {
        GridView calculatorButtons = root.findViewById(R.id.calculator_buttons);
        CalculatorListViewAdapter adapter = new CalculatorListViewAdapter(getContext(), calculatorViewModel, historyBarViewModel);
        calculatorButtons.setAdapter(adapter);
        return adapter.getAllButtons();
    }
}
