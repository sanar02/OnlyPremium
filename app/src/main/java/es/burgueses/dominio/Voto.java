package es.burgueses.aplicacion.dominio;

import java.time.LocalDate;

public class Voto {
    private String usuario;
    private LocalDate fecha;
    private boolean tipo; // true para "me gusta", false para "no me gusta"

    //Constructores
    public Voto() {
        usuario = "";
        fecha = LocalDate.now();
        tipo = true; // Por defecto, el voto es "me gusta"
    }

    public Voto(String usuario, LocalDate fecha, boolean tipo) {
        this.usuario = usuario;
        this.fecha = LocalDate.now();
        this.tipo = true; // Por defecto, el voto es "me gusta"
    }

    //Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    @Override
    public String toString() {
        return "Voto{" +
                "Usuario='" + usuario + '\'' +
                '}';
    }
}
