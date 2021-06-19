package com.kratoskike.ermaker.ver2.ObjetosAdapters;

public class Solicitud {
    String nick;
    String emailSolicitud;
    String emailUsuario;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmailSolicitud() {
        return emailSolicitud;
    }

    public void setEmailSolicitud(String emailSolicitud) {
        this.emailSolicitud = emailSolicitud;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Solicitud() {
    }

    public Solicitud(String nick, String emailSolicitud, String emailUsuario) {
        this.nick = nick;
        this.emailSolicitud = emailSolicitud;
        this.emailUsuario = emailUsuario;
    }
}
