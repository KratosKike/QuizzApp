package com.kratoskike.ermaker.ver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ermaker.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterAgregarAmigo;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Perfil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AgregarAmigoF extends AppCompatActivity {


    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String email = user.getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query coleccion = db.collection("Usuarios");
    private AdapterAgregarAmigo adapter;
    private AdapterAgregarAmigo adapter2;
    TextView nick,email2,estado;
    Button enviar;
    Button buscar;
    String nombre;
    EditText busqueda;
    boolean escucha = false;
    boolean continuar = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1agregar_amigo_f);
        nick = findViewById(R.id.tv_AA_nick);
        email2 = findViewById(R.id.tv_aa_email);
        estado = findViewById(R.id.tv_aa_estado);
        enviar = findViewById(R.id.btn_enviar_solicitud);
        busqueda = findViewById(R.id.editT_busquedaA);


        enviar.setEnabled(false);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference userRef = db.collection("Usuarios").document(email);
                userRef.update("Enviada", FieldValue.arrayUnion(email2.getText()));

                DocumentReference userRef2 = db.collection("Usuarios").document(String.valueOf(email2.getText()));
                userRef2.update("Recibida", FieldValue.arrayUnion(email));

                enviar.setEnabled(false);
            }
        });


        buscar = findViewById(R.id.btn_busquedaA);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(nombre);
                nombre = busqueda.getText().toString();
                busqueda(nombre);
            }
        });


        //setUpRecyclerView();

    }

    private void busqueda(String nombre) {
        Query busqueda = db.collection("Usuarios").whereEqualTo("nick",nombre);
        FirestoreRecyclerOptions<Perfil> options= new FirestoreRecyclerOptions.Builder<Perfil>()
                .setQuery(busqueda, Perfil.class)
                .build();
        System.out.println(options);

        adapter2 = new AdapterAgregarAmigo(options);
        escucha = true;

        RecyclerView recyclerView = findViewById(R.id.recyclerbusquedaA);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter2);

        adapter2.startListening();

        adapter2.setOnItemClickListener(new AdapterAgregarAmigo.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Perfil perfil = documentSnapshot.toObject(Perfil.class);
                System.out.println(perfil.getEmail());
                String id = documentSnapshot.getId();
                nick.setText(perfil.getNick());
                email2.setText(perfil.getEmail());

                comprobarUser(email2);

                Toast.makeText(AgregarAmigoF.this,"Position "+position+" id "+id,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void comprobarUser(TextView email2) {

        final String miEmail = email;

        final String emailSeleccionado = email2.getText().toString();
        //seleccionar perfil seleccionado
        DocumentReference user3 = db.collection("Usuarios").document(emailSeleccionado);



        List<String> amigos = new ArrayList<>();
        List<String> enviado = new ArrayList<>();
        //List<String> recibidoAMi = new ArrayList<>();


        final boolean boton = true;
        //final boolean[] continuar = {true};

        continuar=true;

        enviar.setEnabled(true);

        estado.setText("Puedes solicitud a esa persona");


        if (emailSeleccionado.equals(miEmail)) {
            System.out.println("comprobar si eres tu");
            estado.setText("No te puedes agregar a ti mismo");
            fin(false);
            continuar =false;
            //enviar.setEnabled(false);
        }

        if(continuar){
            user3.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Perfil perfil = documentSnapshot.toObject(Perfil.class);
                    //coger lista recibidos

                    List<String> recibidos = perfil.getRecibida();
                    //comparar lista recibidos con mi email
                    for (int i = 0; i < recibidos.size(); i++) {

                        if (recibidos.get(i).equals(miEmail))
                            System.out.println(recibidos.get(i));

                        estado.setText("Ya has enviado solicitud a esa persona");

                        //boton[0] = false;

                    }

                }
            });
        }


        DocumentReference user4 = db.collection("Usuarios").document(miEmail);
        if(continuar){
            user4.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Perfil perfil2 = documentSnapshot.toObject(Perfil.class);
                    //coger lista enviados

                    List<String> recibido = perfil2.getRecibida();
                    //comparar lista recibidos con email seleccionado
                    for (int i=0;i<recibido.size();i++) {

                        if(recibido.get(i).equals(emailSeleccionado))
                            System.out.println(recibido.get(i));

                        estado.setText("Ya has recibido solicitud de esa persona");
                        //enviar.setEnabled(false);
                        fin(false);
                        continuar =false;

                    }

                }
            });
        }
        if (continuar) {
            user4.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Perfil perfil2 = documentSnapshot.toObject(Perfil.class);
                    //coger lista enviados

                    List<String> amigos = perfil2.getAmigos();
                    //comparar lista recibidos con email seleccionado
                    for (int i=0;i<amigos.size();i++) {

                        if(amigos.get(i).equals(emailSeleccionado))
                            System.out.println(amigos.get(i));

                        estado.setText("Ya sois amigos");
                        //enviar.setEnabled(false);
                        fin(false);
                        continuar =false;

                    }

                }
            });

        }








        //enviar.setEnabled(boton);

    }

    private void fin(boolean b) {

        enviar.setEnabled(b);


    }



    private void setUpRecyclerView() {

        Query query = coleccion;
        FirestoreRecyclerOptions<Perfil> options= new FirestoreRecyclerOptions.Builder<Perfil>()
                .setQuery(query, Perfil.class)
                .build();

        System.out.println(options);

        adapter = new AdapterAgregarAmigo(options);

        RecyclerView recyclerView = findViewById(R.id.recyclerbusquedaA);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterAgregarAmigo.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Perfil perfil = documentSnapshot.toObject(Perfil.class);
                System.out.println(perfil.getEmail());
                String id = documentSnapshot.getId();
                nick.setText(perfil.getNick());
                email2.setText(perfil.getEmail());

                Toast.makeText(AgregarAmigoF.this,"Position "+position+" id "+id,Toast.LENGTH_LONG).show();
            }
        });


    }




   /* @Override
    protected  void onStart(){
        super.onStart();
        adapter.startListening();
    }*/

    @Override
    protected void onStop(){
        super.onStop();
        //adapter.stopListening();
        if(escucha==true){
        adapter2.stopListening();}
    }


}