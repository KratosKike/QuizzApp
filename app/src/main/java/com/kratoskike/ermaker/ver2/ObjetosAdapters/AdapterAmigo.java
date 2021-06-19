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
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class AdapterAmigo extends FirestoreRecyclerAdapter<Perfil, AdapterAmigo.AmigoHolder> {


    private AdapterAmigo.OnItemClickListener listener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterAmigo(@NonNull @NotNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull AmigoHolder holder, int position, @NonNull @NotNull Perfil model) {


        //obtencion de datos
        holder.tVNick.setText(model.getNick());
        holder.tVfecha.setText(model.getEmail());

    }

    @NonNull
    @NotNull
    @Override
    public AmigoHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_1listaamigos,parent,false);

        return new AmigoHolder(v);
    }




    class AmigoHolder extends RecyclerView.ViewHolder{

        //Establecer datos que se ven

        TextView tVfecha;
        TextView tVNick;


        public AmigoHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //enlazar datos
            tVfecha = itemView.findViewById(R.id.tv_afecha);
            tVNick = itemView.findViewById(R.id.tv_anick);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION && listener !=null){

                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });


        }
    }
    public interface OnItemClickListener{

        //Cambiar aqui si se va a pedir mas datos

        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }

    public void setOnItemClickListener(AdapterAmigo.OnItemClickListener listener){
        this.listener = listener;

    }



}
