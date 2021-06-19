package com.kratoskike.ermaker.ver2.ObjetosAdapters;

import java.util.List;

public class Perfil {

    private String nombre;
    private String email;
    private int puntuacion;
    private String nick;
    private List<String> Recibida;
    private List<String> Enviada;
    private List<String> Amigos;
    private String admin;

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Perfil(String nombre, String email, int puntuacion, String nick, List<String> recibida, List<String> enviada, List<String> amigos, String admin) {
        this.nombre = nombre;
        this.email = email;
        this.puntuacion = puntuacion;
        this.nick = nick;
        Recibida = recibida;
        Enviada = enviada;
        Amigos = amigos;
        this.admin = admin;
    }

    public List<String> getRecibida() {
        return Recibida;
    }

    public void setRecibida(List<String> recibida) {
        Recibida = recibida;
    }

    public List<String> getEnviada() {
        return Enviada;
    }

    public void setEnviada(List<String> enviada) {
        Enviada = enviada;
    }

    /*public Perfil(String nombre, String email, int puntuacion, String nick, List<String> recibida, List<String> enviada) {
        this.nombre = nombre;
        this.email = email;
        this.puntuacion = puntuacion;
        this.nick = nick;
        Recibida = recibida;
        Enviada = enviada;
    }*/

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Perfil(){
        //Clase Vacia
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    /*public Perfil(String nombre, String email, int puntuacion, String nick){

        this.nombre = nombre;
        this.email = email;
        this.puntuacion = puntuacion;
        this.nick = nick;

    }*/

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getNick() {
        return nick;
    }

    public List<String> getAmigos() {
        return Amigos;
    }

    public void setAmigos(List<String> amigos) {
        Amigos = amigos;
    }



}
