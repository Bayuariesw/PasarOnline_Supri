package com.example.pasaronline.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pasaronline.Payment;
import com.example.pasaronline.R;
import com.example.pasaronline.adapter.KeranjangAdapter;
import com.example.pasaronline.model.Keranjang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class KeranjangFragment extends Fragment {
    //buat kerjanjang user
    private RecyclerView mRecycle;
    private KeranjangAdapter mAdapter;
    private ProgressBar mProgres;

    private DatabaseReference mDatabaseReff;
    private List<Keranjang> mKeranjang;

    public KeranjangFragment() {
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
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        mRecycle = view.findViewById(R.id.keranjangRv);
        mRecycle.setHasFixedSize(true);
        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mProgres = view.findViewById(R.id.progress_circle1);

        mKeranjang = new ArrayList<>();

        mDatabaseReff = FirebaseDatabase.getInstance().getReference("keranjang");
        mDatabaseReff.orderByChild("idPembeli").equalTo(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot  postSnapShot : snapshot.getChildren()){
                    Keranjang keranjang = postSnapShot.getValue(Keranjang.class);
                    mKeranjang.add(keranjang);

                }

                mAdapter = new KeranjangAdapter(getContext(), mKeranjang);
                mRecycle.setAdapter(mAdapter);
                mProgres.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgres.setVisibility(View.INVISIBLE);
            }
        });

//        if (mDatabaseReff.orderByChild("idPembeli").equals(FirebaseAuth.getInstance().getUid())){
//
//        }else {
//            Toast.makeText(getContext(), "Tidak ada data barang", Toast.LENGTH_SHORT).show();
//        }

        Button bayarr = view.findViewById(R.id.btn_bayar);
        bayarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Payment.class));
            }
        });
        return view;

    }
}