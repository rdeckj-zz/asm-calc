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

import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.databinding.FragmentCalculatorBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<TextView> decInputViews = getDecInputViews();
        List<TextView> allInputViews = getAllInputViews();
        InputFormatClickListener decClickListener = new InputFormatClickListener(viewModel, DEC, decInputViews, allInputViews);

        setFormatListenerOnViews(decInputViews, decClickListener);
    }

    private void setHexFormatListeners() {
        List<TextView> hexInputViews = getHexInputViews();
        List<TextView> allInputViews = getAllInputViews();
        InputFormatClickListener hexClickListener = new InputFormatClickListener(viewModel, HEX, hexInputViews, allInputViews);

        setFormatListenerOnViews(hexInputViews, hexClickListener);
    }

    private void setBinFormatListeners() {
        List<TextView> binInputViews = getBinInputViews();
        List<TextView> allInputViews = getAllInputViews();
        InputFormatClickListener binClickListener = new InputFormatClickListener(viewModel, BIN, binInputViews, allInputViews);

        setFormatListenerOnViews(binInputViews, binClickListener);
    }

    private void setFormatListenerOnViews(List<TextView> inputViews, InputFormatClickListener clickListener) {
        for (TextView view : inputViews) {
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

    private void createCalculatorButtons() {
        GridView calculatorButtons = root.findViewById(R.id.calculator_buttons);
        calculatorButtons.setAdapter(new CalculatorListViewAdapter(getContext(), viewModel));
    }
}
