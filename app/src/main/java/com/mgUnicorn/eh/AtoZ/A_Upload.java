package com.mgUnicorn.eh.AtoZ;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.DiseaseModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class A_Upload extends AppCompatActivity {

    //Widgets

    private Button upload_btn;
    private ImageView imageview_image;
    private TextView showAll_text;
    private ProgressBar progressBar;

    EditText patientName,treatment;

    //firebase
    private DatabaseReference root= FirebaseDatabase.getInstance().getReference();
    private StorageReference reference=FirebaseStorage.getInstance().getReference("AtoZTreatment");
    private Uri imageUri;
    StorageTask mStoragetask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aupload);

        upload_btn=findViewById(R.id.upload_btnAtoZ);
        imageview_image=findViewById(R.id.imageview_imageAtoZ);
        showAll_text=findViewById(R.id.showAll_txtAtoZ);
        progressBar=findViewById(R.id.progressBar_pbAtoZ);
        patientName=findViewById(R.id.etPatient_NameAtoZ);
        treatment=findViewById(R.id.etTreatment_NameAtoZ);

        progressBar.setVisibility(View.INVISIBLE);


        showAll_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(A_Upload.this,A.class);
                startActivity(intent);
            }
        });


        imageview_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallaryIntent=new Intent();
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                gallaryIntent.setType("image/*");
                startActivityForResult(gallaryIntent,2);

            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(patientName.getText().toString().isEmpty()) {
                    patientName.setError("Patient name is empty");
                    return;
                }

               if(treatment.getText().toString().isEmpty()) {
                   treatment.setError("Patient name is empty");
                    return;
                }

                if(mStoragetask!=null && mStoragetask.isInProgress()){


                }
                else
                {
                    if(imageUri!=null){

                        uploadtoFirebase(imageUri);

                    }else
                    {
                    }


                }



            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 && resultCode==RESULT_OK && data!=null){

            imageUri=data.getData();
            imageview_image.setImageURI(imageUri);
        }
    }

    private void uploadtoFirebase(Uri uri) {

        StorageReference fileRef=reference.child(System.currentTimeMillis()
                + "." + getFileExtension(uri));

        mStoragetask= fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        DiseaseModel model=new DiseaseModel(uri.toString(),patientName.getText().toString(),treatment.getText().toString());

                        String modelId=root.push().getKey();

                        assert modelId != null;
                        root.child("AtoZTreatment").child("A").child(modelId)
                                .setValue(model);

                        progressBar.setVisibility(View.INVISIBLE);

                        imageview_image.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
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


    private String getFileExtension(Uri mUri)
    {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(mUri));

    }






}