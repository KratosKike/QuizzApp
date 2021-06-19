package com.kratoskike.ermaker.ver2.ObjetosAdapters;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.Date;

public class Puntuacion {

    public String email;
    public int puntuacion;
    public Timestamp fecha;

    public Puntuacion(){

    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Puntuacion(String email, int puntuacion, Timestamp fecha) {
        this.email = email;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }


}
