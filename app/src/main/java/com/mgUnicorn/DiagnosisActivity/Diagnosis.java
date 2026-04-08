package com.mgUnicorn.DiagnosisActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.mgUnicorn.eh.Fragments.statusFragment;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.databinding.ActivityDiagnosisBinding;

import java.util.HashMap;

public class Diagnosis extends AppCompatActivity {


    ActivityDiagnosisBinding binding;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDiagnosisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();
        binding.btnPinttoCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(binding.etPresentComplaints.getText().toString().isEmpty()) {
                    binding.etPresentComplaints.setError("Please fill up message");
                    return;
                }
                  if(binding.etDiagnosis.getText().toString().isEmpty()) {
                    binding.etDiagnosis.setError("Please fill up message");
                    return;
                }
                   if(binding.etInvestigation.getText().toString().isEmpty()) {
                    binding.etInvestigation.setError("Please fill up message");
                    return;
                }
                  if(binding.etTreatment.getText().toString().isEmpty()) {
                    binding.etTreatment.setError("Please fill up message");
                    return;
                }


                String PresentComplaints=binding.etPresentComplaints.getText().toString();
                String diagnosis=binding.etDiagnosis.getText().toString();
                String Investigation=binding.etInvestigation.getText().toString();
                String Treatment=binding.etTreatment.getText().toString();


                HashMap<String ,Object> obj=new HashMap<>();
                obj.put("Present_Complaints",PresentComplaints);
                obj.put("Diagnosis",diagnosis);
                obj.put("Investigation",Investigation);
                obj.put("Treatment",Treatment);


                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);


                // Normal activity to Fragment activity
                binding.btnPinttoCase.setVisibility(View.GONE);
                binding.txtdiscovery.setVisibility(View.GONE);
                binding.txtpresentcomplants.setVisibility(View.GONE);
                binding.etPresentComplaints.setVisibility(View.GONE);
                binding.txtdiagnosis.setVisibility(View.GONE);
                binding.etDiagnosis.setVisibility(View.GONE);
                binding.txtinestigation.setVisibility(View.GONE);
                binding.etInvestigation.setVisibility(View.GONE);
                binding.txtxTreatment.setVisibility(View.GONE);
                binding.etTreatment.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.diagnosisId,new statusFragment()).commit();



            }


        });

    }
}