package com.example.james.myfragmentapp;

/**
 * Created by james on 9/21/14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by chris on 12/23/13.
 */
public class HandlerDatabase {
    //Database Model
    private ModelDatabase model;

    //Database
    private SQLiteDatabase database;

    //All Fields
    private String[] allColumns = {
            ModelDatabase.CHAT_ID,
            ModelDatabase.CHAT_NAME,
            ModelDatabase.CHAT_MESSAGE,
            ModelDatabase.CHAT_TIME,
    };

    //Public Constructor - create connection to Database
    public HandlerDatabase(Context context){

        model = new ModelDatabase(context);
    }

    /**
     * Add
     */
    public void addChatToDatabase(Chat chat){
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.CHAT_ID, chat.getId());
        values.put(ModelDatabase.CHAT_NAME, chat.getName());
        values.put(ModelDatabase.CHAT_MESSAGE, chat.getMessage());
        values.put(ModelDatabase.CHAT_TIME, chat.getTime());
        database.insertWithOnConflict(ModelDatabase.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public void updateChat(Chat chat){
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.CHAT_ID, chat.getId());
        values.put(ModelDatabase.CHAT_NAME, chat.getName());
        values.put(ModelDatabase.CHAT_MESSAGE, chat.getMessage());
        values.put(ModelDatabase.CHAT_TIME, chat.getTime());
        database.update(ModelDatabase.TABLE_NAME, values, ModelDatabase.CHAT_ID + " like '%'", null);
    }

    /**
     * Get
     */
    public ArrayList<Chat> getAllChats(){
        return sweepCursor(database.query(ModelDatabase.TABLE_NAME, allColumns, null, null, null, null, null));
    }
//    public ArrayList<Chat> getKittiesByCategory(String cat){
//        return sweepCursor(database.query(
//                ModelDatabase.TABLE_NAME,
//                allColumns,
//                ModelDatabase.KITTY_CATEGORY + " like '%" + cat + "%' AND " + ModelDatabase.KITTY_FAVORITE + " like '%false%' AND " + ModelDatabase.KITTY_VISIBLE + " like '%true%'",
//                null, null, null, null, null
//        ));
//    }
//    public ArrayList<Chat> getOwnedKitties(){
//        return sweepCursor(database.query(
//                ModelDatabase.TABLE_NAME,
//                allColumns,
//                ModelDatabase.KITTY_FAVORITE + " like '%true%' AND " + ModelDatabase.KITTY_VISIBLE + " like '%true%'",
//                null, null, null,
//                ModelDatabase.KITTY_CATEGORY));
//    }
//    public Chat getChatById(String id){
//        return sweepCursor(database.query(
//                ModelDatabase.TABLE_NAME,
//                allColumns,
//                ModelDatabase.KITTY_URL + " like '%" + id + "%'",
//                null, null, null, null
//        )).get(0);
//    }

    /**
     * Delete
     */
//    public void deleteKittiesByCategory(String cat){
//        database.delete(
//                ModelDatabase.TABLE_NAME,
//                ModelDatabase.KITTY_CATEGORY + " like '%" + cat + "%' AND " + ModelDatabase.KITTY_FAVORITE + " like '%false%'",
//                null
//        );
//    }
    public void deleteChatById(String id){
        database.delete(
                ModelDatabase.TABLE_NAME,
                ModelDatabase.CHAT_ID + " like '%" + id + "%'",
                null
        );
    }

    /**
     * Additional Helpers
     */
    //Sweep Through Cursor and return a List of Kitties
    private ArrayList<Chat> sweepCursor(Cursor cursor){
        ArrayList<Chat> chats = new ArrayList<Chat>();

        //Get to the beginning of the cursor
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Get the Chat
            Chat chat = new Chat(
                    cursor.getString(1),
                    cursor.getString(2)
            );
            //Add the Chat
            chats.add(chat);
            //Go on to the next Kitty
            cursor.moveToNext();
        }
        return chats;
    }

    //Get Writable Database - open the database
    public void open(){
        database = model.getWritableDatabase();
    }
}