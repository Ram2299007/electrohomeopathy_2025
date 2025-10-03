package com.mgUnicorn.eh.AddDatatoFirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.addDatatoFirebaseModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference.CompletionListener;

public class addDatatoFirebase extends AppCompatActivity {

    //Widgets

    private Button upload_btn;


    EditText name,treatment;

    //firebase
    private DatabaseReference root= FirebaseDatabase.getInstance().getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_datato_firebase);

        upload_btn=findViewById(R.id.buttonTest);

        name=findViewById(R.id.etNameTest);
        treatment=findViewById(R.id.etTreatmentTest);






        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().isEmpty()) {
                    name.setError("Patient name is empty");
                    return;
                }

                if(treatment.getText().toString().isEmpty()) {
                    treatment.setError("Patient name is empty");
                    return;
                }

                addDatatoFirebaseModel model=new addDatatoFirebaseModel(name.getText().toString(),treatment.getText().toString());

                String modelId=root.push().getKey();

                assert modelId != null;
                root.child("AtoZDisease").child("A").child(modelId)
                        .setValue(model, new CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Log.e("Firebase", "Data could not be saved: " + databaseError.getMessage());
                                } else {
                                    Log.d("Firebase", "Data saved successfully");
                                    // Clear the form
                                    name.setText("");
                                    treatment.setText("");
                                }
                            }
                        });



                }




        });
    }






}