package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.mgUnicorn.eh.databinding.ActivityCancerAndEhBinding;
import com.google.firebase.database.DatabaseReference;

public class CancerAndEh extends AppCompatActivity {
    ActivityCancerAndEhBinding binding;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCancerAndEhBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ZoomLinearLayout zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layoutCAEh);
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(CancerAndEh.this);
                return false;
            }
        });



    }
}