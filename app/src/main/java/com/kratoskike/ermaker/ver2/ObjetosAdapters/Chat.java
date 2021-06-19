package com.kratoskike.ermaker.ver2.ObjetosAdapters;

public class Chat {

    public String user;
    public String mesagge;
    public String fecha;

    public Chat() {

    }

    public Chat(String user, String mesagge, String fecha) {
        this.user = user;
        this.mesagge = mesagge;
        this.fecha = fecha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMesagge() {
        return mesagge;
    }

    public void setMesagge(String mesagge) {
        this.mesagge = mesagge;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
