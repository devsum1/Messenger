package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private ListView usersScreen;
    private DatabaseReference db;
    private ArrayList<String> users;
    private ArrayAdapter<String> usersAdapter;
    private Button checkButton;
    private ArrayList<String> userId;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);

        users= new ArrayList<String>();
        userId= new ArrayList<String>();
        usersAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,users);
        db = FirebaseDatabase.getInstance().getReference("users");
        usersScreen =(ListView)findViewById(R.id.usersList);
        usersScreen.setAdapter(usersAdapter);

        //Set onclick listener on listview and fire chatScreen
        usersScreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent fire = new Intent(getApplicationContext(), ChatActivity.class);
                Log.w("receiver",userId.get(i));
                fire.putExtra("receiverId",userId.get(i));
                fire.putExtra("receiverName",users.get(i));
                startActivity(fire);
            }
        });
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User newUser = dataSnapshot.getValue(User.class);
                userId.add(dataSnapshot.getKey());

                users.add(newUser.name);
                usersAdapter.notifyDataSetChanged();
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

    }



}
