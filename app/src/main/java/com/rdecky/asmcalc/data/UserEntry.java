package com.rdecky.asmcalc.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntry {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "value")
    public long value;

    @ColumnInfo(name = "dec_text")
    public String decText;

    @ColumnInfo(name = "hex_text")
    public String hexText;

    @ColumnInfo(name = "short_name")
    public String shortName;

    @ColumnInfo(name = "description")
    public String description;
}
