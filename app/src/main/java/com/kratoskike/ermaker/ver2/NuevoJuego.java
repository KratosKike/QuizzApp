package com.kratoskike.ermaker.ver2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ermaker.R;

public class NuevoJuego extends AppCompatActivity {

    private Button bEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1nuevo_juego);

        bEmpezar=(Button)findViewById(R.id.b_EmpezarJuego);
        bEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrir("partida");

            }
        });


    }

    @Override public void onBackPressed() {
        volveraMenuPrincipal();
    }

    private void volveraMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalF.class);
        startActivity(intent);
    }

    private void abrir(String clase) {
        switch(clase){

            case"partida":
                Intent intent2 = new Intent(this, PartidaF.class);
                startActivity(intent2);

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + clase);
        }
    }
}