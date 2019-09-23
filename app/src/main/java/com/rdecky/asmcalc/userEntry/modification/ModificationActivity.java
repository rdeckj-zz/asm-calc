package com.rdecky.asmcalc.userEntry.modification;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.rdecky.asmcalc.CustomViewModelFactory;
import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.data.source.AsmCalcDatabase;
import com.rdecky.asmcalc.databinding.ActivityModificationUserEntryBinding;

public class ModificationActivity extends AppCompatActivity {

    private ModificationViewModel modificationViewModel;
    private ActivityModificationUserEntryBinding dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomViewModelFactory viewModelFactory = new CustomViewModelFactory(AsmCalcDatabase.getInstance(this));
        modificationViewModel = ViewModelProviders.of(this, viewModelFactory).get(ModificationViewModel.class);

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_modification_user_entry);
        dataBinding.setModificationViewModel(modificationViewModel);

        dataBinding.acceptFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificationViewModel.saveUserEntry();
                finish();
            }
        });
    }
}
