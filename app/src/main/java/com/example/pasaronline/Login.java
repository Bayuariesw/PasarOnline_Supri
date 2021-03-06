package com.example.pasaronline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pasaronline.create.RegisterUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    //buat login 22 nya
    private TextView btnRegister;
    private EditText email, password;
    private Button btnLogin;
    boolean valid = true;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private ProgressBar mProgres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin1);
        btnRegister = findViewById(R.id.register);
        mProgres = findViewById(R.id.progress_circle4);

        mProgres.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(email);
                checkField(password);

                if (valid) {
                    fAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "LogIn Berhasil", Toast.LENGTH_SHORT).show();
                            checkUserAccesLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterUser.class));
            }
        });
    }

    private void checkUserAccesLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        DocumentReference dfKios = fStore.collection("Kios").document(uid);
        //extrak data

        if (df != null) {
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d("TAG", "onSucces:" + documentSnapshot.getData());

                    //identtifikasi user access level
                    if (documentSnapshot.getString("isUser") != null) {
                        //user
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        mProgres.setVisibility(View.VISIBLE);
                        finish();
                    }
                }
            });
        }
        if (dfKios != null) {
            dfKios.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d("TAG", "onSucces:" + documentSnapshot.getData());

                    //identias kios
                    if (documentSnapshot.getString("isKios") != null) {
                        //user
                        startActivity(new Intent(getApplicationContext(), MainPedagang.class));
                        mProgres.setVisibility(View.VISIBLE);
                        finish();
                    }
                }
            });
        }
    }

    private boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference df = fStore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            DocumentReference dfKios = fStore.collection("Kios").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //extrak data

            if (df != null) {
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("TAG", "onSucces:" + documentSnapshot.getData());

                        //identtifikasi user access level
                        if (documentSnapshot.getString("isUser") != null) {
                            //user
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            mProgres.setVisibility(View.INVISIBLE);
                            finish();
                        }
                    }
                });
            }
            if (dfKios != null) {
                dfKios.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("TAG", "onSucces:" + documentSnapshot.getData());

                        //identias kios
                        if (documentSnapshot.getString("isKios") != null) {
                            //user
                            startActivity(new Intent(getApplicationContext(), MainPedagang.class));
                            mProgres.setVisibility(View.INVISIBLE);
                            finish();
                        }
                    }
                });
            }

        }
    }
}