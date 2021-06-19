package com.kratoskike.ermaker.ver2.ObjetosAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ermaker.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;



import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterAgregarAmigo extends FirestoreRecyclerAdapter<Perfil,AdapterAgregarAmigo.PerfilHolder> {

    private OnItemClickListener listener;



    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterAgregarAmigo(@NonNull @NotNull FirestoreRecyclerOptions<Perfil> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull PerfilHolder holder, final int position, @NonNull @NotNull Perfil model) {

        //obtencion de datos
        String email = model.getEmail();
        holder.tVemail.setText(model.getEmail());
        holder.tVNick.setText(model.getNick());

        System.out.println("Probando "+holder.tVemail.getText());



    }

    @NonNull
    @NotNull
    @Override
    public PerfilHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_1listaagregaramigo,parent,false);

        return new PerfilHolder(v);
    }

    class PerfilHolder extends RecyclerView.ViewHolder{

        //Establecer datos que se ven

        TextView tVemail;
        TextView tVNick;
        Button solicitud;


        public PerfilHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            //enlazar datos
            tVemail = itemView.findViewById(R.id.tv_laa_email);
            tVNick = itemView.findViewById(R.id.tv_laa_nick);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION && listener !=null){

                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });






            //solicitud = itemView.findViewById(R.id.btn_laa_enviarsolicitud);

            /*itemView.findViewById(R.id.btn_laa_enviarsolicitud).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Has pulsado para enviar solicitud a "+tVemail.getText());

                    enviarsolicitud((String) tVemail.getText(),(String)tVNick.getText());

                }
            });*/
            //comprobar amigos
           /* FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String emailUsuario = user.getEmail();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference userRef = db.collection("Usuarios");*/



            //System.out.println("Probando "+tVemail.getText());




           // itemView.findViewById(R.id.btn_laa_enviarsolicitud).setEnabled(false);


        }
    }

    private void enviarsolicitud(String email, String nick) {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String emailUsuario = user.getEmail();


        //crear solicitud


        /*
        Map<String, Object> data = new HashMap<>();
        data.put("emailUsuario",emailUsuario);
        data.put("emailSolicitud",email);
        //data.put("nick",nick);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Solicitudes")
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {

                    }
                });
*/
        /*Map<String, Object> data = new HashMap<>();
        data.put("emailSolicitud",email);
        data.put("enviado","true");
        //crear solicitud envio usuario*/

       /* System.out.println("Email usuario: "+emailUsuario);
        System.out.println("Email solicitud: "+email);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Usuarios").document(emailUsuario);
        userRef.update("Enviada", FieldValue.arrayUnion(email)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                System.out.println("Exito");
            }
        });*/
        /*db.collection("Usuarios").document(emailUsuario).collection("Solicitudes")
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {

                    }
                });*/

       /* Map<String, Object> data2 = new HashMap<>();
        data2.put("emailRecibido",emailUsuario);
        data2.put("recibido","true");*/
        //crear solicitud envio usuario
        //FirebaseFirestore db2 = FirebaseFirestore.getInstance();
       /* DocumentReference userRef2 = db.collection("Usuarios").document(email);
        userRef2.update("Recibida", FieldValue.arrayUnion(emailUsuario));*/
        /*db2.collection("Usuarios").document(email).collection("Solicitudes")
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {

                    }
                });*/



    }

    public interface OnItemClickListener{

        //Cambiar aqui si se va a pedir mas datos

        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }

    public void setOnItemClickListener(AdapterAgregarAmigo.OnItemClickListener listener){
        this.listener= listener;

    }



}
