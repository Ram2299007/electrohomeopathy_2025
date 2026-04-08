package com.mgUnicorn.eh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> implements Filterable {

    private Context context;
    private List<File> pdfFile;
    private List<File> getPdfFile;
    private OnPdfSelectListener listener;

    public MainAdapter(Context context, List<File> pdfFile, OnPdfSelectListener listener) {
        this.context = context;
        this.pdfFile = pdfFile;
        this.listener = listener;
        this.getPdfFile=new ArrayList<>(pdfFile);
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.txtName.setText(pdfFile.get(position).getName());
        holder.txtName.setSelected(true);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnPdfSelected(pdfFile.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return pdfFile.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        //run on backgroung Thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<File> filteredList=new ArrayList<>();
            if(charSequence.toString().isEmpty()){

                filteredList.addAll(getPdfFile);
            }
            else{

                for(File pdf : getPdfFile){
                    if(pdf.toString().toLowerCase().contains(charSequence.toString().toLowerCase())){

                        filteredList.add(pdf);
                    }
                }

            }


            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        //run on a ui thred
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            pdfFile.clear();
            pdfFile.addAll((Collection<? extends File>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
