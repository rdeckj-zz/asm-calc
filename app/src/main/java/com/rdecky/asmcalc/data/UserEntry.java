package com.rdecky.asmcalc.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntry {
    @PrimaryKey(autoGenerate = true)
    protected int uid;

    @ColumnInfo(name = "value")
    protected long value;

    @ColumnInfo(name = "dec_text")
    protected String decText;

    @ColumnInfo(name = "hex_text")
    protected String hexText;

    @ColumnInfo(name = "short_name")
    protected String shortName;

    @ColumnInfo(name = "description")
    protected String description;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getDecText() {
        return decText;
    }

    public void setDecText(String decText) {
        this.decText = decText;
    }

    public String getHexText() {
        return hexText;
    }

    public void setHexText(String hexText) {
        this.hexText = hexText;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {
        private long value;
        private String decText;
        private String hexText;
        private String shortName;
        private String description;

        public Builder setValue(long value) {
            this.value = value;
            return this;
        }

        public Builder setDecText(String decText) {
            this.decText = decText;
            return this;
        }

        public Builder setHexText(String hexText) {
            this.hexText = hexText;
            return this;
        }

        public Builder setShortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public UserEntry build() {
            UserEntry userEntry = new UserEntry();
            userEntry.value = value;
            userEntry.decText = decText;
            userEntry.hexText = hexText;
            userEntry.shortName = shortName;
            userEntry.description = description;
            return userEntry;
        }
    }
}
