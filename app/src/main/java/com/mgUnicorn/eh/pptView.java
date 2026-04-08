package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.mgUnicorn.eh.databinding.ActivityPptViewBinding;
import com.mgUnicorn.eh.databinding.ActivityTest2xmlBinding;

public class pptView extends AppCompatActivity {

    ActivityPptViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityPptViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.ElectroApplicationBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://docs.google.com/presentation/d/14ZUsTtHAvQ4fEJpjBEkCSRe9apJ7QumB/edit?usp=sharing&ouid=110823262556454214428&rtpof=true&sd=true";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        binding.ElectroMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://docs.google.com/presentation/d/134RV-ycGkUeTqHAkjpyUpzGhQHIDJDrZ/edit?usp=sharing&ouid=110823262556454214428&rtpof=true&sd=true";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}