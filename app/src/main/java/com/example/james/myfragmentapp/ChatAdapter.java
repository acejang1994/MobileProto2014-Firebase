package com.example.james.myfragmentapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

/**
 * Created by james on 9/11/14.
 */
public class ChatAdapter extends ArrayAdapter<Chat>{
    private List<Chat> chats = new ArrayList<Chat>();
    private int resource;
    private Context context;


    public ChatAdapter(Context context, int resource, List<Chat> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.chats = objects;

    }

    private class ChatHolder {
        TextView name, body, time;
        ImageView picture;
        View background;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        ChatHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resource, parent, false);
        holder = new ChatHolder();

        //TextViews
        holder.name = (TextView) view.findViewById(R.id.item_profile_name);
        holder.body = (TextView) view.findViewById(R.id.item_chat_body);
        holder.time = (TextView) view.findViewById(R.id.item_chat_time);

        //ImageViews
        holder.background = view.findViewById(R.id.item_profile_background);
        holder.picture = (ImageView) view.findViewById(R.id.item_profile_picture);
        fillViews(holder, chats.get(position));
        return view;
    }
    @Override
    public int getCount(){
        return this.chats.size();
    }

    @Override
    public Chat getItem(int position) {
        return this.chats.get(position);
    }

    private void fillViews(ChatHolder holder, Chat chat){
        holder.name.setText(chat.getName());
        holder.body.setText(chat.getMessage());
        holder.time.setText(String.valueOf(chat.getTime()));

        //holder.picture.setImageDrawable(getProfileDrawable(chat.userId));
    }


    public void addChat(Chat chat){
        this.chats.add(chat);
        notifyDataSetChanged();
    }
}
