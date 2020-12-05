package com.example.pasaronline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pasaronline.model.Dagangan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TambahDagang extends AppCompatActivity implements  View.OnClickListener{

    TextView tvName;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Button btnAdd;
    EditText etNama, etJumlah, etDeskripsi;

    private Dagangan dagangan;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_dagang);

        tvName = findViewById(R.id.tvnew);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etNama = findViewById(R.id.etNama);
        etJumlah = findViewById(R.id.etJumlah);
        etDeskripsi = findViewById(R.id.etDesc);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(this);

        dagangan = new Dagangan();

        Button logout = findViewById(R.id.btnLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdd){
            saveDagangan();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        DocumentReference df = FirebaseFirestore.getInstance().collection("Kios").document(FirebaseAuth.getInstance().getUid());

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String nama = documentSnapshot.getString("Name");
                    tvName.setText(nama);
                }else{
                    Toast.makeText(TambahDagang.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void saveDagangan() {
        String namaDgng = etNama.getText().toString().trim();
        String jumlah = etJumlah.getText().toString().trim();
        String deskripsi = etDeskripsi.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(namaDgng)){
            isEmptyFields = true;
            etNama.setText("Field tidak boleh kosong");
        }

        if (TextUtils.isEmpty(jumlah)){
            isEmptyFields = true;
            etJumlah.setText("Field tidak boleh kosong");
        }
        if (TextUtils.isEmpty(deskripsi)){
            isEmptyFields = true;
            etJumlah.setText("Field tidak boleh kosong");
        }

        if (! isEmptyFields){
            Toast.makeText(TambahDagang.this, "Data Berhasil ditambahkan", Toast.LENGTH_SHORT).show();

            DatabaseReference dbDagang = mDatabase.child("dagangan");
            String id = dbDagang.push().getKey();
            dagangan.setId(id);
            dagangan.setNamaDagangan(namaDgng);
            dagangan.setJumlah(jumlah);
            dagangan.setDeskripsi(deskripsi);
            dagangan.setIdKios(FirebaseAuth.getInstance().getUid());

            //insert data
            dbDagang.child(id).setValue(dagangan);

            finish();
        }
    }
}