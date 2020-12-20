package com.example.pasaronline.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private OnItemClickListener mListener;

    public KeranjangAdapter(Context context, List<Keranjang> keranjangs) {
        mContextl = context;
        mKeranjangList = keranjangs;
    }

    public interface OnItemClickListener  {
        void onDeleteClick(int position);
        void onBuyClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
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
        holder.harga.setText("Harga : " + keranjangNow.getHargaBarang());
        holder.jumlah.setText("Jumlah Belanja : " + keranjangNow.getJumlahBarang());

        Picasso.with(mContextl)
                .load(mKeranjangList.get(position).getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(holder.imgKeranjang);
    }

    @Override
    public int getItemCount() {
        return mKeranjangList.size();
    }

    public class KeranjangViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgKeranjang,hapus;
        public Button buy;
        public TextView nama, harga, jumlah;

        public KeranjangViewHolder(@NonNull View itemView) {
            super(itemView);
            hapus = itemView.findViewById(R.id.hapus);
            buy = itemView.findViewById(R.id.buy);
            nama = itemView.findViewById(R.id.tvBarang2);
            harga = itemView.findViewById(R.id.tvHarga2);
            jumlah = itemView.findViewById(R.id.tvJumlah2);
            imgKeranjang = itemView.findViewById(R.id.imgKeranjnag);

            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });

            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onBuyClick(position);
                        }

                    }
                }
            });

        }
    }

}
