package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Scholarship;
import com.example.myapplication.R;

import java.util.List;

public class ScholarCardAdapter extends RecyclerView.Adapter<ScholarCardAdapter.ViewHolder>{
    public Context mContext;
    public List<Scholarship> mScholarship;

    public ScholarCardAdapter(Context mContext, List<Scholarship> mScholarship) {
        this.mContext = mContext;
        this.mScholarship = mScholarship;
    }

    @NonNull
    @Override
    public ScholarCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.scholar_carditem, parent, false);
        return new ScholarCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScholarCardAdapter.ViewHolder holder, int position) {
        Scholarship scholarship = mScholarship.get(position);
        Glide.with(mContext).load(scholarship.getImg_url()).into(holder.img_url);
        if(scholarship.getTitle().equals("")){
            holder.judul.setVisibility(View.GONE);
        }else{
            holder.judul.setVisibility(View.VISIBLE);
            holder.judul.setText(scholarship.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mScholarship.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img_url;
        public TextView judul;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_url = itemView.findViewById(R.id.cardImage);
            judul = itemView.findViewById(R.id.scholarName);
        }
    }
}
