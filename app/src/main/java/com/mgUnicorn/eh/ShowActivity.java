package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.mgUnicorn.eh.Adapter.storageAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.mgUnicorn.eh.models.storageModel;

public class ShowActivity extends AppCompatActivity {

   RecyclerView recyclerView;
   storageAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView=findViewById(R.id.recyclerView_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<storageModel> options=
                new FirebaseRecyclerOptions.Builder<storageModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Medical_Reports").child(FirebaseAuth.getInstance().getUid()),storageModel.class)
                .build();

        adapter=new storageAdapter(options);
        recyclerView.setAdapter(adapter);





    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
                processSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {


        FirebaseRecyclerOptions<storageModel> options=
                new FirebaseRecyclerOptions.Builder<storageModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Medical_Reports")
                        .child(FirebaseAuth.getInstance().getUid()).orderByChild("b").startAt(s).endAt(s+"\uf8ff"),storageModel.class).build();

        adapter=new storageAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);



    }


}