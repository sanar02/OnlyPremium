package es.burgueses.dominio;

import org.bson.codecs.pojo.annotations.BsonId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListaReproduccion {
    private String nombre;
    private Usuario propietario;
    private String descripcion;
    private List<Cancion> canciones;
    private LocalDate fechaCreacion;
    private List<Voto> meGusta;
    private List<Voto> noMeGusta;
    @BsonId
    private String idLista;

    // Constructores
    public ListaReproduccion() {
        this.nombre = "";
        this.propietario = new Usuario();
        this.descripcion = "";
        this.canciones = new ArrayList<>();
        this.meGusta = new ArrayList<>();
        this.noMeGusta = new ArrayList<>();
        this.fechaCreacion = LocalDate.now();
        this.idLista = UUID.randomUUID().toString();
    }

    // validaciones
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ") || nombre.length() > 35 || nombre.trim().equals("")) {
            throw new IllegalArgumentException("El nombre no es correcto.");
        }
    }

    private void validarPropietario(Usuario propietario) {
        if (propietario == null) {
            throw new IllegalArgumentException("El propietario no puede ser nulo.");
        }
    }

    private void validarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty() || descripcion.trim().equals("") || descripcion.length() > 100) {
            throw new IllegalArgumentException("El descripcion no puede ser nulo.");
        }
    }
    private void validarFechaCreacion(LocalDate fechaCreacion) {
        if (fechaCreacion == null || !fechaCreacion.equals(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de creación debe ser la fecha actual.");
        }
    }

    private void validarIdLista(String idLista) {
        if (idLista == null || idLista.isEmpty()) {
            throw new IllegalArgumentException("El ID de la lista no puede ser nulo o vacío.");
        }
    }

    private void validarCanciones(List<Cancion> canciones) {
        if (canciones == null) {
            throw new IllegalArgumentException("La lista de canciones no puede ser nula.");
        }
    }

//    public ListaReproduccion(String nombre, Usuario propietario, String descripcion, List<Cancion> canciones,
//                            LocalDate fechaCreacion, List<Voto> meGusta, List<Voto> noMeGusta, String idLista) {
//        if (nombre == null || nombre.isEmpty() || nombre.contains("  ") || nombre.length() > 35 || nombre.trim().equals("")) {
//            throw new IllegalArgumentException("El nombre no es correcto.");
//        } else {
//            this.nombre = nombre;
//        }
//        if (propietario == null) {
//            throw new IllegalArgumentException("El propietario no puede ser nulo.");
//        } else {
//            this.propietario = propietario;
//        }
//        if (fechaCreacion == null || !fechaCreacion.equals(LocalDate.now())) {
//            throw new IllegalArgumentException("La fecha de creación debe ser la fecha actual.");
//        } else {
//            this.fechaCreacion = fechaCreacion;
//        }
//        this.descripcion = descripcion != null ? descripcion : "";
//        this.canciones = canciones != null ? canciones : new ArrayList<>();
//        this.meGusta = meGusta != null ? meGusta : new ArrayList<>();
//        this.noMeGusta = noMeGusta != null ? noMeGusta : new ArrayList<>();
//        this.idLista = (idLista != null) ? idLista : UUID.randomUUID().toString();
//    }

    // Getters y Setters
    public String getIdLista() {
        return idLista;
    }

    public void setIdLista(String idLista) {
        validarIdLista(idLista);
        this.idLista = idLista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
//        if(nombre == null || nombre.isEmpty() || nombre.contains("  ") || nombre.length() > 35 || nombre.trim().equals("")) {
//            throw new IllegalArgumentException("El nombre no es correcto.");
//        }
        validarNombre(nombre);
        this.nombre = nombre;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        validarPropietario(propietario);
        this.propietario = propietario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        validarDescripcion(descripcion);
        this.descripcion = descripcion;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        validarCanciones(canciones);
        this.canciones = canciones;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        // Permitir cualquier fecha al deserializar desde MongoDB
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
//        if(titulo == null || titulo.isEmpty() || titulo.contains("  ") || titulo.length() > 35 || titulo.trim().equals("")) {
//            throw new IllegalArgumentException("El título no es correcto.");
//        }
        validarNombre(titulo);
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
                ", idLista='" + idLista + '\'' +
                '}';
    }
}