package com.example.hariswedira.todofirebase.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
    private String nama;
    private String namaToko;
    private String username;
    private String password;
    private String kodeUnik;

    public User() {
    }

    public User(String nama, String namaToko, String username, String password, String kodeUnik) {
        this.nama = nama;
        this.namaToko = namaToko;
        this.username = username;
        this.password = password;
        this.kodeUnik = kodeUnik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKodeUnik() {
        return kodeUnik;
    }

    public void setKodeUnik(String kodeUnik) {
        this.kodeUnik = kodeUnik;
    }

    //parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeString(this.namaToko);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.kodeUnik);
    }

    protected User(Parcel in) {
        this.nama = in.readString();
        this.namaToko = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.kodeUnik = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
