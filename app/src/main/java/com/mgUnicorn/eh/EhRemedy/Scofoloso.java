package com.mgUnicorn.eh.EhRemedy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.mgUnicorn.eh.databinding.ActivityScofolosoBinding;

public class Scofoloso extends AppCompatActivity {

    ActivityScofolosoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityScofolosoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/1H7FFwp57y_8-p6s-7lZSzocXg5TlSv0M/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        binding.s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/1PfgRjSC2DAfqBf-4D_MQVCXUkPtAyDpA/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        binding.s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/14yl8X_gXR3Y3Vq-FKC5Fa5Pv_Ol__tNp/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        binding.s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/1837-ld4F5GKWEIELt1_-coTqxFaKdBG9/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        binding.s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/1QR3AHrFgczGr0W2gLkA927tHpqgFFTiT/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        binding.s10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/1YNmnui90S0ZJVloxe3CJdgGS1eyHpRIV/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        binding.s11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/1J5DYJojC0fg8nuMwv9AqDFX3XaseR3L8/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });  binding.s12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/1uSGr13fhV0qPxNocMmvJA0nHCv960trC/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        binding.sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://drive.google.com/file/d/1hZPKX0-iVZJL5e6d874geqOB9u1Zo6_T/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}