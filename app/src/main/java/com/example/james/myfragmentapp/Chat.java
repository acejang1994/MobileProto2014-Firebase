package com.example.james.myfragmentapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by james on 9/21/14.
 */
public class Chat {

    private String id, name, message;
    private long date;

    public Chat(String name, String message){
        this.name = name;
        this.message = message;
        this.date = System.currentTimeMillis();

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        SimpleDateFormat adc = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        Date resultDate = new Date(date);
        return adc.format(resultDate);
    }

    public String getMessage() {
        return message;
    }

    public void setId(String newId){
        this.id = newId;
    }

    public void setMessage(String newMessage){
        this.message = newMessage;
    }


}
