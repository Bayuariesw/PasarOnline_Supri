package com.example.pasaronline.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pasaronline.R;
import com.example.pasaronline.adapter.DaganganAdapter;
import com.example.pasaronline.dagangan.DetailDagangan;
import com.example.pasaronline.model.Dagangan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements DaganganAdapter.OnItemClickListener {
    //buat home 22nya
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_NAMA_BARANG = "namaBarang";
    public static final String EXTRA_HARGA = "hargaBarang";
    public static final String EXTRA_DESKRIPSI = "deskripsiBarang";
    public static final String EXTRA_JUMLAH = "jumlahBarang";

    private RecyclerView mRecycle;
    private DaganganAdapter mAdapter;
    private ProgressBar mProgress;

    private DatabaseReference mDatabaseRef;
    private List<Dagangan> mDagang;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecycle = view.findViewById(R.id.recycleView);
        mRecycle.setHasFixedSize(true);
        mRecycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mProgress = view.findViewById(R.id.progress_circle);

        mDagang = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("dagangan");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    Dagangan dagang = postSnapShot.getValue(Dagangan.class);
                    mDagang.add(dagang);
                }

                mAdapter = new DaganganAdapter(getContext(), mDagang);
                mRecycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mRecycle.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(HomeFragment.this);
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

    @Override
    public void onItemClick(int position) {
        //new inten
        Intent detailIntent = new Intent(getContext(), DetailDagangan.class);
        Dagangan clickItem = mDagang.get(position);
        //get data
        detailIntent.putExtra(EXTRA_URL, clickItem.getmImageUri());
        detailIntent.putExtra(EXTRA_NAMA_BARANG, clickItem.getNamaDagangan());
        detailIntent.putExtra(EXTRA_HARGA, clickItem.getHarga());
        detailIntent.putExtra(EXTRA_DESKRIPSI, clickItem.getDeskripsi());
        detailIntent.putExtra(EXTRA_JUMLAH, clickItem.getJumlah());
        //start
        startActivity(detailIntent);
    }
}