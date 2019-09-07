package com.rdecky.asmcalc.userEntry;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.rdecky.asmcalc.BR;
import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.databinding.RowUserEntryBinding;

import java.util.List;

public class UserEntriesAdapter extends RecyclerView.Adapter<UserEntriesAdapter.UserEntriesViewHolder> {

    static class UserEntriesViewHolder extends RecyclerView.ViewHolder {

         RowUserEntryBinding binding;

        public UserEntriesViewHolder(RowUserEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserEntry entry) {

            binding.setUserEntry(entry);
        }
    }

    private List<UserEntry> userEntries;

    UserEntriesAdapter(List<UserEntry> userEntries) {
        this.userEntries = userEntries;
    }

    @NonNull
    @Override
    public UserEntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowUserEntryBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_user_entry, parent, false);
        return new UserEntriesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserEntriesViewHolder holder, int position) {
        UserEntry entry = userEntries.get(position);
        holder.bind(entry);
    }

    @Override
    public int getItemCount() {
        return userEntries.size();
    }

    public void setUserEntries(List<UserEntry> userEntries) {
        this.userEntries = userEntries;
    }

}
