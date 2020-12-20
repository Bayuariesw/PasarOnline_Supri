package com.example.pasaronline.read;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pasaronline.R;
import com.example.pasaronline.adapter.TransaksiAdapter;
import com.example.pasaronline.model.Transaksi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TransaksiFragment extends Fragment {

    private RecyclerView mRecycle;
    private ProgressBar mProgress;

    private TransaksiAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Transaksi> mTransaksi;


    public TransaksiFragment() {
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
        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);
        mRecycle = view.findViewById(R.id.transaksiRv);
        mRecycle.setHasFixedSize(true);
        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mProgress = view.findViewById(R.id.progress);
        mTransaksi = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("transaksi");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mTransaksi.clear();
                for (DataSnapshot postSnapShot : snapshot.getChildren()){
                    Transaksi transaksi = postSnapShot.getValue(Transaksi.class);
                    mTransaksi.add(transaksi);
                }
                mAdapter = new TransaksiAdapter(getContext(), mTransaksi);
                mRecycle.setAdapter(mAdapter);
                mProgress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgress.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }
}