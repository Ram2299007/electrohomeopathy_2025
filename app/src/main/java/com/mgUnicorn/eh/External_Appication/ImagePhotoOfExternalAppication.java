package com.mgUnicorn.eh.External_Appication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.ZoomLinearLayout;
import com.mgUnicorn.eh.databinding.ActivityExternalApplicationBinding;
import com.mgUnicorn.eh.databinding.ActivityImagePhotoOfExternalAppicationBinding;

public class ImagePhotoOfExternalAppication extends AppCompatActivity {


    ActivityImagePhotoOfExternalAppicationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityImagePhotoOfExternalAppicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ZoomLinearLayout zoomLinearLayout = (ZoomLinearLayout) findViewById(R.id.zoom_linear_layoutIOE);
        zoomLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomLinearLayout.init(ImagePhotoOfExternalAppication.this);
                return false;
            }
        });


    }
}