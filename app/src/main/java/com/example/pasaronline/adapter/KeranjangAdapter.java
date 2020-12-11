package com.example.pasaronline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pasaronline.R;
import com.example.pasaronline.model.Keranjang;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder> {
    private Context mContextl;
    private List<Keranjang> mKeranjangList;
    private DaganganAdapter.OnItemClickListener mListener;

    public KeranjangAdapter(Context context, List<Keranjang> keranjangs) {
        mContextl = context;
        mKeranjangList = keranjangs;
    }

    @NonNull
    @Override
    public KeranjangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContextl).inflate(R.layout.keranjang_item, parent, false);

        return new KeranjangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangViewHolder holder, int position) {
        Keranjang keranjangNow = mKeranjangList.get(position);
        holder.nama.setText(keranjangNow.getNamaBarang());
        holder.harga.setText("Harga :" + keranjangNow.getHargaBarang());
        holder.jumlah.setText("Jumlah Belanja :" + keranjangNow.getJumlahBarang());

        Picasso.with(mContextl)
                .load(mKeranjangList.get(position).getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imgKeranjang);
    }

    @Override
    public int getItemCount() {
        return mKeranjangList.size();
    }

    public class KeranjangViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgKeranjang;
        public TextView nama, harga, jumlah;

        public KeranjangViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.tvBarang2);
            harga = itemView.findViewById(R.id.tvHarga2);
            jumlah = itemView.findViewById(R.id.tvJumlah2);
            imgKeranjang = itemView.findViewById(R.id.imgKeranjnag);

        }
    }
}
