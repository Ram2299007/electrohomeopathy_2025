package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mgUnicorn.eh.External_Appication.AppicationChart;
import com.mgUnicorn.eh.External_Appication.ImagePhotoOfExternalAppication;
import com.mgUnicorn.eh.External_Appication.LOP;
import com.mgUnicorn.eh.External_Appication.PointDetails;
import com.mgUnicorn.eh.databinding.ActivityExternalApplicationBinding;

public class ExternalApplication extends AppCompatActivity {

    ActivityExternalApplicationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding=ActivityExternalApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.lop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LOP.class));
            }
        });



        binding.ac2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AppicationChart.class));
            }
        });



        binding.pt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PointDetails.class));
            }
        });


         binding.ptt3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(), ImagePhotoOfExternalAppication.class));
             }
         });
    }
}