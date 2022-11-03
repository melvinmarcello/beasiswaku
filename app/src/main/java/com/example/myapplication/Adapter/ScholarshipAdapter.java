package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.myapplication.detail_scholar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ScholarshipAdapter extends RecyclerView.Adapter<ScholarshipAdapter.ViewHolder>{
    public Context mContext;
    public List<Scholarship> mScholarship;

    public ScholarshipAdapter(Context mContext, List<Scholarship> mScholarship) {
        this.mContext = mContext;
        this.mScholarship = mScholarship;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.scholarship_item, parent, false);
        return new ScholarshipAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scholarship scholarship = mScholarship.get(position);
        Glide.with(mContext).load(scholarship.getImg_url()).into(holder.img_url);
        if(scholarship.getTitle().equals("")){
            holder.judul.setVisibility(View.GONE);
        }else{
            holder.judul.setVisibility(View.VISIBLE);
            holder.judul.setText(scholarship.getTitle());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("detail", Context.MODE_PRIVATE).edit();
                editor.putString("id", scholarship.getId());
                editor.putString("key", scholarship.getKey());
                editor.apply();
                mContext.startActivity(new Intent(mContext, detail_scholar.class));
            }
        });
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
            img_url = itemView.findViewById(R.id.scholarship);
            judul = itemView.findViewById(R.id.judul);
        }
    }

}
