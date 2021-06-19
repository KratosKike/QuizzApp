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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AdapterPuntuacion extends FirestoreRecyclerAdapter<Puntuacion, AdapterPuntuacion.PuntuacionHolder> {

    private OnItemClickListener listener;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterPuntuacion(@NonNull @NotNull FirestoreRecyclerOptions<Puntuacion> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull final PuntuacionHolder holder, int position, @NonNull @NotNull Puntuacion model) {

        //obtencion de datos

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        System.out.println("email adapterPuntuacion "+model.email);
        DocumentReference docRef = db.collection("Usuarios").document(model.email);

        //final String[] nick = new String[1];
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Perfil perfil = documentSnapshot.toObject(Perfil.class);
                System.out.println(perfil.getNick());
                holder.tVNick.setText(perfil.getNick());
                //nick[0] =perfil.getNick();
            }
        });


        //holder.tVNick.setText(nick[0]);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy kk:mm", Locale.getDefault());



        holder.tVPunt.setText(String.valueOf(model.getPuntuacion()));
        holder.tVFecha.setText(df2.format(model.getFecha().toDate()));


    }

    @NonNull
    @Override
    public PuntuacionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_1listapunt,parent,false);

        return new PuntuacionHolder(v);
    }

    class PuntuacionHolder extends RecyclerView.ViewHolder {


        //Establecer datos que se ven

        TextView tVPunt;
        TextView tVNick;
        TextView tVFecha;


        public PuntuacionHolder(@NonNull View itemView) {
            super(itemView);

            //enlazar datos
            tVPunt = itemView.findViewById(R.id.tv_hold_punt);
            tVNick = itemView.findViewById(R.id.tv_hold_nick);
            tVFecha = itemView.findViewById(R.id.tv_listapunt_fecha);

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

    public void setOnItemClickListener(AdapterPuntuacion.OnItemClickListener listener){
        this.listener = listener;

    }



}
