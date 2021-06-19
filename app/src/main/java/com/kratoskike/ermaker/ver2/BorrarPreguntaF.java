package com.kratoskike.ermaker.ver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ermaker.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterBorrarPregunta;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Pregunta;

public class BorrarPreguntaF extends AppCompatActivity {

    TextView id,pregunta;
    Button borrar;
    String getid;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coleccion = db.collection("Preguntas");
    CollectionReference ref = db.collection("Preguntas");

    private AdapterBorrarPregunta adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1borrar_pregunta_f);

        id = findViewById(R.id.tv_bp_id);
        pregunta = findViewById(R.id.tv_bp_preg);
        borrar = (Button)findViewById(R.id.btn_bp_borrar);


        setUpRecyclerView();


        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Preguntas").document(getid).delete();

                startActivity(new Intent(BorrarPreguntaF.this, MenuPrincipalF.class ));
            }
        });



    }

    private void setUpRecyclerView() {
        Query query = ref;
        FirestoreRecyclerOptions<Pregunta> options = new FirestoreRecyclerOptions.Builder<Pregunta>()
                .setQuery(query, Pregunta.class)
                .build();
        adapter = new AdapterBorrarPregunta(options);
        RecyclerView recyclerView = findViewById(R.id.reciclerBorrarPregunta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterBorrarPregunta.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Pregunta preguntaO = documentSnapshot.toObject(Pregunta.class);
                getid = documentSnapshot.getId();

                id.setText(getid.toString());
                pregunta.setText(preguntaO.getPregunta());
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}