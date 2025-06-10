package es.burgueses.aplicacion.usuario;

import es.burgueses.dominio.Usuario;
import es.burgueses.dominio.IUsuarioRepositorio;

import java.time.LocalDate;

public class AddUserUserCase {
    private final IUsuarioRepositorio usuarioRepositorio;

    public AddUserUserCase(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void addUser(String contrasena, String nombre, String apodo, String pathImagen, boolean activo, LocalDate fechaAlta, Usuario.TipoUsuario tipoUsuario) {
        // Validación
        if (nombre == null || nombre.isEmpty() || apodo == null || apodo.isEmpty()) {
            throw new IllegalArgumentException("Nombre y apodo no pueden ser nulos o vacíos");
        }
        if (contrasena == null || contrasena.isEmpty() || contrasena.length() < 6) {
            throw new IllegalArgumentException("La contraseña no puede ser nula, vacía o menor de 6 caracteres");
        }
        if (pathImagen == null || pathImagen.isEmpty()) {
            throw new IllegalArgumentException("La imagen no puede ser nula o vacía");
        }
        if (usuarioRepositorio.findByApodo(apodo) != null) {
            throw new IllegalArgumentException("El apodo ya está en uso");
        }
        if (fechaAlta == null) {
            fechaAlta = LocalDate.now();
        }
        if (tipoUsuario == null) {
            tipoUsuario = Usuario.TipoUsuario.USUARIO;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApodo(apodo);
        usuario.setContrasena(contrasena);
        usuario.setPathImagen(pathImagen);
        usuario.setActivo(activo);
        usuario.setFechaAlta(fechaAlta);
        usuario.setTipoUsuario(tipoUsuario);

        usuarioRepositorio.add(usuario);
    }
}