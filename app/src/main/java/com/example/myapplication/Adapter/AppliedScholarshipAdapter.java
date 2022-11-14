package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

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
