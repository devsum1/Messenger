package com.example.chatapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<String> {
    private  String currUser =  FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Activity context;
    private ArrayList<ChatModel> chatUser;
    private ArrayList<String> messages;


    public  ChatAdapter(Activity context,@NonNull ArrayList<ChatModel> chatUser,ArrayList<String> messages){
        super(context, R.layout.messages_layout, messages);
        this.context = context;
        this.chatUser = chatUser;
        this.messages = messages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater chatInflater = LayoutInflater.from(getContext());
        View chatMsgView = chatInflater.inflate(R.layout.messages_layout,parent,false);
        LinearLayout chatSection =(LinearLayout) chatMsgView.findViewById(R.id.chatmsgSection);
        TextView showmsg = (TextView) chatMsgView.findViewById(R.id.senderMsg);


        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(15);

         Log.w("currsmsg",chatUser.get(position).sender);
        Log.w("currsmsg",currUser);

        if(chatUser.get(position).sender.equals(currUser)){
            showmsg.setText(chatUser.get(position).message);
            showmsg.setTextColor(Color.BLACK);
            shape.setColor(Color.LTGRAY);
            showmsg.setBackground(shape);
        }else{
            chatSection.setGravity(Gravity.END);
            showmsg.setTextColor(Color.WHITE);
            shape.setColor(Color.BLACK);
            showmsg.setBackgroundColor(Color.BLACK);
            showmsg.setText(chatUser.get(position).message);
            showmsg.setBackground(shape);
        }


        return chatMsgView;
    }
}
