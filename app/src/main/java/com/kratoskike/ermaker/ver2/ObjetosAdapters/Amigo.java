package com.kratoskike.ermaker.ver2.ObjetosAdapters;

public class Amigo {

    String nick;
    String fecha;

    public Amigo() {

    }

    public Amigo(String nick, String fecha) {
        this.nick = nick;
        this.fecha = fecha;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
