package com.example.pasaronline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pasaronline.R;
import com.example.pasaronline.model.Transaksi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {

    private Context mContext2;
    private List<Transaksi> mTransaksiList;

    public TransaksiAdapter(Context context, List<Transaksi> transaksis) {
        mContext2 = context;
        mTransaksiList = transaksis;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext2).inflate(R.layout.transaksi_item, parent, false);

        return new TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        final String[] nama = new String[1];
        Transaksi transNow = mTransaksiList.get(position);
//        int harga = Integer.parseInt();
//        int jml = Integer.parseInt(transNow.getJumlahBarang());
//        int total = harga*jml;
        holder.namaBarang.setText(transNow.getNamaBarang());
        holder.hargaBarang.setText(transNow.getHargaBarang());
        holder.jumlahBarang.setText(transNow.getJumlahBarang());
        DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(transNow.getIdPembeli());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    nama[0] = documentSnapshot.getString("Name");
                }
            }
        });
        holder.pembeli.setText(nama[0]);
    }

    @Override
    public int getItemCount() {
        return mTransaksiList.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBarang;
        public TextView namaBarang, hargaBarang, jumlahBarang, pembeli;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarang = itemView.findViewById(R.id.namaBarang);
            hargaBarang = itemView.findViewById(R.id.transHarga);
            jumlahBarang = itemView.findViewById(R.id.total);
            pembeli = itemView.findViewById(R.id.pembeli);
            imgBarang = itemView.findViewById(R.id.imgTransaksi);

        }
    }
}

