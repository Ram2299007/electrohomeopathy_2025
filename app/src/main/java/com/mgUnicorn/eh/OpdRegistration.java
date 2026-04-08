package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.mgUnicorn.eh.Adapter.opdadapter;
import com.mgUnicorn.eh.databinding.ActivityAdvertiseBinding;
import com.mgUnicorn.eh.databinding.ActivityOpdRegistrationBinding;
import com.mgUnicorn.eh.models.opdModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class OpdRegistration extends AppCompatActivity {

    public ActivityOpdRegistrationBinding binding;
   public opdadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpdRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         binding.opdRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        ImageViewCompat.setImageTintList(
                binding.floatingActionButton3,
                ColorStateList.valueOf(Color.WHITE));


        FirebaseRecyclerOptions<opdModel> options=
                new FirebaseRecyclerOptions.Builder<opdModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("OPDPatient").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())),opdModel.class)
                        .build();

        adapter=new opdadapter(options);
        binding.opdRecyclerview.setAdapter(adapter);

        binding.floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.opdRecyclerview.scrollToPosition(  Objects.requireNonNull(binding.opdRecyclerview.getAdapter()).getItemCount() - 1);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView) item.getActionView();
       // searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processSearch(s);

                //processSearch2(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //   processSearch(s);

                processSearch(s);

                //processSearch2(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {


        FirebaseRecyclerOptions<opdModel> options=
                new FirebaseRecyclerOptions.Builder<opdModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("OPDPatient")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).orderByChild("a").startAt(s).endAt(s+"\uf8ff"),opdModel.class).build();

        adapter=new opdadapter(options);
        adapter.startListening();
        binding.opdRecyclerview.setAdapter(adapter);
        //  recyclerView.smoothScrollToPosition(adapter.getItemCount());


    }

}

