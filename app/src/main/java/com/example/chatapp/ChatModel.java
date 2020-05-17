package com.example.chatapp;

public class ChatModel {
    public String sender;
    public String receiver;
    public String message;

    ChatModel(){

    }

    public ChatModel(String sender,String receiver,String message){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

}
