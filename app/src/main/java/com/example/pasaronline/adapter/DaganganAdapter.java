package com.example.pasaronline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pasaronline.R;
import com.example.pasaronline.model.Dagangan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DaganganAdapter extends RecyclerView.Adapter<DaganganAdapter.DagangViewHolder> {
    //buat untuk adapter
    private Context mContext;
    private List<Dagangan> mDagang;
    public List<Dagangan> filteredUserDataList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public DaganganAdapter(Context context, List<Dagangan> dagangans) {
        this.mContext = context;
        this.mDagang = dagangans;
        this.filteredUserDataList = dagangans;
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
        holder.tvHarga.setText("Rp." + daganganSekarang.getHarga());
        holder.tvDeskripsi.setText(daganganSekarang.getDeskripsi());


        //set image to img view
        Picasso.with(mContext)
                .load(mDagang.get(position).getmImageUri())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(holder.imgDagang);

//        Glide.with(mContext)
//                .load(daganganSekarang.getmImageUri()).into(holder.imgDagang);
    }


    @Override
    public int getItemCount() {
        return filteredUserDataList.size();
    }

    public class DagangViewHolder extends RecyclerView.ViewHolder {
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
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }

                }
            });

        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String Key = charSequence.toString();
                if (Key.isEmpty()) {
                    filteredUserDataList = mDagang;
                } else {
                    List<Dagangan> lstFiltered = new ArrayList<>();
                    for (Dagangan row : mDagang) {
                        if (row.getNamaDagangan().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }
                    filteredUserDataList = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserDataList = (List<Dagangan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
