package com.mgUnicorn.eh;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.mgUnicorn.eh.AtoZ.A_Upload;
import com.mgUnicorn.eh.MainActivity;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.databinding.ActivitySettingsBinding;
import com.mgUnicorn.eh.models.SETTINGHOLD;
import com.mgUnicorn.eh.models.Users;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

@Keep
public class settingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    private final DatabaseReference root= FirebaseDatabase.getInstance().getReference();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String value=(mSharedPreference.getString("clinicKey", "Default_Value"));
        String nameDoctor=(mSharedPreference.getString("userNameKey", "Default_Value"));
        String cachedProfilePic = mSharedPreference.getString("profilePicKey", "");

        binding.etDoctorusername.setText(nameDoctor);

        if (!cachedProfilePic.isEmpty()) {
            Picasso.get()
                    .load(cachedProfilePic)
                    .placeholder(R.drawable.doctortwo)
                    .into(binding.profileImage);
        }

        binding.leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(settingsActivity.this, MainActivity.class);
                i.putExtra("doctorName",binding.etDoctorusername.getText().toString());
                settingsActivity.this.overridePendingTransition(0,0);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
            }
        });


        //HOLDING SETTING

        root.child("SETTINGHOLD").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SETTINGHOLD model=snapshot.getValue(SETTINGHOLD.class);

                        try{



                            System.out.println(model.geta());

                            binding.etStatusSetting.setText(model.geta());
                            binding.etClinicNameSetting.setText(model.getb());
                            binding.etDegreeSetting.setText(model.getc());

                            binding.etRegSetting.setText(model.getd());
                            binding.etAddressSetting.setText(model.gete());





                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=binding.etDoctorusername.getText().toString();
                String status=binding.etStatusSetting.getText().toString();
                String clinic=binding.etClinicNameSetting.getText().toString();
                String degree=binding.etDegreeSetting.getText().toString();
                String reg=binding.etRegSetting.getText().toString();
                String address=binding.etAddressSetting.getText().toString();





                HashMap<String ,Object> obj=new HashMap<>();
                obj.put("userName",userName);
                obj.put("status",status);
                obj.put("clinic",clinic);
                obj.put("degree",degree);
                obj.put("reg",reg);
                obj.put("address",address);

                database.getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                        .updateChildren(obj);













                //data holding


                //  String paymentHold=binding.PaymentEtMessage1.getText().toString();
                SETTINGHOLD pm=new SETTINGHOLD(binding.etStatusSetting.getText().toString(),binding.etClinicNameSetting.getText().toString()
                        ,binding.etDegreeSetting.getText().toString(),binding.etRegSetting.getText().toString(),binding.etAddressSetting.getText().toString()
                );

                root.child("SETTINGHOLD").child(FirebaseAuth.getInstance().getUid())
                        .setValue(pm);










                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("clinicKey", clinic);
                editor.putString("mobileKey", status);
                editor.putString("degreeKey", degree);
                editor.putString("regKey", reg);
                editor.putString("addressKey", address);
                editor.putString("userNameKey", userName);
                editor.commit();
                Toast.makeText(settingsActivity.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
            }
        });




        database.getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users= snapshot.getValue(Users.class);
                        if (users == null) return;
                        Picasso.get()
                                .load(users.getProfilepic())
                                .placeholder(R.drawable.doctortwo)
                                .into(binding.profileImage);
                        try {
                            if (users.getUserName() != null && !users.getUserName().isEmpty()) {
                                binding.etDoctorusername.setText(users.getUserName());
                            }
                        }catch (Exception e){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);

            }
        });




        binding.txtShowSqlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), A_Upload.class));
            }
        });
        String clinic=binding.etClinicNameSetting.getText().toString();



    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 33 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            Toast.makeText(this, "Uploading photo...", Toast.LENGTH_SHORT).show();

            final StorageReference reference = storage.getReference().child("Profile_Picture")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));

            reference.putFile(sFile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoUrl = uri.toString();
                                    database.getReference().child("Users")
                                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                            .child("profilepic").setValue(photoUrl);
                                    SharedPreferences.Editor editor = PreferenceManager
                                            .getDefaultSharedPreferences(getApplicationContext()).edit();
                                    editor.putString("profilePicKey", photoUrl);
                                    editor.apply();
                                    Picasso.get()
                                            .load(photoUrl)
                                            .placeholder(R.drawable.doctortwo)
                                            .into(binding.profileImage);
                                    Toast.makeText(settingsActivity.this, "Profile photo updated!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(settingsActivity.this,
                            "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }



}