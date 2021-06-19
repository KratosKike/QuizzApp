package com.kratoskike.ermaker.ver2.ObjetosAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ermaker.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class AdapterChatFirestore extends FirestoreRecyclerAdapter<Chat, AdapterChatFirestore.ChatHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterChatFirestore(@NonNull @NotNull FirestoreRecyclerOptions<Chat> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ChatHolder holder, int position, @NonNull @NotNull Chat model) {

        holder.username.setText(model.getUser());
        holder.message.setText(model.getMesagge());
        holder.text_time.setText(model.getFecha());
    }

    @NonNull
    @NotNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_1listachat,parent,false);


        return new ChatHolder(v);
    }

    public class ChatHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView message;
        TextView text_time;


        public ChatHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            username =itemView.findViewById(R.id.tv_chatUser);
            message =itemView.findViewById(R.id.tv_chatMes);
            text_time =itemView.findViewById(R.id.tv_chatFecha);


        }
    }
}
