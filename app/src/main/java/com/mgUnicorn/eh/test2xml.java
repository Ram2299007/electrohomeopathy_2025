


package com.mgUnicorn.eh;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.common.internal.service.Common;
import com.mgUnicorn.eh.AtoZ.A;
import com.mgUnicorn.eh.databinding.ActivityTest2xmlBinding;
import com.mgUnicorn.eh.models.PAYMENTPATIENTHOLD;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.mgUnicorn.eh.Adapter.chatAdapter;

import com.mgUnicorn.eh.casePaperShow.showCasepaper;
import com.mgUnicorn.eh.databinding.ActivityFirstPatientBinding;
import com.mgUnicorn.eh.models.MessageModel;
import com.mgUnicorn.eh.models.Users;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class test2xml extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    ActivityTest2xmlBinding binding;

    //for send data;
    FirebaseStorage storage;
    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView recyclerView;

    private final String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/test.pdf";
    private final File file = new File(stringFilePath);


//pdf data

    private static final int STORAGE_CODE = 1000;
    TextView userName;


    EditText doctorName, mobileNo, clinicName, Degree, RegNo;
    Users users;

    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTest2xmlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = findViewById(R.id.PatientchatsRecyclerviewtest);


        EditText autoDate = (EditText) findViewById(R.id.Date_etMessage5);


        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String value = (mSharedPreference.getString("clinicKey", "Default_Value"));


        //add photo in chatactivity
        String profilePic = getIntent().getStringExtra("patient_pic");


        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), zoomfordetails.class);
                intent.putExtra("profilePic2", profilePic);
                startActivity(intent);
            }
        });


        Picasso.get().load(profilePic).placeholder(R.drawable.coronavirus).into(binding.profileImage);

        SimpleDateFormat dateF = new SimpleDateFormat("___EEE , dd - MM - yyyy , HH:mm_________", Locale.getDefault());


        // only for Ediitext
        SimpleDateFormat timef = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        String time = timef.format(Calendar.getInstance().getTime());

        autoDate.setText(date);


        Spinner spinner = findViewById(R.id.B_P_etText2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //spinner for temperament
        Spinner spinner2temp = findViewById(R.id.Temp_etText9);
        ArrayAdapter<CharSequence> adapterTemp = ArrayAdapter.createFromResource(this, R.array.Temperament, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2temp.setAdapter(adapterTemp);
        spinner2temp.setOnItemSelectedListener(this);

        getSupportActionBar().hide();
        // for send data;
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


       /* final SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strCC = (mSharedPreference1.getString("CCkey3", "Default_Value"));
        String strCF = (mSharedPreference1.getString("CFkey3", "Default_Value"));
        String StrD = (mSharedPreference1.getString("Dkey3", "Default_Value"));
        String StrInv = (mSharedPreference1.getString("Ikey3", "Default_Value"));
        String StrARN = (mSharedPreference1.getString("ARNkey3", "Default_Value"));
        String StrT = (mSharedPreference1.getString("Tkey3", "Default_Value"));



        binding.PCEtText21.setText(strCC);
        binding.CFEtText21.setText(strCF);
        binding.DGSSEtText22.setText(StrD);
        binding.INGNEtText22.setText(StrInv);
        binding.ARNEtText22.setText(StrARN);
        binding.TTMTEtText22.setText(StrT);


        */


        // Pdf Doctor name for pdf set doctor name from firebase
        database.getReference().child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);

                        try {


                            assert users != null;
                            binding.etDoctorName2.setText(users.getUserName());
                            binding.etClinicName2.setText(users.getClinic());
                            binding.etDegreeName2.setText(users.getDegree());
                            binding.etRegNumber2.setText(users.getReg());
                            binding.etMobileNumber2.setText(users.getStatus());
                            binding.etDoctorAddress2.setText(users.getAddress());

                            //Discovery
                            binding.etPresentComplaints2.setText(users.getPresent_Complaints());
                            binding.etDiagnosis2.setText(users.getDiagnosis());
                            binding.etInvestigation2.setText(users.getInvestigation());
                            binding.etTreatment2.setText(users.getTreatment());
                        } catch (Exception e) {

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        final String senderId = auth.getUid();


        String receiverId = getIntent().getStringExtra("userId");


        String userName = getIntent().getStringExtra("UserName");
        binding.userNameChat1.setText(userName);

     /*   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.DateSend5.performClick();
            }
        },500);


      */
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //for send data;
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final chatAdapter chatAdapter = new chatAdapter(messageModels, this, receiverId);
        binding.PatientchatsRecyclerviewtest.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.PatientchatsRecyclerviewtest.setLayoutManager(linearLayoutManager);

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(lm);


        final String senderRoom = senderId + receiverId;
        final String receiveRoom = receiverId + senderId;

        database.getReference().child("PatientChatstest")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            try {
                                MessageModel model = snapshot1.getValue(MessageModel.class);

                                // String  key=snapshot1.getKey();
                                model.setMessageId(snapshot1.getKey());
                                messageModels.add(model);

                                chatAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        root.child("patientPAYMENTtest").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PAYMENTPATIENTHOLD model = snapshot.getValue(PAYMENTPATIENTHOLD.class);

                        try {


                            System.out.println(model.getPayment());

                            binding.PaymentEtMessage1.setText(model.getPayment());
                            binding.AgeEtMessage5.setText(model.getAddress());
                            binding.SexSpinner.setText(model.getOccupation());
                            // binding.BPEtText2.setText(model.getWeight());
                            binding.WeightEtMessage3.setText(model.getAge());
                            binding.occupationEtMessage1.setText(model.getBp());
                            binding.AddressEtMessage1.setText(model.getWeight());
                            //  binding.TempEtText9.setText(model.getAddress());
                            binding.PCEtText21.setText(model.getChiefcomplaints());
                            binding.CFEtText21.setText(model.getClinicalfeature());
                            binding.DGSSEtText22.setText(model.getDiagnosis());
                            binding.INGNEtText22.setText(model.getInvestigation());
                            binding.ARNEtText22.setText(model.getAdvice());
                            binding.TTMTEtText22.setText(model.getTreatment());


                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


//for send data;

        binding.DateSend5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = binding.DateEtMessage5.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());
                //  binding.DateEtMessage5.setText("");

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.TTMTSend22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = binding.TTMTEtText22.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());
                //  binding.DateEtMessage5.setText("");

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.PCSend21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = binding.PCEtText21.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());
                //  binding.DateEtMessage5.setText("");

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.INGNSend22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = binding.INGNEtText22.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());
                //  binding.DateEtMessage5.setText("");

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.DGSSSend22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = binding.DGSSEtText22.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());
                //  binding.DateEtMessage5.setText("");

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.AgeSend5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.AgeEtMessage5.getText().toString().isEmpty()) {
                    binding.AgeEtMessage5.setError("Please fill up message");
                    return;
                }
                String message = binding.AgeEtMessage5.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.WeightSend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.WeightEtMessage3.getText().toString().isEmpty()) {
                    binding.WeightEtMessage3.setError("Please fill up message");
                    return;
                }
                String message = binding.WeightEtMessage3.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.AddressSend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.AddressEtMessage1.getText().toString().isEmpty()) {
                    binding.AddressEtMessage1.setError("Please fill up message");
                    return;
                }
                String message = binding.AddressEtMessage1.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.BPSend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = spinner.getSelectedItem().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());


                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });

        //for send data;

        binding.TempSend9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = spinner2temp.getSelectedItem().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());


                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });


        //for send data;

        binding.CfSend22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.CFEtText21.getText().toString().isEmpty()) {
                    binding.CFEtText21.setError("Please fill up message");
                    return;
                }
                String message = binding.CFEtText21.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });


            }
        });


        binding.arnSend22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.ARNEtText22.getText().toString().isEmpty()) {
                    binding.ARNEtText22.setError("Please fill up message");
                    return;
                }
                String message = binding.ARNEtText22.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });


            }
        });

        binding.OccupationSend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.occupationEtMessage1.getText().toString().isEmpty()) {
                    binding.occupationEtMessage1.setError("Please fill up message");
                    return;
                }
                String message = binding.occupationEtMessage1.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });


            }

        });


        //for send data;

        binding.paymentSend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //for sequence in recycler view


                binding.TTMTSend22.performClick();
                //required send data
                binding.arnSend22.performClick();
                binding.INGNSend22.performClick();
                binding.DGSSSend22.performClick();

                // require send data
                binding.CfSend22.performClick();
                binding.PCSend21.performClick();
                binding.TempSend9.performClick();


                if (binding.PaymentEtMessage1.getText().toString().isEmpty()) {
                    binding.PaymentEtMessage1.setError("Please fill up message");
                    return;
                }
                String message = binding.PaymentEtMessage1.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });

                final MediaPlayer mp = MediaPlayer.create(test2xml.this, R.raw.airsound);
                mp.start();

                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });


                //Pdf Data and delee from send button on all component Edited Recently
                binding.DateSend5.performClick();

                binding.btnPdf.performClick();

                //     binding.PaymentEtMessage1.setText("");
                //    binding.PaymentEtMessage1.setText("*Pending(Rs)*  ");


                //send address data for holding


                //  String paymentHold=binding.PaymentEtMessage1.getText().toString();
                PAYMENTPATIENTHOLD pm = new PAYMENTPATIENTHOLD(binding.PaymentEtMessage1.getText().toString(), binding.AgeEtMessage5.getText().toString()
                        , binding.SexSpinner.getText().toString(), binding.WeightEtMessage3.getText().toString(), binding.occupationEtMessage1.getText().toString(), binding.AddressEtMessage1.getText().toString()
                        , binding.PCEtText21.getText().toString(), binding.CFEtText21.getText().toString(),
                        binding.DGSSEtText22.getText().toString(), binding.INGNEtText22.getText().toString(), binding.ARNEtText22.getText().toString(), binding.TTMTEtText22.getText().toString()
                );

                root.child("patientPAYMENTtest").child(FirebaseAuth.getInstance().getUid())
                        .setValue(pm);
                binding.PaymentEtMessage1.setText("");
                binding.AddressEtMessage1.setText("");
                binding.SexSpinner.setText("");
                binding.WeightEtMessage3.setText("");
                //  binding.BPEtText2.setText("");
                binding.occupationEtMessage1.setText("");
                binding.AgeEtMessage5.setText("");
                binding.AddressEtMessage1.setText("");
                //  binding.TempEtText9.setText("");


                //send data with shared preference
                String stChiefComplaints = binding.PCEtText21.getText().toString();
                String strClinicalFeatures = binding.CFEtText21.getText().toString();
                String strDiagnosis = binding.DGSSEtText22.getText().toString();
                String strInvestigation = binding.INGNEtText22.getText().toString();
                String strAdvice = binding.ARNEtText22.getText().toString();
                String strTreatment = binding.TTMTEtText22.getText().toString();

/*
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("CCkey3", stChiefComplaints);
                editor.putString("CFkey3", strClinicalFeatures);
                editor.putString("Dkey3", strDiagnosis);
                editor.putString("Ikey3", strInvestigation);
                editor.putString("ARNkey3", strAdvice);
                editor.putString("Tkey3", strTreatment);
                editor.commit();


 */

            }

        });


        //for send data;

        binding.SexSend4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.SexSpinner.getText().toString().isEmpty()) {
                    binding.SexSpinner.setError("Please fill up message");
                    return;
                }
                String message = binding.SexSpinner.getText().toString();

                final MessageModel model = new MessageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());

                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                    }
                });


                database.getReference().child("PatientChatstest")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        database.getReference().child("PatientChatstest")
                                .child(receiveRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }

        });

        //Pdf Data Report Upload
        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(test2xml.this, StorageActivity.class);
                startActivity(intent);
            }
        });


        //Diagnosis  activity intent

        binding.imgDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(test2xml.this, showCasepaper.class);
                startActivity(intent);

            }
        });

        //disease  activity intent

        binding.imgDieaase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(test2xml.this, A.class);
                startActivity(intent);

            }
        });


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void btnPdfView(View view) {

        String address = binding.AgeEtMessage5.getText().toString();
        String occupation = binding.SexSpinner.getText().toString();
        String age = binding.WeightEtMessage3.getText().toString();
        String sex = binding.BPEtText2.getSelectedItem().toString();
        String TemperamentString = binding.TempEtText9.getSelectedItem().toString();
        String bp = binding.occupationEtMessage1.getText().toString();
        String weight = binding.AddressEtMessage1.getText().toString();
        String payment = binding.PaymentEtMessage1.getText().toString();
        String drTextPdf = binding.etDoctorName2.getText().toString();
        String clinicPdf = binding.etClinicName2.getText().toString();
        String DegreePdf = binding.etDegreeName2.getText().toString();
        String RegPdf = binding.etRegNumber2.getText().toString();
        String mobilePdf = binding.etMobileNumber2.getText().toString();

        String DoctorAddressnew2 = binding.etDoctorAddress2.getText().toString();

        //for Discovery data get and send in pdf
        String etPresentComplaints = binding.etPresentComplaints2.getText().toString();
        String etDiagnosis = binding.etDiagnosis2.getText().toString();
        String etInvestigation = binding.etInvestigation2.getText().toString();
        String etTreatment = binding.etTreatment2.getText().toString();

        //Extra Details in Patient activity

        String pc = binding.PCEtText21.getText().toString();
        String INVGN = binding.INGNEtText22.getText().toString();
        String DGNSS = binding.DGSSEtText22.getText().toString();
        String TTMT = binding.TTMTEtText22.getText().toString();
        String ARN = binding.ARNEtText22.getText().toString();


        //Add Date and Time
        //only for pdf date
        SimpleDateFormat DatePdf = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        SimpleDateFormat TimePdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String strDatePdf = DatePdf.format(Calendar.getInstance().getTime());
        String strTimePdf = TimePdf.format(Calendar.getInstance().getTime());
        //clinic Name

        final SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String value1 = (mSharedPreference1.getString("clinicKey", "Default_Value"));
        String mobileKey = (mSharedPreference1.getString("mobileKey", "Default_Value"));
        String degreeKey = (mSharedPreference1.getString("degreeKey", "Default_Value"));
        String regKey = (mSharedPreference1.getString("regKey", "Default_Value"));
        String addresskey = (mSharedPreference1.getString("addressKey", "Default_Value"));
        String doctorName = (mSharedPreference1.getString("userNameKey", "Default_Value"));


        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();


        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(250, 620, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();


        //Date
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(6.0f);
        myPaint.setColor(Color.rgb(112, 119, 119));
        canvas.drawText(strDatePdf, myPageInfo1.getPageWidth() / 2, 20, myPaint);


        //clinic Name
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(10f);
        canvas.drawText(value1, myPageInfo1.getPageWidth() / 2, 30, myPaint);


        //doctor name   40 is nothimg but the Top margin
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(9f);
        myPaint.setColor(Color.BLACK);
        canvas.drawText(doctorName, myPageInfo1.getPageWidth() / 2, 40, myPaint);


        //DEGREE name   40 is nothimg but the Top margin
        myPaint.setTextSize(9f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setColor(Color.rgb(112, 119, 119));
        canvas.drawText(degreeKey, myPageInfo1.getPageWidth() / 2, 50, myPaint);


        //address name   40 is nothimg but the Top margin
        myPaint.setTextSize(9f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setColor(Color.rgb(112, 119, 119));
        canvas.drawText(addresskey, myPageInfo1.getPageWidth() / 2, 60, myPaint);


        //REG name   40 is nothimg but the Top margin
        myPaint.setTextSize(9f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setColor(Color.rgb(112, 119, 119));
        canvas.drawText("Reg. No." + regKey, myPageInfo1.getPageWidth() / 2, 70, myPaint);


        //mobile number   40 is nothimg but the Top margin
        myPaint.setTextSize(9f);
        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setColor(Color.rgb(112, 119, 119));
        canvas.drawText(mobileKey, myPageInfo1.getPageWidth() / 2, 80, myPaint);


        //draw line horizontal paint
        //line difference between y to 16
        int startXPosition = 10;
        int endXPosition = myPageInfo1.getPageWidth() - 10;
        int startYPosition = 90;
        myPaint.setColor(Color.rgb(255, 69, 0));
        myPaint.setStrokeWidth(2);
        canvas.drawLine(startXPosition, startYPosition + 3, endXPosition, startYPosition + 3, myPaint);


        //Patient name   40 is nothimg but the Top margin
        //name of patient
        userName = findViewById(R.id.userNameChat1);
        String PATIENTNAME = userName.getText().toString();
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(PATIENTNAME, 12, 109, myPaint);


        //Patient age   40 is nothimg but the Top margin
        //age of patient

        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Age :" + age, 12, 119, myPaint);

        //gender of patient
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Gender :" + sex, 12, 129, myPaint);

        //address of patient
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(address, 12, 139, myPaint);

        //mobile
        String number = getIntent().getStringExtra("Number");
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(number, 12, 149, myPaint);


        //occupation
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(occupation, 12, 159, myPaint);


        //weight
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Weight :" + weight, 12, 169, myPaint);


        //weight
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("B.P :" + bp, 12, 179, myPaint);


        //draw line horizontal paint
        //line difference between y to 16

        int startYPosition1 = 189;
        myPaint.setColor(Color.rgb(255, 69, 0));
        myPaint.setStrokeWidth(2);
        canvas.drawLine(startXPosition, startYPosition1 + 3, endXPosition, startYPosition1 + 3, myPaint);
        //ends with 195 top margin


        //temperament
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Temperament", 12, 208, myPaint);


        //temp 2
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(TemperamentString, 12, 218, myPaint);

        //cheif complaints
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Chief Complaints ", 12, 238, myPaint);

        //cc 2
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(pc, 12, 248, myPaint);

        //cheif complaints
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Clinical Features", 12, 288, myPaint);

        //cc 2
        String cf = binding.CFEtText21.getText().toString();
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(cf, 12, 298, myPaint);


        //Diagnosis
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Diagnosis", 12, 338, myPaint);

        //diagnosis
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(DGNSS, 12, 348, myPaint);

        //Diagnosis
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Investigation", 12, 368, myPaint);

        //diagnosis
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(INVGN, 12, 378, myPaint);

        //Diagnosis
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Advice/Referrals", 12, 418, myPaint);

        //ARN
        myPaint.setColor(Color.rgb(112, 119, 119));
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(ARN, 12, 428, myPaint);


        //Investigation
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(12);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Rx", 12, 458, myPaint);


        //Investigation
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(8.5f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(TTMT, 12, 468, myPaint);





        myPdfDocument.finishPage(myPage1);
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            //  textView.setText("Error in Creating");
        }
        myPdfDocument.close();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printPdf() {

        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(getApplicationContext(),
                    Common.getAppPath(this) + "test_pdf.pdf");
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());

        } catch (Exception e) {

        }

    }


    //Pdf Data ******
 /*   private void savePdf() {

        Paragraph p = new Paragraph();
        String pName = binding.userNameChat1.getText().toString();

        Document mDoc = new Document();
        String mFileName = new SimpleDateFormat("dd - MM - yyyy , HH:mm:ss", Locale.getDefault()
        ).format(System.currentTimeMillis());

        String dcim = "DCIM";
        String mFilepath = Environment.getExternalStorageDirectory() + "/" + dcim + "/" + pName + "_" + mFileName + ".pdf";

        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilepath));
            mDoc.open();

            String address = binding.AgeEtMessage5.getText().toString();
            String occupation = binding.SexSpinner.getText().toString();
            String age = binding.WeightEtMessage3.getText().toString();
            String sex = binding.BPEtText2.getSelectedItem().toString();
            String TemperamentString = binding.TempEtText9.getSelectedItem().toString();
            String bp = binding.occupationEtMessage1.getText().toString();
            String weight = binding.AddressEtMessage1.getText().toString();
            String payment = binding.PaymentEtMessage1.getText().toString();
            String drTextPdf = binding.etDoctorName2.getText().toString();
            String clinicPdf = binding.etClinicName2.getText().toString();
            String DegreePdf = binding.etDegreeName2.getText().toString();
            String RegPdf = binding.etRegNumber2.getText().toString();
            String mobilePdf = binding.etMobileNumber2.getText().toString();

            String DoctorAddressnew2 = binding.etDoctorAddress2.getText().toString();

            //for Discovery data get and send in pdf
            String etPresentComplaints = binding.etPresentComplaints2.getText().toString();
            String etDiagnosis = binding.etDiagnosis2.getText().toString();
            String etInvestigation = binding.etInvestigation2.getText().toString();
            String etTreatment = binding.etTreatment2.getText().toString();

            //Extra Details in Patient activity

            String pc = binding.PCEtText21.getText().toString();
            String INVGN = binding.INGNEtText22.getText().toString();
            String DGNSS = binding.DGSSEtText22.getText().toString();
            String TTMT = binding.TTMTEtText22.getText().toString();
            String ARN = binding.ARNEtText22.getText().toString();


            //Add Date and Time
            //only for pdf date
            SimpleDateFormat DatePdf = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
            SimpleDateFormat TimePdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String strDatePdf = DatePdf.format(Calendar.getInstance().getTime());
            String strTimePdf = TimePdf.format(Calendar.getInstance().getTime());
            //clinic Name

            final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String value = (mSharedPreference.getString("clinicKey", "Default_Value"));
            String mobileKey = (mSharedPreference.getString("mobileKey", "Default_Value"));
            String degreeKey = (mSharedPreference.getString("degreeKey", "Default_Value"));
            String regKey = (mSharedPreference.getString("regKey", "Default_Value"));
            String addresskey = (mSharedPreference.getString("addressKey", "Default_Value"));
            String doctorName = (mSharedPreference.getString("userNameKey", "Default_Value"));


            mDoc.addAuthor("MindGames");

            //Setting
            mDoc.setPageSize(PageSize.A4);

            //Font Setting

            BaseColor colorAccent = new BaseColor(0, 135, 204, 255);
            BaseColor colorAccent2 = new BaseColor(0, 0, 0, 255);
            BaseColor orangerx = new BaseColor(255, 69, 0, 255);
            BaseColor blackrx = new BaseColor(0, 0, 0, 255);
            float fontSize = 12.0f;
            float valueFontSize = 26.0f;

            //Custom font

            BaseFont fontNameBold = BaseFont.createFont("assets/fonts/vardenabold.ttf", "UTF-8", BaseFont.EMBEDDED);
            BaseFont fontNameNormal = BaseFont.createFont("assets/fonts/vardana.TTF", "UTF-8", BaseFont.EMBEDDED);

            BaseFont rxFontOrange = BaseFont.createFont("assets/fonts/Famosa-BlackDemo.otf", "UTF-8", BaseFont.EMBEDDED);

            //Crete Title of Document

            Font titleFont = new Font(fontNameBold, 19.0f, Font.NORMAL, BaseColor.BLACK);
            Font titleFontMedium = new Font(fontNameBold, 18.0f, Font.NORMAL, BaseColor.BLACK);
            Font titleFontMedium2 = new Font(fontNameBold, 17.0f, Font.ITALIC, BaseColor.BLACK);


            Font rxFontOrangenew = new Font(rxFontOrange, 16.0f, Font.ITALIC, orangerx);

            Font rxblackFont = new Font(rxFontOrange, 26.0f, Font.BOLD, blackrx);


            Font caseFont = new Font(fontNameNormal, 17.0f, Font.NORMAL, BaseColor.BLACK);

            Font drotherlow = new Font(fontNameNormal, 15.0f, Font.NORMAL, BaseColor.BLACK);
            Font dev = new Font(fontNameNormal, 14.0f, Font.NORMAL, BaseColor.BLACK);

            Font drotherlowbold = new Font(fontNameNormal, 15.0f, Font.BOLD, BaseColor.BLACK);


            Font drOther = new Font(fontNameNormal, 16.0f, Font.NORMAL, BaseColor.BLACK);
            Font drOtherBold = new Font(fontNameNormal, 20.0f, Font.BOLD, BaseColor.BLACK);


            Font dateFont = new Font(fontNameNormal, 14.0f, Font.ITALIC, BaseColor.BLACK);

            //Add more
            Font DoctorNameFont = new Font(fontNameNormal, fontSize, Font.NORMAL, colorAccent);

            //Add Left Abd right names
            Font DoctorNameValueFont = new Font(fontNameBold, fontSize, Font.NORMAL, BaseColor.BLACK);
            mDoc.add(new Paragraph("1"));

            addNewItem(mDoc, "Date :" + strDatePdf, Element.ALIGN_CENTER, dateFont);


            //clinic value
            addNewItem(mDoc, value, Element.ALIGN_CENTER, titleFont);

            //dr name
            addNewItem(mDoc, doctorName, Element.ALIGN_CENTER, titleFontMedium);

            //degree
            addNewItem(mDoc, degreeKey, Element.ALIGN_CENTER, drOther);

            //address
            addNewItem(mDoc, addresskey, Element.ALIGN_CENTER, drOther);

            //reg key
            addNewItem(mDoc, "Reg. No." + regKey, Element.ALIGN_CENTER, drOther);

            //mobileNo doctor
            addNewItem(mDoc, mobileKey, Element.ALIGN_CENTER, drOther);

            addLineSeperator(mDoc);


            //name of patient
            //NAME AND AGE OF PATIENT
            userName = findViewById(R.id.userNameChat1);
            String PATIENTNAME = userName.getText().toString();

            addNewItem(mDoc, PATIENTNAME, Element.ALIGN_LEFT, drOther);

            //age pTIENT
            addNewItem(mDoc, "Age :" + age, Element.ALIGN_LEFT, drotherlow);

            //gender
            addNewItem(mDoc, "Gender :" + sex, Element.ALIGN_LEFT, drotherlow);

            //address
            addNewItem(mDoc, address, Element.ALIGN_LEFT, drotherlow);


            //mobile
            String number = getIntent().getStringExtra("Number");
            addNewItem(mDoc, number, Element.ALIGN_LEFT, drotherlow);


            //occupation
            addNewItem(mDoc, occupation, Element.ALIGN_LEFT, drotherlow);

            //weight
            addNewItem(mDoc, "Weight :" + weight, Element.ALIGN_LEFT, drotherlow);

            //bp
            addNewItem(mDoc, "B.P :" + bp, Element.ALIGN_LEFT, drotherlow);

            addLineSeperator(mDoc);

            //pc
            addNewItem(mDoc, "Temperament", Element.ALIGN_LEFT, drotherlowbold);

            addNewItem(mDoc, TemperamentString, Element.ALIGN_LEFT, drotherlow);

            //Chief complaints
            //chief complaints = present complaints
            addNewItem(mDoc, "Chief Complaints ", Element.ALIGN_LEFT, drotherlowbold);
            addNewItem(mDoc, pc, Element.ALIGN_LEFT, drotherlow);


            //require space--clinical
            String cf = binding.CFEtText21.getText().toString();
            addNewItem(mDoc, "Clinical Features", Element.ALIGN_LEFT, drotherlowbold);
            addNewItem(mDoc, cf, Element.ALIGN_LEFT, drotherlow);


            addNewItem(mDoc, drTextPdf, Element.ALIGN_RIGHT, titleFontMedium2);

            addNewItem(mDoc, degreeKey, Element.ALIGN_RIGHT, drOther);
            String eh = "EH : Practice";
            addNewItemWithLefAndRight(mDoc, eh, regKey, rxFontOrangenew, drOther);


            //-------****--------------------Below is a new page pdf  --------------------------*****-------------------------

            mDoc.newPage();
            mDoc.add(new Paragraph("2"));

            addNewItem(mDoc, "Date :" + strDatePdf, Element.ALIGN_CENTER, dateFont);
            //clinic value
            addNewItem(mDoc, value, Element.ALIGN_CENTER, titleFont);

            //dr name
            addNewItem(mDoc, doctorName, Element.ALIGN_CENTER, titleFontMedium);

            //degree
            addNewItem(mDoc, degreeKey, Element.ALIGN_CENTER, drOther);

            //address
            addNewItem(mDoc, addresskey, Element.ALIGN_CENTER, drOther);

            //reg key
            addNewItem(mDoc, "Reg. No." + regKey, Element.ALIGN_CENTER, drOther);

            //mobileNo doctor
            addNewItem(mDoc, mobileKey, Element.ALIGN_CENTER, drOther);

            addLineSeperator(mDoc);


            //require space--diagnosis
            addNewItem(mDoc, "Diagnosis", Element.ALIGN_LEFT, drotherlowbold);
            addNewItem(mDoc, DGNSS, Element.ALIGN_LEFT, drotherlow);
            mDoc.add(Chunk.NEWLINE);


            addNewItem(mDoc, "Investigations ", Element.ALIGN_LEFT, drotherlowbold);
            addNewItem(mDoc, INVGN, Element.ALIGN_LEFT, drotherlow);
            mDoc.add(Chunk.NEWLINE);
            mDoc.add(Chunk.NEWLINE);


            addNewItem(mDoc, "Advice/Referrals ", Element.ALIGN_LEFT, drotherlowbold);
            addNewItem(mDoc, ARN, Element.ALIGN_LEFT, drotherlow);
            mDoc.add(Chunk.NEWLINE);
            mDoc.add(Chunk.NEWLINE);


            String rx = "Rx";

            addNewItem(mDoc, rx, Element.ALIGN_LEFT, rxblackFont);

            addNewItem(mDoc, TTMT, Element.ALIGN_LEFT, drotherlow);
            mDoc.add(Chunk.NEWLINE);
            mDoc.add(Chunk.NEWLINE);
            mDoc.add(Chunk.NEWLINE);


            addNewItem(mDoc, drTextPdf, Element.ALIGN_RIGHT, titleFontMedium2);

            addNewItem(mDoc, degreeKey, Element.ALIGN_RIGHT, drOther);
            addNewItemWithLefAndRight(mDoc, eh, regKey, rxFontOrangenew, drOther);

            mDoc.close();

        } catch (Exception ex) {
        }

    }


  */
    //pdf data for left and write data
    private void addNewItemWithLefAndRight(Document mDoc, String textLeft, String textRight, Font titleLeftFont, Font titleRightFont) throws DocumentException {

        Chunk chunkTextLeft = new Chunk(textLeft, titleLeftFont);
        Chunk chunkTextRight = new Chunk(textRight, titleRightFont);

        Paragraph p = new Paragraph(chunkTextLeft);
        p.add(new Chunk(new VerticalPositionMark()));
        p.add(chunkTextRight);

        mDoc.add(p);
    }

    //Pdf Data for Space and Line
    private void addLineSeperator(Document mDoc) throws DocumentException {

        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(255, 69, 0, 255));
        lineSeparator.setLineWidth(4);


        addLineSpace(mDoc);
        addLineSpace(mDoc);
        addLineSpace(mDoc);
        mDoc.add(new Chunk(lineSeparator));
        addLineSpace(mDoc);
        addLineSpace(mDoc);
        addLineSpace(mDoc);
    }

    //pdf data
    private void addLineSpace(Document mDoc) throws DocumentException {

        mDoc.add(new Paragraph(""));


    }


    //Pdf Data New Item Code
    private void addNewItem(Document mDoc, String text, int align, Font font) throws DocumentException {

        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        mDoc.add(paragraph);


    }

    //pdf Data handle permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_CODE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //createPDFFile();

                } else {

                }
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text1 = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}