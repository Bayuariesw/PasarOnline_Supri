package com.example.pasaronline.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

public class Keranjang implements Parcelable {
    private String id;
    private String idPembeli;
    private String barangId;
    private String hargaBarang;
    private String namaBarang;
    private String jumlahBarang;
    private String imageUrl;
    private String mKey;

    public Keranjang(){

    }

    protected Keranjang(Parcel in) {
        id = in.readString();
        idPembeli = in.readString();
        barangId = in.readString();
        hargaBarang = in.readString();
        namaBarang = in.readString();
        jumlahBarang = in.readString();
        imageUrl = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarangId() {
        return barangId;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(String jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public static final Creator<Keranjang> CREATOR = new Creator<Keranjang>() {
        @Override
        public Keranjang createFromParcel(Parcel in) {
            return new Keranjang(in);
        }

        @Override
        public Keranjang[] newArray(int size) {
            return new Keranjang[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idPembeli);
        dest.writeString(barangId);
        dest.writeString(hargaBarang);
        dest.writeString(namaBarang);
        dest.writeString(jumlahBarang);
        dest.writeString(imageUrl);
    }

    public double getTotalPrice(){
        return 1;
    }
}
