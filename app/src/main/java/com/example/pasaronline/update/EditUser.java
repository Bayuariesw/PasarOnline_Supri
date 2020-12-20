package com.example.pasaronline.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pasaronline.MainActivity;
import com.example.pasaronline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.pasaronline.read.ProfileFragment.EXTRA_EMAIL;
import static com.example.pasaronline.read.ProfileFragment.EXTRA_NAMA;
import static com.example.pasaronline.read.ProfileFragment.EXTRA_TELP;

public class EditUser extends AppCompatActivity {

    private EditText namabaru, emailbaru, telpbaru;
    private String nama, email, telp;
    private Button edit;
    private DatabaseReference database;
    private FirebaseAuth fAuth;
    private EditText cekNama, cekEmail, cekTelp;
    private boolean valid = true;
    private FirebaseUser fUser;
    private FirebaseFirestore fStore;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        namabaru = findViewById(R.id.namabaruU);
        emailbaru = findViewById(R.id.emailbaruU);
        telpbaru = findViewById(R.id.telpbaruU);
        edit = findViewById(R.id.btn_update);
        mProgress = findViewById(R.id.progress_circlee);
        mProgress.setVisibility(View.INVISIBLE);

        final Intent intent = getIntent();
        nama = intent.getStringExtra(EXTRA_NAMA);
        email = intent.getStringExtra(EXTRA_EMAIL);
        telp = intent.getStringExtra(EXTRA_TELP);

        namabaru.setText(nama);
        emailbaru.setText(email);
        telpbaru.setText(telp);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

//        getData();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namabaru = findViewById(R.id.namabaruU);
                emailbaru = findViewById(R.id.emailbaruU);
                telpbaru = findViewById(R.id.telpbaruU);

                checkField(namabaru);
                checkField(emailbaru);
                checkField(telpbaru);

                if (valid) {
                    fUser.updateEmail(emailbaru.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference df = fStore.collection("Users").document(fAuth.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Name", namabaru.getText().toString());
                            userInfo.put("Email", emailbaru.getText().toString());
                            userInfo.put("Telp", telpbaru.getText().toString());

                            df.update(userInfo);

                            Intent intent1 = new Intent(EditUser.this, MainActivity.class);
                            intent1.putExtra("profile", "keProfile");
                            startActivity(intent1);
                            mProgress.setVisibility(View.VISIBLE);

                            Toast.makeText(EditUser.this, "Profile Berhasil diupdate", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });
                } else {
                    Toast.makeText(EditUser.this, "Jangan kosongkan field", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            Toast.makeText(this, "Isi field", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }

}