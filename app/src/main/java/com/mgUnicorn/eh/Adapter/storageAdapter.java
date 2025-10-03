package com.mgUnicorn.eh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.storageModel;
import com.mgUnicorn.eh.zoomStorage;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class storageAdapter extends FirebaseRecyclerAdapter<storageModel, storageAdapter.myviewholder>{
    Context context;

    public storageAdapter(@NonNull FirebaseRecyclerOptions<storageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull storageModel model) {
        holder.text.setText(model.getName());
        Glide.with(holder.img.getContext()).load(model.getImageUrl()).placeholder(R.drawable.backgroundbox).into(holder.img);


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.img.getContext(), zoomStorage.class);
                intent.putExtra("imgKey",model.getImageUrl());
                holder.img.getContext().startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storage,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView text;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.m_image);
            text=(TextView) itemView.findViewById(R.id.m_TextView);
        }
    }
}