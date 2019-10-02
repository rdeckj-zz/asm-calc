package com.rdecky.asmcalc.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntry implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "value")
    protected long value;

    @ColumnInfo(name = "dec_text")
    private String decText;

    @ColumnInfo(name = "hex_text")
    private String hexText;

    @ColumnInfo(name = "short_name")
    private String shortName;

    @ColumnInfo(name = "description")
    private String description;

    public UserEntry() {}

    protected UserEntry(Parcel in) {
        uid = in.readInt();
        value = in.readLong();
        decText = in.readString();
        hexText = in.readString();
        shortName = in.readString();
        description = in.readString();
    }

    public static final Creator<UserEntry> CREATOR = new Creator<UserEntry>() {
        @Override
        public UserEntry createFromParcel(Parcel in) {
            return new UserEntry(in);
        }

        @Override
        public UserEntry[] newArray(int size) {
            return new UserEntry[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeLong(value);
        parcel.writeString(decText);
        parcel.writeString(hexText);
        parcel.writeString(shortName);
        parcel.writeString(description);
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
