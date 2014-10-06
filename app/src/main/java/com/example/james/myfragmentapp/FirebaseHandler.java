package com.example.james.myfragmentapp;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by james on 9/29/14.
 */
public class FirebaseHandler {

    final Firebase myFirebaseRef;
    public FirebaseHandler(String link){
        myFirebaseRef = new Firebase(link);
    }


    public void addChatToFirebase(Chat chat) {


        myFirebaseRef.push().setValue(chat, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });
    }


}

