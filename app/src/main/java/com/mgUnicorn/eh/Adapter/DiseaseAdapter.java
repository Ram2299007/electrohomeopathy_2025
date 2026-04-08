package com.mgUnicorn.eh.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.DiseaseModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DiseaseAdapter extends FirebaseRecyclerAdapter<DiseaseModel, DiseaseAdapter.myviewholder>{
    Context context;

    public DiseaseAdapter(@NonNull FirebaseRecyclerOptions<DiseaseModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull DiseaseModel model) {
        try {
            Log.d("DiseaseAdapter", "onBindViewHolder called for position: " + position);
            Log.d("DiseaseAdapter", "Model data - Name: " + model.getName() + ", Treatment: " + model.getTreatment());
            Log.d("DiseaseAdapter", "Firebase data - a: " + model.getA() + ", b: " + model.getB());

            // Let's also check the raw data from Firebase
            com.google.firebase.database.DataSnapshot snapshot = getSnapshots().getSnapshot(position);
            Log.d("DiseaseAdapter", "Raw Firebase data: " + snapshot.getValue());
            Log.d("DiseaseAdapter", "Raw Firebase key: " + snapshot.getKey());

            // Use Firebase field names for display
            String diseaseName = model.getA() != null ? model.getA() : model.getName();
            String diseaseTreatment = model.getB() != null ? model.getB() : model.getTreatment();

            if (diseaseName == null) diseaseName = "Unknown Disease";
            if (diseaseTreatment == null) diseaseTreatment = "No treatment available";

            holder.text.setText(diseaseName);
            holder.treatment.setText(diseaseTreatment);
        } catch (Exception e) {
            Log.e("DiseaseAdapter", "Error in onBindViewHolder: " + e.getMessage());
            holder.text.setText("Error loading data");
            holder.treatment.setText("Please try again");
        }

       // Glide.with(holder.img.getContext()).load(model.getImageUrl()).placeholder(R.drawable.backgroundbox).into(holder.img);



    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.diseas_row,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView text,treatment;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.m_image);
            text=(TextView) itemView.findViewById(R.id.diseasnameDiseas);
            treatment=(TextView) itemView.findViewById(R.id.treatmentDiseas);
        }
    }
}