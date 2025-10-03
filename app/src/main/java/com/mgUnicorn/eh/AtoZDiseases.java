package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mgUnicorn.eh.AtoZ.A;
import com.mgUnicorn.eh.databinding.ActivityAtoZdiseasesBinding;

public class AtoZDiseases extends AppCompatActivity {

    ActivityAtoZdiseasesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAtoZdiseasesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), A.class));
            }
        });


    }
}