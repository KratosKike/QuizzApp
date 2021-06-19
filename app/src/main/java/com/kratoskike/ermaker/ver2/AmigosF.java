package com.kratoskike.ermaker.ver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ermaker.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterAmigo;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterSolicitudes;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Amigo;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Perfil;

import org.w3c.dom.Text;

public class AmigosF extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String email = user.getEmail();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query coleccion = db.collection("Usuarios").document(email).collection("amigos");
    private Query coleccion2 = db.collection("Solicitudes").whereEqualTo("emailUsuario",email);
    private Query coleccion3 = db.collection("Usuarios").whereArrayContains("Enviada",email);
    private Query coleccion4 = db.collection("Usuarios").whereArrayContains("amigos",email);
    private AdapterAmigo adapter;
    private AdapterSolicitudes adapter2;
    //Button solicitud;
    //RecyclerView solici;

    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1amigos_f);

        //Button solicitud = findViewById(R.id.btn_solicitudes);

        //ocultar recicler
        RecyclerView solici = findViewById(R.id.reciclertest);
        //solici.setVisibility(View.INVISIBLE);
        //solicitud.findViewById(R.id.btn_solicitudes);

        System.out.println(email);
        setUpRecyclerView();
        setUpRecyclerSolicitudes();


        /*solicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //solici.setVisibility(View.VISIBLE);
                setUpRecyclerSolicitudes();
            }
        });*/





        Button agregar = (Button)findViewById(R.id.btn_agregaramigo);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir();
            }
        });

    }

    private void setUpRecyclerSolicitudes() {
        Query query = coleccion3;
        FirestoreRecyclerOptions<Perfil>options= new FirestoreRecyclerOptions.Builder<Perfil>()
                .setQuery(query,Perfil.class)
                .build();

        adapter2 = new AdapterSolicitudes(options);
        RecyclerView recyclerView = findViewById(R.id.reciclertest);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter2);


    }

    private void abrir() {
        Intent intent2 = new Intent(this, AgregarAmigoF.class);
        startActivity(intent2);
    }

    private void setUpRecyclerView() {

        Query query = coleccion4;
        FirestoreRecyclerOptions<Perfil>options= new FirestoreRecyclerOptions.Builder<Perfil>()
                .setQuery(query,Perfil.class)
                .build();

        adapter = new AdapterAmigo(options);

        RecyclerView recyclerView = findViewById(R.id.reciclerAmigo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterAmigo.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //abrir popup con perfil

                Perfil  prefil= documentSnapshot.toObject(Perfil.class);
                System.out.println(prefil.getNick());
                createdialog(prefil.getNick(),prefil.getPuntuacion(),prefil.getEmail());
            }
        });


    }
    @Override
    protected  void onStart(){
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
        adapter2.startListening();
    }

    public void createdialog(String nick, int puntuacion, final String email3){
        dialogbuilder = new AlertDialog.Builder(this);
        final View cPopupView = getLayoutInflater().inflate(R.layout.popupperfil,null);

        TextView nickP = cPopupView.findViewById(R.id.tv_popP_Nick);
        TextView puntP = cPopupView.findViewById(R.id.tv_popP_puntuacion);

        nickP.setText("Nick: "+nick);
        puntP.setText("Puntuacion maxima: "+String.valueOf(puntuacion));

        final Button borrar = cPopupView.findViewById(R.id.btn_popP_borrar);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarUsuarioSeleccionado(email3);
            }
        });





        dialogbuilder.setView(cPopupView);
        dialog = dialogbuilder.create();
        dialog.show();

    }

    private void borrarUsuarioSeleccionado(String email3) {
        //Borrar amigo
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Usuarios").document(email3);
        userRef.update("amigos", FieldValue.arrayRemove(email));


        DocumentReference userRef2 = db.collection("Usuarios").document(String.valueOf(email));

        userRef2.update("amigos", FieldValue.arrayRemove(email3));
        dialog.dismiss();
    }

}