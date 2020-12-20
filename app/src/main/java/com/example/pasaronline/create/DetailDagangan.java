package com.example.pasaronline.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pasaronline.MainActivity;
import com.example.pasaronline.MainPedagang;
import com.example.pasaronline.R;
import com.example.pasaronline.model.Keranjang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import static com.example.pasaronline.read.HomeFragment.EXTRA_DESKRIPSI;
import static com.example.pasaronline.read.HomeFragment.EXTRA_HARGA;
import static com.example.pasaronline.read.HomeFragment.EXTRA_ID_BARANG;
import static com.example.pasaronline.read.HomeFragment.EXTRA_JUMLAH;
import static com.example.pasaronline.read.HomeFragment.EXTRA_NAMA_BARANG;
import static com.example.pasaronline.read.HomeFragment.EXTRA_URL;

public class DetailDagangan extends AppCompatActivity implements View.OnClickListener{
    //ini buat 22nya
    private int total = 0;
    private ImageView imgView;
    private TextView nama, harga, deskripsi, jumlah, banyak;
    private String jmlBarang;
    private String descBarang;
    private String hargaBarang;
    private String namaBarang;
    private String imgUrl, idBarang;
    private Button tambahKeranjang;
    private DatabaseReference mDatabase;
    private FirebaseUser fUser;
    private FirebaseFirestore fStore;
    private Keranjang keranjang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dagangan);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        imgView = findViewById(R.id.imgDetail);
        nama = findViewById(R.id.namaTv);
        harga = findViewById(R.id.hargaTv);
        deskripsi = findViewById(R.id.deskripsiTv);
        jumlah = findViewById(R.id.jumlahTv);
        banyak = findViewById(R.id.banyakTv);
        tambahKeranjang = findViewById(R.id.btnKeranjang);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        imgUrl = intent.getStringExtra(EXTRA_URL);
        namaBarang = intent.getStringExtra(EXTRA_NAMA_BARANG);
        hargaBarang = intent.getStringExtra(EXTRA_HARGA);
        descBarang = intent.getStringExtra(EXTRA_DESKRIPSI);
        jmlBarang = intent.getStringExtra(EXTRA_JUMLAH);
        idBarang = intent.getStringExtra(EXTRA_ID_BARANG);

        //imgView.setImageURI(Uri.parse(imgUrl));

        //set data
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.ic_launcher_background).fit().centerCrop().into(imgView);
        nama.setText(namaBarang);
        harga.setText(hargaBarang);
        deskripsi.setText(descBarang);
        jumlah.setText("Jumlah : "+jmlBarang);

        tambahKeranjang.setOnClickListener(this);
        keranjang = new Keranjang();

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
        String banyakBarang = String.valueOf(total).trim();
        String barangId = idBarang.trim();
        String harga = hargaBarang.trim();
        String barangNama = namaBarang.trim();
        String url = imgUrl;

        if(total != 0){
            Toast.makeText(this, "Berhasil Menambahkan Barang ke Keranjang", Toast.LENGTH_SHORT).show();
            DatabaseReference dbChart = mDatabase.child("keranjang");
            String id = dbChart.push().getKey();
            keranjang.setId(id);
            keranjang.setBarangId(barangId);
            keranjang.setIdPembeli(FirebaseAuth.getInstance().getUid());
            keranjang.setHargaBarang(harga);
            keranjang.setNamaBarang(barangNama);
            keranjang.setJumlahBarang(banyakBarang);
            keranjang.setImageUrl(url);

            dbChart.child(id).setValue(keranjang);

            Intent intent = new Intent(DetailDagangan.this, MainActivity.class);
            intent.putExtra("keranjang", "keKeranjang");
            startActivity(intent);

            finish();
        }else {
            Toast.makeText(DetailDagangan.this, "Masukan Jumlah Barang", Toast.LENGTH_SHORT).show();
        }
    }
}  