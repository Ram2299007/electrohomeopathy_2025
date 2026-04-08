package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mgUnicorn.eh.Temperamenet.Bilious;
import com.mgUnicorn.eh.Temperamenet.Lymphatic;
import com.mgUnicorn.eh.Temperamenet.Nervous;
import com.mgUnicorn.eh.Temperamenet.Sanguine;
import com.mgUnicorn.eh.databinding.ActivityTemparamentMaterialBinding;

public class TemparamentMaterial extends AppCompatActivity {

    ActivityTemparamentMaterialBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTemparamentMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.temp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TemparamentMaterial.this, Sanguine.class));
            }
        });


        binding.temp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TemparamentMaterial.this, Lymphatic.class));
            }
        });


        binding.temp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TemparamentMaterial.this, Nervous.class));
            }
        });

        binding.temp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TemparamentMaterial.this, Bilious.class));
            }
        });
    }
}