package es.burgueses.dominio;

import org.bson.codecs.pojo.annotations.BsonId;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Usuario {

    public enum TipoUsuario {
        ADMINISTRADOR, USUARIO
    }

    @BsonId
    private String id;

    private String contrasena;
    private String nombre;
    private String apodo;
    private String pathImagen;
    private boolean activo;
    private LocalDate fechaAlta;
    private TipoUsuario tipoUsuario;
    private List<ListaReproduccion> listasPropias;
    private List<ListaReproduccion> listasFavoritas;
    private List<Cancion> ultimasCanciones;

    // Constructores
    public Usuario() {
        id = UUID.randomUUID().toString();
        nombre = "";
        contrasena = "";
        apodo = "";
        fechaAlta = LocalDate.now();
        tipoUsuario = TipoUsuario.USUARIO;
        activo = false;
        pathImagen = "";
    }

    public Usuario(String id, String nombre, String contrasena, String apodo, LocalDate fechaAlta) {
        this.id = validarId(id);
        this.nombre = validarNombre(nombre);
        this.contrasena = validarContrasena(contrasena);
        this.apodo = validarApodo(apodo);
        this.fechaAlta = validarFechaAlta(fechaAlta);
        this.activo = false;
        this.tipoUsuario = TipoUsuario.USUARIO;
    }

    private String validarId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return id;
    }

    private String validarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        return nombre;
    }

    private String validarContrasena(String contrasena) {
        if (contrasena == null || contrasena.isEmpty() || contrasena.length() < 6) {
            throw new IllegalArgumentException("La contraseña no puede ser nula, vacía o menor de 6 caracteres");
        }
        return contrasena;
    }

    private String validarApodo(String apodo) {
        if (apodo == null || apodo.isEmpty() || apodo.contains(" ")) {
            throw new IllegalArgumentException("El apodo no puede ser nulo o vacío, ni tener espacios en blanco");
        } else if (apodo.length() > 8) {
            throw new IllegalArgumentException("El apodo no puede tener más de 8 caracteres");
        }
        return apodo;
    }

    private LocalDate validarFechaAlta(LocalDate fechaAlta) {
        if (fechaAlta == null || !fechaAlta.equals(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta debe ser la fecha actual");
        }
        return fechaAlta;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        this.id = id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.isEmpty() || contrasena.length() < 6) {
            throw new IllegalArgumentException("La contraseña no puede ser nula, vacía o menor de 6 caracteres");
        }
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        if (apodo == null || apodo.isEmpty() || apodo.contains(" ")) {
            throw new IllegalArgumentException("El apodo no puede ser nulo o vacío, ni tener espacios en blanco");
        } else if (apodo.length() > 8) {
            throw new IllegalArgumentException("El apodo no puede tener más de 8 caracteres");
        }
        this.apodo = apodo;
    }

    public void setPathImagen(String pathImagen) {
        if (pathImagen == null || pathImagen.isEmpty()) {
            throw new IllegalArgumentException("La imagen no puede ser nula o estar vacía");
        }
        String[] extensionesValidas = { ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp", ".svg" };
        boolean extensionValida = false;
        for (String ext : extensionesValidas) {
            if (pathImagen.toLowerCase().endsWith(ext)) {
                extensionValida = true;
                break;
            }
        }
        if (!extensionValida) {
            throw new IllegalArgumentException("La ruta de la imagen no es válida");
        }
        this.pathImagen = pathImagen;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public List<ListaReproduccion> getListasPropias() {
        return listasPropias;
    }

    public void setListasPropias(List<ListaReproduccion> listasPropias) {
        if (listasPropias == null) {
            throw new IllegalArgumentException("Las listas propias no pueden ser nulas");
        }
        this.listasPropias = listasPropias;
    }

    public List<ListaReproduccion> getListasFavoritas() {
        return listasFavoritas;
    }

    public void setListasFavoritas(List<ListaReproduccion> listasFavoritas) {
        if (listasFavoritas == null) {
            throw new IllegalArgumentException("Las listas favoritas no pueden ser nulas");
        }
        this.listasFavoritas = listasFavoritas;
    }

    public List<Cancion> getUltimasCanciones() {
        return ultimasCanciones;
    }

    public void setUltimasCanciones(List<Cancion> ultimasCanciones) {
        if (ultimasCanciones == null) {
            throw new IllegalArgumentException("Las últimas canciones no pueden ser nulas");
        }
        this.ultimasCanciones = ultimasCanciones;
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
        if (fechaAlta == null) {
            throw new IllegalArgumentException("La fecha de alta no puede ser null");
        }
        if (fechaAlta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser futura");
        }
        this.fechaAlta = fechaAlta;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("El tipo de usuario no puede ser nulo");
        }
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", imagen=" + pathImagen +
                ", activo=" + activo +
                ", fechaAlta=" + fechaAlta +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}
