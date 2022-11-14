package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.GlobalVariable;
import com.example.myapplication.Model.Message;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public DatabaseReference reference;
    public Context mContext;
    public List<Message> mMessage;

    public MessageAdapter(Context mContext, List<Message> mMessage, DatabaseReference reference) {
        this.mContext = mContext;
        this.mMessage = mMessage;
        this.reference = reference;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Message message = mMessage.get(position);
        Log.i("imgpprof", message.getImgProf());
        if(message.getName().equals(GlobalVariable.name)){
            holder.tvTitle.setText("You: \n" + message.getMessage());
            holder.llMessage.setGravity(Gravity.START);
            holder.llMessage.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            holder.tvTitle.setBackgroundColor(Color.parseColor("#DFE2E2"));
            holder.delBtn.setBackgroundColor(Color.TRANSPARENT);
            Glide.with(holder.itemView.getContext()).load(message.getImgProf()).into(holder.imgPict);
        }else{
            holder.tvTitle.setText(message.getName() + ": \n" + message.getMessage());
            holder.delBtn.setVisibility(View.GONE);
            Glide.with(holder.itemView.getContext()).load(message.getImgProf()).into(holder.imgPict);
        }
    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgPict;
        public TextView tvTitle;
        public ImageButton delBtn;
        public LinearLayout llMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPict = itemView.findViewById(R.id.imgPict);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            delBtn = itemView.findViewById(R.id.ibDelete);
            llMessage = itemView.findViewById(R.id.llMessage);

            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reference.child(mMessage.get(getAdapterPosition()).getKey()).removeValue();
                }
            });
        }
    }
}
