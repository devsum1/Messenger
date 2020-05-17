package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    private EditText messageContainer;
    private Button sendBtn;
    private DatabaseReference db;
    private String senderId;
    private String receiverId,receiverName;
    private String connectionKey;
    private HashMap<String,String> idmsg;
    private  String message;
    private ListView chatList;
    private  ArrayList<String>chats;
    private ArrayList<ChatModel> chatUsers;
    private ArrayAdapter<String>chatAdapter;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen);

        Intent intent  = getIntent();
        receiverName = intent.getStringExtra("receiverName");

        setTitle("  "+receiverName);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);


        idmsg = new HashMap<String, String>();
        chats= new ArrayList<String>();
        chatUsers= new ArrayList<ChatModel>();
        //Custom chatAdapter used
        chatAdapter= new ChatAdapter(this,chatUsers,chats);
        db = FirebaseDatabase.getInstance().getReference("chats");
        chatList =(ListView)findViewById(R.id.chatList);
        chatList.setAdapter(chatAdapter);

        messageContainer = (EditText) findViewById(R.id.messageInput);

        sendBtn = (Button) findViewById(R.id.sendButton);

        senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        receiverId = intent.getStringExtra("receiverId");

        if(senderId.compareTo(receiverId)> 0)
            connectionKey = senderId + receiverId;
        else
            connectionKey = receiverId + senderId;

        db.child(connectionKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatModel msgstatus = dataSnapshot.getValue(ChatModel.class);
                String key = dataSnapshot.getKey();
                if(!idmsg.containsKey(key)){
                    Log.w("newkey",key);
                    idmsg.put(key, msgstatus.message);
                    chats.add(msgstatus.message);
                    chatUsers.add(msgstatus);
                    chatAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("send",senderId);
                Log.w("receive",receiverId);
                //Making connection key for chat db

                Log.w("key",connectionKey);
                message = messageContainer.getText().toString();
                Log.w("send",message);
                final ChatModel chat  = new ChatModel(senderId, receiverId,message);
                db.child(connectionKey).push().setValue(chat);
                messageContainer.setText("");


            }


        });


    }
}
