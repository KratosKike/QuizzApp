package com.kratoskike.ermaker.ver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ermaker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Pregunta;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PartidaF extends AppCompatActivity {

    Button o1,o2,o3,o4, c50, cp;
    TextView tP;
    TextView tvpuntuacion;
    TextView co1,co2,co3,co4;
    private static final String TAG = "Partida";

    ConstraintLayout comodin;

    int total =0;
    int correcta = 0;
    int error = 0;
    int tamaño;
    int ra;


    Pregunta pregunta;
    DatabaseReference db;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;


    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1partida);

        o1 = (Button)findViewById(R.id.b_opcion1);
        o2 = (Button)findViewById(R.id.b_opcion2);
        o3 = (Button)findViewById(R.id.b_opcion3);
        o4 = (Button)findViewById(R.id.b_opcion4);

        tP = (TextView)findViewById(R.id.tV_pregunta);
        tvpuntuacion = (TextView) findViewById(R.id.tV_puntuacion);

        c50 = (Button)findViewById(R.id.boton_comodin50);
        cp = (Button)findViewById(R.id.btn_comodinP);

        co1 = (TextView)findViewById(R.id.tV_comodin_o1);
        co2 = (TextView)findViewById(R.id.tV_comodin_o2);
        co3 = (TextView)findViewById(R.id.tV_comodin_o3);
        co4 = (TextView)findViewById(R.id.tV_comodin_o4);

        comodin = (ConstraintLayout)findViewById(R.id.comodinPublico);


        cargarPreguntas();





    }

    private void cargarPreguntas() {

        //Obtener lista de preguntas


        firestore.collection("Preguntas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            System.out.println("Funciona");
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                list.add(document.getId());
                            }
                            Log.d(TAG, list.toString());

                            tamaño = list.size();
                            System.out.println(list.get(0));

                            System.out.println(list.size());

                            actualizarPregunta();

                        }else{
                            System.out.println("No Funciona");
                        }
                    }
                });


    }

    private void actualizarPregunta() {



        System.out.println(total);
        tvpuntuacion.setText("Puntuacion: "+total);

        System.out.println("Entrando en bucle actualizar");

        if(error==1){
            //cargar activity de resultado
        }else{

            Random r = new Random();
            System.out.println(tamaño);

            ra = r.nextInt(list.size());

            System.out.println(list.get(ra));

            db=FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Preguntas")
                    .child(list.get(ra));

            System.out.println("Instance");

            final DocumentReference docRef = firestore.collection("Preguntas").document(list.get(ra));

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    System.out.println("Engancha "+docRef.getId());
                    DocumentSnapshot doc = task.getResult();
                    pregunta = doc.toObject(Pregunta.class);
                    o1.setText(pregunta.getOpcion1());o1.setBackgroundColor(Color.parseColor("#03BB85"));
                    o2.setText(pregunta.getOpcion2());o2.setBackgroundColor(Color.parseColor("#03BB85"));
                    o3.setText(pregunta.getOpcion3());o3.setBackgroundColor(Color.parseColor("#03BB85"));
                    o4.setText(pregunta.getOpcion4());o4.setBackgroundColor(Color.parseColor("#03BB85"));

                    //ocultar comodinPublico
                    comodin.setVisibility(View.INVISIBLE);

                    tP.setText(pregunta.getPregunta());

                    //setear botones
                    o1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //comparar con la respuesta
                            if(o1.getText().toString().equals(pregunta.getCorrecta())){
                                System.out.println("Correcta");
                                total++;
                                actualizarPregunta();
                            }else{
                                System.out.println("Erronea");
                                //mandar a activity de resultados
                                findejuego();
                            }
                        }
                    });
                    //
                    o2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //comparar con la respuesta
                            if(o2.getText().toString().equals(pregunta.getCorrecta())){
                                System.out.println("Correcta");
                                total++;
                                actualizarPregunta();
                            }else{
                                System.out.println("Erronea");
                                //mandar a activity de resultados
                                findejuego();
                            }
                        }
                    });
                    //
                    o3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //comparar con la respuesta
                            if(o3.getText().toString().equals(pregunta.getCorrecta())){
                                System.out.println("Correcta");
                                total++;
                                actualizarPregunta();
                            }else{
                                System.out.println("Erronea");
                                //mandar a activity de resultados
                                findejuego();
                            }
                        }
                    });
                    //
                    o4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //comparar con la respuesta
                            if(o4.getText().toString().equals(pregunta.getCorrecta())){
                                System.out.println("Correcta");
                                total++;
                                actualizarPregunta();
                            }else{
                                System.out.println("Erronea");
                                //mandar a activity de resultados
                                findejuego();
                            }
                        }
                    });

                    c50.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //coger boton correcto
                            List <Button> botones = new ArrayList<>();
                            botones.add(o1);botones.add(o2);botones.add(o3);botones.add(o4);
                            //Encontrar respuesta
                            for(int i=0;i<botones.size();i++){
                                if(botones.get(i).getText().toString().equals(pregunta.getCorrecta())){
                                    //eliminar opcion correcta
                                    botones.remove(i);
                                }
                            }
                            //Pintar un boton de los 3 de rojo
                            int r1 = new Random().nextInt(2);
                            botones.get(r1).setBackgroundColor(Color.parseColor("#FF0000"));
                            //Eliminar primer boton
                            botones.remove(r1);
                            //Pintar un boton de los 3 de rojo
                            int r2 = new Random().nextInt(1);
                            botones.get(r2).setBackgroundColor(Color.parseColor("#FF0000"));
                            //Eliminar primer boton
                            botones.remove(r2);

                            //Desactivar comodin
                            c50.setEnabled(false);



                            Iterator iter = botones.iterator();
                            while (iter.hasNext())
                                System.out.println(iter.next());


                        }



                    });

                    cp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Buscar boton
                            List <Button> botones = new ArrayList<>();
                            List <TextView> porcentajes = new ArrayList<>();

                            botones.add(o1);botones.add(o2);botones.add(o3);botones.add(o4);
                            porcentajes.add(co1);porcentajes.add(co2);porcentajes.add(co3);porcentajes.add(co4);
                            //buscar boton correcto
                            int por1 = new Random().nextInt(61)+40;//numero de 40 a 100
                            for(int i=0;i<botones.size();i++){
                                if(botones.get(i).getText().toString().equals(pregunta.getCorrecta())){

                                    int correcta = i;

                                    System.out.println("Porcentaje1= "+por1);
                                    System.out.println(botones.get(correcta));
                                    porcentajes.get(correcta).setText(por1+" % --> "+botones.get(correcta).getText());
                                    botones.remove(correcta);
                                    porcentajes.remove(correcta);
                                }
                            }
                        /*    //poner comodin primera pregunta
                        int por1 = new Random().nextInt(61)+40;//numero de 40 a 100
                            System.out.println("Porcentaje1= "+por1);
                            System.out.println(botones.get(correcta));
                            porcentajes.get(correcta).setText(por1+" % --> "+botones.get(correcta).getText());
                            botones.remove(correcta);*/

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Aqui deben quedar 3 preguntas");
                            Iterator iter = botones.iterator();
                            while (iter.hasNext())
                                System.out.println(iter.next());


                            //Pintar un boton de los 3
                            int r1 = new Random().nextInt(2);
                            int por2;
                            if(100-por1==0){
                                por2 = 0;
                            }else{
                                por2 = new Random().nextInt(100-por1)+1;
                            }
                            //int por2 = new Random().nextInt(100-por1)+1;
                            System.out.println("Porcentaje2= "+por2);
                            porcentajes.get(r1).setText(por2+" % --> "+botones.get(r1).getText());
                            botones.remove(r1);
                            porcentajes.remove(r1);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            System.out.println("Aqui deben quedar 2 preguntas");

                            Iterator iter2 = botones.iterator();
                            while (iter2.hasNext())
                                System.out.println(iter2.next());

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //Pintar un boton de los 2
                            int r2 = new Random().nextInt(1);
                            int por3;
                            if(100-por1-por2==0){
                                por3 = 0;
                            }else{
                                por3 = new Random().nextInt(100-por1-por2)+1;
                            }
                            //int por3 = new Random().nextInt(100-por1-por2)+1;
                            System.out.println("Porcentaje3= "+por3);
                            porcentajes.get(r2).setText(por3+" % --> "+botones.get(r2).getText());
                            botones.remove(r2);
                            porcentajes.remove(r2);

                            System.out.println("Aqui deben quedar 1 preguntas");

                            Iterator iter3 = botones.iterator();
                            while (iter3.hasNext())
                                System.out.println(iter3.next());

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            //pintar ultimo boton
                            int ultimo = 100 - por1 - por2 - por3;
                            porcentajes.get(0).setText(ultimo+" % --> "+botones.get(0).getText());
                            System.out.println("Porcentaje4= "+ultimo);



                            Iterator iter4 = botones.iterator();
                            while (iter4.hasNext())
                                System.out.println(iter4.next());


                            comodin.setVisibility(View.VISIBLE);
                            cp.setEnabled(false);


                        }
                    });





                }
            });




        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        createDialog();

    }

    private void createDialog() {
        dialogbuilder = new AlertDialog.Builder(this);
        final View cPopupView = getLayoutInflater().inflate(R.layout.popupsalir,null);

        TextView mesg = cPopupView.findViewById(R.id.tv_popSalir);
        mesg.setText("¿Quieres salir de la prueba?");

        Button si = cPopupView.findViewById(R.id.btn_pops_si);
        Button no = cPopupView.findViewById(R.id.btn_pops_no);

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finalizar aplicacion
                dialog.dismiss();
                Intent intent = new Intent(PartidaF.this,MenuPrincipalF.class);
                startActivity(intent);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });




        dialogbuilder.setView(cPopupView);
        dialog = dialogbuilder.create();
        dialog.show();
    }


    private void findejuego() {
        System.out.println(total);
        Intent intent = new Intent(this, ResultadoF.class);
        intent.putExtra("puntuacion",total);
        startActivity(intent);
    }
}