package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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

import java.util.List;

public class SavedScholarshipAdapter extends RecyclerView.Adapter<SavedScholarshipAdapter.ViewHolder>{
    public Context mContext;
    public List<Scholarship> mScholarship;

    public SavedScholarshipAdapter(Context mContext, List<Scholarship> mScholarship) {
        this.mContext = mContext;
        this.mScholarship = mScholarship;
    }

    @NonNull
    @Override
    public SavedScholarshipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.scholar_carditem, parent, false);
        return new SavedScholarshipAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedScholarshipAdapter.ViewHolder holder, int position) {
        Scholarship scholarship = mScholarship.get(position);
        Log.i("new", scholarship.getKey());
        Glide.with(mContext).load(scholarship.getImg_url()).into(holder.cardImg);
        holder.scholarName.setText(scholarship.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("detail", Context.MODE_PRIVATE).edit();
                editor.putString("id", scholarship.getId());
                editor.putString("key", scholarship.getKey());
                editor.apply();
                mContext.startActivity(new Intent(mContext, detail_scholar.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mScholarship.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView cardImg;
        public TextView scholarName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImg = itemView.findViewById(R.id.cardImage);
            scholarName = itemView.findViewById(R.id.scholarName);
        }
    }
}
