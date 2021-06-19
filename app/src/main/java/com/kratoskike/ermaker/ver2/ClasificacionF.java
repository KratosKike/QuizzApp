package com.kratoskike.ermaker.ver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ermaker.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestoreDataSource;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.local.QueryEngine;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterPuntuacion;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Perfil;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Puntuacion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class ClasificacionF extends AppCompatActivity {


    private AdapterPuntuacion adapter;
    private AdapterPuntuacion adapter2;
    private AdapterPuntuacion adapter3;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private CollectionReference db2 = (CollectionReference) db.collection("Puntuacion").orderBy("puntuacion", Query.Direction.DESCENDING);

    //Query dia
    Timestamp fecha = Timestamp.now();
    Timestamp fecha1Dias = new Timestamp((long) (fecha.getSeconds()-86400),fecha.getNanoseconds());
    Timestamp fecha1Semana = new Timestamp((long)(fecha.getSeconds()-604800),fecha.getNanoseconds());
    Timestamp fecha1Mes = new Timestamp((long)(fecha.getSeconds()-2628000),fecha.getNanoseconds());




    //private Query coleccion = db.collection("Puntuacion").whereGreaterThan("fecha",fecha1Dias);
    //private Query coleccion = db.collection("Puntuacion").orderBy("puntuacion", Query.Direction.DESCENDING).whereGreaterThan("fecha",fecha1Dias);


    private Query coleccionDiaAnterior = db.collection("Puntuacion").whereGreaterThan("fecha",fecha1Dias);
    private Query coleccionSemanaAnterior = db.collection("Puntuacion").whereGreaterThan("fecha",fecha1Semana);
    private Query coleccionMesAnterior = db.collection("Puntuacion").whereGreaterThan("fecha",fecha1Mes);

    Button volver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1clasificacion_f);
        //final RecyclerView recyclerView2 = findViewById(R.id.reciclersemana);


        setUpRecyclerView();
        setUpRecyclerSemana();
        setUpRecyclerMes();
        //recyclerView2.setVisibility(View.INVISIBLE);



        volver = findViewById(R.id.btn_class_volver);


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClasificacionF.this,MenuPrincipalF.class);//Cambiar por la nueva clase
                startActivity(intent);

            }
        });



    }

    private void setUpRecyclerMes() {
        Query query = coleccionMesAnterior;
        //Query query2 = this.coleccionDiaAnterior;
        FirestoreRecyclerOptions<Puntuacion>options = new FirestoreRecyclerOptions.Builder<Puntuacion>()
                .setQuery(query,Puntuacion.class)
                .build();
        adapter3 = new AdapterPuntuacion(options);
        RecyclerView recyclerView3 = findViewById(R.id.reciclermes);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView3.setAdapter(adapter3);

    }

    private void setUpRecyclerSemana() {
        Query query = coleccionSemanaAnterior;
        //Query query2 = this.coleccionDiaAnterior;
        FirestoreRecyclerOptions<Puntuacion>options = new FirestoreRecyclerOptions.Builder<Puntuacion>()
                .setQuery(query,Puntuacion.class)
                .build();
        adapter2 = new AdapterPuntuacion(options);
        RecyclerView recyclerView2 = findViewById(R.id.reciclersemana);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView2.setAdapter(adapter2);
    }

    private void setUpRecyclerView() {

        Query query = coleccionDiaAnterior;
        //Query query2 = this.coleccionDiaAnterior;
        FirestoreRecyclerOptions<Puntuacion>options = new FirestoreRecyclerOptions.Builder<Puntuacion>()
                .setQuery(query,Puntuacion.class)
                .build();

        adapter = new AdapterPuntuacion(options);

        RecyclerView recyclerView = findViewById(R.id.reciclePunt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter);





    }
    @Override
    protected  void onStart(){
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
        adapter3.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
        adapter2.startListening();
        adapter3.startListening();
    }



}


