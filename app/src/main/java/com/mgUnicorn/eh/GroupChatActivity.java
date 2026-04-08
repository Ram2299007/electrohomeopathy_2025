package com.mgUnicorn.eh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mgUnicorn.eh.Adapter.chatAdapter;
import com.mgUnicorn.eh.databinding.ActivityGroupChatBinding;
import com.mgUnicorn.eh.models.MessageModel;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ActivityGroupChatBinding binding;
    EditText etAge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView=findViewById(R.id.chatsRecyclerview);







        getSupportActionBar().hide();
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //very important intent all time
                Intent intent=new Intent(GroupChatActivity.this, MainActivity.class);
                GroupChatActivity.this.overridePendingTransition(0,0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });



        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        ArrayList<MessageModel> messageModels=new ArrayList<>();

        final String senderId= FirebaseAuth.getInstance().getUid();
        binding.userName.setText("Doctor's Chat");

        final chatAdapter  adapter=new chatAdapter(messageModels,this);
        binding.chatsRecyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatsRecyclerview.setLayoutManager(layoutManager);


        database.getReference().child("Group Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        try {
                            messageModels.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                MessageModel model = dataSnapshot.getValue(MessageModel.class);
                                messageModels.add(model);
                            }


                            adapter.notifyDataSetChanged();
                        }catch (Exception e){}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etMessage.getText().toString().isEmpty()) {
                    binding.etMessage.setError("Please fill up message");
                    return;
                }

                final String message=binding.etMessage.getText().toString();
                final  MessageModel model=new MessageModel(senderId,message);
                model.setTimeStamp(new Date().getTime());
                binding.etMessage.setText("");


                //recycler view for auto scroll up
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    }
                });


                final MediaPlayer mp= MediaPlayer.create(GroupChatActivity.this,R.raw.hollow);
                mp.start();
                database.getReference().child("Group Chat")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });


            }
        });

    }
}