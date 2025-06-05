package es.burgueses.aplicacion.usuario;

import es.burgueses.dominio.IUsuarioRepositorio;
import es.burgueses.dominio.Usuario;

public class ModUserUserCase {
    private final IUsuarioRepositorio usuarioRepositorio;

    public ModUserUserCase(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void modificarUsuario(String apodoActual, String nuevoApodo, String nuevoNombre, String nuevaContrasena) {
        if (nuevoApodo == null || nuevoNombre == null || nuevaContrasena == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos");
        }
        if (nuevoNombre.isEmpty() || nuevaContrasena.isEmpty() || nuevoApodo.isEmpty()) {
            throw new IllegalArgumentException("El nombre, el usuario y la contraseña no pueden estar vacíos");
        }
        Usuario usuario = usuarioRepositorio.findByApodo(apodoActual);
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ese apodo");
        }

        // Validar que el nuevo apodo no esté ya en uso (si cambia)
        if (!apodoActual.equals(nuevoApodo) &&
                usuarioRepositorio.findByApodo(nuevoApodo) != null) {
            throw new IllegalArgumentException("Ya hay un usuario con ese nuevo apodo");
        }

        // Validar que la nueva contraseña no coincida con la actual
        if (usuario.getContrasena().equals(nuevaContrasena)) {
            throw new IllegalArgumentException("La nueva contraseña no puede ser la misma que la actual");
        }

        // Actualizar los campos del usuario
        usuario.setApodo(nuevoApodo);
        usuario.setNombre(nuevoNombre);
        usuario.setContrasena(nuevaContrasena);

        // Guardar los cambios en el repositorio
        usuarioRepositorio.update(usuario);
        
        System.out.println("Usuario modificado correctamente: " + usuario.getId());
    }
}
