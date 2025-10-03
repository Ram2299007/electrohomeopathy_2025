package com.mgUnicorn.eh.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.MessageModel;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    int SENDER_VIEW_TYPE= 1;
    int RECEIVER_VIEW_TYPE= 2;
    String recId;

    public chatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    public chatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);

            return  new SenderViewHolder(view);
        }
      else
        {
            View view=LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);

            return new  ReceiverViewHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = messageModels.get(position);
        if (messageModel == null) {
            return RECEIVER_VIEW_TYPE; // Default to receiver view if message is null
        }
        
        String messageUid = messageModel.getuId();
        String currentUid = FirebaseAuth.getInstance().getUid();
        
        // Add null checks to prevent NullPointerException
        if (messageUid != null && currentUid != null && messageUid.equals(currentUid)) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);
        
        // Add null check to prevent crashes
        if (messageModel == null) {
            return;
        }

        if(holder.getClass()==SenderViewHolder.class) {
            String message = messageModel.getMessage();
            ((SenderViewHolder)holder).senderMsg.setText(message != null ? message : "");
        }
        else
        {
            String message = messageModel.getMessage();
            ((ReceiverViewHolder)holder).recieverMsg.setText(message != null ? message : "");
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom=FirebaseAuth.getInstance().getUid() + recId;
                                database.getReference().child("chats").child(senderRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView recieverMsg,receiverTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMsg=itemView.findViewById(R.id.reccieverText);
            receiverTime=itemView.findViewById(R.id.recieverTime);

        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMsg,senderTime,username;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
            username=itemView.findViewById(R.id.userName);

        }
    }
}