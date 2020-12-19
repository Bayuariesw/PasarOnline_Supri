package com.example.pasaronline.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pasaronline.MainPedagang;
import com.example.pasaronline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.pasaronline.read.PedagangFragment.EXTRA_EMAIL_KIOS;
import static com.example.pasaronline.read.PedagangFragment.EXTRA_NAMA_KIOS;
import static com.example.pasaronline.read.PedagangFragment.EXTRA_NAMA_PEDAGANG;
import static com.example.pasaronline.read.PedagangFragment.EXTRA_TELP_KIOS;

public class EditPedagang extends AppCompatActivity {
    //edit profil pedagang
    private EditText namaPedagang, namaKios, email, telp;
    private String pedagang, kios, emails, telpon;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private FirebaseFirestore fStore;
    private ProgressBar mProggress;
    private boolean valid = true;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pedagang);

        namaPedagang = findViewById(R.id.editUser);
        namaKios = findViewById(R.id.editKios);
        email = findViewById(R.id.editEmail);
        telp = findViewById(R.id.editTelp);
        mProggress = findViewById(R.id.progress_circles);
        mProggress.setVisibility(View.INVISIBLE);

        final Intent intent = getIntent();
        pedagang = intent.getStringExtra(EXTRA_NAMA_PEDAGANG);
        kios = intent.getStringExtra(EXTRA_NAMA_KIOS);
        emails = intent.getStringExtra(EXTRA_EMAIL_KIOS);
        telpon = intent.getStringExtra(EXTRA_TELP_KIOS);

        namaPedagang.setText(pedagang);
        namaKios.setText(kios);
        email.setText(emails);
        telp.setText(telpon);

        update = findViewById(R.id.btn_update2);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaPedagang = findViewById(R.id.editUser);
                namaKios = findViewById(R.id.editKios);
                email = findViewById(R.id.editEmail);
                telp = findViewById(R.id.editTelp);

                checkField(namaPedagang);
                checkField(namaKios);
                checkField(email);
                checkField(telp);

                if (valid){
                    final String emaill = email.getText().toString();
                    fUser.updateEmail(emaill).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference df = fStore.collection("Kios").document(fAuth.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("Name", namaPedagang.getText().toString());
                            userInfo.put("NameKios", namaKios.getText().toString());
                            userInfo.put("Email", emaill);
                            userInfo.put("Telp", telp.getText().toString());

                            df.update(userInfo);

                            Intent intent1 = new Intent(EditPedagang.this, MainPedagang.class);
                            intent1.putExtra("profile", "keProfile");
                            startActivity(intent1);
                            mProggress.setVisibility(View.VISIBLE);

                            Toast.makeText(EditPedagang.this, "Profile Berhasil diupdate", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });
                }else {
                    Toast.makeText(EditPedagang.this, "Jangan kosongkan field", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkField(EditText textField) {
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            Toast.makeText(this, "Isi field yang kosong", Toast.LENGTH_SHORT).show();
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}