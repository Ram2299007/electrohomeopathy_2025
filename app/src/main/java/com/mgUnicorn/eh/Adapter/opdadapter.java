package com.mgUnicorn.eh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.opdModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class opdadapter extends FirebaseRecyclerAdapter<opdModel, opdadapter.myviewholder>{
    Context context;

    public opdadapter(@NonNull FirebaseRecyclerOptions<opdModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull opdModel model) {
        holder.date.setText(model.getDate());
        holder.name.setText(model.getName());
        holder.number.setText(model.getNumber());

        // Glide.with(holder.img.getContext()).load(model.getImageUrl()).placeholder(R.drawable.backgroundbox).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.opd_single_row,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

       // ImageView img;
       public  TextView date,name,number;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            //img=(ImageView) itemView.findViewById(R.id.m_image);
            date=(TextView) itemView.findViewById(R.id.Patient_Date);
            name=(TextView) itemView.findViewById(R.id.Patient_Name);
            number=(TextView) itemView.findViewById(R.id.Patient_Number);
        }
    }
}