package com.mgUnicorn.eh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mgUnicorn.eh.databinding.ActivityPaymentpatientBinding;

import java.util.HashMap;

public class paymentpatientActivity extends AppCompatActivity {



    FirebaseDatabase database;
    ActivityPaymentpatientBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPaymentpatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();


        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(binding.etTotalPayment.getText().toString().isEmpty()) {
                    binding.etTotalPayment.setError("Please fill up message");
                    return;
                }

                 if(binding.etUnpaid.getText().toString().isEmpty()) {
                    binding.etUnpaid.setError("Please fill up message");
                    return;
                }


                 if(binding.etPaid.getText().toString().isEmpty()) {
                    binding.etPaid.setError("Please fill up message");
                    return;
                }



                //send data to firebasefor unpaid payment

                String unpaid=binding.etUnpaid.getText().toString();
                String tpayment=binding.etTotalPayment.getText().toString();
                String etpaid=binding.etPaid.getText().toString();
                HashMap<String ,Object> obj=new HashMap<>();
                obj.put("unpaid",unpaid);
                obj.put("TotalPaid",tpayment);
                obj.put("paid",etpaid);


                database.getReference().child("TotalPayment").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);


            }
        });
    }
}