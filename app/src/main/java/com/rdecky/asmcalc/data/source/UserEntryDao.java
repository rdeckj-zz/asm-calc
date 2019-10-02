package com.rdecky.asmcalc.data.source;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rdecky.asmcalc.data.UserEntry;

import java.util.List;

@Dao
public interface UserEntryDao {

    @Query("SELECT * FROM userentry")
    LiveData<List<UserEntry>> getUserEntries();

    @Query("SELECT * FROM userentry WHERE uid = :id")
    UserEntry getUserEntryById(int id);

    @Insert
    void insert(UserEntry userEntry);

    @Update
    void update(UserEntry userEntry);

    @Delete
    void deleteAll(List<UserEntry> userEntries);
}
