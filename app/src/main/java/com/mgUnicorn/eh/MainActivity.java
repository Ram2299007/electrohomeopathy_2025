package com.mgUnicorn.eh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mgUnicorn.eh.Adapter.fragmentsAdapter;
import com.mgUnicorn.eh.databinding.ActivityMainBinding;
import com.mgUnicorn.eh.models.Users;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    ArrayList<Users> list;

    DatabaseReference reference;
    RecyclerView rv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
        rv=findViewById(R.id.chatsRecyclerview);


        //define adapter here




        binding.viewPager.setAdapter(new fragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);






        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        int itemId = item.getItemId();
        
        if (itemId == R.id.setting) {
            Intent intent2=new Intent(MainActivity.this,settingsActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.logout) {
            auth.signOut();
            //very important intent all time
            Intent intent=new Intent(MainActivity.this, signInActivity.class);
            startActivity(intent);
        }






        return true;


    }

    @Override
    public void onBackPressed() {
        // Check if we came from a payment flow
        if (getIntent().getBooleanExtra("from_payment", false)) {
            // Go back to the screen before payment (MoneyPage or previous activity)
            Intent intent = new Intent(this, MoneyPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else if (getIntent().getBooleanExtra("from_money_page", false)) {
            // If came from MoneyPage, exit the app
            finishAffinity(); // This will close all activities and exit the app
        } else {
            // Default behavior - exit app or go to previous screen
            super.onBackPressed();
        }
    }








}