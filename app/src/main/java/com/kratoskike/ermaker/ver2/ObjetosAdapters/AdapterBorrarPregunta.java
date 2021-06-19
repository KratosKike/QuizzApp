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

public class AdapterBorrarPregunta extends FirestoreRecyclerAdapter<Pregunta,AdapterBorrarPregunta.PreguntaHolder> {

    private AdapterBorrarPregunta.OnItemClickListener listener;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterBorrarPregunta(@NonNull @NotNull FirestoreRecyclerOptions<Pregunta> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull @NotNull AdapterBorrarPregunta.PreguntaHolder holder, int position, @NonNull @NotNull Pregunta model) {

        holder.pregunta.setText(model.getPregunta());
        holder.id.setText(model.getCorrecta());


    }

    @NonNull
    @NotNull
    @Override
    public PreguntaHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_1listapregunta,parent,false);

        return new PreguntaHolder(v);
    }

    public class PreguntaHolder extends RecyclerView.ViewHolder {

        TextView pregunta;
        TextView id;


        public PreguntaHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pregunta = itemView.findViewById(R.id.tv_lista_pregunta);
            id = itemView.findViewById(R.id.tv_lista_id);

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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }




}
