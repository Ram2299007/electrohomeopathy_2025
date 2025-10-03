package com.mgUnicorn.eh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mgUnicorn.eh.databinding.ActivityMoneyPageBinding;
import com.mgUnicorn.eh.models.paymentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MoneyPage extends AppCompatActivity {

    ActivityMoneyPageBinding binding;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoneyPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        Vibrator v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);


        binding.runn.setSelected(true);


        FirebaseDatabase.getInstance().getReference().child("payment").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        paymentModel model = snapshot.getValue(paymentModel.class);

                        try {
                            System.out.println(model.getPaymentMessage());

                            binding.imgClose.setVisibility(View.VISIBLE);


                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.btnonefivezerozero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GooglePayPayment.class));
            }
        });


        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get instance of Vibrator from current Context

// Vibrate for 400 milliseconds
                v.vibrate(80);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("from_money_page", true);
                startActivity(intent);
                // Don't finish() - keep MoneyPage in the stack

            }
        });


        binding.fivezerozero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), GooglePayment2.class));
            }
        });
    }





}