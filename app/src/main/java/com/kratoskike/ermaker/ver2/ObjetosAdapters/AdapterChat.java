package com.kratoskike.ermaker.ver2.ObjetosAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ermaker.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class AdapterChat extends ArrayAdapter<Chat> {


    public AdapterChat(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NotNull
    @Override

    public View getView(int position, View convertView, @NotNull ViewGroup parent){
        final ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.activity_1listachat, parent, false);

        }

        TextView username = convertView.findViewById(R.id.tv_chatUser);
        TextView message = convertView.findViewById(R.id.tv_chatMes);
        TextView text_time = convertView.findViewById(R.id.tv_chatFecha);

        Chat chat = getItem(position);

        username.setText(chat.getUser());
        message.setText(chat.getMesagge());
        text_time.setText(chat.getFecha());


        return convertView;
    }

    private static class ViewHolder{
        TextView username;
        TextView message;
        TextView text_time;
    }

}
