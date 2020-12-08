package com.example.pasaronline.dagangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pasaronline.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import static com.example.pasaronline.fragment.HomeFragment.EXTRA_DESKRIPSI;
import static com.example.pasaronline.fragment.HomeFragment.EXTRA_HARGA;
import static com.example.pasaronline.fragment.HomeFragment.EXTRA_JUMLAH;
import static com.example.pasaronline.fragment.HomeFragment.EXTRA_NAMA_BARANG;
import static com.example.pasaronline.fragment.HomeFragment.EXTRA_URL;

public class DetailDagangan extends AppCompatActivity implements View.OnClickListener{

    private int total = 0;
    private ImageView imgView;
    private TextView nama, harga, deskripsi, jumlah, banyak;
    private String jmlBarang;
    private String descBarang;
    private String hargaBarang;
    private String namaBarang;
    private String imgUrl;
    private Button tambahKeranjang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dagangan);

        imgView = findViewById(R.id.imgDetail);
        nama = findViewById(R.id.namaTv);
        harga = findViewById(R.id.hargaTv);
        deskripsi = findViewById(R.id.deskripsiTv);
        jumlah = findViewById(R.id.jumlahTv);
        banyak = findViewById(R.id.banyakTv);
        tambahKeranjang = findViewById(R.id.btnKeranjang);

        Intent intent = getIntent();
        imgUrl = intent.getStringExtra(EXTRA_URL);
        namaBarang = intent.getStringExtra(EXTRA_NAMA_BARANG);
        hargaBarang = intent.getStringExtra(EXTRA_HARGA);
        descBarang = intent.getStringExtra(EXTRA_DESKRIPSI);
        jmlBarang = intent.getStringExtra(EXTRA_JUMLAH);

        //imgView.setImageURI(Uri.parse(imgUrl));

        //set data
        Picasso.with(this).load(imgUrl).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imgView);
        nama.setText(namaBarang);
        harga.setText(hargaBarang);
        deskripsi.setText(descBarang);
        jumlah.setText("Jumlah : "+jmlBarang);

        tambahKeranjang.setOnClickListener(this);


    }

    public void tambahSatu(View view){
        total += 1;
        int barang = Integer.parseInt(jmlBarang);
        if (banyak != null){
            if (total >= barang){
                total = barang;
            }
            banyak.setText(Integer.toString(total));
        }
    }

    public void kurangSatu(View view){
        total -= 1;
        if (banyak != null){
            if (total <= 0){
                total = 0;
            }
            banyak.setText(Integer.toString(total));
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnKeranjang){
            addBarang();
        }
    }

    private void addBarang() {
        String banyakBarang = String.valueOf(total);
//        String idBarang =
    }
}