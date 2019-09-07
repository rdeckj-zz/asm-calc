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
import com.rdecky.asmcalc.databinding.FragmentUserEntryBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class UserEntryFragment extends Fragment {

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
        View v = inflater.inflate(R.layout.fragment_user_entry, container, false);
        super.onCreate(savedInstanceState);

        RecyclerView userEntryList = v.findViewById(R.id.user_entry_list);
        userEntryList.setHasFixedSize(true);
        userEntryList.setLayoutManager(new LinearLayoutManager(v.getContext()));


        final UserEntriesAdapter userEntriesAdapter = new UserEntriesAdapter(Collections.<UserEntry>emptyList());
        userEntryList.setAdapter(userEntriesAdapter);


        viewModel.items.observe(this, new Observer<List<UserEntry>>() {
            @Override
            public void onChanged(List<UserEntry> userEntries) {
                userEntriesAdapter.setUserEntries(userEntries);
            }
        });

        return v;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        RecyclerView userEntryList = root.findViewById(R.id.user_entry_list);
//        userEntryList.setHasFixedSize(true);
//
//
//        //test code
//
//        UserEntry userEntry1 = new UserEntry();
//        userEntry1.shortName = "test1";
//
//        UserEntry userEntry2 = new UserEntry();
//        userEntry1.shortName = "test2";
//
//        List<UserEntry> userEntries = new ArrayList<>();
//        userEntries.add(userEntry1);
//        userEntries.add(userEntry2);
//
//        userEntryList.setAdapter(new UserEntriesAdapter(userEntries));
//    }
}
