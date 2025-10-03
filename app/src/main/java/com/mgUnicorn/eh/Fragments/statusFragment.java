package com.mgUnicorn.eh.Fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mgUnicorn.eh.Adapter.testAdapter;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.add_petient;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mgUnicorn.eh.databinding.FragmentStatusBinding;
import com.mgUnicorn.eh.models.patientModel;

public class statusFragment extends Fragment  {

    public statusFragment() {

    }

    FragmentStatusBinding binding;
   // ArrayList<patientModel>list=new ArrayList<>();
    testAdapter adapter;





    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding=FragmentStatusBinding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance();
        //adapter=new patient_adapter(list,getContext());

        // Debug logging
        Log.d("StatusFragment", "onCreateView called");
        String currentUserId = FirebaseAuth.getInstance().getUid();
        Log.d("StatusFragment", "Current user ID: " + currentUserId);

        binding.statusRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (currentUserId != null) {
            // First, let's check if there's any data in the database
            DatabaseReference patientRef = FirebaseDatabase.getInstance().getReference().child("patient").child(currentUserId);
            patientRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("StatusFragment", "Data snapshot received. Children count: " + dataSnapshot.getChildrenCount());
                    if (dataSnapshot.getChildrenCount() == 0) {
                        Log.d("StatusFragment", "No data found for user: " + currentUserId);
                    } else {
                        Log.d("StatusFragment", "Found " + dataSnapshot.getChildrenCount() + " patients");
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Log.d("StatusFragment", "Patient key: " + child.getKey());
                            Log.d("StatusFragment", "Patient data: " + child.getValue());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e("StatusFragment", "Error reading data: " + error.getMessage());
                }
            });

            FirebaseRecyclerOptions<patientModel> options=
                    new FirebaseRecyclerOptions.Builder<patientModel>()
                            .setQuery(patientRef, patientModel.class)
                            .build();

            adapter=new testAdapter(options);
            binding.statusRecyclerView.setAdapter(adapter);
            Log.d("StatusFragment", "Adapter created and set");
        } else {
            Log.e("StatusFragment", "User not authenticated!");
        }



       // binding.statusRecyclerView.setAdapter(adapter);
      //  LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        //binding.statusRecyclerView.setLayoutManager(layoutManager);
     //   binding.statusRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),layoutManager.getOrientation()));






        //for send data;

     //   FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        //String uid=FirebaseAuth.getInstance().getUid();


  //      assert uid != null;
      /*  database.getReference().child("patient").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        try {

                            Log.e("data", String.valueOf(dataSnapshot));
                            patientModel patientmodel = dataSnapshot.getValue(patientModel.class);

                            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                list.add(patientmodel);
                            }

                            adapter.notifyDataSetChanged();

                        }catch (Exception e){{
                        }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

       */

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), add_petient.class));
            }
        });


        ImageViewCompat.setImageTintList(
                binding.floatingActionButton,
                ColorStateList.valueOf(Color.WHITE)
        );

        ImageViewCompat.setImageTintList(
                binding.floatingActionButton2,
                ColorStateList.valueOf(Color.WHITE)
        );


        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.statusRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.statusRecyclerView.scrollToPosition(  binding.statusRecyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 1000);

            }
        });
        return binding.getRoot();






    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item=menu.findItem(R.id.search);


        SearchView searchView=(SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });

    }



    private void processSearch(String s) {


        FirebaseRecyclerOptions<patientModel> options=
                new FirebaseRecyclerOptions.Builder<patientModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("patient")
                                .child(FirebaseAuth.getInstance().getUid()).orderByChild("b").startAt(s).endAt(s+"\uf8ff"),patientModel.class).build();

        adapter=new testAdapter(options);
        adapter.startListening();
        binding.statusRecyclerView.setAdapter(adapter);



    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d("StatusFragment", "onStart called");
        if (adapter != null) {
            adapter.startListening();
            Log.d("StatusFragment", "Adapter started listening");
        } else {
            Log.e("StatusFragment", "Adapter is null in onStart!");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("StatusFragment", "onStop called");
        if (adapter != null) {
            adapter.stopListening();
            Log.d("StatusFragment", "Adapter stopped listening");
        }
    }





}