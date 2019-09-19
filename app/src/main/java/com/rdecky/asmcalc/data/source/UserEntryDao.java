package com.rdecky.asmcalc.data.source;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.rdecky.asmcalc.data.UserEntry;

import java.util.List;

@Dao
public interface UserEntryDao {

    @Query("SELECT * FROM userentry")
    LiveData<List<UserEntry>> getUserEntries();

    @Insert
    void insert(UserEntry userEntry);

    @Delete
    void deleteAll(List<UserEntry> userEntries);
}
