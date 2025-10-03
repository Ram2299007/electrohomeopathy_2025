package com.mgUnicorn.eh;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mgUnicorn.eh.R;
import com.mgUnicorn.eh.models.patientModel;

public class FirebaseDebugActivity extends AppCompatActivity {

    private static final String TAG = "FirebaseDebug";
    private EditText etName, etNumber;
    private Button btnWrite, btnRead;
    private TextView tvResults;
    private FirebaseDatabase database;
    private DatabaseReference patientRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_debug);

        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        tvResults = findViewById(R.id.tvResults);

        database = FirebaseDatabase.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getUid();
        
        if (currentUserId != null) {
            patientRef = database.getReference().child("patient").child(currentUserId);
            Log.d(TAG, "Database reference created for user: " + currentUserId);
        } else {
            Log.e(TAG, "User not authenticated!");
            return;
        }

        btnWrite.setOnClickListener(v -> writeTestData());
        btnRead.setOnClickListener(v -> readTestData());
    }

    private void writeTestData() {
        String name = etName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();

        if (name.isEmpty() || number.isEmpty()) {
            return;
        }

        patientModel testPatient = new patientModel("https://example.com/image.jpg", name, number, "2024-01-01");
        
        patientRef.push().setValue(testPatient, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    Log.e(TAG, "Error writing data: " + error.getMessage());
                } else {
                    Log.d(TAG, "Data written successfully");
                }
            }
        });
    }

    private void readTestData() {
        tvResults.setText("Reading data...");
        
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Data change detected. Children count: " + dataSnapshot.getChildrenCount());
                
                StringBuilder result = new StringBuilder();
                result.append("Found ").append(dataSnapshot.getChildrenCount()).append(" patients:\n\n");
                
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    try {
                        patientModel patient = child.getValue(patientModel.class);
                        if (patient != null) {
                            result.append("Name: ").append(patient.getName())
                                  .append("\nNumber: ").append(patient.getNumber())
                                  .append("\nDate: ").append(patient.getDate())
                                  .append("\nKey: ").append(child.getKey())
                                  .append("\n---\n");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing patient data: " + e.getMessage());
                        result.append("Error parsing data: ").append(e.getMessage()).append("\n");
                    }
                }
                
                tvResults.setText(result.toString());
                Log.d(TAG, "Data read completed");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Error reading data: " + error.getMessage());
                tvResults.setText("Error: " + error.getMessage());
            }
        });
    }
}
