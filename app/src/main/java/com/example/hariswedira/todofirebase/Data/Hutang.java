package com.example.hariswedira.todofirebase.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Hutang implements Parcelable{
    private String kodeHutang;
    private String namaPenghutang;
    private int totalHutang;

    public Hutang() {
    }

    public Hutang(String kodeHutang, String namaPenghutang, int totalHutang) {
        this.kodeHutang = kodeHutang;
        this.namaPenghutang = namaPenghutang;
        this.totalHutang = totalHutang;
    }

    public String getKodeHutang() {
        return kodeHutang;
    }

    public void setKodeHutang(String kodeHutang) {
        this.kodeHutang = kodeHutang;
    }

    public String getNamaPenghutang() {
        return namaPenghutang;
    }

    public void setNamaPenghutang(String namaPenghutang) {
        this.namaPenghutang = namaPenghutang;
    }

    public int getTotalHutang() {
        return totalHutang;
    }

    public void setTotalHutang(int totalHutang) {
        this.totalHutang = totalHutang;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kodeHutang);
        dest.writeString(this.namaPenghutang);
        dest.writeInt(this.totalHutang);
    }

    protected Hutang(Parcel in) {
        this.kodeHutang = in.readString();
        this.namaPenghutang = in.readString();
        this.totalHutang = in.readInt();
    }

    public static final Creator<Hutang> CREATOR = new Creator<Hutang>() {
        @Override
        public Hutang createFromParcel(Parcel source) {
            return new Hutang(source);
        }

        @Override
        public Hutang[] newArray(int size) {
            return new Hutang[size];
        }
    };
}
