package es.burgueses.aplicacion.usuario;

import es.burgueses.dominio.IUsuarioRepositorio;

public class ModUserUserCase {
    private final IUsuarioRepositorio usuarioRepositorio;

    public ModUserUserCase(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void modificarUsuario(String apodo, String nuevoNombre, String nuevaContrasena) {
        if (apodo == null || nuevoNombre == null || nuevaContrasena == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (nuevoNombre.isEmpty() || nuevaContrasena.isEmpty()) {
            throw new IllegalArgumentException("El nombre y la contraseña no pueden estar vacíos");
        }
        // Comprobar si el usuario existe
        if (usuarioRepositorio.findByApodo(apodo) != null) {
            throw new IllegalArgumentException("Ya hay un usuario con ese apodo");
        }
        
        usuarioRepositorio.modificarUsuario(apodo, nuevoNombre, nuevaContrasena);
    }
}
