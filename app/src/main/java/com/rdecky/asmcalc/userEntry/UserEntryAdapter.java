package com.rdecky.asmcalc.userEntry;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.rdecky.asmcalc.R;
import com.rdecky.asmcalc.databinding.RowUserEntryBinding;
import com.rdecky.asmcalc.userEntry.modification.ModificationActivity;

import java.util.List;

public class UserEntryAdapter extends RecyclerView.Adapter<UserEntryAdapter.UserEntriesViewHolder> {
    public static final String EXTRA_USER_ENTRY = "com.rdecky.extra.user_entry_model";
    private List<UserEntryModel> userEntryModels;
    private SelectionTracker<Long> selectionTracker;
    private Activity parentActivity;

    UserEntryAdapter(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public UserEntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowUserEntryBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_user_entry, parent, false);
        return new UserEntriesViewHolder(binding, parentActivity);
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

        private RowUserEntryBinding binding;
        private Activity parentActivity;

        UserEntriesViewHolder(RowUserEntryBinding binding, Activity parentActivity) {
            super(binding.getRoot());
            this.binding = binding;
            this.parentActivity = parentActivity;
        }

        void bind(UserEntryModel model, boolean isSelected) {
            updateBinding(model, isSelected);
            setOnClickListener();
        }

        private void updateBinding(UserEntryModel model, boolean isSelected) {
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

        private void setOnClickListener() {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(parentActivity, ModificationActivity.class);
                    intent.putExtra(EXTRA_USER_ENTRY, binding.getUserEntryModel().getUserEntry());
                    parentActivity.startActivity(intent);
                }
            });
        }
    }
}
