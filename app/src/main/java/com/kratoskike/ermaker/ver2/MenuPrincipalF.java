package com.kratoskike.ermaker.ver2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ermaker.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import com.kratoskike.ermaker.ver2.ObjetosAdapters.Perfil;

import java.util.HashMap;
import java.util.Map;

public class MenuPrincipalF extends AppCompatActivity {

    private Button bPerfil;

    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button bNJuego;
    private Button bClass;
    private Button bChat;
    private Button bCrear;
    private Button borrar;
    String admin;
    ImageView perfil;

    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1menu_principal_f);

        String name = null;

        //imagen perfil
        perfil = findViewById(R.id.imagenPerfil);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir("perfil");
            }
        });

        //Boton Perfil
        bPerfil=(Button)findViewById(R.id.b_Perfil);
        bPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir("perfil");

            }
        });
        //Boton Nuevo Juego
        bNJuego=(Button)findViewById(R.id.b_nuevoJ);
        bNJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir("juego");

            }
        });
        //Boton Clasificacion
        bClass=(Button)findViewById(R.id.btn_clasificacion);
        bClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir("clasificacion");

            }
        });
        //Boton Chat
        bChat=(Button)findViewById(R.id.btn_chat);
        bChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir("chat");
            }
        });
        //Boton Crear
        bCrear=(Button)findViewById(R.id.btn_mp_editor);
        bCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir("crear");
            }
        });

        borrar=(Button)findViewById(R.id.btn_mp_borrar);
        borrar.setVisibility(View.INVISIBLE);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir("borrar");
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //Hay usuario registrado
            //Obtener datos usuario
            name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            datosUsuario();

        }else{

        }
        String emailComprueba = user.getEmail();



        DocumentReference docRef = db.collection("Usuarios").document(emailComprueba);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Perfil perfil = documentSnapshot.toObject(Perfil.class);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    admin=perfil.getAdmin().toString();

                    if(admin.equals("si")){
                        borrar.setVisibility(View.VISIBLE);
                    }
                }
            });




        //Inicio datos Firebase



        TextView bienvenido = findViewById(R.id.tV_Mostrar);
        bienvenido.setText("Bienvenido "+name);

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        createDialog();

    }

    private void createDialog() {
        dialogbuilder = new AlertDialog.Builder(this);
        final View cPopupView = getLayoutInflater().inflate(R.layout.popupsalir,null);

        Button si = cPopupView.findViewById(R.id.btn_pops_si);
        Button no = cPopupView.findViewById(R.id.btn_pops_no);

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finalizar aplicacion
                dialog.dismiss();
                finishAffinity ();
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

    private void abrir(String clase) {

        switch(clase){
            case "perfil":
                Intent intent = new Intent(this, PerfilF.class);
                startActivity(intent);

                break;

            case"juego":
                Intent intent2 = new Intent(this, NuevoJuego.class);
                startActivity(intent2);

                break;

            case"clasificacion":
                Intent intent3 = new Intent(this, ClasificacionF.class);
                startActivity(intent3);

                break;

            case"chat":
                Intent intent4 = new Intent(this, ChatF.class);
                startActivity(intent4);

                break;
            case"crear":
                Intent intent5 = new Intent(this, CrearPreguntaF.class);
                startActivity(intent5);

                break;
            case"borrar":
                Intent intent6 = new Intent(this, BorrarPreguntaF.class);
                startActivity(intent6);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clase);
        }





    }

    /*public void saveNote (){

        String title = user.getDisplayName();
        String email = user.getEmail();
        Map<String,Object> note = new HashMap<>();
        note.put("nombre",title);
        note.put("email",email);
        note.put("puntuacion",0);
        //note.put("nick",email.substring(0,5));

        db.collection("Usuarios").document(email).set(note);

    }*/

    public void datosUsuario(){

        //comprobar que esta en el sistema
        String emailComprueba = user.getEmail();




        DocumentReference noteRef = db.collection("Usuarios").document(emailComprueba);

        noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    //existe documento, no hace falta crea un documento nuevo
                }else{
                    //No existe documento,crear documento nuevo
                    //saveNote();

                }
            }
        });




    }


}