package es.burgueses.dominio;

import java.time.LocalDate;
import java.util.List;

import javax.swing.text.html.ImageView;

public class Usuario {

    public enum TipoUsuario {
        ADMINISTRADOR, USUARIO
    }
    private String contraseña;
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
        // Constructor por defecto
        contraseña = "";
        nombre = "";
        apodo = "";
        pathImagen = "";
        activo = false;
        fechaAlta = null;
        tipoUsuario = TipoUsuario.USUARIO; // Por defecto, un usuario es de tipo USUARIO

    }

    public Usuario(String contraseña, String nombre, String apodo, String pathImagen, boolean activo, LocalDate fechaAlta,
            TipoUsuario tipoUsuario) {
        if(contraseña == null || contraseña.isEmpty() || contraseña.length() < 6) {
            throw new IllegalArgumentException("La contraseña no puede ser nula, vacía o menor de 6 caracteres");
        } else {
            this.contraseña = contraseña;
        }
        if(nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        } else {
            this.nombre = nombre;
        }
        if(apodo == null || apodo.isEmpty() || apodo.contains(" ")) {
            throw new IllegalArgumentException("El apodo no puede ser nulo o vacío, ni tener espacios en blanco");
        } else if (apodo.length() > 8) {
            throw new IllegalArgumentException("El apodo no puede tener más de 8 caracteres");
        } else {
            this.apodo = apodo;
        }
        if (pathImagen == null || pathImagen.isEmpty()) {
            throw new IllegalArgumentException("La imagen no puede ser nula o vacía");
        } else {
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
        this.activo = activo;
        if(fechaAlta == null || !fechaAlta.equals(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta debe ser la fecha actual");
        } else {
            this.fechaAlta = fechaAlta;
        }
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y Setters
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        if (contraseña == null || contraseña.isEmpty() || contraseña.length() < 6) {
            throw new IllegalArgumentException("La contraseña no puede ser nula, vacía o menor de 6 caracteres");
        } else {
            this.contraseña = contraseña;
        }
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        } else {
            this.nombre = nombre;
        }

    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        /*if (apodo == null || apodo.isEmpty() || apodo.contains("")) {
            throw new IllegalArgumentException("El apodo no puede ser nulo o vacío, ni tener espacios en blanco");
        } else if (apodo.length() > 8) {
            throw new IllegalArgumentException("El apodo no puede tener más de 8 caracteres");
        } else {
            this.apodo = apodo;
        }*/
        this.apodo = apodo;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        // Extensiones válidas de audio
        String[] extensionesValidas = { ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp", ".svg" };
        boolean extensionValida = false;
        for (String ext : extensionesValidas) {
            if (pathImagen.toLowerCase().endsWith(ext)) {
                extensionValida = true;
                break;
            }
        }
        if (pathImagen == null || pathImagen.isEmpty() || pathImagen.contains(" ") || !extensionValida) {
            throw new IllegalArgumentException("La ruta no es válida");
        } else {
            this.pathImagen = pathImagen;
        }
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
        /* if (fechaAlta == null || fechaAlta != (LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no es correcta");
        }*/
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
                ", apodo='" + apodo + '\'' +
                ", imagen=" + pathImagen +
                ", activo=" + activo +
                ", fechaAlta=" + fechaAlta +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}
