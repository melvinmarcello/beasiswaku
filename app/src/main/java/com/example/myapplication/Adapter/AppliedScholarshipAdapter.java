package com.example.myapplication.Adapter;

import android.content.Context;
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

import java.util.List;

public class AppliedScholarshipAdapter extends RecyclerView.Adapter<AppliedScholarshipAdapter.ViewHolder> {
    public Context mContext;
    public List<Scholarship> mScholarship;

    public AppliedScholarshipAdapter(Context mContext, List<Scholarship> mScholarship) {
        this.mContext = mContext;
        this.mScholarship = mScholarship;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.scholarship_item, parent, false);
        return new AppliedScholarshipAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scholarship scholarship = mScholarship.get(position);
        Log.i("new", scholarship.getKey());
        Glide.with(mContext).load(scholarship.getImg_url()).into(holder.cardImg);
        holder.scholarName.setText(scholarship.getTitle());
    }

    public int getItemCount() {
        return mScholarship.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView cardImg;
        public TextView scholarName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImg = itemView.findViewById(R.id.scholarship);
            scholarName = itemView.findViewById(R.id.judul);
        }
    }
}
