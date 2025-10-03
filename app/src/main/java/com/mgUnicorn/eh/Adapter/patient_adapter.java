package com.mgUnicorn.eh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mgUnicorn.eh.*;
import com.mgUnicorn.eh.models.patientModel;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class patient_adapter extends RecyclerView.Adapter<patient_adapter.myviewholder> {

    ArrayList<patientModel> list;
    ArrayList<patientModel> myList;


    DatabaseReference ref;


    public patient_adapter(ArrayList<patientModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.singlrerow, parent, false);


        return new myviewholder(view);

    }




    @Override
    public void onBindViewHolder(@NonNull myviewholder holder,final int position) {



        patientModel patientmodel1 = list.get(position);

        //  Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.man).into(holder.image);
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.coronavirus).into(holder.img);

        holder.txtname.setText(patientmodel1.getName());
        holder.txtnumber.setText(patientmodel1.getNumber());

        holder.RegDate.setText(String.valueOf(position + 1));
        //holder.RegDate.setText( list.get(position));

        //  holder.RegDate.setText((CharSequence) list.get(position));


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemView.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true, 1100)
                        .create();


                View myview = dialogPlus.getHolderView();
                final EditText mobile = myview.findViewById(R.id.uMobile);
                final EditText name = myview.findViewById(R.id.uName);
                Button submit = myview.findViewById(R.id.usubmit);
                mobile.setText(patientmodel1.getNumber());
                name.setText(patientmodel1.getName());

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("b", name.getText().toString());
                        map.put("c", mobile.getText().toString());


                   

                    FirebaseDatabase.getInstance().getReference().child("patient")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("-Mwk9IqorrJtTBqtmq0B").updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });






                    }
                });
                return true;
            }
        });



        holder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent intent = new Intent(holder.img.getContext(), zoomstorageadapter.class);
                intent.putExtra("patient_pic", patientmodel1.getImageUrl());
                holder.img.getContext().startActivity(intent);
                return true;
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();

    }

    private Context context;


    class myviewholder extends RecyclerView.ViewHolder {

        CircleImageView img;


        TextView txtname, txtnumber, RegDate;

        public myviewholder(@NonNull View itemView) {

            super(itemView);

            context = itemView.getContext();

            img = (CircleImageView) itemView.findViewById(R.id.patient_image);

            txtname = (TextView) itemView.findViewById(R.id.patient_name);
            txtnumber = (TextView) itemView.findViewById(R.id.patient_number);
            RegDate = (TextView) itemView.findViewById(R.id.txtDateSingleRow);

        }
    }


}



