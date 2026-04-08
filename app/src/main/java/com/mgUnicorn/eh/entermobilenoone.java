package com.mgUnicorn.eh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class entermobilenoone extends AppCompatActivity {

    EditText enternumber;
    Button getotbutton;
    ProgressBar progressbar;
    FirebaseDatabase database;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entermobilenoone);
        getSupportActionBar().hide();
        if(Build.VERSION.SDK_INT >=21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        enternumber=findViewById(R.id.input_mobile_number);
        getotbutton=findViewById(R.id.buttongetotp);
        progressbar=findViewById(R.id.progressbar_sending_otp);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        getotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!enternumber.getText().toString().trim().isEmpty())
                {
                    if((enternumber.getText().toString().trim()).length()==10)
                    {

                        progressbar.setVisibility(View.VISIBLE);
                        getotbutton.setVisibility(View.INVISIBLE);
                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + enternumber.getText().toString(), 60, TimeUnit.SECONDS, entermobilenoone.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                progressbar.setVisibility(View.GONE);
                                getotbutton.setVisibility(View.VISIBLE);



                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                progressbar.setVisibility(View.GONE);
                                getotbutton.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressbar.setVisibility(View.GONE);
                                getotbutton.setVisibility(View.VISIBLE);

                                Intent intent=new Intent(getApplicationContext(),verifyenterotptwo.class);
                                intent.putExtra("mobile",enternumber.getText().toString());
                                intent.putExtra("backendotp",backendotp);
                            //    entermobilenoone.this.overridePendingTransition(0,0);
                              //  intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                            }
                        });


                        Intent intent=new Intent(getApplicationContext(),verifyenterotptwo.class);
                        intent.putExtra("mobile",enternumber.getText().toString());

                        startActivity(intent);

                    }
                    else
                    {
                    }
                }
                else
                {
                }
            }
        });
    }
}