package com.example.james.myfragmentapp;

import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by james on 9/29/14.
 */
public class FirebaseHandler {

    final Firebase myFirebaseRef;
    public FirebaseHandler(String link){
        myFirebaseRef = new Firebase(link);
    }

    public void addChatToFirebase(Chat chat){


    }
}

