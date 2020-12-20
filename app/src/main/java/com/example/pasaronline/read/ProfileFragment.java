package com.example.pasaronline.read;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pasaronline.update.EditUser;
import com.example.pasaronline.Login;
import com.example.pasaronline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    //buat profile user
    private ProgressBar mProgres;
    private TextView nama, email, telp;
    private Button logOut, edit;

    public static final String EXTRA_NAMA = "namaUser";
    public static final String EXTRA_EMAIL = "emailUser";
    public static final String EXTRA_TELP = "tlpUser";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nama = view.findViewById(R.id.namaProfil);
        email = view.findViewById(R.id.emailProfil);
        telp = view.findViewById(R.id.telpProfil);
        mProgres = view.findViewById(R.id.progress_circle2);

        //nama email dan telpon user
        DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String namaUser = documentSnapshot.getString("Name");
                    String emailUser = documentSnapshot.getString("Email");
                    String telpUser = documentSnapshot.getString("Telp");

                    nama.setText(namaUser);
                    email.setText(emailUser);
                    telp.setText(telpUser);
                }
                mProgres.setVisibility(View.INVISIBLE);
            }
        });

        edit = view.findViewById(R.id.btn_update);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditUser.class);
                intent.putExtra(EXTRA_NAMA, nama.getText().toString());
                intent.putExtra(EXTRA_EMAIL, email.getText().toString());
                intent.putExtra(EXTRA_TELP, telp.getText().toString());

                startActivity(intent);
            }
        });

        logOut = view.findViewById(R.id.btnLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity().getApplicationContext(), Login.class));
                getActivity().finish();
            }
        });

        return view;
    }
}