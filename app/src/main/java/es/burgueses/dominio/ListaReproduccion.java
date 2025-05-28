package es.burgueses.dominio;

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
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ") || nombre.length() > 35 || nombre.trim().equals("")) {
            throw new IllegalArgumentException("El nombre no es correcto.");
        } else {
            this.nombre = nombre;
        }
        if (propietario == null) {
            throw new IllegalArgumentException("El propietario no puede ser nulo.");
        } else {
            this.propietario = propietario;
        }
        if (fechaCreacion == null || !fechaCreacion.equals(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de creación debe ser la fecha actual.");
        } else {
            this.fechaCreacion = fechaCreacion;
        }
        if (canciones == null) {
            canciones = new ArrayList<>();
        } else {
            this.canciones = canciones;
        }
        this.canciones = canciones;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.isEmpty() || nombre.contains("  ") || nombre.length() > 35 || nombre.trim().equals("")) {
            throw new IllegalArgumentException("El nombre no es correcto.");
        }
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
        if(fechaCreacion == null || fechaCreacion!=LocalDate.now()) {
            throw new IllegalArgumentException("La fecha incorrecta.");
        }
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

    public String getTitulo() {
        return nombre;
    }
    public void setTitulo(String titulo) {
        if(titulo == null || titulo.isEmpty() || titulo.contains("  ") || titulo.length() > 35 || titulo.trim().equals("")) {
            throw new IllegalArgumentException("El título no es correcto.");
        }
        this.nombre = titulo;
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
    
}
