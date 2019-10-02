package com.rdecky.asmcalc.userEntry.modification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.rdecky.asmcalc.CustomViewModelFactory;
import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.AsmCalcDatabase;
import com.rdecky.asmcalc.databinding.ActivityModificationUserEntryBinding;

import static com.rdecky.asmcalc.userEntry.UserEntryAdapter.EXTRA_USER_ENTRY;

public class ModificationActivity extends AppCompatActivity {

    private ModificationViewModel modificationViewModel;
    private ActivityModificationUserEntryBinding dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createViewModel();
        setupDataBinding();

        setAcceptOnClickListener();
        updateViewWithExtra(getIntent());
    }

    private void setupDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_modification_user_entry);
        dataBinding.setModificationViewModel(modificationViewModel);
    }

    private void createViewModel() {
        CustomViewModelFactory viewModelFactory = new CustomViewModelFactory(AsmCalcDatabase.getInstance(this));
        modificationViewModel = ViewModelProviders.of(this, viewModelFactory).get(ModificationViewModel.class);
    }

    private void setAcceptOnClickListener() {
        dataBinding.acceptFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificationViewModel.saveUserEntry();
                finish();
            }
        });
    }

    private void updateViewWithExtra(Intent intent) {
        if (intent != null) {
            UserEntry userEntry = intent.getParcelableExtra(EXTRA_USER_ENTRY);
            if (userEntry != null) {
                modificationViewModel.setUserEntry(userEntry);
                dataBinding.executePendingBindings();
            }
        }
    }
}
