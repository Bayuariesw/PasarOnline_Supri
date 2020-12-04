package com.example.pasaronline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tvnew);

        DocumentReference df = FirebaseFirestore.getInstance().collection("Kios").document(FirebaseAuth.getInstance().getUid());

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String nama = documentSnapshot.getString("NamaPemilik");
                    tv.setText(nama);
                }else{
                    Toast.makeText(MainActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
//        Task<DocumentSnapshot> data = df.get();
//        DocumentSnapshot doc = data.getResult();

//        FirebaseUser user = this.fAuth.getCurrentUser();
//        String uid = user.getUid();
//        FirebaseFirestore

//        tv.setText();
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
//        if(v.getId() == R.id.btnAdd){
//            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
//            startActivity(intent);
//        }
    }


}