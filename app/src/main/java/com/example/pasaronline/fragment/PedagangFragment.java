package com.example.pasaronline.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pasaronline.Login;
import com.example.pasaronline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class PedagangFragment extends Fragment {
    //buatbuat profile pedagang

    private TextView namaPemilik, namaKios, email, telp;
    private Button logOut;
    private ProgressBar mProgres;

    public PedagangFragment() {
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
        View view = inflater.inflate(R.layout.fragment_pedagang, container, false);

        namaPemilik = view.findViewById(R.id.namaUser1);
        namaKios = view.findViewById(R.id.namaKios);
        email = view.findViewById(R.id.emailProfil1);
        telp = view.findViewById(R.id.telpProfil1);
        mProgres = view.findViewById(R.id.progress_circle3);

        //nama email dan telpon user
        DocumentReference df = FirebaseFirestore.getInstance().collection("Kios").document(FirebaseAuth.getInstance().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String namaUser = documentSnapshot.getString("Name");
                    String namaToko = documentSnapshot.getString("NameKios");
                    String emailUser = documentSnapshot.getString("Email");
                    String telpUser = documentSnapshot.getString("Telp");

                    namaPemilik.setText(namaUser);
                    namaKios.setText(namaToko);
                    email.setText(emailUser);
                    telp.setText(telpUser);
                }
                mProgres.setVisibility(View.INVISIBLE);
            }
        });

        //logout
        logOut = view.findViewById(R.id.btnLogOut);
        logOut.setOnClickListener(new View.OnClickListener(){
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