package com.example.pasaronline.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dagangan implements Parcelable {
    private String id;
    private String namaDagangan;
    private String jumlah;
    private String deskripsi;
    private String idKios;

    public Dagangan(){

    }

    protected Dagangan(Parcel in) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdKios() {
        return idKios;
    }

    public void setIdKios(String idKios) {
        this.idKios = idKios;
    }

    public String getNamaDagangan() {
        return namaDagangan;
    }

    public void setNamaDagangan(String namaDagangan) {
        this.namaDagangan = namaDagangan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public static final Creator<Dagangan> CREATOR = new Creator<Dagangan>() {
        @Override
        public Dagangan createFromParcel(Parcel source) {
            return new Dagangan(source);
        }

        @Override
        public Dagangan[] newArray(int size) {
            return new Dagangan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.namaDagangan);
        dest.writeString(this.jumlah);
        dest.writeString(this.deskripsi);
    }


}
