package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class BMI extends AppCompatActivity {



    //facebook ads

    private final String TAG = BMI.class.getSimpleName();

    TextView mcurrentheight;
    TextView mcurrentweight,mcurrentage;
    ImageView mincrementage,mdecrementage,mincrementweight,mdecrementweight,leftarrow;
    SeekBar mseekbarforheight;
    Button mcalculatebmi;
    RelativeLayout mmale,mfemale;

    int intweight=55;
    int intage=22;
    int currentprogress;
    String mintprogress="170";
    String typerofuser="0";
    String weight2="55";
    String age2="22";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_bmi);

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown


        //facebook ads ends here interstital





        mcurrentage=findViewById(R.id.currentage);
        mcurrentweight=findViewById(R.id.currentweight);
        mcurrentheight=findViewById(R.id.currentheight);
        mincrementage=findViewById(R.id.incrementage);
        mdecrementage=findViewById(R.id.decrementage);
        mincrementweight=findViewById(R.id.incremetweight);
        mdecrementweight=findViewById(R.id.decrementweight);
        mcalculatebmi=findViewById(R.id.calculatebmi);
        mseekbarforheight=findViewById(R.id.seekbarforheight);
        mmale=findViewById(R.id.male);
        mfemale=findViewById(R.id.female);
        leftarrow=findViewById(R.id.leftarrow);




        mmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalefocus));
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalenotfocus));
                typerofuser="Male";

            }
        });


        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalefocus));
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malefemalenotfocus));
                typerofuser="Female";
            }
        });

        mseekbarforheight.setMax(300);
        mseekbarforheight.setProgress(170);
        mseekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentprogress=progress;
                mintprogress=String.valueOf(currentprogress);
                mcurrentheight.setText(mintprogress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mincrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intweight=intweight+1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);

                mincrementweight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttonbackground));
                mdecrementweight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
                mincrementage.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
                mdecrementage.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
            }
        });

        mincrementage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intage=intage+1;
                age2=String.valueOf(intage);
                mcurrentage.setText(age2);

                mincrementage.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttonbackground));
                mdecrementage.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
                mincrementweight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
                mdecrementweight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
            }
        });


        mdecrementage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intage=intage-1;

                age2=String.valueOf(intage);
                mcurrentage.setText(age2);

                mdecrementage.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttonbackground));
                mincrementage.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
                mincrementweight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
                mdecrementweight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));

            }
        });


        mdecrementweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intweight=intweight-1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);

                mdecrementweight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttonbackground));
                mincrementweight.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
                mincrementage.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));
                mdecrementage.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plusminus));

            }
        });



        mcalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(typerofuser.equals("0"))
                {
                }
                else if(mintprogress.equals("0"))
                {
                }
                else if(intage==0 || intage<0)
                {
                }

                else if(intweight==0|| intweight<0)
                {
                }
                else {

                    Intent intent = new Intent(BMI.this, bmiactivity.class);
                    intent.putExtra("gender", typerofuser);
                    intent.putExtra("height", mintprogress);
                    intent.putExtra("weight", weight2);
                    intent.putExtra("age", age2);
                    startActivity(intent);

                }


            }
        });


        leftarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }



}