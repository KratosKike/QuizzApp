package com.kratoskike.ermaker.ver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.ermaker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import com.kratoskike.ermaker.ver2.ObjetosAdapters.Pregunta;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CrearPreguntaF extends AppCompatActivity {

    EditText p,o1,o2,o3,o4;
    private RadioGroup rdgGrupo;
    private Object RadioGroup;
    String respuesta ;
    Button crear;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1crear_pregunta);

        p = findViewById(R.id.et_crear_pregunta);
        o1 = findViewById(R.id.et_crear_1);
        o2 = findViewById(R.id.et_crear_2);
        o3 = findViewById(R.id.et_crear_3);
        o4 = findViewById(R.id.et_crear_4);

        crear = findViewById(R.id.btn_crear);

        rdgGrupo = (RadioGroup)findViewById(R.id.radioRespu);

        //Ocultar boton
        crear.setEnabled(false);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarTexto();
            }
        });




        rdgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.RadioGroup group, int checkedId) {

                if(checkedId == R.id.radioButton){
                    comprobarEdit();
                    respuesta= String.valueOf(o1.getText());
                    System.out.println("a "+respuesta);

                }else if(checkedId == R.id.radioButton2){
                    comprobarEdit();
                    respuesta= String.valueOf(o2.getText());
                }else if(checkedId == R.id.radioButton3){
                    comprobarEdit();
                    respuesta= String.valueOf(o3.getText());
                }else if(checkedId == R.id.radioButton4){
                    comprobarEdit();
                    respuesta= String.valueOf(o4.getText());
                }

            }
        });



    }

    private void comprobarTexto() {
        if(TextUtils.isEmpty(p.getText())){
            crear.setEnabled(false);
        } else if(TextUtils.isEmpty(o1.getText())){
            crear.setEnabled(false);
        } else if(TextUtils.isEmpty(o2.getText())){
            crear.setEnabled(false);
        }else if(TextUtils.isEmpty(o3.getText())){
            crear.setEnabled(false);
        }else if(TextUtils.isEmpty(o4.getText())){
            crear.setEnabled(false);
        }else{
            crearPregunta();
        }
    }

    private void crearPregunta() {
        //Pregunta(String categoria, String correcta, String dificultad, String opcion1, String opcion2, String opcion3, String opcion4, String pregunta)
        Pregunta pregunta = new Pregunta("",respuesta,"",o1.getText().toString(),o2.getText().toString(),o3.getText().toString(),o4.getText().toString(),p.getText().toString());

        db.collection("Preguntas").document().set(pregunta).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                startActivity(new Intent(CrearPreguntaF.this, MenuPrincipalF.class ));
            }
        });
    }

    private void comprobarEdit() {

        if(TextUtils.isEmpty(p.getText())){
            crear.setEnabled(false);
        } else if(TextUtils.isEmpty(o1.getText())){
            crear.setEnabled(false);
        } else if(TextUtils.isEmpty(o2.getText())){
            crear.setEnabled(false);
        }else if(TextUtils.isEmpty(o3.getText())){
            crear.setEnabled(false);
        }else if(TextUtils.isEmpty(o4.getText())){
            crear.setEnabled(false);
        }else{
            crear.setEnabled(true);
        }
    }


}