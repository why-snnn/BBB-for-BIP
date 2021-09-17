package com.example.bbb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Message> messagesList = new ArrayList<>();
    private Context context;

    public Adapter(ArrayList<Message> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public  TextView usernameText;
        public  TextView threadText;
        public  TextView contentText;
        public  ImageView image;
        public  Button bipButton;
        public  Button bopButton;

        public ViewHolder(View view) {
            super(view);

            usernameText = view.findViewById(R.id.authorUsernameView);
            threadText = view.findViewById(R.id.threadView);
            contentText = view.findViewById(R.id.contentView);
            //image = view.findViewById(R.id.imageAction);
            bipButton = view.findViewById(R.id.bipButton);
            bopButton = view.findViewById(R.id.bopButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(this.context)
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    // сопоставление меток с данными: меткам из viewHolder ставится значение данных с индексом position
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Message message = messagesList.get(position);
        viewHolder.usernameText.setText("@" + message.getUserName());
        viewHolder.threadText.setText("/" + message.getThread() + "/");
        viewHolder.contentText.setText(message.getContent());

        // TODO сделать картинку
        //viewHolder.image .contentText.setText(message.getContent());

        viewHolder.bipButton.setText(Integer.toString(message.getLikes()) + " БИП" );
        viewHolder.bopButton.setText("БОП");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}
