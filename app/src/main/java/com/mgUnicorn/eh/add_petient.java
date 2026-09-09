package com.mgUnicorn.eh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mgUnicorn.eh.models.opdModel;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mgUnicorn.eh.databinding.AddPetientBinding;
import com.mgUnicorn.eh.models.patientModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class add_petient extends AppCompatActivity {

    // adb logcat -s AddPatient
    private static final String TAG = "AddPatient";

    public static final String EXTRA_NEW_PATIENT_KEY = "newPatientKey";

    private ProgressBar progressBar;
    //firebase
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private String uid;

    AddPetientBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AddPetientBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());


        progressBar = findViewById(R.id.progressBar_pb2);

        progressBar.setVisibility(View.INVISIBLE);

        // Without a signed in user every save below would crash on a null uid.
        uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "onCreate: uid=" + uid);
        if (uid == null) {
            Log.e(TAG, "onCreate: no signed-in user, closing screen");
            Toast.makeText(this, "Please sign in first to add a patient", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        // Only name and number are collected. The icon at the top is decoration.
        binding.buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "SUBMIT pressed | name=\"" + binding.etPersonName.getText()
                        + "\" number=\"" + binding.etNumber.getText() + "\"");

                if (!isFormValid()) {
                    return;
                }

                setBusy(true);
                savePatient();
            }
        });
    }


    private boolean isFormValid() {
        if (binding.etPersonName.getText().toString().trim().isEmpty()) {
            Log.w(TAG, "validation failed: patient name empty");
            binding.etPersonName.setError("Patient name is empty");
            binding.etPersonName.requestFocus();
            return false;
        }

        if (binding.etNumber.getText().toString().trim().isEmpty()) {
            Log.w(TAG, "validation failed: mobile number empty");
            binding.etNumber.setError("Mobile number is empty");
            binding.etNumber.requestFocus();
            return false;
        }

        return true;
    }


    private void setBusy(boolean busy) {
        Log.d(TAG, "setBusy(" + busy + ")");
        progressBar.setVisibility(busy ? View.VISIBLE : View.INVISIBLE);
        binding.buttonUpload.setEnabled(!busy);
    }


    /**
     * Writes the patient under patient/{uid} and OPDPatient/{uid}.
     */
    private void savePatient() {

        SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy , HH:mm a", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());

        String name = binding.etPersonName.getText().toString().trim();
        String number = binding.etNumber.getText().toString().trim();
        String regNo = "Reg.No-" + binding.etDate.getText().toString();

        patientModel model = new patientModel(name, number, regNo);

        Log.d(TAG, "savePatient: name=" + name + " number=" + number + " regNo=" + regNo);

        String modelId = root.push().getKey();
        if (modelId == null) {
            Log.e(TAG, "savePatient: push() returned a null key");
            setBusy(false);
            Toast.makeText(this, "Could not save, please try again", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "savePatient: writing to patient/" + uid + "/" + modelId);

        root.child("patient").child(uid).child(modelId)
                .setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.i(TAG, "savePatient: OK -> patient/" + uid + "/" + modelId);

                        // this is for opd registration
                        opdModel opdModel = new opdModel(date, name, number);
                        String id = root.push().getKey();
                        if (id != null) {
                            Log.d(TAG, "savePatient: writing to OPDPatient/" + uid + "/" + id);
                            root.child("OPDPatient").child(uid).child(id).setValue(opdModel)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void v) {
                                            Log.i(TAG, "OPD entry saved");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG, "OPD entry FAILED", e);
                                        }
                                    });
                        } else {
                            Log.e(TAG, "savePatient: OPD push() returned a null key");
                        }

                        setBusy(false);
                        Toast.makeText(add_petient.this, "Patient saved", Toast.LENGTH_SHORT).show();

                        // tells statusFragment which row to highlight
                        Intent result = new Intent();
                        result.putExtra(EXTRA_NEW_PATIENT_KEY, modelId);
                        setResult(RESULT_OK, result);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "savePatient FAILED at patient/" + uid + "/" + modelId, e);
                        setBusy(false);
                        Toast.makeText(add_petient.this,
                                "Could not save patient: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


}
