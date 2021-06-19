package com.kratoskike.ermaker.ver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ermaker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Perfil;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Puntuacion;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;

public class ResultadoF extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Perfil perfil;
    int puntAnt;

    int puntA;

    Button volver;




    TextView tvpA,tvpAnt,tvR;
    private static final String TAG = "ResultadoF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1resultado_f);

        puntA = getIntent().getExtras().getInt("puntuacion");

        tvpA=(TextView)findViewById(R.id.tV_punt_nueva);
        tvpAnt=(TextView)findViewById(R.id.tv_p_ultima);
        tvR=(TextView)findViewById(R.id.tv_r_act);

        tvpA.setText("Puntuacion realizada: "+puntA);

        //Obtener puntuacion usuario
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        final DocumentReference docRef = db.collection("Usuarios").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                perfil = doc.toObject(Perfil.class);
                puntAnt = perfil.getPuntuacion();

                tvpAnt.setText("Mejor puntuacion: "+puntAnt);

                //comparar puntuaciones
                if(puntA >puntAnt){
                    tvR.setText("Ha obtenido un nuevo record");
                    docRef.update("puntuacion",puntA)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    });

                }else{
                    tvR.setText("No has mejorado tu mejor puntuacion");
                }

            }
        });

        //Guardar Puntuacion con fecha



        volver = (Button)findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });

        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Timestamp fecha = Timestamp.now();


        Puntuacion punt = new Puntuacion(email,puntA,fecha);

         db.collection("Puntuacion").document().set(punt);






    }

    private void volver() {
        Intent intent = new Intent(this, MenuPrincipalF.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuPrincipalF.class);
        startActivity(intent);
    }

}