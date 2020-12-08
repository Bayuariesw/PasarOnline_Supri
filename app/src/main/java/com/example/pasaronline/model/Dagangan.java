package com.example.pasaronline.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dagangan implements Parcelable {
    //buat model dari dagangan
    private String id;
    private String namaDagangan;
    private String jumlah;
    private String deskripsi;
    private String harga;
    private String idKios;
    private String mImageUri;

    public Dagangan() {

    }

    protected Dagangan(Parcel in) {
        id = in.readString();
        namaDagangan = in.readString();
        jumlah = in.readString();
        deskripsi = in.readString();
        harga = in.readString();
        idKios = in.readString();
        mImageUri = in.readString();
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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
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
        dest.writeString(this.harga);
        dest.writeString(this.deskripsi);
        dest.writeString(this.idKios);
        dest.writeString(this.mImageUri);
    }


}
