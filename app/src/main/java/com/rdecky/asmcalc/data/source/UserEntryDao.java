package com.rdecky.asmcalc.data.source;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.rdecky.asmcalc.data.UserEntry;

import java.util.List;

@Dao
public interface UserEntryDao {
    @Query("SELECT * FROM userentry")
    List<UserEntry> getUserEntries();

    @Insert
    void insertAll(UserEntry... userEntries);

    @Delete
    void delete(UserEntry userEntry);
}
