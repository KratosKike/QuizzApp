package com.kratoskike.ermaker.ver2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ermaker.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterChat;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterChatFirestore;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.AdapterPuntuacion;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Chat;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Perfil;
import com.kratoskike.ermaker.ver2.ObjetosAdapters.Puntuacion;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Date;

public class ChatF extends AppCompatActivity {

    private ListView listview;
    private EditText message;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Perfil perfil;
    private String nick;
    private AdapterChat chatAdapter;
    private DatabaseReference database1;
    private DatabaseReference reference;
    private ImageView boton;
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Query coleccion = db.collection("chat").orderBy("fecha", Query.Direction.ASCENDING);

    RecyclerView recyclerView ;

    private AdapterChatFirestore adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1chat_f);
        recyclerView = findViewById(R.id.recyclerChat);

        setUpRecyclerView();




        //
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        final DocumentReference docRef = db.collection("Usuarios").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                perfil = doc.toObject(Perfil.class);

                nick = perfil.getNick();

            }
        });


        boton = findViewById(R.id.iVEnviar);


        //listview = findViewById(R.id.listchat);
        message = findViewById(R.id.edt_mensaje);

        //chatAdapter = new AdapterChat(this, R.id.listchat);
        //listview.setAdapter(chatAdapter);

        database1 = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Chat");




        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                System.out.println(nick);
                System.out.println(message.getText().toString().trim());
                System.out.println(currentDateTimeString);
                Chat msg = new Chat (nick,message.getText().toString().trim(),currentDateTimeString);
                //reference.push().setValue(msg);
                //mensaje para cloudfirestore
                db.collection("chat").add(msg);
                message.setText("");
                scrolltoBottom();
            }
        });



    }

    private void setUpRecyclerView() {
        Query query = coleccion;
        FirestoreRecyclerOptions<Chat> options = new FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(query,Chat.class)
                .build();

        adapter = new AdapterChatFirestore(options);

        //final RecyclerView recyclerView = findViewById(R.id.recyclerChat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(adapter);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        }, 1000);



    }

    private void scrolltoBottom() {
        /*listview.post(new Runnable() {
            @Override
            public void run() {
                listview.setSelection(chatAdapter.getCount() - 1);

            }
        });*/
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        }, 1000);

    }

    @Override
    protected  void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }


}