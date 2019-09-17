package com.rdecky.asmcalc.userEntry;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class UserEntryLookup extends ItemDetailsLookup<Long> {

    private RecyclerView recyclerView;

    UserEntryLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if(view != null) {
            final RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if(viewHolder instanceof UserEntryAdapter.UserEntriesViewHolder) {
                final UserEntryAdapter.UserEntriesViewHolder userEntriesViewHolder = (UserEntryAdapter.UserEntriesViewHolder) viewHolder;
                return userEntriesViewHolder.getItemDetails();
            }
        }
        return null;
    }
}
