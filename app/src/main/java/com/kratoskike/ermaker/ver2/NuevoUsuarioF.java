package com.kratoskike.ermaker.ver2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ermaker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class NuevoUsuarioF extends AppCompatActivity {

    EditText nick;
    Button registrar;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView estadoNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1nuevo_usuario_f);

        nick = findViewById(R.id.eT_Nick);
        registrar = findViewById(R.id.btn_nuevousuario);
        estadoNick = findViewById(R.id.tv_nu_nick);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //comprobar que esta en el sistema

                if(nick.getText().toString().isEmpty()){
                    estadoNick.setText("*No puede ser un campo vacio");
                }else{
                    String emailComprueba = user.getEmail();

                    System.out.println("Entra DR");

                    DocumentReference noteRef = db.collection("Usuarios").document(emailComprueba);

                    noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                //existe documento, no hace falta crea un documento nuevo
                            }else{
                                //No existe documento,crear documento nuevo
                                System.out.println("Pasa por aqui");
                                saveNote();

                            }
                        }
                    });



                }



            }
        });


    }

    public void saveNote (){

        String title = user.getDisplayName();
        String email = user.getEmail();
        Map<String,Object> note = new HashMap<>();
        note.put("nombre",title);
        note.put("email",email);
        note.put("puntuacion",0);
        note.put("nick", nick.getText().toString());
        note.put("admin","no");
        //note.put("nick",email.substring(0,5));

        db.collection("Usuarios").document(email).set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(NuevoUsuarioF.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NuevoUsuarioF.this,MenuPrincipalF.class);//Cambiar por la nueva clase
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        System.out.println("Se crea documento");



    }



}