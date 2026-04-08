package com.mgUnicorn.eh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mgUnicorn.eh.databinding.ActivityGooglePayPaymentBinding;
import com.mgUnicorn.eh.models.paymentModel;

import java.util.ArrayList;
import java.util.Objects;

public class GooglePayPayment extends AppCompatActivity {

    EditText note;
    Button send;
    TextView name, upivirtualId, amount;
    String TAG = "main";


    ActivityGooglePayPaymentBinding binding;


    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    final int UPI_PAYMENT = 0;
    //ProgressDialog progressDialog;


    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGooglePayPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }


        //main data


        send = findViewById(R.id.btnSendUpip);
        note = findViewById(R.id.etNotep);
        name = findViewById(R.id.etName);
        amount = findViewById(R.id.etAmountp);
        upivirtualId = findViewById(R.id.etUpiIdp);

    root.child("payment").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        paymentModel model = snapshot.getValue(paymentModel.class);

                        try {
                            System.out.println(model.getPaymentMessage());
                          Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                          intent.putExtra("from_payment", true);
                          startActivity(intent);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            finish();


                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(name.getText().toString().trim())) {


                } else if (TextUtils.isEmpty(upivirtualId.getText().toString().trim())) {

                } else if (TextUtils.isEmpty(note.getText().toString())) {

                } else if (TextUtils.isEmpty(amount.getText().toString().trim())) {

                } else {
                    payUsingUpi("Dr.Kundan Madne", "kbxkundan9ru51503@yesbank",
                            note.getText().toString(), amount.getText().toString());

                }

            }
        });

    }

    private void payUsingUpi(String name, String upiId, String note, String amount) {
        Log.e("main", "name" + name + "--up--" + upiId + "--" + note + "--" + amount);

        //main :name Dr. kundan madne n--up--kbxkundan9ru51503@yesbank-Text Upi payment--2000.00

        Uri uri
                = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tr", "261433")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main", "response " + resultCode);

        switch (requestCode) {

            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {

                    if (data != null) {

                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> datalist = new ArrayList<>();
                        datalist.add(trxt);
                        upiPaymentDataOperation(datalist);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> datalist = new ArrayList<>();
                        datalist.add("nothing");
                        upiPaymentDataOperation(datalist);
                    }
                } else {

                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> datalist = new ArrayList<>();
                    datalist.add("nothing");
                    upiPaymentDataOperation(datalist);

                }
                break;
        }

    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(GooglePayPayment.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";

                }
            }

            if (status.equals("success")) {




                String msg = "payment Successful";

                paymentModel model = new paymentModel(msg);
                root.child("payment").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                        .setValue(model);

                Intent intent = new Intent(GooglePayPayment.this, MainActivity.class);
                intent.putExtra("from_payment", true);
                startActivity(intent);


                Log.e("UPI", "payment successful: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            } else {
                Log.e("UPI", "failed payment: " + approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");

        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected() && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}



