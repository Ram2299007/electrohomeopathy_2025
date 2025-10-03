package com.mgUnicorn.eh.External_Appication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.ZoomLinearLayout;
import com.mgUnicorn.eh.databinding.ActivityLopBinding;

public class LOP extends AppCompatActivity {


    ActivityLopBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ZoomLinearLayout zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layoutlop);
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(LOP.this);
                return false;
            }
        });

    }
}