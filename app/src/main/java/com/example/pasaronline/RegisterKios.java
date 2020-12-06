package com.example.pasaronline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterKios extends AppCompatActivity {

    TextView login;
    EditText namaKios, email, namaPemilik, password, telp, alamat;
    Button btnRgis;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_kios);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        namaKios = findViewById(R.id.etKios);
        namaPemilik = findViewById(R.id.etNama);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        telp = findViewById(R.id.etTelp);
        alamat = findViewById(R.id.etAlamat);

        login = findViewById(R.id.tvLogin);
        btnRgis = findViewById(R.id.btnRegisterKios);

        btnRgis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(namaPemilik);
                checkField(namaKios);
                checkField(email);
                checkField(password);
                checkField(telp);
                checkField(alamat);

                if(valid){
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(RegisterKios.this, "Akun Kios Berhasil dibuat", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Kios").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("NameKios", namaKios.getText().toString());
                            userInfo.put("Name", namaPemilik.getText().toString());
                            userInfo.put("Email", email.getText().toString());
                            userInfo.put("Telp", telp.getText().toString());
                            userInfo.put("Alamat", alamat.getText().toString());

                            userInfo.put("isKios", "1");
                            df.set(userInfo);

                            startActivity(new Intent(getApplicationContext(), MainPedagang.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterKios.this, "Gagal Membuat Akun", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

    private boolean checkField(EditText textField) {
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }


}