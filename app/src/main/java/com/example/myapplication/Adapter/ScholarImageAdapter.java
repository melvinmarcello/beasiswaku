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

public class ScholarImageAdapter extends RecyclerView.Adapter<ScholarImageAdapter.ViewHolder>{
    public Context mContext;
    public List<Scholarship> mScholarship;

    public ScholarImageAdapter(Context mContext, List<Scholarship> mScholarship) {
        this.mContext = mContext;
        this.mScholarship = mScholarship;
    }

    @NonNull
    @Override
    public ScholarImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.scholar_imageitem, parent, false);
        return new ScholarImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScholarImageAdapter.ViewHolder holder, int position) {
        Scholarship scholarship = mScholarship.get(position);
        Glide.with(mContext).load(scholarship.getImg_url()).into(holder.img_url);
    }

    @Override
    public int getItemCount() {
        return mScholarship.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_url = itemView.findViewById(R.id.carousel);
        }
    }
}
