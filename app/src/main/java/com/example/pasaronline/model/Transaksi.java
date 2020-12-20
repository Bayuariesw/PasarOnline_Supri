package com.example.pasaronline.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaksi implements Parcelable {
    private String idTransaksi;
    private String idPembeli;
    private String namaPembeli;
    private String emailPembeli;
    private String barangId;
    private String hargaBarang;
    private String namaBarang;
    private String jumlahBarang;
    private String status;
    private String imageUrl;
    private String mKey;

    public Transaksi() {

    }

    protected Transaksi(Parcel in) {
        idTransaksi = in.readString();
        idPembeli = in.readString();
        namaPembeli = in.readString();
        emailPembeli = in.readString();
        barangId = in.readString();
        hargaBarang = in.readString();
        namaBarang = in.readString();
        jumlahBarang = in.readString();
        status = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Transaksi> CREATOR = new Creator<Transaksi>() {
        @Override
        public Transaksi createFromParcel(Parcel in) {
            return new Transaksi(in);
        }

        @Override
        public Transaksi[] newArray(int size) {
            return new Transaksi[size];
        }
    };

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getIdPembeli() {
        return idPembeli;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setJumlahBarang(String jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public void setBarangId(String barangId) {
        this.barangId = barangId;
    }

    public String getBarangId() {
        return barangId;
    }

    public void setEmailPembeli(String emailPembeli) {
        this.emailPembeli = emailPembeli;
    }

    public String getEmailPembeli() {
        return emailPembeli;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idTransaksi);
        dest.writeString(idPembeli);
        dest.writeString(namaPembeli);
        dest.writeString(emailPembeli);
        dest.writeString(barangId);
        dest.writeString(hargaBarang);
        dest.writeString(namaBarang);
        dest.writeString(jumlahBarang);
        dest.writeString(status);
        dest.writeString(imageUrl);
    }
}
