package com.rdecky.asmcalc.userEntry.modification;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    private EditText decTextInput;
    private EditText hexTextInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createViewModel();
        setupDataBinding();
        saveViewReferences();
        setAcceptOnClickListener();
        updateViewWithExtra(getIntent());
        setInputWatcher(decTextInput, decTextWatcher);
        setInputWatcher(hexTextInput, hexTextWatcher);
    }

    private void setupDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_modification_user_entry);
        dataBinding.setModificationViewModel(modificationViewModel);
    }

    private void createViewModel() {
        CustomViewModelFactory viewModelFactory = new CustomViewModelFactory(AsmCalcDatabase.getInstance(this));
        modificationViewModel = ViewModelProviders.of(this, viewModelFactory).get(ModificationViewModel.class);
    }

    private void saveViewReferences() {
        decTextInput = findViewById(R.id.edit_dec_text);
        hexTextInput = findViewById(R.id.edit_hex_text);
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

    private void setInputWatcher(final EditText watchedInput, final TextWatcher watcher) {
        watchedInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    watchedInput.addTextChangedListener(watcher);
                }
                else{
                    watchedInput.removeTextChangedListener(watcher);
                }
            }
        });
    }

    final TextWatcher hexTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            modificationViewModel.hexTextChanged(hexTextInput.getText());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    final TextWatcher decTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            modificationViewModel.decTextChanged(decTextInput.getText());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
