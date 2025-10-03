package com.mgUnicorn.eh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mgUnicorn.eh.models.opdModel;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mgUnicorn.eh.databinding.AddPetientBinding;
import com.mgUnicorn.eh.models.patientModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class add_petient extends AppCompatActivity {

    private ProgressBar progressBar;
    //firebase
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private StorageReference reference = FirebaseStorage.getInstance().getReference("patients");
    private Uri imageUri;
    StorageTask mStoragetask;
    int count;

    AddPetientBinding binding;

    //facebook banner ads


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AddPetientBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());


// Initialize the Mobile Ads SDK





        progressBar = findViewById(R.id.progressBar_pb2);

        progressBar.setVisibility(View.INVISIBLE);


        SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy , HH:mm a", Locale.getDefault());


        // only for Ediitext
        SimpleDateFormat timef = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        String time = timef.format(Calendar.getInstance().getTime());




        binding.newpetients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallaryIntent = new Intent();
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                gallaryIntent.setType("image/*");
                startActivityForResult(gallaryIntent, 2);

            }
        });


        binding.buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.etPersonName.getText().toString().isEmpty()) {
                    binding.etPersonName.setError("Patient name is empty");
                    return;
                }

                if (binding.etNumber.getText().toString().isEmpty()) {
                    binding.etNumber.setError("Patient name is empty");
                    return;
                }


                if (mStoragetask != null && mStoragetask.isInProgress()) {


                } else {
                    if (imageUri != null) {

                        uploadtoFirebase(imageUri);

                    } else {
                    }


                }


            }
        });


        binding.buttonWithoutUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(binding.etPersonName.getText().toString().isEmpty()){

                    binding.etPersonName.setError("Fill up message");
                    return;
                }


                if(binding.etNumber.getText().toString().isEmpty()){

                    binding.etNumber.setError("Fill up message");
                    return;
                }





                patientModel model = new patientModel(binding.etPersonName.getText().toString(), binding.etNumber.getText().toString(),"Reg.No-"+binding.etDate.getText().toString());
                String modelId = root.push().getKey();
                assert modelId != null;
                root.child("patient").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child(modelId)
                        .setValue(model);

                progressBar.setVisibility(View.INVISIBLE);





                opdModel opdModel=new opdModel(date,binding.etPersonName.getText().toString(),binding.etNumber.getText().toString());
                String id=root.push().getKey();
                assert id != null;
                root.child("OPDPatient").child(FirebaseAuth.getInstance().getUid()).child(id)
                        .setValue(opdModel);

                //startActivity(new Intent(add_petient.this, MainActivity.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            binding.newpetients.setImageURI(imageUri);
        }
    }


    private void uploadtoFirebase(Uri uri) {


        SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy , HH:mm a", Locale.getDefault());


        // only for Ediitext
        SimpleDateFormat timef = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        String time = timef.format(Calendar.getInstance().getTime());



        StorageReference fileRef = reference.child(System.currentTimeMillis()
                + "." + getFileExtension(uri));

        mStoragetask = fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {



                        patientModel model = new patientModel(uri.toString(), binding.etPersonName.getText().toString(), binding.etNumber.getText().toString(),"Reg.No-"+binding.etDate.getText().toString());
                        String modelId = root.push().getKey();
                        assert modelId != null;
                        root.child("patient").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child(modelId)
                                .setValue(model);

                        progressBar.setVisibility(View.INVISIBLE);


                        binding.newpetients.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

                        //this is for opd registration
                   //     opdModel opdModel=new opdModel(date,binding.etPersonName.getText().toString(),binding.etNumber.getText().toString());


                      opdModel opdModel=new opdModel(date,binding.etPersonName.getText().toString(),binding.etNumber.getText().toString());
                        String id=root.push().getKey();
                        assert id != null;
                        root.child("OPDPatient").child(FirebaseAuth.getInstance().getUid()).child(id)
                                .setValue(opdModel);


                    }
                });


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        progressBar.setVisibility(View.VISIBLE);
                    }
                });

    }


    private String getFileExtension(Uri mUri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(mUri));

    }


}


