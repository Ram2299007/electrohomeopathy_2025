package com.mgUnicorn.eh.AtoZ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.mgUnicorn.eh.Adapter.DiseaseAdapter;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.DiseaseModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class A extends AppCompatActivity {

    RecyclerView recyclerView;


    //require change
    DiseaseAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        recyclerView=findViewById(R.id.recAViewAtoZA);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Log.d("AtoZActivity", "Creating Firebase query for AtoZTreatment/A");
        
        // Set up RecyclerView with stable IDs to prevent crashes
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null); // Disable animations to prevent crashes
        
        FirebaseRecyclerOptions<DiseaseModel> options=
                new FirebaseRecyclerOptions.Builder<DiseaseModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AtoZTreatment").child("A"),DiseaseModel.class)
                        .build();

        adapter=new DiseaseAdapter(options);
        recyclerView.setAdapter(adapter);
        Log.d("AtoZActivity", "Adapter created and set to RecyclerView");
        
        // Add some test data if the database is empty
        addTestDataIfEmpty();
        
        // Debug: Check what data is actually in Firebase
        checkFirebaseData();





    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("AtoZActivity", "onStart called, starting adapter listening");
        if (adapter != null) {
            // Add a small delay to ensure RecyclerView is fully initialized
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    adapter.startListening();
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView) item.getActionView();
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
             //   processSearch(s);
                processSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {


        FirebaseRecyclerOptions<DiseaseModel> options=
                new FirebaseRecyclerOptions.Builder<DiseaseModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AtoZTreatment")
                                .child("A").orderByChild("a").startAt(s).endAt(s+"\uf8ff"),DiseaseModel.class).build();

        adapter=new DiseaseAdapter(options);
        adapter.startListening();
       recyclerView.setAdapter(adapter);
      //  recyclerView.smoothScrollToPosition(adapter.getItemCount());


    }

    private void addTestDataIfEmpty() {
        DatabaseReference aToZRef = FirebaseDatabase.getInstance().getReference().child("AtoZTreatment").child("A");
        
        aToZRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    Log.d("AtoZActivity", "No data found, adding test data");
                    
                    // Add some sample diseases starting with 'A'
                    DiseaseModel disease1 = new DiseaseModel("", "Acne", "Sulphur 30, Pulsatilla 30, Sepia 30");
                    DiseaseModel disease2 = new DiseaseModel("", "Allergy", "Arsenic Album 30, Apis Mellifica 30, Natrum Mur 30");
                    DiseaseModel disease3 = new DiseaseModel("", "Asthma", "Arsenic Album 30, Ipecac 30, Antimonium Tart 30");
                    DiseaseModel disease4 = new DiseaseModel("", "Anxiety", "Aconite 30, Argentum Nitricum 30, Gelsemium 30");
                    DiseaseModel disease5 = new DiseaseModel("", "Arthritis", "Rhus Tox 30, Bryonia 30, Ruta 30");
                    
                    aToZRef.push().setValue(disease1);
                    aToZRef.push().setValue(disease2);
                    aToZRef.push().setValue(disease3);
                    aToZRef.push().setValue(disease4);
                    aToZRef.push().setValue(disease5);
                    
                    Log.d("AtoZActivity", "Test data added successfully");
                } else {
                    Log.d("AtoZActivity", "Data already exists, count: " + dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("AtoZActivity", "Error checking data: " + databaseError.getMessage());
            }
        });
    }

    private void checkFirebaseData() {
        DatabaseReference aToZRef = FirebaseDatabase.getInstance().getReference().child("AtoZTreatment").child("A");
        
        aToZRef.limitToFirst(3).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("AtoZActivity", "Checking Firebase data structure:");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("AtoZActivity", "Key: " + child.getKey());
                    Log.d("AtoZActivity", "Value: " + child.getValue());
                    Log.d("AtoZActivity", "Children count: " + child.getChildrenCount());
                    
                    // Check what fields are actually in the data
                    for (DataSnapshot field : child.getChildren()) {
                        Log.d("AtoZActivity", "Field: " + field.getKey() + " = " + field.getValue());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("AtoZActivity", "Error checking Firebase data: " + databaseError.getMessage());
            }
        });
    }

}