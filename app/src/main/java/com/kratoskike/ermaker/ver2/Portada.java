package com.kratoskike.ermaker.ver2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ermaker.R;

public class Portada extends AppCompatActivity {

    private Button boton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1portada);

        boton= (Button)findViewById(R.id.BtnPortada);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transicionLogin();
            }
        });

    }

    private void transicionLogin() {
        Intent intent = new Intent(this, LoginF.class);
        startActivity(intent);
    }


}