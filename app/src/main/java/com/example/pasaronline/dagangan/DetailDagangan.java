package com.example.pasaronline.dagangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pasaronline.R;
import com.squareup.picasso.Picasso;

import static com.example.pasaronline.fragment.HomeFragment.EXTRA_DESKRIPSI;
import static com.example.pasaronline.fragment.HomeFragment.EXTRA_HARGA;
import static com.example.pasaronline.fragment.HomeFragment.EXTRA_JUMLAH;
import static com.example.pasaronline.fragment.HomeFragment.EXTRA_NAMA_BARANG;
import static com.example.pasaronline.fragment.HomeFragment.EXTRA_URL;

public class DetailDagangan extends AppCompatActivity {

    private ImageView imgView;
    private TextView nama, harga, deskripsi, jumlah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dagangan);

        imgView = findViewById(R.id.imgDetail);
        nama = findViewById(R.id.namaTv);
        harga = findViewById(R.id.hargaTv);
        deskripsi = findViewById(R.id.deskripsiTv);
        jumlah = findViewById(R.id.jumlahTv);

        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra(EXTRA_URL);
        String namaBarang = intent.getStringExtra(EXTRA_NAMA_BARANG);
        String hargaBarang = intent.getStringExtra(EXTRA_HARGA);
        String descBarang = intent.getStringExtra(EXTRA_DESKRIPSI);
        String jmlBarang = intent.getStringExtra(EXTRA_JUMLAH);

        //imgView.setImageURI(Uri.parse(imgUrl));

        //set data
        Picasso.with(this).load(imgUrl).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imgView);
        nama.setText(namaBarang);
        harga.setText(hargaBarang);
        deskripsi.setText(descBarang);
        jumlah.setText("Jumlah : "+jmlBarang);

    }
}