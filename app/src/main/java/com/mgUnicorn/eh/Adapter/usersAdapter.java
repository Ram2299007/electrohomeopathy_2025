package com.mgUnicorn.eh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.ViewHolder> {


    ArrayList<Users> list;
    ArrayList<Users> myList;


    Context context;


    public usersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;


    }

    public usersAdapter(ArrayList<Users> myList) {
        this.myList = myList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Users users = list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.man).into(holder.image);
        holder.userName.setText(users.getUserName());

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(FirebaseAuth
                        .getInstance()
                        .getUid() + users
                        .getUserId()).orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {

                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                try {
                                    holder.userName.setText(users.getUserName());
                                    holder.lastName.setText(snapshot1.child("message").getValue().toString());
                                } catch (Exception e) {
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView userName, lastName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userName);
            lastName = itemView.findViewById(R.id.lastMessage);
        }


    }

}
