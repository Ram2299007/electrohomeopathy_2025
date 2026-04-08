package com.mgUnicorn.eh.SpeOfAge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mgUnicorn.eh.EhRemedy.Angitico;
import com.mgUnicorn.eh.EhRemedy.Conceroso;
import com.mgUnicorn.eh.EhRemedy.Scofoloso;
import com.mgUnicorn.eh.EhRemedy.electricity;
import com.mgUnicorn.eh.EhRemedy.febrifugo;
import com.mgUnicorn.eh.EhRemedy.lympatic;
import com.mgUnicorn.eh.EhRemedy.petroales;
import com.mgUnicorn.eh.EhRemedy.vermoifugo;
import com.mgUnicorn.eh.EhRemedy.veronica;
import com.mgUnicorn.eh.databinding.ActivityEhRemedyBinding;

public class EhRemedy extends AppCompatActivity {

    ActivityEhRemedyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEhRemedyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.scofoloso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, Scofoloso.class));
            }
        });



        binding.conceroso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, Conceroso.class));
            }
        });

        binding.angitico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, Angitico.class));
            }
        });

        binding.FEBRIFUGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, febrifugo.class));
            }
        });

        binding.VERONICA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, veronica.class));
            }
        });

        binding.lympatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, lympatic.class));
            }
        });

        binding.vermifugo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, vermoifugo.class));
            }
        });

        binding.Electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, electricity.class));
            }
        });

        binding.petrolae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EhRemedy.this, petroales.class));
            }
        });
    }
}