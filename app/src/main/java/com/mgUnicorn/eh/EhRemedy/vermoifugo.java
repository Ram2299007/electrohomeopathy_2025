package com.mgUnicorn.eh.EhRemedy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.mgUnicorn.eh.databinding.ActivityVermoifugoBinding;
import com.mgUnicorn.eh.databinding.ActivityVeronicaBinding;

public class vermoifugo extends AppCompatActivity {

    ActivityVermoifugoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityVermoifugoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.verm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://drive.google.com/file/d/1Gp_Dh4khZCvolCf4WEcPGsBFsEvWnmdu/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        binding.verm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://drive.google.com/file/d/1-sYnLR4a1qw3kMdTMqVVCtgVA_Vc6H3O/view?usp=sharing";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}