package com.example.pasaronline.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pasaronline.MainActivity;
import com.example.pasaronline.R;
import com.example.pasaronline.dagangan.DetailDagangan;
import com.example.pasaronline.model.Dagangan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DaganganAdapter extends RecyclerView.Adapter<DaganganAdapter.DagangViewHolder> {

    private Context mContext;
    private List<Dagangan> mDagang;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public DaganganAdapter(Context context, List<Dagangan> dagangans){
        mContext = context;
        mDagang = dagangans;
    }

    @NonNull
    @Override
    public DagangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dagang_item, parent, false);
        return new DagangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DagangViewHolder holder, int position) {
        Dagangan daganganSekarang = mDagang.get(position);
        holder.tvName.setText(daganganSekarang.getNamaDagangan());
        holder.tvHarga.setText("Rp."+daganganSekarang.getHarga());
        holder.tvDeskripsi.setText(daganganSekarang.getDeskripsi());
//        DocumentReference df = FirebaseFirestore.getInstance().collection("Kios").document(daganganSekarang.getIdKios());
//        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()){
//                    String nama = documentSnapshot.getString("NameKios");
//                    DagangViewHolder holder = null;
//                    holder.tvKios.setText(nama);
//                }
//            }
//        });

//        Uri uri = Uri.parse(daganganSekarang.getmImageUri());
//        holder.imgDagang.setImageURI(uri);

        //set image to img view
        Picasso.with(mContext)
                .load(mDagang.get(position).getmImageUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imgDagang);

//        Glide.with(mContext)
//                .load(daganganSekarang.getmImageUri()).into(holder.imgDagang);
    }


    @Override
    public int getItemCount() {
        return mDagang.size();
    }

    public class DagangViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName, tvDeskripsi, tvHarga;
        public ImageView imgDagang;

        public DagangViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvBarang);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            imgDagang = itemView.findViewById(R.id.imgNew);

            //onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    //new intent
//                    Intent intent = new Intent(v.getContext(), DetailDagangan.class);
//                    intent.putExtra("item", mDagang.get(getAdapterPosition()));     //send data
//                    v.getContext().startActivity(intent);   //start activity baru
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }
}
