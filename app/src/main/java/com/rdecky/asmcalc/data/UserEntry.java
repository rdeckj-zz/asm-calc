package com.rdecky.asmcalc.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntry {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "decimal_value")
    public long decimalValue;

    @ColumnInfo(name = "hex_value")
    public String hexValue;

    @ColumnInfo(name = "short_name")
    public String shortName;

    @ColumnInfo(name = "description")
    public String description;
}
