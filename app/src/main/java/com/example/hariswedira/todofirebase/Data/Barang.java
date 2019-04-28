package com.example.hariswedira.todofirebase.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Barang implements Parcelable{
    private String kodeBarang;
    private String namaBarang;
    private String jenisBarang;
    private int hargaBarang;

    public Barang() {
    }

    public Barang(String kodeBarang, String namaBarang, String jenisBarang, int hargaBarang) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.jenisBarang = jenisBarang;
        this.hargaBarang = hargaBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getJenisBarang() {
        return jenisBarang;
    }

    public void setJenisBarang(String jenisBarang) {
        this.jenisBarang = jenisBarang;
    }

    public int getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(int hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kodeBarang);
        dest.writeString(this.namaBarang);
        dest.writeString(this.jenisBarang);
        dest.writeInt(this.hargaBarang);
    }

    protected Barang(Parcel in) {
        this.kodeBarang = in.readString();
        this.namaBarang = in.readString();
        this.jenisBarang = in.readString();
        this.hargaBarang = in.readInt();
    }

    public static final Creator<Barang> CREATOR = new Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel source) {
            return new Barang(source);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
        }
    };
}
