package com.mgUnicorn.eh.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mgUnicorn.eh.ChatDetailsPatient3;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.patientModel;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class testAdapter extends FirebaseRecyclerAdapter<patientModel, testAdapter.myviewholder> {
    Context context;

    private static final long HIGHLIGHT_MS = 2000;

    // Firebase key of the patient just added, drawn with a coloured card. May be set
    // before that row has loaded; the row picks the colour up when it binds.
    private String highlightedKey;
    private boolean highlightTimerStarted;

    private final Handler highlightHandler = new Handler(Looper.getMainLooper());
    private final Runnable clearHighlight = new Runnable() {
        @Override
        public void run() {
            Log.d("TestAdapter", "highlight expired, clearing");
            highlightedKey = null;
            notifyDataSetChanged();
        }
    };

    public testAdapter(@NonNull FirebaseRecyclerOptions<patientModel> options) {
        super(options);
    }

    public void setHighlightedKey(String key) {
        highlightHandler.removeCallbacks(clearHighlight);
        this.highlightedKey = key;
        // the countdown starts once the row is actually on screen, not now: with a long
        // patient list Firebase may take longer than the highlight itself to deliver it
        this.highlightTimerStarted = false;
        notifyDataSetChanged();
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder,final int position, @NonNull patientModel model) {

        Log.d("TestAdapter", "onBindViewHolder called for position: " + position);
        Log.d("TestAdapter", "Model data - Name: " + model.getName() + ", Number: " + model.getNumber() + ", ImageUrl: " + model.getImageUrl());
        Log.d("TestAdapter", "Firebase data - b: " + model.getB() + ", c: " + model.getC());
        
        // Let's also check the raw data from Firebase
        DataSnapshot snapshot = getSnapshots().getSnapshot(position);
        Log.d("TestAdapter", "Raw Firebase data: " + snapshot.getValue());
        Log.d("TestAdapter", "Raw Firebase key: " + snapshot.getKey());

        // Use Firebase field names (b for name, c for number)
        String patientName = model.getB() != null ? model.getB() : model.getName();
        String patientNumber = model.getC() != null ? model.getC() : model.getNumber();
        String imageUrl = model.getImageUrl();

        Glide.with(holder.img.getContext()).load(imageUrl).placeholder(R.drawable.coronavirus).into(holder.img);

        holder.txtname.setText(patientName);
        holder.txtnumber.setText(patientNumber);

        holder.RegDate.setText(String.valueOf(position + 1));

        String key=getRef(position).getKey();

        boolean isJustAdded = key != null && key.equals(highlightedKey);
        if (holder.itemView instanceof CardView) {
            ((CardView) holder.itemView).setCardBackgroundColor(
                    ContextCompat.getColor(holder.itemView.getContext(),
                            isJustAdded ? R.color.new_patient_highlight : R.color.white));
        }
        if (isJustAdded && !highlightTimerStarted) {
            highlightTimerStarted = true;
            Log.d("TestAdapter", "highlighting newly added patient at position " + position
                    + " for " + HIGHLIGHT_MS + "ms");
            highlightHandler.postDelayed(clearHighlight, HIGHLIGHT_MS);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.img.getContext(), ChatDetailsPatient3.class);
                intent.putExtra("UserName", patientName);
                intent.putExtra("Number", patientNumber);
                intent.putExtra("patient_pic", imageUrl);
                intent.putExtra("regKeynew", holder.RegDate.getText().toString());
                intent.putExtra("ReceiverKey", key);
                holder.img.getContext().startActivity(intent);
            }
        });

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
                mobile.setText(patientNumber);
                name.setText(patientName);
                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("b", name.getText().toString());
                        map.put("c", mobile.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("patient")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child(Objects.requireNonNull(key)).updateChildren(map)
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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(holder.delete.getContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation

                                FirebaseDatabase.getInstance().getReference().child("patient").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child(Objects.requireNonNull(key))
                                        .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });




    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlrerow, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        CircleImageView img;
        ImageView delete;


        TextView txtname, txtnumber, RegDate;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.patient_image);
            delete = (ImageView) itemView.findViewById(R.id.imgDelete);

            txtname = (TextView) itemView.findViewById(R.id.patient_name);
            txtnumber = (TextView) itemView.findViewById(R.id.patient_number);
            RegDate = (TextView) itemView.findViewById(R.id.txtDateSingleRow);
        }
    }
}