package com.rdecky.asmcalc.data.source;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rdecky.asmcalc.data.UserEntry;

@Database(entities = {UserEntry.class}, version = 4)
public abstract class AsmCalcDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "UserEntries.db";
    private static AsmCalcDatabase INSTANCE = null;

    public abstract UserEntryDao userEntryDao();

    public static AsmCalcDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AsmCalcDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
