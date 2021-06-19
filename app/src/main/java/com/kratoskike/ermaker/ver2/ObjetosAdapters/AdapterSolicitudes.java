package com.kratoskike.ermaker.ver2.ObjetosAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ermaker.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//public class AdapterSolicitudes extends FirestoreRecyclerAdapter<Solicitud, AdapterSolicitudes.SolicitudHolder> {
public class AdapterSolicitudes extends FirestoreRecyclerAdapter<Perfil, com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterSolicitudes.SolicitudHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    //public AdapterSolicitudes(@NonNull @NotNull FirestoreRecyclerOptions<Solicitud> options) {
    public AdapterSolicitudes(@NonNull @NotNull FirestoreRecyclerOptions<Perfil> options) {
        super(options);
    }

    @Override
    //protected void onBindViewHolder(@NonNull @NotNull SolicitudHolder holder, int position, @NonNull @NotNull Solicitud model) {
    protected void onBindViewHolder(@NonNull @NotNull SolicitudHolder holder, int position, @NonNull @NotNull Perfil model) {

        holder.tvemail.setText(model.getEmail());
        holder.tvnick.setText(model.getNick());


    }

    @NonNull
    @NotNull
    @Override
    public SolicitudHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_1listasolicitudes,parent,false);

        return new SolicitudHolder(v);
    }

    public class SolicitudHolder extends RecyclerView.ViewHolder {

        TextView tvnick;
        TextView tvemail;
        Button aceptar;


        public SolicitudHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvnick = itemView.findViewById(R.id.tV_nicksol);
            tvemail = itemView.findViewById(R.id.tV_emailsol);

            itemView.findViewById(R.id.btn_aceptarSol).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aceptarSolicitud(tvnick,tvemail);
                }
            });

            itemView.findViewById(R.id.btn_rechazar_sol).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Funciona boton rechazar");
                    rechazarSolicitud(tvemail);
                }
            });


        }
    }

    private void rechazarSolicitud(TextView tvemail) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String emailUsuario = user.getEmail();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Usuarios").document(emailUsuario);
        DocumentReference userRef2 = db.collection("Usuarios").document(String.valueOf(tvemail.getText()));


        //limpiar solicitudes
        userRef.update("Enviada", FieldValue.arrayRemove(tvemail.getText()));
        userRef.update("Recibida", FieldValue.arrayRemove(tvemail.getText()));

        userRef2.update("Recibida", FieldValue.arrayRemove(emailUsuario));
        userRef2.update("Enviada", FieldValue.arrayRemove(emailUsuario));

    }

    private void aceptarSolicitud(TextView nick, TextView email) {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String emailUsuario = user.getEmail();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


        //Crear array amigo en los 2
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Usuarios").document(emailUsuario);
        userRef.update("amigos", FieldValue.arrayUnion(email.getText()));


        DocumentReference userRef2 = db.collection("Usuarios").document(String.valueOf(email.getText()));

        userRef2.update("amigos", FieldValue.arrayUnion(emailUsuario));

        //limpiar solicitudes
        userRef.update("Enviada", FieldValue.arrayRemove(email.getText()));
        userRef.update("Recibida", FieldValue.arrayRemove(email.getText()));

        userRef2.update("Recibida", FieldValue.arrayRemove(emailUsuario));
        userRef2.update("Enviada", FieldValue.arrayRemove(emailUsuario));





    }
}
