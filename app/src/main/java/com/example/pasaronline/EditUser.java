package com.example.pasaronline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUser extends AppCompatActivity {

    private EditText namabaru,emailbaru,telpbaru;
    private Button edit;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String cekNama, cekEmail, cekTelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        namabaru= findViewById(R.id.namabaruU);
        emailbaru= findViewById(R.id.emailbaruU);
        telpbaru= findViewById(R.id.telpbaruU);
        edit = findViewById(R.id.btn_update);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
//        getData();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekNama = namabaru.getText().toString();
                cekEmail = emailbaru.getText().toString();
                cekTelp = telpbaru.getText().toString();

//                if(isEmpty(cekNama)||isEmpty(cekEmail)||isEmpty(cekTelp)){
                    Toast.makeText(EditUser.this, "Diisi dong kak", Toast.LENGTH_LONG).show();
//                }else {

//                }
            }
        });
    }
}