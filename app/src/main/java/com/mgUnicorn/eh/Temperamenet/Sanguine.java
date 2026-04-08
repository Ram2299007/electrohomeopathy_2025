package com.mgUnicorn.eh.Temperamenet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.ZoomLinearLayout;
import com.mgUnicorn.eh.databinding.ActivitySanguineBinding;

public class Sanguine extends AppCompatActivity {

    ActivitySanguineBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySanguineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ZoomLinearLayout zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layoutSanguine);
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(Sanguine.this);
                return false;
            }
        });

    }
}