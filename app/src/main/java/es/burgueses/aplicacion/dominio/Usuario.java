package es.burgueses.dominio;

import java.time.LocalDate;
import java.util.List;

import javax.swing.text.html.ImageView;

public class Usuario {
    private String nombre;
    private String apodo;
    private String pathImagen;
    private boolean activo;
    private LocalDate fechaAlta;

    public enum TipoUsuario {
        ADMINISTRADOR, USUARIO
    }

    private TipoUsuario tipoUsuario;
    private List<ListaReproduccion> listasPropias;
    private List<ListaReproduccion> listasFavoritas;
    private List<Cancion> ultimasCanciones;

    // Constructores
    public Usuario() {
        nombre = "";
        apodo = "";
        pathImagen = "";
        activo = false;
        fechaAlta = null;
        tipoUsuario = TipoUsuario.USUARIO; // Por defecto, un usuario es de tipo USUARIO

    }

    public Usuario(String nombre, String apodo, ImageView imagen, boolean activo, LocalDate fechaAlta,
            TipoUsuario tipoUsuario) {
        this.nombre = nombre;
        this.apodo = apodo;
        this.pathImagen = pathImagen;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apodo='" + apodo + '\'' +
                ", imagen=" + pathImagen +
                ", activo=" + activo +
                ", fechaAlta=" + fechaAlta +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}
