package com.kratoskike.ermaker.ver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ermaker.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Perfil;

import org.jetbrains.annotations.NotNull;

public class PerfilF extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String nombre;
    String emaill;
    Perfil perfil;
    int puntuacion;
    String nick;
    String nicknuevo;
    Button desc;
    ImageView admin;
    private GoogleSignInOptions gso;

    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogbuilder2;
    private AlertDialog dialog2;




    private GoogleApiClient googleApiClient;

    private static final String TAG = "PerfilF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1perfil_f);

        Button botonAmigo = (Button) findViewById(R.id.btn_amigos);
        botonAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir();
            }
        });

        admin = findViewById(R.id.imageViewadmin);


        //Obtener datos de perfil
        user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        final String email = user.getEmail();
        final TextView ema = findViewById(R.id.tV_p_email);
        final TextView nom = findViewById(R.id.tV_p_nombre);
        final TextView pun = findViewById(R.id.tV_p_puntuacion);
        final TextView nic = findViewById(R.id.tv_p_nick);
        //final ScrollView sCambiar = findViewById(R.id.scrollCambiar);
        final Button botonCambiar = findViewById(R.id.btn_cambiarnick);
        //final Button botonActualizar = findViewById(R.id.botonUpdateNick);
        //final EditText etnicknuevo = findViewById(R.id.editT_nick);

        desc = findViewById(R.id.btn_p_desconectar);


        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(PerfilF.this,Portada.class);//Cambiar por la nueva clase
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();*/

                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){

                            user = FirebaseAuth.getInstance().getCurrentUser();
                            String email = user.getEmail();
                            final DocumentReference docRef = db.collection("Usuarios").document(email);
                            docRef.update("admin","no").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            });

                            FirebaseAuth fAuth = FirebaseAuth.getInstance();
                            fAuth.signOut();

                            Intent intent = new Intent(PerfilF.this, Portada.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(getApplicationContext(),"Session not close",Toast.LENGTH_LONG).show();

                        }
                    }
                });



            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createDialog();
            }
        });



        //sCambiar.setVisibility(View.GONE);


        System.out.println("Test:"+email);

        final DocumentReference docRef = db.collection("Usuarios").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                perfil = doc.toObject(Perfil.class);
                nombre= perfil.getNombre();
                emaill = perfil.getEmail();
                puntuacion = perfil.getPuntuacion();
                nick = perfil.getNick();

                System.out.println("Nick: "+nick);

                nom.setText("Nombre: "+nombre);
                ema.setText("Email: "+emaill);
                pun.setText("Puntuacion maxima: "+puntuacion);
                nic.setText("Nick: "+nick);

                System.out.println("Nick: "+nick);


            }
        });



        //botonCambiar.findViewById(R.id.btn_cambiarnick);
       botonCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostrar cambiar nick
                //sCambiar.setVisibility(View.VISIBLE);
                createDialog2();
            }
        });

       /* botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chequear nombre
                nicknuevo=etnicknuevo.getText().toString();
                System.out.println("Edit: "+nicknuevo);

                if (nicknuevo.isEmpty()){
                    //No hay valor
                    System.out.println("No hay valor");
                }else{
                    //poner nuevo nick
                    docRef.update("nick",nicknuevo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    });

                }
            }
        });*/




        /*docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Perfil perfil = documentSnapshot.toObject(Perfil.class);

                nombre= perfil.getNombre();

                emaill = perfil.getEmail();

                nom.setText("Nombre: "+nombre);

                ema.setText("Email: "+emaill);

            }
        });*/

        //System.out.println(nombre);
        //System.out.println(emaill);
        //comprobar email en la db Firebase




        /*noteRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                        //crear objeto del perfil
                        Perfil perfil = document.toObject(Perfil.class);

                        nombre= perfil.getNombre();

                        emaill = perfil.getEmail();

                        System.out.println("Test "+nombre);


                        nom.setText("Nombre: "+nombre);

                        ema.setText("Email: "+emaill);


                    }else{
                        System.out.println("Fallo exists");
                    }
                }else{
                    System.out.println("Fallo");
                }

            }
        });*/


        //Rellenar Datos



    }

    private void createDialog2() {
        dialogbuilder2 = new AlertDialog.Builder(this);
        final View cPopupView2 = getLayoutInflater().inflate(R.layout.popupnick,null);

        final EditText newnick = cPopupView2.findViewById(R.id.edt_pop_nick);

        final TextView camb = cPopupView2.findViewById(R.id.tv_pop_nick);

        Button btnCambiar = cPopupView2.findViewById(R.id.edt_pop_bcambiar);

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chequear nombre
                nicknuevo=newnick.getText().toString();
                System.out.println("Edit: "+nicknuevo);

                if (nicknuevo.isEmpty()){
                    //No hay valor
                    camb.setText("No puede estar vacio");
                    System.out.println("No hay valor");
                }else{
                    //poner nuevo nick
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    String name = user.getDisplayName();
                    final String email = user.getEmail();
                    final DocumentReference docRef = db.collection("Usuarios").document(email);
                    docRef.update("nick",nicknuevo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            dialog.dismiss();
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    });
            }}
        });




        dialogbuilder2.setView(cPopupView2);
        dialog2 = dialogbuilder2.create();
        dialog2.show();

    }

    private void abrir() {
        Intent intent2 = new Intent(this, AmigosF.class);
        startActivity(intent2);

    }

    public void createDialog(){
        dialogbuilder = new AlertDialog.Builder(this);
        final View cPopupView = getLayoutInflater().inflate(R.layout.popupadmin,null);

        final EditText edtPass = cPopupView.findViewById(R.id.eT_adminpass);
        Button b_enter = cPopupView.findViewById(R.id.button3);

        b_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edtPass.getText().toString();
                if(pass.equals("1234")){
                    //Contraseña correcta
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    String email = user.getEmail();
                    final DocumentReference docRef = db.collection("Usuarios").document(email);
                    docRef.update("admin","si").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    });
                    dialog.dismiss();
                }else{
                    //contraseña incorrecta
                    dialog.dismiss();
                }
            }
        });



        dialogbuilder.setView(cPopupView);
        dialog = dialogbuilder.create();
        dialog.show();

    }



}