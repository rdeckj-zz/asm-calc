package com.rdecky.asmcalc.userEntry;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.databinding.RowUserEntryBinding;

import java.util.List;

public class UserEntryAdapter extends RecyclerView.Adapter<UserEntryAdapter.UserEntriesViewHolder> {
    private List<UserEntryModel> userEntryModels;
    private SelectionTracker<Long> selectionTracker;

    @NonNull
    @Override
    public UserEntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowUserEntryBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_user_entry, parent, false);
        return new UserEntriesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserEntriesViewHolder holder, int position) {
        UserEntryModel model = userEntryModels.get(position);

        boolean isSelected = false;
        if (selectionTracker != null && selectionTracker.isSelected((long) position)) {
            isSelected = true;
        }

        holder.bind(model, isSelected);
    }

    @Override
    public int getItemCount() {
        return userEntryModels.size();
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    void setUserEntryModels(List<UserEntryModel> userEntryModels) {
        this.userEntryModels = userEntryModels;
        notifyDataSetChanged();
    }

    class UserEntriesViewHolder extends RecyclerView.ViewHolder {

        RowUserEntryBinding binding;

        UserEntriesViewHolder(RowUserEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(UserEntryModel model, boolean isSelected) {
            binding.setUserEntryModel(model);
            binding.setIsSelected(isSelected);
            itemView.setActivated(isSelected);
            binding.executePendingBindings();
        }

        ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return new ItemDetailsLookup.ItemDetails<Long>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Nullable
                @Override
                public Long getSelectionKey() {
                    return getItemId();
                }
            };
        }
    }
}
