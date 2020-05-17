package com.example.chatapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

   private ImageView editStatus;
   private ImageView editPhone;
   private AlertDialog pop;
   private EditText userChange;
   private String currChange;
   private  String performAction;
   private TextView userStatus;
   private Button follow;
   private TextView userContact;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);

        editPhone = (ImageView) findViewById(R.id.editPhone);
        editStatus = (ImageView) findViewById(R.id.editStatus);
        follow = (Button) findViewById(R.id.followBtn);

        editPhone.setClickable(true);
        editStatus.setClickable(true);
        Toast.makeText(getApplicationContext(),"icon clicked",Toast.LENGTH_LONG).show();

        pop = new AlertDialog.Builder(this).create();
        userChange = new EditText(this);

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Button clicked",Toast.LENGTH_LONG).show();
                changeUserValue("contact");
            }
        });
        editStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserValue("status");

            }
        });
        editPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserValue("contact");

            }
        });

        pop.setButton(DialogInterface.BUTTON_POSITIVE, performAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.w("Popup",performAction);
                if(currChange.equals("contact"))
                    userContact.setText(userChange.getText().toString());
                else
                    userContact.setText(userStatus.getText().toString());

            }
        });
    }

    public  void changeUserValue(String action){
        Log.w("Click",action);

        if(action.equals("contact"))
            pop.setTitle("Edit the status");
        else
            pop.setTitle("Edit Contact Number");

        performAction = action;
        currChange = action;
        pop.setView(userChange);
        pop.show();
    }




}
