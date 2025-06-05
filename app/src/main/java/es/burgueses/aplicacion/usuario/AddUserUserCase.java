package es.burgueses.aplicacion.usuario;

import es.burgueses.dominio.Usuario;
import es.burgueses.dominio.IUsuarioRepositorio;

import java.time.LocalDate;
import java.util.UUID;

public class AddUserUserCase {
    private final IUsuarioRepositorio usuarioRepositorio;

    public AddUserUserCase(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void addUser(String contrasena, String nombre, UUID id, String pathImagen, LocalDate fechaCreacion) {
        // Validación 
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        
        if (contrasena == null || contrasena.isEmpty() || contrasena.length() < 6) {
            throw new IllegalArgumentException("La contraseña no puede ser nula, vacía o menor de 6 caracteres");
        }

        if (pathImagen == null || pathImagen.isEmpty()) {
            throw new IllegalArgumentException("La imagen no puede ser nula o vacía");
        }

        if (usuarioRepositorio.findById(id.toString()) != null) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        Usuario usuario = new Usuario(id.toString(), nombre, contrasena, nombre, fechaCreacion);
        usuarioRepositorio.add(usuario);
    }

    /*
    public void addUser(String contraseña, String nombre, String apodo, String pathImagen, Usuario.TipoUsuario tipoUsuario) {
        // Validación
        if (nombre == null || nombre.isEmpty() || apodo == null || apodo.isEmpty()) {
            throw new IllegalArgumentException("Nombre y apodo no pueden ser nulos o vacíos");
        }
        // Comprobar si el usuario ya existe
        if (usuarioRepositorio.findByApodo(apodo) != null) {
            throw new IllegalArgumentException("El apodo ya está en uso");
        }
        // Crear y guardar el usuario
        Usuario usuario = new Usuario(contraseña, nombre, apodo, pathImagen, true, LocalDate.now(), tipoUsuario);
        usuarioRepositorio.add(usuario);
    }*/
}
