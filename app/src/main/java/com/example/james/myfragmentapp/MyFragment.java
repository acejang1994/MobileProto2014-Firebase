package com.example.james.myfragmentapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by james on 9/11/14.
 */
public class MyFragment extends Fragment{

    public ChatAdapter adapter;
    public ListView myListView;
    Context context;
    String userName = "James";
    public ArrayList<Chat> listChats;


    public MyFragment()  {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        final HandlerDatabase handler = new HandlerDatabase(context);
        handler.open();

        final FirebaseHandler handler1 = new FirebaseHandler("https://brilliant-torch-5491.firebaseio.com/");

        final Firebase myFirebaseRef = new Firebase("https://brilliant-torch-5491.firebaseio.com/");

//        final Firebase myFirebaseRef = ref.child("listChats");
        listChats = new ArrayList<Chat>();
        myListView = (ListView) rootView.findViewById(R.id.my_list_view);

//        final ArrayList<Chat> listChats = handler.getAllChats();


        loadDatabaseToChatAdapter();

        final EditText editText = (EditText)rootView.findViewById(R.id.my_edittext);


//        myFirebaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot child:snapshot.getChildren()){
//                    Chat chat = new Chat(child.child("message").getValue().toString(),child.child("name").getValue().toString());
//                    if (listChats.size() != 0){
//                        for (Chat c : listChats)
//                            if (c.getMessage() == chat.getMessage() && c.getId() == chat.getId()) {
//                            } else {
//                                listChats.add(chat);
//                            }
//                    }
//                }
//            }
//            @Override public void onCancelled(FirebaseError error) { }
//        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Delete or Edit Chat")
                        .setMessage("Delete this Entry or Edit?");

                final EditText edit = new EditText(context);
                alert.setView(edit);
                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String change = edit.getText().toString();

                        Chat chat = adapter.getItem(i);
                        chat.setMessage(change);

                        handler.addChatToDatabase(chat);
                        adapter.notifyDataSetChanged();

                    }
                })
                        .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                Chat chat = adapter.getItem(i);
                                String id = chat.getId();
                                handler.deleteChatById(id);
                                listChats.remove(i);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        Button myButton = (Button)rootView.findViewById(R.id.my_button);

        Button changeUser = (Button)rootView.findViewById(R.id.user_button);
        changeUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setMessage("Change Username");

                final EditText edit = new EditText(context);
                alert.setView(edit);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String change = edit.getText().toString();
                        userName = change;

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editText.getText().toString();

                Chat chat = new Chat(userName, message);
                handler.addChatToDatabase(chat);

//                listChats.add(chat);
                editText.getText().clear();

//                myFirebaseRef.push().setValue(chat);
                handler1.addChatToFirebase(chat);

                Log.i("does this run", "this");
                adapter.notifyDataSetChanged();
//                Log.i("debug","button");
             }
        });

        return rootView;
    }
    public void loadDatabaseToChatAdapter() {
        FirebaseHandler handler = new FirebaseHandler("https://brilliant-torch-5491.firebaseio.com/");
        handler.myFirebaseRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                Chat newChat = new Chat(newPost.get("name").toString(),newPost.get("message").toString());
                listChats.add(newChat);
                adapter = new ChatAdapter(getActivity(), R.layout.chat_item, listChats);
                myListView.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
                String title = (String) snapshot.child("title").getValue();
                System.out.println("The updated post title is " + title);
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                String title = (String) snapshot.child("title").getValue();
                System.out.println("The blog post titled " + title + " has been deleted");
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String f) {
                String title = (String) snapshot.child("title").getValue();
                System.out.println("The blog post titled " + title + " has been deleted");
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println(error.getMessage());
            }
        });

    }

}
