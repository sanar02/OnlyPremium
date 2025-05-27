package es.burgueses.aplicacion.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListaReproduccion {
    private String nombre;
    private Usuario propietario;
    private String descripcion;
    private List<Cancion> canciones;
    private LocalDate fechaCreacion;
    private List<Voto> meGusta;
    private List<Voto> noMeGusta;

    // Constructores
    public ListaReproduccion() {
        nombre = "";
        propietario = new Usuario(); // Asignar un usuario por defecto
        descripcion = "";
        this.canciones = new ArrayList<>();
        this.meGusta = new ArrayList<>();
        this.noMeGusta = new ArrayList<>();
        this.fechaCreacion = LocalDate.now();
    }

    public ListaReproduccion(String nombre, Usuario propietario, String descripcion, List<Cancion> canciones,
            LocalDate fechaCreacion, List<String> meGusta, List<String> noMeGusta) {
        this.nombre = nombre;
        this.propietario = propietario;
        this.descripcion = descripcion;
        this.canciones = canciones;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public List<Voto> getMeGusta() {
        return meGusta;
    }
    public void setMeGusta(List<Voto> meGusta) {
        this.meGusta = meGusta;
    }
    public List<Voto> getNoMeGusta() {
        return noMeGusta;
    }
    public void setNoMeGusta(List<Voto> noMeGusta) {
        this.noMeGusta = noMeGusta;
    }

    @Override
    public String toString() {
        return "ListaReproduccion{" +
                "nombre='" + nombre + '\'' +
                ", propietario='" + propietario + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", canciones=" + canciones +
                ", fechaCreacion=" + fechaCreacion +
                ", meGusta=" + meGusta +
                ", noMeGusta=" + noMeGusta +
                '}';
    }

    public String getTitulo() {
        return nombre;
    }

    
}
