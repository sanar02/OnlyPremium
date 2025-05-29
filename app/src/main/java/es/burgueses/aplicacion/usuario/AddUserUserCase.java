package es.burgueses.aplicacion.usuario;

import es.burgueses.dominio.Usuario;
import es.burgueses.dominio.IUsuarioRepositorio;

import java.time.LocalDate;

public class AddUserUserCase {
    private final IUsuarioRepositorio usuarioRepositorio;

    public AddUserUserCase(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void addUser(String nombre, String apodo, String pathImagen, Usuario.TipoUsuario tipoUsuario) {
        // Validación básica
        if (nombre == null || nombre.isEmpty() || apodo == null || apodo.isEmpty()) {
            throw new IllegalArgumentException("Nombre y apodo no pueden ser nulos o vacíos");
        }
        // Comprobar si el usuario ya existe
        if (usuarioRepositorio.findByApodo(apodo) != null) {
            throw new IllegalArgumentException("El apodo ya está en uso");
        }
        // Crear y guardar el usuario
        Usuario usuario = new Usuario(
            nombre,
            apodo,
            pathImagen,
            true, // activo
            LocalDate.now(),
            tipoUsuario
        );
        usuarioRepositorio.add(usuario);
    }
}
